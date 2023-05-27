package bga.com.fe;

import bga.com.fe.exceptions.FeException;

import bga.com.fe.factura.FacturaElectronica;
import bga.com.fe.model.Encabezado;
import bga.com.fe.notacredito.NotaCreditoElectronica;
import bga.com.fe.notadebito.NotaDebitoElectronica;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author bgarita, 06/12/2022
 */
public class Convert {

    private Encabezado encabezado;
    private DetalleFactura detalleFactura;
    private DetalleNotaCredito detalleNotaCredito;
    private DetalleNotaDebito detalleNotaDebito;

    /**
     * Loads an xml file into java object and then into database.
     *
     * @param xml String absolute path
     * @param schema String versión del xml soportada por este sistema.
     */
    public void xml(String xml, String schema) throws FeException {
        this.encabezado = new Encabezado();
        this.detalleFactura = new DetalleFactura();
        this.detalleNotaCredito = new DetalleNotaCredito();
        this.detalleNotaDebito = new DetalleNotaDebito();

        // Determinar el tipo de documento (FAC, NCR, NDB)
        String tipoDocumento = getTipo(Paths.get(xml));
        if (tipoDocumento.equals("N/A")) {
            throw new FeException(
                    this.getClass().getName(),
                    "",
                    "Este xml [" + xml + "] no es un documento electrónico según el Ministerio de Hacienda.");
        }

        String wrkFile = createWorkFile(xml); // Se usa una copia
        validXML(wrkFile, xml, schema);

        switch (tipoDocumento) {
            case "FAC" : {
                removeInvoiceAttributes(wrkFile);
                generarFactura(wrkFile, xml, tipoDocumento);
                break;
            }
            case "NCR" : {
                removeNotaCrAttributes(wrkFile);
                generarNotaCR(wrkFile, xml, tipoDocumento);
                break;
            }
            case "NDB" : {
                removeNotaDbAttributes(wrkFile);
                generarNotaDB(wrkFile, xml, tipoDocumento);
                break;
            }
            default : {
                throw new FeException(
                        this.getClass().getName(),
                        "class root",
                        "Este tipo de documento no es válido -> " + tipoDocumento);
            }
        }

    }

    private void removeInvoiceAttributes(String xml) throws FeException {
        Path path = Paths.get(xml);
        String xmlString = Util.fileToString(path);
        String textToFind = "<FacturaElectronica";
        int pos1 = xmlString.indexOf(textToFind);
        int pos2;
        if (pos1 > 0) {
            textToFind = "<C";
            pos2 = xmlString.indexOf(textToFind);
            textToFind = "";
            if (pos2 > pos1) {
                textToFind = xmlString.substring(pos1, pos2 + 1);
            }
            if (!textToFind.isEmpty()) {
                xmlString = xmlString.replace(textToFind, "<FacturaElectronica><");
            }
        }

        // Remove signature
        textToFind = "<ds:Signature";
        pos1 = xmlString.indexOf(textToFind);
        textToFind = "</FacturaElectronica>";
        pos2 = xmlString.indexOf(textToFind);
        textToFind = xmlString.substring(pos1, pos2 + 1);
        xmlString = xmlString.replace(textToFind, "<");
        textToFind = "<Fax xsi:nil=\"true\"/>";
        xmlString = xmlString.replace(textToFind, "");

        try {
            // Save xml
            Util.stringToFile(xmlString, xml, false);
        } catch (IOException ex) {
            throw new FeException(
                    this.getClass().getName(),
                    "removeInvoiceAttributes()",
                    ex.getMessage(), ex);
        }

    }

    private void removeNotaCrAttributes(String xml) throws FeException {
        Path path = Paths.get(xml);
        String xmlString = Util.fileToString(path);
        String textToFind = "<NotaCreditoElectronica";
        int pos1 = xmlString.indexOf(textToFind);
        int pos2;
        if (pos1 > 0) {
            textToFind = "<C";
            pos2 = xmlString.indexOf(textToFind);
            textToFind = "";
            if (pos2 > pos1) {
                textToFind = xmlString.substring(pos1, pos2 + 1);
            }
            if (!textToFind.isEmpty()) {
                xmlString = xmlString.replace(textToFind, "<NotaCreditoElectronica><");
            }
        }

        // Remove signature
        textToFind = "<ds:Signature";
        pos1 = xmlString.indexOf(textToFind);
        textToFind = "</NotaCreditoElectronica>";
        pos2 = xmlString.indexOf(textToFind);
        textToFind = xmlString.substring(pos1, pos2 + 1);
        xmlString = xmlString.replace(textToFind, "<");

        try {
            // Save xml file
            Util.stringToFile(xmlString, xml, false);
        } catch (IOException ex) {
            throw new FeException(
                    this.getClass().getName(),
                    "removeNotaCrAttributes()",
                    ex.getMessage(), ex);
        }

    }

