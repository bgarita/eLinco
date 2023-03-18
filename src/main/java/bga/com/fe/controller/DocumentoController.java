package bga.com.fe.controller;

import bga.com.fe.Convert;
import bga.com.fe.ResultadoCarga;
import bga.com.fe.Util;
import bga.com.fe.exceptions.FeException;
import bga.com.fe.DetalleFactura;
import bga.com.fe.DetalleNotaCredito;
import bga.com.fe.Impuesto;
import bga.com.fe.LineaDetalle;
import bga.com.fe.model.Company;
import bga.com.fe.model.Detalle;
import bga.com.fe.model.Emisor;
import bga.com.fe.model.Empresa;
import bga.com.fe.model.Encabezado;
import bga.com.fe.service.CompanyService;
import bga.com.fe.service.DetalleService;
import bga.com.fe.service.ImpuestoService;
import bga.com.fe.service.EmisorService;
import bga.com.fe.service.EmpresaService;
import bga.com.fe.service.EncabezadoService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reports.Reportes;

/**
 *
 * @author bgarita, 06/12/2022
 */
@Controller
@RequestMapping("/factura")
public class DocumentoController {

    @Value("${fe.repo}")
    private String repo;
    @Value("${fe.proc}")
    private String proc;
    @Value("${fe.schema}") // Es la versión del xml usado por Hacienda
    private String schema;

    @Autowired
    private EncabezadoService encabezadoService;
    @Autowired
    private DetalleService detalleService;
    @Autowired
    private ImpuestoService impuestoService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmisorService emisorService;
    @Autowired
    private EmpresaService empresaService; // Vista de receptores

    @Autowired
    DataSource datasource;

    /**
     * Convierte documentos electrónicos (xml) a objetos java y los guarda en la
     * base de datos.
     *
     * @param model
     * @return HTML con los resultados de la carga.
     * @throws FeException
     */
    @Transactional
    @RequestMapping("/save")
    public String save(Model model) throws FeException {
        checkDir();

        List<ResultadoCarga> resultados = new ArrayList<>();
        List<Company> companies = companyService.findAll();
        List<Emisor> emisores = emisorService.findAll();

        // Obtener una lista de todos los archivos en el repositorio de facturas electrónicas.
        File[] files = new File(repo).listFiles();

        // Procesar todos los archivos xml
        for (File file : files) {
            // No se procesan las subcarpetas ni archivos diferentes de xml
            String name = file.getName().toLowerCase();
            if (file.isDirectory() || !name.endsWith(".xml")) {
                continue;
            }

            ResultadoCarga resultado = new ResultadoCarga();
            resultado.setArchivo(file.getName());
            Convert convert = new Convert();
            Encabezado encabezado;
            DetalleFactura detalleFactura;
            DetalleNotaCredito detalleNotaCredito;
            try {
                // Convertir el xml en objetos java
                convert.xml(file.getAbsolutePath(), schema);

                encabezado = convert.getEncabezado();
                detalleFactura = convert.getDetalleFactura();
                detalleNotaCredito = convert.getDetalleNotaCredito();

                if (encabezado.getNombreComercialReceptor() == null) {
                    encabezado.setNombreComercialReceptor(encabezado.getNombreReceptor());
                }

                // Si el receptor no existe lo agrego a la lista y a la base de datos.
                saveReceptor(
                        companies, encabezado.getNumeroReceptor(),
                        encabezado.getNombreReceptor(), encabezado.getNombreComercialReceptor());

                // Si el emisor no existe lo agrego a la lista y a la base de datos.
                saveEmisor(emisores, encabezado.getNumeroEmisor(), encabezado.getNombreEmisor());

                // Antes de agregar el registro verifico si éste ya existe
                // en cuyo caso no se envía nuevamente a la base de datos.
                Encabezado saved = encabezadoService.findByClave(encabezado.getClave());

                if (saved == null) {
                    // Guardar el encabezado de la factura
                    saved = encabezadoService.save(encabezado);

                    // Guardar el detalle del documento
                    String tipo = saved.getTipoDocumento();
                    if (tipo.equals(ResultadoCarga.FACTURA) || tipo.equals(ResultadoCarga.NOTA_DEBITO)) {
                        saveInvoiceDetail(detalleFactura, saved);
                        resultado.setTipoArchivo(tipo);
                    } else {
                        saveNoteDetail(detalleNotaCredito, saved);
                        resultado.setTipoArchivo(tipo);
                    }

                    // Setear el resultado de la carga
                    resultado.setEstado(ResultadoCarga.EXITO);
                    resultado.setMensaje("Archivo cargado exitosamente.");
                } else {
                    resultado.setTipoArchivo(saved.getTipoDocumento());
                    resultado.setEstado(ResultadoCarga.ADEVERTENCIA);
                    resultado.setMensaje("El archivo "
                            + file.getName()
                            + " ya había sido procesado y los datos no serán actualizados."
                            + " Si necesita actualizar los datos debe eliminar el registro"
                            + " en la base de datos y volver a cargar el xml");
                }

                // Mover el archivo a la carpeta de xmls procesados
                File procesados = new File("proc/" + file.getName());
                Util.copyFile(file, procesados);
                Files.delete(file.toPath());

                resultado.setEmisor(saved.getNombreEmisor());

            } catch (FeException | IOException ex) {
                resultado.setEstado(ResultadoCarga.ERROR);
                resultado.setMensaje(ex.toString());
            }
            resultados.add(resultado);
        }

        model.addAttribute("resultados", resultados);

        return "resultadoCarga";
    }

