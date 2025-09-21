package bga.com.fe;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {
    "numeroLinea", "codigo", "codigoComercial", "cantidad", "unidadMedida",
    "detalle", "precioUnitario", "montoTotal", "descuento", "subTotal",
    "baseImponible", "impuestos", "otrosC", "montoTotalLinea", "impuestoNeto",
    "codigoCABYS"
})
public class LineaDetalle {

    @XmlElement(name = "NumeroLinea")
    private int numeroLinea;

    @XmlElement(name = "Codigo")
    private String codigo;

    @XmlElement(name = "CodigoComercial")
    private Codigo codigoComercial;

    @XmlElement(name = "Cantidad")
    private double cantidad;

    @XmlElement(name = "UnidadMedida")
    private String unidadMedida;

    @XmlElement(name = "Detalle")
    private String detalle;

    @XmlElement(name = "PrecioUnitario")
    private double precioUnitario;

    @XmlElement(name = "MontoTotal")
    private double montoTotal;

    @XmlElement(name = "Descuento")
    private Descuento descuento;

    @XmlElement(name = "SubTotal")
    private double subTotal;

    @XmlElement(name = "BaseImponible")
    private double baseImponible;

    // Repite <Impuesto> directamente bajo <LineaDetalle>
    @XmlElement(name = "Impuesto")
    private List<Impuesto> impuestos;

    @XmlElement(name = "OtrosCargos")
    private OtrosCargos otrosC;

    @XmlElement(name = "MontoTotalLinea")
    private double montoTotalLinea;

    @XmlElement(name = "ImpuestoNeto")
    private double impuestoNeto;

    @XmlElement(name = "CodigoCABYS")
    private String codigoCABYS;

    // Constructor con valores default por si no vienen en el XML
    public LineaDetalle() {
        this.codigo = "";
        this.codigoCABYS = "";
        Codigo cc = new Codigo();
        cc.setCodigo("");
        cc.setTipo("");
        this.codigoComercial = cc;
    }

    // ==== Getters/Setters SIN anotaciones JAXB ====
    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Codigo getCodigoComercial() {
        return codigoComercial;
    }

    public void setCodigoComercial(Codigo codigoComercial) {
        this.codigoComercial = codigoComercial;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Descuento getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(double baseImponible) {
        this.baseImponible = baseImponible;
    }

    public List<Impuesto> getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(List<Impuesto> impuestos) {
        this.impuestos = impuestos;
    }

    public OtrosCargos getOtrosC() {
        return otrosC;
    }

    public void setOtrosC(OtrosCargos otrosC) {
        this.otrosC = otrosC;
    }

    public double getMontoTotalLinea() {
        return montoTotalLinea;
    }

    public void setMontoTotalLinea(double montoTotalLinea) {
        this.montoTotalLinea = montoTotalLinea;
    }

    public double getImpuestoNeto() {
        return impuestoNeto;
    }

    public void setImpuestoNeto(double impuestoNeto) {
        this.impuestoNeto = impuestoNeto;
    }

    public String getCodigoCABYS() {
        return codigoCABYS;
    }

    public void setCodigoCABYS(String codigoCABYS) {
        this.codigoCABYS = codigoCABYS;
    }
}