    private void removeNotaDbAttributes(String xml) throws FeException {
        Path path = Paths.get(xml);
        String xmlString = Util.fileToString(path);
        String textToFind = "<NotaDebitoElectronica";
        int pos1 = xmlString.indexOf(textToFind);
        int pos2;
        if (pos1 > 0) {
            textToFind = "<C"; // donde inicia <Clave>
            pos2 = xmlString.indexOf(textToFind);
            textToFind = "";
            if (pos2 > pos1) {
                textToFind = xmlString.substring(pos1, pos2 + 1); // texto que será eliminado
            }
            if (!textToFind.isEmpty()) {
                xmlString = xmlString.replace(textToFind, "<NotaDebitoElectronica><");
            }
        }

        // Remove signature
        textToFind = "<ds:Signature";
        pos1 = xmlString.indexOf(textToFind);
        textToFind = "</NotaDebitoElectronica>";
        pos2 = xmlString.indexOf(textToFind);
        textToFind = xmlString.substring(pos1, pos2 + 1);
        xmlString = xmlString.replace(textToFind, "<");

        try {
            // Save xml file
            Util.stringToFile(xmlString, xml, false);
        } catch (IOException ex) {
            throw new FeException(
                    this.getClass().getName(),
                    "removeNotaDbAttributes()",
                    ex.getMessage(), ex);
        }

    }

    private void validXML(String xml, String nombreOriginal, String schema) throws FeException {
        Path path = Paths.get(xml);
        String xmlString = Util.fileToString(path);
        String textToFind = "<ds:Signature";
        String tipoDocumento = getTipo(xmlString);
        String msg = "";
        int pos = xmlString.indexOf(textToFind);

        if (pos <= 0) {
            msg = "El archivo " + nombreOriginal + " no está firmado.";
        }

        if (msg.length() == 0) {
            switch (tipoDocumento) {
                case ResultadoCarga.FACTURA : {
                    textToFind = "<FacturaElectronica";
                    break;
                }
                case ResultadoCarga.NOTA_CREDITO : {
                    textToFind = "<NotaCreditoElectronica";
                    break;
                }
                case ResultadoCarga.NOTA_DEBITO : {
                    textToFind = "<NotaDebitoElectronica";
                    break;
                }
                default : {
                    textToFind = "N/A";
                }
            }

            pos = xmlString.indexOf(textToFind);
            if (pos <= 0 || textToFind.equals("N/A")) {
                msg = "El archivo " + nombreOriginal + " no contiene un tipo de documento soportado.";
            }
        }

        // Validar la versión del esquema de Hacienda
        if (msg.length() == 0) {
            textToFind = "xml-schemas/";
            pos = xmlString.indexOf(textToFind);
            xmlString = xmlString.substring(pos);
            String ver = xmlString.substring(12, 16);
            if (!ver.equals(schema)) {
                msg = "La versión " + ver + " del xml no es soportada actualmente.";
            }
        }
        if (msg.length() > 0) {
            throw new FeException(this.getClass().getName(),
                    "validXML()",
                    msg);
        }
    }

    private String createWorkFile(String xml) {
        // Si existiera se sobreescribe
        File wrkFile = new File("workFile.xml");
        File src = new File(xml);

        try {
            Util.copyFile(src, wrkFile);
        } catch (IOException ex) {
            throw new FeException(
                    this.getClass().getName(),
                    "createWorkFile()",
                    ex.getMessage(), ex);
        }
        return wrkFile.getName();
    }

    // 2022-08-18T21:04:48-06:00
    private Date formatDateString(String fechaEmision) throws ParseException {
        String fecha = fechaEmision.substring(0, 19).replace("T", " ");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // 2022-08-18T21:04:48-06:00
        return dateFormat.parse(fecha);
    }