    @RequestMapping("/formReporte01")
    public String formReporte01(Model model) throws FeException {

        List<Company> companies = new ArrayList<>();
        List<Emisor> emisores = new ArrayList<>();
        List<Empresa> empresas = empresaService.findAllDistinct();

        companies.add(new Company("todos", "Todos", "Todos"));
        emisores.add(new Emisor("todos", "Todos"));

        List<Company> temp = companyService.findAll();
        temp.forEach(item -> companies.add(item));

        List<Emisor> temp2 = emisorService.findAll();
        temp2.forEach(item -> emisores.add(item));

        model.addAttribute("companies", companies);
        model.addAttribute("emisores", emisores);
        model.addAttribute("empresas", empresas);
        return "formReporte01";
    }

    @RequestMapping("/reporte01")
    public String createDocument(
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month,
            @RequestParam("receptor") String receptor,
            @RequestParam("emisor") String emisor,
            @RequestParam("tipoDoc") String tipoDoc,
            @RequestParam("nombreCom") String nombreComercial)
            throws FeException {
        String fileName;
        try {
            Reportes rep = new Reportes(getConnection());

            fileName = rep.createDocument(
                    "Xmls.jasper", Reportes.PDF, year, month, receptor, emisor,
                    tipoDoc, nombreComercial);

        } catch (NumberFormatException | SQLException ex) {
            throw new FeException(this.getClass().getName(), "createDocument()", ex.getMessage());
        }
        return "redirect:/factura/pdf?q=" + fileName;
    }

    // Por ahora solo se envía un pdf, pero luego habrá que hacer lo mismo para xlsx
    // Falta investigar. 25/12/2022
    @GetMapping(value = "/pdf")
    public void showPDF(@RequestParam("q") String doc, HttpServletResponse response) throws FeException {
        try {
            response.setContentType("application/pdf");
            try ( InputStream inputStream = new FileInputStream(new File(doc))) {
                int nRead;
                while ((nRead = inputStream.read()) != -1) {
                    response.getWriter().write(nRead);
                }
            }
        } catch (IOException ex) {
            throw new FeException(this.getClass().getName(), "showPDF()", ex.getMessage());
        }
    } // end showPDF

