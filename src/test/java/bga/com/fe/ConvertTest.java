package bga.com.fe;

import bga.com.fe.model.Encabezado;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author bgarita, 21/09/2025
 */
class ConvertTest {

    private Path tempFile;

    @AfterEach
    void cleanup() throws Exception {
        if (tempFile != null) {
            Files.deleteIfExists(tempFile);
        }
        Files.deleteIfExists(Path.of("workFile.xml"));
    }

    @Test
    @DisplayName("Factura v4.4: Encabezado y Detalle OK (CodigoMoneda + TC=1.0)")
    void testFacturaFlow() throws Exception {
        String xml = buildFacturaXML_v44(1000.0, 1.0, 1.0);
        tempFile = Files.createTempFile("fe_", ".xml");
        Files.writeString(tempFile, xml);

        Convert conv = new Convert();
        conv.xml(tempFile.toAbsolutePath().toString(), "v4.4");

        Encabezado e = conv.getEncabezado();
        assertNotNull(e);
        assertEquals("50623010100310123456700100001010000000001100000001", e.getClave());
        assertEquals("FAC", e.getTipoDocumento());
        assertEquals("56101", e.getCodigoActividadEmisor());
        assertEquals("00100001010000000001", e.getNumeroConsecutivo());
        assertEquals("3101234567", e.getNumeroEmisor());
        assertEquals("2012345678", e.getNumeroReceptor());
        assertEquals("Mi Emisor S.A.", e.getNombreEmisor());
        assertEquals("EmisorCom", e.getNombreComercialEmisor());

        // Moneda (tomada del encabezado final, no de la clase interna)
        assertEquals("CRC", e.getCodigoMoneda());
        assertEquals(1.0, e.getTipoCambio(), 0.0001);

        assertEquals("01", e.getCondicionVenta());
        assertEquals(30, e.getPlazoCredito());
        assertEquals("01", e.getMedioPago());
        assertEquals(1000.0, e.getTotalComprobante(), 0.0001);

        DetalleFactura df = conv.getDetalleFactura();
        assertNotNull(df);
        assertEquals(1, df.getLinea().size());
        LineaDetalle l1 = df.getLinea().get(0);
        assertEquals(1, l1.getNumeroLinea());
        assertEquals("Servicio X", l1.getDetalle());
        assertEquals(1.0, l1.getCantidad(), 0.0001);
        assertEquals("2118403000107", l1.getCodigoCABYS());
    }

    @Test
    @DisplayName("Nota de Crédito v4.4: totales negativizados")
    void testNotaCreditoFlow() throws Exception {
        String xml = buildNCRXML_v44(500.0, 1.0, 1.0);
        tempFile = Files.createTempFile("ncr_", ".xml");
        Files.writeString(tempFile, xml);

        Convert conv = new Convert();
        conv.xml(tempFile.toAbsolutePath().toString(), "v4.4");

        Encabezado e = conv.getEncabezado();
        assertNotNull(e);
        assertEquals("NCR", e.getTipoDocumento());
        assertEquals("CRC", e.getCodigoMoneda());
        assertEquals(1.0, e.getTipoCambio(), 0.0001);
        assertEquals(-500.0, e.getTotalComprobante(), 0.0001);
        assertEquals(-500.0, e.getTotalVenta(), 0.0001);

        DetalleNotaCredito dnc = conv.getDetalleNotaCredito();
        assertNotNull(dnc);
        assertEquals(1, dnc.getLinea().size());
        LineaDetalle l1 = dnc.getLinea().get(0);
        assertEquals(-1.0, l1.getCantidad(), 0.0001);
        assertEquals(-500.0, l1.getMontoTotal(), 0.0001);
    }

    @Test
    @DisplayName("Nota de Débito v4.4: totales positivos, moneda/tc OK")
    void testNotaDebitoFlow() throws Exception {
        String xml = buildNDBXML_v44(250.0, 1.0, 1.0);
        tempFile = Files.createTempFile("ndb_", ".xml");
        Files.writeString(tempFile, xml);

        Convert conv = new Convert();
        conv.xml(tempFile.toAbsolutePath().toString(), "v4.4");

        Encabezado e = conv.getEncabezado();
        assertNotNull(e);
        assertEquals("NDB", e.getTipoDocumento());
        assertEquals("CRC", e.getCodigoMoneda());
        assertEquals(1.0, e.getTipoCambio(), 0.0001);
        assertEquals(250.0, e.getTotalComprobante(), 0.0001);

        DetalleFactura df = conv.getDetalleFactura();
        assertNotNull(df);
        assertEquals(1, df.getLinea().size());
        assertEquals(2.0, df.getLinea().get(0).getCantidad(), 0.0001);
    }

    // ===== Helpers (v4.4 con namespace y CodigoMoneda) =====
    private String nsHeader() {
        return """
               <?xml version="1.0" encoding="UTF-8"?>
               """;
    }

    private String emisorBlock() {
        return """
                <Emisor>
                  <Nombre>Mi Emisor S.A.</Nombre>
                  <NombreComercial>EmisorCom</NombreComercial>
                  <CorreoElectronico>info@emisor.com</CorreoElectronico>
                  <Identificacion>
                    <Tipo>01</Tipo>
                    <Numero>3101234567</Numero>
                  </Identificacion>
                </Emisor>
               """;
    }

    private String receptorBlock() {
        return """
                <Receptor>
                  <Nombre>Cliente XYZ</Nombre>
                  <Identificacion>
                    <Tipo>01</Tipo>
                    <Numero>2012345678</Numero>
                  </Identificacion>
                </Receptor>
               """;
    }

