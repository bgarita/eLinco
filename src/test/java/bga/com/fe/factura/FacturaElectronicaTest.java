package bga.com.fe.factura;

import bga.com.fe.DetalleFactura;
import bga.com.fe.Emisor;
import bga.com.fe.MedioPago;
import bga.com.fe.Normativa;
import bga.com.fe.Otros;
import bga.com.fe.Receptor;
import bga.com.fe.Resumen;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author bgarita, 21/09/2025
 */
public class FacturaElectronicaTest {

    @Test
    @DisplayName("Getters/Setters: asigna y recupera todos los campos")
    void testGettersSetters() {
        FacturaElectronica fe = new FacturaElectronica();

        String clave = "50621010100310199999900100001010000000001100000001";
        String proveedorSistemas = "MiSistema";
        String codigoActividadEmisor = "56101";
        String consecutivo = "00100001010000000001";
        String fecha = "2025-09-21T10:00:00-06:00";
        String condicionVenta = "01";
        Integer plazoCredito = 15;

        Emisor emisor = new Emisor();
        Receptor receptor = new Receptor();
        MedioPago medioPago = new MedioPago();
        DetalleFactura detalle = new DetalleFactura();
        Resumen resumen = new Resumen();
        Normativa normativa = new Normativa();
        Otros otros = new Otros();

        fe.setClave(clave);
        fe.setProveedorSistemas(proveedorSistemas);
        fe.setCodigoActividadEmisor(codigoActividadEmisor);
        fe.setNumeroConsecutivo(consecutivo);
        fe.setFechaEmision(fecha);
        fe.setEmisor(emisor);
        fe.setReceptor(receptor);
        fe.setCondicionVenta(condicionVenta);
        fe.setPlazoCredito(plazoCredito);
        fe.setMedioPago(medioPago);
        fe.setDetalle(detalle);
        fe.setResumen(resumen);
        fe.setNormativa(normativa);
        fe.setOtros(otros);

        assertEquals(clave, fe.getClave());
        assertEquals(proveedorSistemas, fe.getProveedorSistemas());
        assertEquals(codigoActividadEmisor, fe.getCodigoActividadEmisor());
        assertEquals(consecutivo, fe.getNumeroConsecutivo());
        assertEquals(fecha, fe.getFechaEmision());
        assertSame(emisor, fe.getEmisor());
        assertSame(receptor, fe.getReceptor());
        assertEquals(condicionVenta, fe.getCondicionVenta());
        assertEquals(plazoCredito, fe.getPlazoCredito());
        assertSame(medioPago, fe.getMedioPago());
        assertSame(detalle, fe.getDetalle());
        assertSame(resumen, fe.getResumen());
        assertSame(normativa, fe.getNormativa());
        assertSame(otros, fe.getOtros());
        // nota es lista: por defecto null; si más adelante quieres setearla, aquí puedes validar
        assertNull(fe.getNota());
    }

    @Test
    @DisplayName("JAXB: Unmarshal de XML mínimo válido")
    void testJaxbUnmarshalMinimalXml() throws Exception {
        String xml = ""
                + "<FacturaElectronica>"
                + "  <Clave>123</Clave>"
                + "  <ProveedorSistemas>MiSistema</ProveedorSistemas>"
                + "  <CodigoActividadEmisor>56101</CodigoActividadEmisor>"
                + "  <NumeroConsecutivo>001</NumeroConsecutivo>"
                + "  <FechaEmision>2025-09-21T10:00:00-06:00</FechaEmision>"
                + "  <Emisor/>"
                + "  <Receptor/>"
                + "  <CondicionVenta>01</CondicionVenta>"
                + "  <PlazoCredito>15</PlazoCredito>"
                + "  <MedioPago/>"
                + "  <DetalleServicio/>"
                + "  <ResumenFactura/>"
                + "  <Normativa/>"
                + "  <Otros/>"
                + "</FacturaElectronica>";

        JAXBContext ctx = JAXBContext.newInstance(FacturaElectronica.class);
        Unmarshaller u = ctx.createUnmarshaller();
        FacturaElectronica fe = (FacturaElectronica) u.unmarshal(new StringReader(xml));

        assertNotNull(fe);
        assertEquals("123", fe.getClave());
        assertEquals("MiSistema", fe.getProveedorSistemas());
        assertEquals("56101", fe.getCodigoActividadEmisor());
        assertEquals("001", fe.getNumeroConsecutivo());
        assertEquals("2025-09-21T10:00:00-06:00", fe.getFechaEmision());

        // Elementos compuestos presentes como tags vacíos → instancia creada (no null),
        // pero sus campos internos quedarán en null (lo cual es esperado aquí).
        assertNotNull(fe.getEmisor());
        assertNotNull(fe.getReceptor());
        assertNotNull(fe.getMedioPago());
        assertNotNull(fe.getDetalle());
        assertNotNull(fe.getResumen());
        assertNotNull(fe.getNormativa());
        assertNotNull(fe.getOtros());

        assertEquals("01", fe.getCondicionVenta());
        assertEquals(Integer.valueOf(15), fe.getPlazoCredito());
    }

    @Test
    @DisplayName("Defaults: constructor vacío deja campos en null salvo que el modelo los inicialice")
    void testDefaults() {
        FacturaElectronica fe = new FacturaElectronica();
        assertNull(fe.getClave());
        assertNull(fe.getProveedorSistemas());
        assertNull(fe.getCodigoActividadEmisor());
        assertNull(fe.getNumeroConsecutivo());
        assertNull(fe.getFechaEmision());
        assertNull(fe.getEmisor());
        assertNull(fe.getReceptor());
        assertNull(fe.getCondicionVenta());
        assertNull(fe.getPlazoCredito());
        assertNull(fe.getMedioPago());
        assertNull(fe.getDetalle());
        assertNull(fe.getResumen());
        assertNull(fe.getNota());
        assertNull(fe.getNormativa());
        assertNull(fe.getOtros());
    }
}
