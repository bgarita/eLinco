package bga.com.fe;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bosco, 17/12/2022
 */
@XmlType(propOrder = {
    "numeroLinea", "codigo", "codigoComercial", "cantidad", "unidadMedida", 
    "detalle", "precioUnitario", "montoTotal", "descuento", "subTotal", 
    "baseImponible", "impuestos", "otrosC", "montoTotalLinea", "impuestoNeto"})
public class LineaDetalle {

    private int numeroLinea;
    private String codigo;
    private Codigo codigoComercial;
    private double cantidad;
    private String unidadMedida;
    private String detalle;
    private double precioUnitario;
    private double montoTotal;
    private Descuento descuento;
    private double subTotal;
    private double baseImponible;
    private List<Impuesto> impuestos;
    private OtrosCargos otrosC;
    private double montoTotalLinea;
    private double impuestoNeto;

    public LineaDetalle() {

    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    @XmlElement(name = "NumeroLinea")
    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public String getCodigo() {
        return codigo;
    }

    @XmlElement(name = "Codigo")
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Codigo getCodigoComercial() {
        return codigoComercial;
    }

    @XmlElement(name = "CodigoComercial")
    public void setCodigoComercial(Codigo codigoComercial) {
        this.codigoComercial = codigoComercial;
    }

    public double getCantidad() {
        return cantidad;
    }

    @XmlElement(name = "Cantidad")
    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    @XmlElement(name = "UnidadMedida")
    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getDetalle() {
        return detalle;
    }

    @XmlElement(name = "Detalle")
    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    @XmlElement(name = "PrecioUnitario")
    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    @XmlElement(name = "MontoTotal")
    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Descuento getDescuento() {
        return descuento;
    }

    @XmlElement(name = "Descuento")
    public void setDescuento(Descuento descuento) {
        this.descuento = descuento;
    }

    public double getSubTotal() {
        return subTotal;
    }

    @XmlElement(name = "SubTotal")
    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getBaseImponible() {
        return baseImponible;
    }

    @XmlElement(name = "BaseImponible")
    public void setBaseImponible(double baseImponible) {
        this.baseImponible = baseImponible;
    }

    public List<Impuesto> getImpuestos() {
        return impuestos;
    }

    @XmlElement(name = "Impuesto")
    public void setImpuestos(List<Impuesto> impuestos) {
        this.impuestos = impuestos;
    }

    public OtrosCargos getOtrosC() {
        return otrosC;
    }

    @XmlElement(name = "OtrosCargos")
    public void setOtrosC(OtrosCargos otrosC) {
        this.otrosC = otrosC;
    }

    public double getMontoTotalLinea() {
        return montoTotalLinea;
    }

    @XmlElement(name = "MontoTotalLinea")
    public void setMontoTotalLinea(double montoTotalLinea) {
        this.montoTotalLinea = montoTotalLinea;
    }

    public double getImpuestoNeto() {
        return impuestoNeto;
    }

    @XmlElement(name = "ImpuestoNeto")
    public void setImpuestoNeto(double impuestoNeto) {
        this.impuestoNeto = impuestoNeto;
    }

} // end class