    public Encabezado getEncabezado() {
        return encabezado;
    }

    public DetalleFactura getDetalleFactura() {
        return detalleFactura;
    }

    public DetalleNotaCredito getDetalleNotaCredito() {
        return detalleNotaCredito;
    }

    public DetalleNotaDebito getDetalleNotaDebito() {
        return detalleNotaDebito;
    }

    private String getTipo(String xml) {
        String tipo = ResultadoCarga.NO_DEFINIDO;
        if (xml.contains("<FacturaElectronica")) {
            tipo = ResultadoCarga.FACTURA;
        } else if (xml.contains("<NotaCreditoElectronica")) {
            tipo = ResultadoCarga.NOTA_CREDITO;
        } else if (xml.contains("<NotaDebitoElectronica")) {
            tipo = ResultadoCarga.NOTA_DEBITO;
        }

        return tipo;
    }

    private String getTipo(Path path) {
        String xmlString = Util.fileToString(path);
        return getTipo(xmlString);
    }

    // Generar los objetos Java a partir de un archivo xml
    private void generarFactura(String wrkFile, String xml, String tipoDocumento) {
        JAXBContext context;
        Unmarshaller unmarshaller;
        FacturaElectronica fa;

        try {
            context = JAXBContext.newInstance(FacturaElectronica.class);
            unmarshaller = context.createUnmarshaller();
            fa = (FacturaElectronica) unmarshaller.unmarshal(new File(wrkFile));
        } catch (JAXBException ex) {
            throw new FeException(
                    this.getClass().getName(),
                    "",
                    ex.getMessage());
        }

        // Revisar que el documento contenga receptor
        String idReceptor = fa.getReceptor().getIdentificacion().getNumero();
        if (idReceptor == null) {
            throw new FeException(
                    this.getClass().getName(),
                    "",
                    "Este xml [" + xml + "] carece de receptor.");
        }
        
        // Si el documento no trae moneda se asume que la moneda es CRC 
        // y el tipo de cambio es 1.00
        if (fa.getResumen().getCodigoTipoMoneda() == null) {
            Resumen resumen = fa.getResumen();
            CodigoTipoMoneda codigoTipoMoneda = new CodigoTipoMoneda();
            codigoTipoMoneda.setCodigoTipoMoneda("CRC");
            codigoTipoMoneda.setTipoCambio(1.00);
            resumen.setCodigoTipoMoneda(codigoTipoMoneda);
            fa.setResumen(resumen);
        }

        encabezado.setClave(fa.getClave());
        encabezado.setTipoDocumento(tipoDocumento);
        encabezado.setCodigoActividad(fa.getCodigoActividad());
        encabezado.setComprobante(fa.getNumeroConsecutivo());

        encabezado.setNumeroReceptor(idReceptor);
        encabezado.setNumeroEmisor(fa.getEmisor().getIdentificacion().getNumero());

        Date fecha;
        try {
            fecha = formatDateString(fa.getFechaEmision());
        } catch (ParseException ex) {
            throw new FeException(
                    this.getClass().getName(),
                    "generarFactura()",
                    ex.getMessage());
        }

        encabezado.setFechaEmision(fecha);

        encabezado.setNombreEmisor(fa.getEmisor().getNombre());
        encabezado.setCorreoElectronicoEmisor(fa.getEmisor().getCorreoElectronico());
        encabezado.setNumeroEmisor(fa.getEmisor().getIdentificacion().getNumero());
        encabezado.setTipoIdEmisor(fa.getEmisor().getIdentificacion().getTipo());

        encabezado.setNombreReceptor(fa.getReceptor().getNombre());
        encabezado.setTipoIdReceptor(fa.getReceptor().getIdentificacion().getTipo());
        String nc = fa.getReceptor().getNombreComercial();
        encabezado.setNombreComercialReceptor(nc == null ? "" : nc);

        encabezado.setMedioPago(fa.getMedioPago());
        encabezado.setCondicionVenta(fa.getCondicionVenta());
        encabezado.setPlazoCredito(fa.getPlazoCredito());
        encabezado.setCodigoMoneda(fa.getResumen().getCodigoTipoMoneda().getCodigoTipoMoneda());

        // Algunas facturas traen este campo en cero
        encabezado.setTipoCambio(
                fa.getResumen().getCodigoTipoMoneda().getTipoCambio() == 0.0 ? 1.0
                : fa.getResumen().getCodigoTipoMoneda().getTipoCambio()
        );
        
        // Si el tipo de cambio de colones viene incorrecto se hace la corrección de una vez
        if (fa.getResumen().getCodigoTipoMoneda().getCodigoTipoMoneda().equals("CRC") &&
                fa.getResumen().getCodigoTipoMoneda().getTipoCambio() != 1.0) {
            encabezado.setTipoCambio(1.0);
        }

        encabezado.setTotalComprobante(fa.getResumen().getTotalComprobante());
        encabezado.setTotalDescuentos(fa.getResumen().getTotalDescuentos());
        encabezado.setTotalExento(fa.getResumen().getTotalExento());
        encabezado.setTotalExonerado(fa.getResumen().getTotalExonerado());
        encabezado.setTotalGravado(fa.getResumen().getTotalGravado());
        encabezado.setTotalIVADevuelto(fa.getResumen().getTotalIVADevuelto());
        encabezado.setTotalImpuesto(fa.getResumen().getTotalImpuesto());
        encabezado.setTotalMercExonerada(fa.getResumen().getTotalMercExonerada());
        encabezado.setTotalMercanciasExentas(fa.getResumen().getTotalMercanciasExentas());
        encabezado.setTotalMercanciasGravadas(fa.getResumen().getTotalMercanciasGravadas());
        encabezado.setTotalOtrosCargos(fa.getResumen().getTotalOtrosCargos());
        encabezado.setTotalServExentos(fa.getResumen().getTotalServExentos());
        encabezado.setTotalServExonerado(fa.getResumen().getTotalServExonerado());
        encabezado.setTotalServGravados(fa.getResumen().getTotalServGravados());
        encabezado.setTotalVenta(fa.getResumen().getTotalVenta());
        encabezado.setTotalVentaNeta(fa.getResumen().getTotalVentaNeta());

        detalleFactura = fa.getDetalle();

    }

