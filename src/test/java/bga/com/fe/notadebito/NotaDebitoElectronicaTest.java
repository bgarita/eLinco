package bga.com.fe.notadebito;

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
class NotaDebitoElectronicaTest {

    @Test
    @DisplayName("Getters/Setters: asigna y recupera todos los campos (NDB)")
    void testGettersSetters() {
        NotaDebitoElectronica nd = new NotaDebitoElectronica();

        String clave = "NDB-123";
        String proveedorSistemas = "MiSistemaNDB";
        String codigoActividadEmisor = "56101";
        String consecutivo = "003";
        String fecha = "2025-09-21T10:00:00-06:00";
        String condicionVenta = "02";
        int plazoCredito = 0; // en NDB es int (no Integer)

        Emisor emisor = new Emisor();
        Receptor receptor = new Receptor();
        MedioPago medioPago = new MedioPago();
        DetalleFactura detalle = new DetalleFactura();
        Resumen resumen = new Resumen();
        Normativa normativa = new Normativa();
        Otros otros = new Otros();

        nd.setClave(clave);
        nd.setProveedorSistemas(proveedorSistemas);
        nd.setCodigoActividadEmisor(codigoActividadEmisor);
        nd.setNumeroConsecutivo(consecutivo);
        nd.setFechaEmision(fecha);
        nd.setEmisor(emisor);
        nd.setReceptor(receptor);
        nd.setCondicionVenta(condicionVenta);
        nd.setPlazoCredito(plazoCredito);
        nd.setMedioPago(medioPago);
        nd.setDetalle(detalle);
        nd.setResumen(resumen);
        nd.setNormativa(normativa);
        nd.setOtros(otros);

        assertEquals(clave, nd.getClave());
        assertEquals(proveedorSistemas, nd.getProveedorSistemas());
        assertEquals(codigoActividadEmisor, nd.getCodigoActividadEmisor());
        assertEquals(consecutivo, nd.getNumeroConsecutivo());
        assertEquals(fecha, nd.getFechaEmision());
        assertSame(emisor, nd.getEmisor());
        assertSame(receptor, nd.getReceptor());
        assertEquals(condicionVenta, nd.getCondicionVenta());
        assertEquals(plazoCredito, nd.getPlazoCredito());
        assertSame(medioPago, nd.getMedioPago());
        assertSame(detalle, nd.getDetalle());
        assertSame(resumen, nd.getResumen());
        assertSame(normativa, nd.getNormativa());
        assertSame(otros, nd.getOtros());
        assertNull(nd.getNota());
    }

    @Test
    @DisplayName("JAXB: Unmarshal de XML mínimo válido (NDB)")
    void testJaxbUnmarshalMinimalXml() throws Exception {
        String xml = ""
                + "<NotaDebitoElectronica>"
                + "  <Clave>NDB-123</Clave>"
                + "  <ProveedorSistemas>MiSistemaNDB</ProveedorSistemas>"
                + "  <CodigoActividadEmisor>56101</CodigoActividadEmisor>"
                + "  <NumeroConsecutivo>003</NumeroConsecutivo>"
                + "  <FechaEmision>2025-09-21T10:00:00-06:00</FechaEmision>"
                + "  <Emisor/>"
                + "  <Receptor/>"
                + "  <CondicionVenta>02</CondicionVenta>"
                + "  <PlazoCredito>0</PlazoCredito>"
                + "  <MedioPago/>"
                + "  <DetalleServicio/>"
                + "  <ResumenFactura/>"
                + "  <Normativa/>"
                + "  <Otros/>"
                + "</NotaDebitoElectronica>";

        JAXBContext ctx = JAXBContext.newInstance(NotaDebitoElectronica.class);
        Unmarshaller u = ctx.createUnmarshaller();
        NotaDebitoElectronica nd = (NotaDebitoElectronica) u.unmarshal(new StringReader(xml));

        assertNotNull(nd);
        assertEquals("NDB-123", nd.getClave());
        assertEquals("MiSistemaNDB", nd.getProveedorSistemas());
        assertEquals("56101", nd.getCodigoActividadEmisor());
        assertEquals("003", nd.getNumeroConsecutivo());
        assertEquals("2025-09-21T10:00:00-06:00", nd.getFechaEmision());
        assertEquals("02", nd.getCondicionVenta());
        assertEquals(0, nd.getPlazoCredito());

        assertNotNull(nd.getEmisor());
        assertNotNull(nd.getReceptor());
        assertNotNull(nd.getMedioPago());
        assertNotNull(nd.getDetalle());
        assertNotNull(nd.getResumen());
        assertNotNull(nd.getNormativa());
        assertNotNull(nd.getOtros());
    }
}