    private void checkDir() {
        File dir = new File(repo);
        if (!dir.exists()) {
            dir.mkdir();
        }
        System.out.println("Using repo " + dir.getAbsolutePath());

        dir = new File(proc);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    /**
     * Verifica si el receptor (compañía) existe, caso contrario se agrega a la
     * base de datos y a la lista.
     *
     * @param companies Lista de receptores
     * @param numeroReceptor String número de receptor a verificar.
     * @param nombreReceptor
     * @param nombreComercial
     */
    private void saveReceptor(
            List<Company> companies,
            String numeroReceptor,
            String nombreReceptor,
            String nombreComercial) {
        boolean existe = false;
        for (Company comp : companies) {
            if (numeroReceptor.equals(comp.getNumeroReceptor())) {
                existe = true;
                break;
            }
        }

        if (existe) {
            return;
        }

        Company company
                = new Company(numeroReceptor, nombreReceptor, nombreComercial);
        companies.add(company);

        companyService.save(company);
    }

    /**
     * Verifica si el emisor existe, caso contrario se agrega a la base de datos
     * y a la lista.
     *
     * @param emisores Lista de emisores
     * @param numeroEmisor String número de emisor a verificar.
     * @param nombreEmisor
     */
    private void saveEmisor(List<Emisor> emisores, String numeroEmisor, String nombreEmisor) {
        boolean existe = false;
        for (Emisor emisor : emisores) {
            if (numeroEmisor.equals(emisor.getNumeroEmisor())) {
                existe = true;
                break;
            }
        }

        if (existe) {
            return;
        }

        Emisor emisor = new Emisor(numeroEmisor, nombreEmisor);
        emisores.add(emisor);

        emisorService.save(emisor);
    }

    private void saveInvoiceDetail(DetalleFactura detalleFactura, Encabezado saved) {
        List<LineaDetalle> lineas = detalleFactura.getLinea();
        for (LineaDetalle linea : lineas) {
            Detalle detalle = new Detalle();
            detalle.setId(null); // Esto permite que se agregue un nuevo registro
            detalle.setClave(saved.getClave());
            detalle.setNumeroLinea(linea.getNumeroLinea());
            detalle.setCodigo(linea.getCodigo());

            detalle.setCodigoComercial("");
            detalle.setTipoCodigoComercial("");

            if (linea.getCodigoComercial() != null) {
                detalle.setCodigoComercial(linea.getCodigoComercial().getCodigo());
                detalle.setTipoCodigoComercial(linea.getCodigoComercial().getTipo());
            }

            detalle.setCantidad(linea.getCantidad());
            detalle.setUnidadMedida(linea.getUnidadMedida());
            detalle.setDescrip(linea.getDetalle());
            detalle.setPrecioUnitario(linea.getPrecioUnitario());
            detalle.setMontoTotal(linea.getMontoTotal());

            detalle.setMontoDescuento(0.00);
            detalle.setNaturalezaDescuento("");

            if (linea.getDescuento() != null) {
                detalle.setMontoDescuento(linea.getDescuento().getMontoDescuento());
                detalle.setNaturalezaDescuento(linea.getDescuento().getNaturalezaDescuento());
            }

            detalle.setSubTotal(linea.getSubTotal());
            detalle.setBaseImponible(linea.getBaseImponible());
            detalle.setImpuestoNeto(linea.getImpuestoNeto());
            detalle.setMontoTotalLinea(linea.getMontoTotalLinea());

            Detalle det = detalleService.save(detalle);

            // Guardar los impuestos
            if (linea.getImpuestos() != null) {
                saveImpuestos(linea.getImpuestos(), det.getId());
            }
        }
    }

    private void saveNoteDetail(DetalleNotaCredito detalleNotaCredito, Encabezado saved) {
        List<LineaDetalle> lineas = detalleNotaCredito.getLinea();
        for (LineaDetalle linea : lineas) {
            Detalle detalle = new Detalle();
            detalle.setId(null); // Esto permite que se agregue un nuevo registro
            detalle.setClave(saved.getClave());
            detalle.setNumeroLinea(linea.getNumeroLinea());
            detalle.setCodigo(linea.getCodigo());

            detalle.setCodigoComercial("");
            detalle.setTipoCodigoComercial("");

            if (linea.getCodigoComercial() != null) {
                detalle.setCodigoComercial(linea.getCodigoComercial().getCodigo());
                detalle.setTipoCodigoComercial(linea.getCodigoComercial().getTipo());
            }

            detalle.setCantidad(linea.getCantidad());
            detalle.setUnidadMedida(linea.getUnidadMedida());
            detalle.setDescrip(linea.getDetalle());
            detalle.setPrecioUnitario(linea.getPrecioUnitario());
            detalle.setMontoTotal(linea.getMontoTotal());

            detalle.setMontoDescuento(0.00);
            detalle.setNaturalezaDescuento("");

            if (linea.getDescuento() != null) {
                detalle.setMontoDescuento(linea.getDescuento().getMontoDescuento());
                detalle.setNaturalezaDescuento(linea.getDescuento().getNaturalezaDescuento());
            }

            detalle.setSubTotal(linea.getSubTotal());
            detalle.setBaseImponible(linea.getBaseImponible());

            if (linea.getImpuestos() != null && linea.getImpuestoNeto() == 0) {
                Double in = 0.0;
                for (Impuesto iv : linea.getImpuestos()) {
                    in += iv.getMonto();
                }
                linea.setImpuestoNeto(in);
            }

            detalle.setImpuestoNeto(linea.getImpuestoNeto());
            detalle.setMontoTotalLinea(linea.getMontoTotalLinea());

            Detalle det = detalleService.save(detalle);

            // Guardar los impuestos
            if (linea.getImpuestos() != null) {
                saveImpuestos(linea.getImpuestos(), det.getId());
            }
        }
    }

    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }

    private void saveImpuestos(List<Impuesto> impuestos, Integer detalleId) {
        impuestos.forEach(impuesto -> {
            bga.com.fe.model.Impuesto imp = new bga.com.fe.model.Impuesto();
            imp.setId(null);
            imp.setCodigoImpuesto(impuesto.getCodigo());
            imp.setCodigoTarifa(impuesto.getCodigoTarifa());
            imp.setDetalleId(detalleId);
            imp.setMonto(impuesto.getMonto());
            imp.setTarifa(impuesto.getTarifa());
            impuestoService.save(imp);
        });
    }
}