    private String resumenBlock_v44(double total, double tcResumen, double tcEsperado) {
        // MedioPago anidado (Convert primero intenta en Resumen)
        return """
                <ResumenFactura>
                  <CodigoTipoMoneda>
                    <CodigoMoneda>CRC</CodigoMoneda>
                    <TipoCambio>%1$.2f</TipoCambio>
                  </CodigoTipoMoneda>
                  <MedioPago>
                    <TipoMedioPago>01</TipoMedioPago>
                  </MedioPago>
                  <TotalServGravados>0</TotalServGravados>
                  <TotalServExentos>0</TotalServExentos>
                  <TotalServExonerado>0</TotalServExonerado>
                  <TotalServNoSujeto>0</TotalServNoSujeto>
                  <TotalMercanciasGravadas>0</TotalMercanciasGravadas>
                  <TotalMercanciasExentas>0</TotalMercanciasExentas>
                  <TotalMercExonerada>0</TotalMercExonerada>
                  <TotalMercNoSujeta>0</TotalMercNoSujeta>
                  <TotalGravado>0</TotalGravado>
                  <TotalExento>0</TotalExento>
                  <TotalExonerado>0</TotalExonerado>
                  <TotalNoSujeto>0</TotalNoSujeto>
                  <TotalVenta>%2$.2f</TotalVenta>
                  <TotalDescuentos>0</TotalDescuentos>
                  <TotalVentaNeta>%2$.2f</TotalVentaNeta>
                  <TotalImpuesto>0</TotalImpuesto>
                  <TotalIVADevuelto>0</TotalIVADevuelto>
                  <TotalOtrosCargos>0</TotalOtrosCargos>
                  <TotalComprobante>%2$.2f</TotalComprobante>
                </ResumenFactura>
               """.formatted(tcResumen, total);
    }

    private String detalleFacturaBlock(double cantidad, double montoTotal, String cabys) {
        return """
                <DetalleServicio>
                  <LineaDetalle>
                    <NumeroLinea>1</NumeroLinea>
                    <Detalle>Servicio X</Detalle>
                    <Cantidad>%1$.2f</Cantidad>
                    <UnidadMedida>Sp</UnidadMedida>
                    <PrecioUnitario>%2$.2f</PrecioUnitario>
                    <MontoTotal>%2$.2f</MontoTotal>
                    <SubTotal>%2$.2f</SubTotal>
                    <BaseImponible>%2$.2f</BaseImponible>
                    <ImpuestoNeto>0</ImpuestoNeto>
                    <MontoTotalLinea>%2$.2f</MontoTotalLinea>
                    <CodigoCABYS>%3$s</CodigoCABYS>
                  </LineaDetalle>
                </DetalleServicio>
               """.formatted(cantidad, montoTotal, cabys);
    }

    private String dsSig() {
        return "<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">...</ds:Signature>";
    }

    private String buildFacturaXML_v44(double total, double tcResumen, double tcEsperado) {
        return nsHeader() + """
               <FacturaElectronica xmlns="https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.4/facturaElectronica">
                 <Clave>50623010100310123456700100001010000000001100000001</Clave>
                 <ProveedorSistemas>MiSistema</ProveedorSistemas>
                 <CodigoActividadEmisor>56101</CodigoActividadEmisor>
                 <NumeroConsecutivo>00100001010000000001</NumeroConsecutivo>
                 <FechaEmision>2025-09-21T10:00:00</FechaEmision>
               """ + emisorBlock() + receptorBlock() + """
                 <CondicionVenta>01</CondicionVenta>
                 <PlazoCredito>30</PlazoCredito>
                 <MedioPago><TipoMedioPago>99</TipoMedioPago></MedioPago>
               """ + detalleFacturaBlock(1.0, total, "2118403000107")
                + resumenBlock_v44(total, tcResumen, tcEsperado)
                + dsSig()
                + "\n</FacturaElectronica>";
    }

    private String buildNCRXML_v44(double total, double tcResumen, double tcEsperado) {
        return nsHeader() + """
               <NotaCreditoElectronica xmlns="https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.4/facturaElectronica">
                 <Clave>NCR-001</Clave>
                 <ProveedorSistemas>MiSistema</ProveedorSistemas>
                 <CodigoActividadEmisor>56101</CodigoActividadEmisor>
                 <NumeroConsecutivo>0000000002</NumeroConsecutivo>
                 <FechaEmision>2025-09-21T11:00:00</FechaEmision>
               """ + emisorBlock() + receptorBlock() + """
                 <CondicionVenta>01</CondicionVenta>
                 <PlazoCredito>30</PlazoCredito>
                 <MedioPago><TipoMedioPago>99</TipoMedioPago></MedioPago>
               """ + detalleFacturaBlock(1.0, total, "2118403000107")
                + resumenBlock_v44(total, tcResumen, tcEsperado)
                + dsSig()
                + "\n</NotaCreditoElectronica>";
    }

    private String buildNDBXML_v44(double total, double tcResumen, double tcEsperado) {
        return nsHeader() + """
               <NotaDebitoElectronica xmlns="https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.4/facturaElectronica">
                 <Clave>NDB-001</Clave>
                 <ProveedorSistemas>MiSistema</ProveedorSistemas>
                 <CodigoActividadEmisor>56101</CodigoActividadEmisor>
                 <NumeroConsecutivo>0000000003</NumeroConsecutivo>
                 <FechaEmision>2025-09-21T12:00:00</FechaEmision>
               """ + emisorBlock() + receptorBlock() + """
                 <CondicionVenta>02</CondicionVenta>
                 <PlazoCredito>0</PlazoCredito>
                 <MedioPago><TipoMedioPago>01</TipoMedioPago></MedioPago>
               """ + detalleFacturaBlock(2.0, total, "2118403000107")
                + resumenBlock_v44(total, tcResumen, tcEsperado)
                + dsSig()
                + "\n</NotaDebitoElectronica>";

    }
}