    private void generarNotaCR(String wrkFile, String xml, String tipoDocumento) {

        JAXBContext context;
        Unmarshaller unmarshaller;
        NotaCreditoElectronica nc;

        try {
            context = JAXBContext.newInstance(NotaCreditoElectronica.class);
            unmarshaller = context.createUnmarshaller();
            nc = (NotaCreditoElectronica) unmarshaller.unmarshal(new File(wrkFile));
        } catch (JAXBException ex) {
            throw new FeException(
                    this.getClass().getName(),
                    "generarNotaCR()",
                    ex.getMessage());
        }

        // Revisar que el documento contenga receptor
        String idReceptor = nc.getReceptor().getIdentificacion().getNumero();
        if (idReceptor == null) {
            throw new FeException(
                    this.getClass().getName(),
                    "",
                    "Este xml [" + xml + "] carece de receptor.");
        }

        // Si el documento no trae moneda se asume que la moneda es CRC 
        // y el tipo de cambio es 1.00
        if (nc.getResumen().getCodigoTipoMoneda() == null) {
            Resumen resumen = nc.getResumen();
            CodigoTipoMoneda codigoTipoMoneda = new CodigoTipoMoneda();
            codigoTipoMoneda.setCodigoTipoMoneda("CRC");
            codigoTipoMoneda.setTipoCambio(1.00);
            resumen.setCodigoTipoMoneda(codigoTipoMoneda);
            nc.setResumen(resumen);
        }
        
        encabezado.setClave(nc.getClave());
        encabezado.setTipoDocumento(tipoDocumento);
        encabezado.setCodigoActividad(nc.getCodigoActividad());
        encabezado.setComprobante(nc.getNumeroConsecutivo());

        encabezado.setNumeroReceptor(idReceptor);
        encabezado.setNumeroEmisor(nc.getEmisor().getIdentificacion().getNumero());

        Date fecha;
        try {
            fecha = formatDateString(nc.getFechaEmision());
        } catch (ParseException ex) {
            throw new FeException(
                    this.getClass().getName(),
                    "",
                    ex.getMessage());
        }

        encabezado.setFechaEmision(fecha);

        encabezado.setNombreEmisor(nc.getEmisor().getNombre());
        encabezado.setCorreoElectronicoEmisor(nc.getEmisor().getCorreoElectronico());
        encabezado.setNumeroEmisor(nc.getEmisor().getIdentificacion().getNumero());
        encabezado.setTipoIdEmisor(nc.getEmisor().getIdentificacion().getTipo());

        encabezado.setNombreReceptor(nc.getReceptor().getNombre());
        String nco = nc.getReceptor().getNombreComercial();
        encabezado.setNombreComercialReceptor(nco == null ? "" : nco);
        encabezado.setTipoIdReceptor(nc.getReceptor().getIdentificacion().getTipo());

        encabezado.setMedioPago(nc.getMedioPago());
        encabezado.setCondicionVenta(nc.getCondicionVenta());
        encabezado.setPlazoCredito(nc.getPlazoCredito());
        encabezado.setCodigoMoneda(nc.getResumen().getCodigoTipoMoneda().getCodigoTipoMoneda());
        encabezado.setTipoCambio(
                nc.getResumen().getCodigoTipoMoneda().getTipoCambio() == 0.0 ? 1.0
                : nc.getResumen().getCodigoTipoMoneda().getTipoCambio()
        );

        encabezado.setTotalComprobante(nc.getResumen().getTotalComprobante() * -1);
        encabezado.setTotalDescuentos(nc.getResumen().getTotalDescuentos() * -1);
        encabezado.setTotalExento(nc.getResumen().getTotalExento() * -1);
        encabezado.setTotalExonerado(nc.getResumen().getTotalExonerado() * -1);
        encabezado.setTotalGravado(nc.getResumen().getTotalGravado() * -1);
        encabezado.setTotalIVADevuelto(nc.getResumen().getTotalIVADevuelto() * -1);
        encabezado.setTotalImpuesto(nc.getResumen().getTotalImpuesto() * -1);
        encabezado.setTotalMercExonerada(nc.getResumen().getTotalMercExonerada() * -1);
        encabezado.setTotalMercanciasExentas(nc.getResumen().getTotalMercanciasExentas() * -1);
        encabezado.setTotalMercanciasGravadas(nc.getResumen().getTotalMercanciasGravadas() * -1);
        encabezado.setTotalOtrosCargos(nc.getResumen().getTotalOtrosCargos() * -1);
        encabezado.setTotalServExentos(nc.getResumen().getTotalServExentos() * -1);
        encabezado.setTotalServExonerado(nc.getResumen().getTotalServExonerado() * -1);
        encabezado.setTotalServGravados(nc.getResumen().getTotalServGravados() * -1);
        encabezado.setTotalVenta(nc.getResumen().getTotalVenta() * -1);
        encabezado.setTotalVentaNeta(nc.getResumen().getTotalVentaNeta() * -1);

        setNegative(nc.getDetalle());
        detalleNotaCredito = nc.getDetalle();

    }

