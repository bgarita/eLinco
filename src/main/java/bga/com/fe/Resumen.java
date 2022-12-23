package bga.com.fe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author bosco
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Resumen {

    @XmlElement(name = "CodigoTipoMoneda")
    private CodigoTipoMoneda codigoTipoMoneda;  // Ver documento Codigodemoneda_V4.2.pdf
    @XmlElement(name = "TotalServGravados")
    private double totalServGravados;
    @XmlElement(name = "TotalServExentos")
    private double totalServExentos;
    @XmlElement(name = "TotalServExonerado")
    private double totalServExonerado;
    @XmlElement(name = "TotalMercanciasGravadas")
    private double totalMercanciasGravadas;
    @XmlElement(name = "TotalMercanciasExentas")
    private double totalMercanciasExentas;
    @XmlElement(name = "TotalMercExonerada")
    private double totalMercExonerada;
    @XmlElement(name = "TotalGravado")
    private double totalGravado;
    @XmlElement(name = "TotalExento")
    private double totalExento;
    @XmlElement(name = "TotalExonerado")
    private double totalExonerado;
    @XmlElement(name = "TotalVenta")
    private double totalVenta;
    @XmlElement(name = "TotalDescuentos")
    private double totalDescuentos;
    @XmlElement(name = "TotalVentaNeta")
    private double totalVentaNeta;
    @XmlElement(name = "TotalImpuesto")
    private double totalImpuesto;
    @XmlElement(name = "TotalIVADevuelto")
    private double totalIVADevuelto;
    @XmlElement(name = "TotalOtrosCargos")
    private double totalOtrosCargos;
    @XmlElement(name = "TotalComprobante")
    private double totalComprobante;

    public Resumen() {
        
    }

    public CodigoTipoMoneda getCodigoTipoMoneda() {
        return codigoTipoMoneda;
    }

    public void setCodigoTipoMoneda(CodigoTipoMoneda codigoTipoMoneda) {
        this.codigoTipoMoneda = codigoTipoMoneda;
    }

    public double getTotalServGravados() {
        return totalServGravados;
    }

    public void setTotalServGravados(double totalServGravados) {
        this.totalServGravados = totalServGravados;
    }

    public double getTotalServExentos() {
        return totalServExentos;
    }

    public void setTotalServExentos(double totalServExentos) {
        this.totalServExentos = totalServExentos;
    }

    public double getTotalServExonerado() {
        return totalServExonerado;
    }

    public void setTotalServExonerado(double totalServExonerado) {
        this.totalServExonerado = totalServExonerado;
    }

    public double getTotalMercanciasGravadas() {
        return totalMercanciasGravadas;
    }

    public void setTotalMercanciasGravadas(double totalMercanciasGravadas) {
        this.totalMercanciasGravadas = totalMercanciasGravadas;
    }

    public double getTotalMercanciasExentas() {
        return totalMercanciasExentas;
    }

    public void setTotalMercanciasExentas(double totalMercanciasExentas) {
        this.totalMercanciasExentas = totalMercanciasExentas;
    }

    public double getTotalMercExonerada() {
        return totalMercExonerada;
    }

    public void setTotalMercExonerada(double totalMercExonerada) {
        this.totalMercExonerada = totalMercExonerada;
    }

    public double getTotalGravado() {
        return totalGravado;
    }

    public void setTotalGravado(double totalGravado) {
        this.totalGravado = totalGravado;
    }

    public double getTotalExento() {
        return totalExento;
    }

    public void setTotalExento(double totalExento) {
        this.totalExento = totalExento;
    }

    public double getTotalExonerado() {
        return totalExonerado;
    }

    public void setTotalExonerado(double totalExonerado) {
        this.totalExonerado = totalExonerado;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public double getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(double totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public double getTotalVentaNeta() {
        return totalVentaNeta;
    }

    public void setTotalVentaNeta(double totalVentaNeta) {
        this.totalVentaNeta = totalVentaNeta;
    }

    public double getTotalImpuesto() {
        return totalImpuesto;
    }

    public void setTotalImpuesto(double totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    public double getTotalIVADevuelto() {
        return totalIVADevuelto;
    }

    public void setTotalIVADevuelto(double totalIVADevuelto) {
        this.totalIVADevuelto = totalIVADevuelto;
    }

    public double getTotalOtrosCargos() {
        return totalOtrosCargos;
    }

    public void setTotalOtrosCargos(double totalOtrosCargos) {
        this.totalOtrosCargos = totalOtrosCargos;
    }

    public double getTotalComprobante() {
        return totalComprobante;
    }

    public void setTotalComprobante(Double totalComprobante) {
        this.totalComprobante = totalComprobante;
    }

} // end class
