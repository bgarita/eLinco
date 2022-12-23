package bga.com.fe;

import bga.com.fe.exceptions.FeException;

import bga.com.fe.factura.FacturaElectronica;
import bga.com.fe.model.Encabezado;
import bga.com.fe.notacredito.NotaCreditoElectronica;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        // Determinar el tipo de documento (FAC, NCR)
        String tipoDocumento = getTipo(Paths.get(xml));
        if (tipoDocumento.equals("N/A")) {
            throw new FeException(
                    this.getClass().getName(),
                    "",
                    "Este xml [" + xml + "] no es un documento electrónico según el Ministerio de Hacienda.");
        }

        String wrkFile = createWorkFile(xml); // Se usa una copia
        validXML(wrkFile, xml, schema);

        if (tipoDocumento.equals("FAC")) {
            removeInvoiceAttributes(wrkFile);
            generarFactura(wrkFile, xml, tipoDocumento);
        } else {
            removeNoteAttributes(wrkFile);
            generarNotaCR(wrkFile, xml, tipoDocumento);
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

    private void removeNoteAttributes(String xml) throws FeException {
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
                    "removeNoteAttributes()",
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

        if (pos > 0) {
            textToFind = tipoDocumento.equals(ResultadoCarga.FACTURA) ? "<FacturaElectronica" : "<NotaCreditoElectronica";
            pos = xmlString.indexOf(textToFind);
            if (pos <= 0) {
                msg = "El archivo " + nombreOriginal + " no es un xml válido.";
            }
        }

        // Validar la versión del esquema de Hacienda
        if (pos > 0) {
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

    private String getTipo(String xml) {
        String tipo = ResultadoCarga.NO_DEFINIDO;
        if (xml.contains("<FacturaElectronica")) {
            tipo = ResultadoCarga.FACTURA;
        } else if (xml.contains("<NotaCreditoElectronica")) {
            tipo = ResultadoCarga.NOTA_CREDITO;
        }

        return tipo;
    }

    private String getTipo(Path path) {
        String xmlString = Util.fileToString(path);
        return getTipo(xmlString);
    }

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
                    "",
                    ex.getMessage());
        }

        encabezado.setFechaEmision(fecha);

        encabezado.setNombreEmisor(fa.getEmisor().getNombre());
        encabezado.setCorreoElectronicoEmisor(fa.getEmisor().getCorreoElectronico());
        encabezado.setNumeroEmisor(fa.getEmisor().getIdentificacion().getNumero());
        encabezado.setTipoIdEmisor(fa.getEmisor().getIdentificacion().getTipo());

        encabezado.setNombreReceptor(fa.getReceptor().getNombre());
        encabezado.setTipoIdReceptor(fa.getReceptor().getIdentificacion().getTipo());

        encabezado.setMedioPago(fa.getMedioPago());
        encabezado.setCondicionVenta(fa.getCondicionVenta());
        encabezado.setPlazoCredito(fa.getPlazoCredito());
        encabezado.setCodigoMoneda(fa.getResumen().getCodigoTipoMoneda().getCodigoTipoMoneda());
        
        // Algunas facturas traen este campo en cero
        encabezado.setTipoCambio(
                fa.getResumen().getCodigoTipoMoneda().getTipoCambio() == 0.0 ? 1.0
                : fa.getResumen().getCodigoTipoMoneda().getTipoCambio()
        );

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
                    "",
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
        encabezado.setTipoIdReceptor(nc.getReceptor().getIdentificacion().getTipo());

        encabezado.setMedioPago(nc.getMedioPago());
        encabezado.setCondicionVenta(nc.getCondicionVenta());
        encabezado.setPlazoCredito(nc.getPlazoCredito());
        encabezado.setCodigoMoneda(nc.getResumen().getCodigoTipoMoneda().getCodigoTipoMoneda());
        encabezado.setTipoCambio(
                nc.getResumen().getCodigoTipoMoneda().getTipoCambio() == 0.0 ? 1.0
                : nc.getResumen().getCodigoTipoMoneda().getTipoCambio()
        );

        encabezado.setTotalComprobante(nc.getResumen().getTotalComprobante());
        encabezado.setTotalDescuentos(nc.getResumen().getTotalDescuentos());
        encabezado.setTotalExento(nc.getResumen().getTotalExento());
        encabezado.setTotalExonerado(nc.getResumen().getTotalExonerado());
        encabezado.setTotalGravado(nc.getResumen().getTotalGravado());
        encabezado.setTotalIVADevuelto(nc.getResumen().getTotalIVADevuelto());
        encabezado.setTotalImpuesto(nc.getResumen().getTotalImpuesto());
        encabezado.setTotalMercExonerada(nc.getResumen().getTotalMercExonerada());
        encabezado.setTotalMercanciasExentas(nc.getResumen().getTotalMercanciasExentas());
        encabezado.setTotalMercanciasGravadas(nc.getResumen().getTotalMercanciasGravadas());
        encabezado.setTotalOtrosCargos(nc.getResumen().getTotalOtrosCargos());
        encabezado.setTotalServExentos(nc.getResumen().getTotalServExentos());
        encabezado.setTotalServExonerado(nc.getResumen().getTotalServExonerado());
        encabezado.setTotalServGravados(nc.getResumen().getTotalServGravados());
        encabezado.setTotalVenta(nc.getResumen().getTotalVenta());
        encabezado.setTotalVentaNeta(nc.getResumen().getTotalVentaNeta());

        detalleNotaCredito = nc.getDetalle();

    }
}