    private void generarNotaDB(String wrkFile, String xml, String tipoDocumento) {
        JAXBContext context;
        Unmarshaller unmarshaller;
        NotaDebitoElectronica nd;

        try {
            context = JAXBContext.newInstance(NotaDebitoElectronica.class);
            unmarshaller = context.createUnmarshaller();
            nd = (NotaDebitoElectronica) unmarshaller.unmarshal(new File(wrkFile));
        } catch (JAXBException ex) {
            throw new FeException(
                    this.getClass().getName(),
                    "generarNotaDB()",
                    ex.getMessage());
        }

        // Revisar que el documento contenga receptor
        String idReceptor = nd.getReceptor().getIdentificacion().getNumero();
        if (idReceptor == null) {
            throw new FeException(
                    this.getClass().getName(),
                    "",
                    "Este xml [" + xml + "] carece de receptor.");
        }

        // Si el documento no trae moneda se asume que la moneda es CRC 
        // y el tipo de cambio es 1.00
        if (nd.getResumen().getCodigoTipoMoneda() == null) {
            Resumen resumen = nd.getResumen();
            CodigoTipoMoneda codigoTipoMoneda = new CodigoTipoMoneda();
            codigoTipoMoneda.setCodigoTipoMoneda("CRC");
            codigoTipoMoneda.setTipoCambio(1.00);
            resumen.setCodigoTipoMoneda(codigoTipoMoneda);
            nd.setResumen(resumen);
        }
        
        encabezado.setClave(nd.getClave());
        encabezado.setTipoDocumento(tipoDocumento);
        encabezado.setCodigoActividad(nd.getCodigoActividad());
        encabezado.setComprobante(nd.getNumeroConsecutivo());

        encabezado.setNumeroReceptor(idReceptor);
        encabezado.setNumeroEmisor(nd.getEmisor().getIdentificacion().getNumero());

        Date fecha;
        try {
            fecha = formatDateString(nd.getFechaEmision());
        } catch (ParseException ex) {
            throw new FeException(
                    this.getClass().getName(),
                    "",
                    ex.getMessage());
        }

        encabezado.setFechaEmision(fecha);

        encabezado.setNombreEmisor(nd.getEmisor().getNombre());
        encabezado.setCorreoElectronicoEmisor(nd.getEmisor().getCorreoElectronico());
        encabezado.setNumeroEmisor(nd.getEmisor().getIdentificacion().getNumero());
        encabezado.setTipoIdEmisor(nd.getEmisor().getIdentificacion().getTipo());

        encabezado.setNombreReceptor(nd.getReceptor().getNombre());
        encabezado.setTipoIdReceptor(nd.getReceptor().getIdentificacion().getTipo());

        encabezado.setMedioPago(nd.getMedioPago());
        encabezado.setCondicionVenta(nd.getCondicionVenta());
        encabezado.setPlazoCredito(nd.getPlazoCredito());
        encabezado.setCodigoMoneda(nd.getResumen().getCodigoTipoMoneda().getCodigoTipoMoneda());

        // Algunas facturas traen este campo en cero
        encabezado.setTipoCambio(
                nd.getResumen().getCodigoTipoMoneda().getTipoCambio() == 0.0 ? 1.0
                : nd.getResumen().getCodigoTipoMoneda().getTipoCambio()
        );

        encabezado.setTotalComprobante(nd.getResumen().getTotalComprobante());
        encabezado.setTotalDescuentos(nd.getResumen().getTotalDescuentos());
        encabezado.setTotalExento(nd.getResumen().getTotalExento());
        encabezado.setTotalExonerado(nd.getResumen().getTotalExonerado());
        encabezado.setTotalGravado(nd.getResumen().getTotalGravado());
        encabezado.setTotalIVADevuelto(nd.getResumen().getTotalIVADevuelto());
        encabezado.setTotalImpuesto(nd.getResumen().getTotalImpuesto());
        encabezado.setTotalMercExonerada(nd.getResumen().getTotalMercExonerada());
        encabezado.setTotalMercanciasExentas(nd.getResumen().getTotalMercanciasExentas());
        encabezado.setTotalMercanciasGravadas(nd.getResumen().getTotalMercanciasGravadas());
        encabezado.setTotalOtrosCargos(nd.getResumen().getTotalOtrosCargos());
        encabezado.setTotalServExentos(nd.getResumen().getTotalServExentos());
        encabezado.setTotalServExonerado(nd.getResumen().getTotalServExonerado());
        encabezado.setTotalServGravados(nd.getResumen().getTotalServGravados());
        encabezado.setTotalVenta(nd.getResumen().getTotalVenta());
        encabezado.setTotalVentaNeta(nd.getResumen().getTotalVentaNeta());

        detalleFactura = nd.getDetalle();
    }

    private void setNegative(DetalleNotaCredito detalle) {
        detalle.getLinea().forEach(linea -> {
            linea.setBaseImponible(linea.getBaseImponible() * -1);
            linea.setCantidad(linea.getCantidad() * -1);

            Descuento descuento = linea.getDescuento();
            if (descuento != null) {
                descuento.setMontoDescuento(descuento.getMontoDescuento() * -1);
                linea.setDescuento(descuento);
            }

            linea.setImpuestoNeto(linea.getImpuestoNeto() * -1);

            List<Impuesto> impuestos = linea.getImpuestos();
            if (impuestos != null) {
                impuestos.forEach(impuesto -> impuesto.setMonto(impuesto.getMonto() * -1));
                linea.setImpuestos(impuestos);
            }

            linea.setMontoTotal(linea.getMontoTotal() * -1);
            linea.setMontoTotalLinea(linea.getMontoTotalLinea() * -1);
            linea.setPrecioUnitario(linea.getPrecioUnitario() * -1);
            linea.setSubTotal(linea.getSubTotal() * -1);
        });
    }
}
