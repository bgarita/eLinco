package bga.com.fe.notacredito;

import bga.com.fe.DetalleNotaCredito;
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
class NotaCreditoElectronicaTest {

    @Test
    @DisplayName("Getters/Setters: asigna y recupera todos los campos (NC)")
    void testGettersSetters() {
        NotaCreditoElectronica nc = new NotaCreditoElectronica();

        String clave = "NC-123";
        String proveedorSistemas = "MiSistemaNC";
        String codigoActividadEmisor = "56101";
        String consecutivo = "002";
        String fecha = "2025-09-21T10:00:00-06:00";
        String condicionVenta = "01";
        Integer plazoCredito = 30;

        Emisor emisor = new Emisor();
        Receptor receptor = new Receptor();
        MedioPago medioPago = new MedioPago();
        DetalleNotaCredito detalle = new DetalleNotaCredito();
        Resumen resumen = new Resumen();
        Normativa normativa = new Normativa();
        Otros otros = new Otros();

        nc.setClave(clave);
        nc.setProveedorSistemas(proveedorSistemas);
        nc.setCodigoActividadEmisor(codigoActividadEmisor);
        nc.setNumeroConsecutivo(consecutivo);
        nc.setFechaEmision(fecha);
        nc.setEmisor(emisor);
        nc.setReceptor(receptor);
        nc.setCondicionVenta(condicionVenta);
        nc.setPlazoCredito(plazoCredito);
        nc.setMedioPago(medioPago);
        nc.setDetalle(detalle);
        nc.setResumen(resumen);
        nc.setNormativa(normativa);
        nc.setOtros(otros);

        assertEquals(clave, nc.getClave());
        assertEquals(proveedorSistemas, nc.getProveedorSistemas());
        assertEquals(codigoActividadEmisor, nc.getCodigoActividadEmisor());
        assertEquals(consecutivo, nc.getNumeroConsecutivo());
        assertEquals(fecha, nc.getFechaEmision());
        assertSame(emisor, nc.getEmisor());
        assertSame(receptor, nc.getReceptor());
        assertEquals(condicionVenta, nc.getCondicionVenta());
        assertEquals(plazoCredito, nc.getPlazoCredito());
        assertSame(medioPago, nc.getMedioPago());
        assertSame(detalle, nc.getDetalle());
        assertSame(resumen, nc.getResumen());
        assertSame(normativa, nc.getNormativa());
        assertSame(otros, nc.getOtros());
        assertNull(nc.getNota()); // por defecto no seteada
    }

    @Test
    @DisplayName("JAXB: Unmarshal de XML mínimo válido (NC)")
    void testJaxbUnmarshalMinimalXml() throws Exception {
        String xml = ""
            + "<NotaCreditoElectronica>"
            + "  <Clave>NC-123</Clave>"
            + "  <ProveedorSistemas>MiSistemaNC</ProveedorSistemas>"
            + "  <CodigoActividadEmisor>56101</CodigoActividadEmisor>"
            + "  <NumeroConsecutivo>002</NumeroConsecutivo>"
            + "  <FechaEmision>2025-09-21T10:00:00-06:00</FechaEmision>"
            + "  <Emisor/>"
            + "  <Receptor/>"
            + "  <CondicionVenta>01</CondicionVenta>"
            + "  <PlazoCredito>30</PlazoCredito>"
            + "  <MedioPago/>"
            + "  <DetalleServicio/>"
            + "  <ResumenFactura/>"
            + "  <Normativa/>"
            + "  <Otros/>"
            + "</NotaCreditoElectronica>";

        JAXBContext ctx = JAXBContext.newInstance(NotaCreditoElectronica.class);
        Unmarshaller u = ctx.createUnmarshaller();
        NotaCreditoElectronica nc = (NotaCreditoElectronica) u.unmarshal(new StringReader(xml));

        assertNotNull(nc);
        assertEquals("NC-123", nc.getClave());
        assertEquals("MiSistemaNC", nc.getProveedorSistemas());
        assertEquals("56101", nc.getCodigoActividadEmisor());
        assertEquals("002", nc.getNumeroConsecutivo());
        assertEquals("2025-09-21T10:00:00-06:00", nc.getFechaEmision());
        assertEquals("01", nc.getCondicionVenta());
        assertEquals(Integer.valueOf(30), nc.getPlazoCredito());

        assertNotNull(nc.getEmisor());
        assertNotNull(nc.getReceptor());
        assertNotNull(nc.getMedioPago());
        assertNotNull(nc.getDetalle());
        assertNotNull(nc.getResumen());
        assertNotNull(nc.getNormativa());
        assertNotNull(nc.getOtros());
    }
}
