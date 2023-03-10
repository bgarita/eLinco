package bga.com.fe.controller;

import bga.com.fe.Convert;
import bga.com.fe.ResultadoCarga;
import bga.com.fe.Util;
import bga.com.fe.exceptions.FeException;
import bga.com.fe.DetalleFactura;
import bga.com.fe.DetalleNotaCredito;
import bga.com.fe.LineaDetalle;
import bga.com.fe.model.Company;
import bga.com.fe.model.Detalle;
import bga.com.fe.model.Encabezado;
import bga.com.fe.service.CompanyService;
import bga.com.fe.service.DetalleService;
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
public class EncabezadoController {

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
    private CompanyService companyService;
    @Autowired
    DataSource datasource;

    /**
     * Convierete facturas electrónicas (xml) a objetos java y los guarda en la
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

        // Obtener una lista de todos los archivos en el repositorio de facturas electrónicas.
        File[] files = new File(repo).listFiles();

        // Procesar todos los archivos xml
        for (File file : files) {
            // No se procesan las subcarpetas ni archivos diferentes de xml
            if (file.isDirectory() || !file.getName().endsWith(".xml")) {
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

                // Si el receptor no existe lo agrego a la lista y a la base de datos.
                saveReceptor(companies, encabezado.getNumeroReceptor(), encabezado.getNombreReceptor());

                // Antes de agregar el registro verifico si éste ya existe
                // en cuyo caso no se envía nuevamente a la base de datos.
                Encabezado saved = encabezadoService.findByClave(encabezado.getClave());

                if (saved == null) {
                    // Guardar el encabezado de la factura
                    saved = encabezadoService.save(encabezado);

                    // Guardar el detalle del documento
                    if (saved.getTipoDocumento().equals(ResultadoCarga.FACTURA)) {
                        saveInvoiceDetail(detalleFactura, saved);
                        resultado.setTipoArchivo(ResultadoCarga.FACTURA);
                    } else {
                        saveNoteDetail(detalleNotaCredito, saved);
                        resultado.setTipoArchivo(ResultadoCarga.NOTA_CREDITO);
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
                resultado.setMensaje(ex.getMessage());
            }
            resultados.add(resultado);
        }

        model.addAttribute("resultados", resultados);
        
        return "resultadoCarga";
    }

    @RequestMapping("/formReporte01")
    public String formReporte01(Model model) throws FeException {
        
        List<Company> companies = companyService.findAll();
        model.addAttribute("companies", companies);
        return "formReporte01";
    }
    
    // Falta crear el formulario HTML que invoque este end point 25/12/2022
    // Debe enviar el pdf a una ventana nueva. Para eso se debe agregar
    // target="_blank" al href
    @RequestMapping("/reporte01")
    public String createDocument(
            @RequestParam("year") Integer year,
            @RequestParam("month") Integer month) throws FeException {
        String fileName;
        try {
            Reportes rep = new Reportes(getConnection());

            fileName = rep.createDocument("Xmls.jasper", Reportes.PDF, year, month);

        } catch (NumberFormatException | SQLException ex) {
            throw new FeException(this.getClass().getName(), "createDocument()", ex.getMessage());
        }
        return "redirect:/factura/pdf?q=" + fileName;
    }

    // Por ahora solo se envía un pdf, pero luego habrá que hacer lo mismo pero pada xlsx
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
     */
    private void saveReceptor(List<Company> companies, String numeroReceptor, String nombreReceptor) {
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

        Company company = new Company(numeroReceptor, nombreReceptor);
        companies.add(company);

        companyService.save(company);
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

            detalle.setCodigoImpuesto("");
            detalle.setCodigoTarifa("");
            detalle.setFactorIVA(0f);
            detalle.setTarifa(0f);
            detalle.setMontoIVA(0.0);

            if (linea.getImpuesto() != null) {
                detalle.setCodigoImpuesto(linea.getImpuesto().getCodigo());
                detalle.setCodigoTarifa(linea.getImpuesto().getCodigoTarifa());
                detalle.setFactorIVA(linea.getImpuesto().getFactorIVA());
                detalle.setTarifa(linea.getImpuesto().getTarifa());
                detalle.setMontoIVA(linea.getImpuesto().getMonto());
            }

            detalle.setMontoTotalLinea(linea.getMontoTotalLinea());

            detalleService.save(detalle);
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

            detalle.setCodigoImpuesto("");
            detalle.setCodigoTarifa("");
            detalle.setFactorIVA(0f);
            detalle.setTarifa(0f);
            detalle.setMontoIVA(0.0);

            if (linea.getImpuesto() != null) {
                detalle.setCodigoImpuesto(linea.getImpuesto().getCodigo());
                detalle.setCodigoTarifa(linea.getImpuesto().getCodigoTarifa());
                detalle.setFactorIVA(linea.getImpuesto().getFactorIVA());
                detalle.setTarifa(linea.getImpuesto().getTarifa());
                detalle.setMontoIVA(linea.getImpuesto().getMonto());
            }

            detalle.setMontoTotalLinea(linea.getMontoTotalLinea());

            detalleService.save(detalle);
        }
    }
    
    public Connection getConnection() throws SQLException {
        return datasource.getConnection();
    }
}
