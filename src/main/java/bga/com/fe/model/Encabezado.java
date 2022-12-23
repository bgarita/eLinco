package bga.com.fe.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author bgarita, 05/12/2022
 */
@Entity
public class Encabezado implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String clave;
    
    @Column(name="tipo_documento")
    private String tipoDocumento;
    
    @Column(name="codigoActividad")
    private String codigoActividad;
    
    private String comprobante; // Número consecutivo
    
    @Column(name="fecha_emision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;
    
    @Column(name="tipo_id_emisor")
    private String tipoIdEmisor;
    @Column(name = "nombre_emisor")
    private String nombreEmisor;
    @Column(name = "numero_emisor")
    private String numeroEmisor;
    @Column(name = "correo_electronico_emisor")
    private String correoElectronicoEmisor;
    
    @Column(name="tipo_id_Receptor")
    private String tipoIdReceptor;
    @Column(name = "nombre_receptor")
    private String nombreReceptor;
    @Column(name = "numero_receptor")
    private String numeroReceptor;
    
    @Column(name = "condicion_venta")
    private String condicionVenta;
    @Column(name = "plazo_credito")
    private Integer plazoCredito;
    @Column(name = "medio_pago")
    private String medioPago;
    @Column(name = "codigo_moneda")
    private String codigoMoneda;
    @Column(name = "tipo_cambio")
    private Double tipoCambio;
    @Column(name = "total_serv_gravados")
    private Double totalServGravados;
    @Column(name = "total_serv_exentos")
    private Double totalServExentos;
    @Column(name = "total_serv_exonerado")
    private Double totalServExonerado;
    @Column(name = "total_mercancias_gravadas")
    private Double totalMercanciasGravadas;
    @Column(name = "total_mercancias_exentas")
    private Double totalMercanciasExentas;
    @Column(name = "total_merc_exonerada")
    private Double totalMercExonerada;
    @Column(name = "total_gravado")
    private Double totalGravado;
    @Column(name = "total_exento")
    private Double totalExento;
    @Column(name = "total_exonerado")
    private Double totalExonerado;
    @Column(name = "total_venta")
    private Double totalVenta;
    @Column(name = "total_descuentos")
    private Double totalDescuentos;
    @Column(name = "total_venta_neta")
    private Double totalVentaNeta;
    @Column(name = "total_impuesto")
    private Double totalImpuesto;
    @Column(name = "total_IVA_devuelto")
    private Double totalIVADevuelto;
    @Column(name = "total_otros_cargos")
    private Double totalOtrosCargos;
    @Column(name = "total_comprobante")
    private Double totalComprobante;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    
    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getTipoIdEmisor() {
        return tipoIdEmisor;
    }

    public void setTipoIdEmisor(String tipoIdEmisor) {
        this.tipoIdEmisor = tipoIdEmisor;
    }

    public String getNombreEmisor() {
        return nombreEmisor;
    }

    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    public String getNumeroEmisor() {
        return numeroEmisor;
    }

    public void setNumeroEmisor(String numeroEmisor) {
        this.numeroEmisor = numeroEmisor;
    }

    public String getCondicionVenta() {
        return condicionVenta;
    }

    public void setCondicionVenta(String condicionVenta) {
        this.condicionVenta = condicionVenta;
    }

    public Integer getPlazoCredito() {
        return plazoCredito;
    }

    public void setPlazoCredito(Integer plazoCredito) {
        this.plazoCredito = plazoCredito;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }

    public String getCodigoMoneda() {
        return codigoMoneda;
    }

    public void setCodigoMoneda(String codigoMoneda) {
        this.codigoMoneda = codigoMoneda;
    }

    public Double getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public Double getTotalServGravados() {
        return totalServGravados;
    }

    public void setTotalServGravados(Double totalServGravados) {
        this.totalServGravados = totalServGravados;
    }

    public Double getTotalServExentos() {
        return totalServExentos;
    }

    public void setTotalServExentos(Double totalServExentos) {
        this.totalServExentos = totalServExentos;
    }

    public Double getTotalServExonerado() {
        return totalServExonerado;
    }

    public void setTotalServExonerado(Double totalServExonerado) {
        this.totalServExonerado = totalServExonerado;
    }

    public Double getTotalMercanciasGravadas() {
        return totalMercanciasGravadas;
    }

    public void setTotalMercanciasGravadas(Double totalMercanciasGravadas) {
        this.totalMercanciasGravadas = totalMercanciasGravadas;
    }

    public Double getTotalMercanciasExentas() {
        return totalMercanciasExentas;
    }

    public void setTotalMercanciasExentas(Double totalMercanciasExentas) {
        this.totalMercanciasExentas = totalMercanciasExentas;
    }

    public Double getTotalMercExonerada() {
        return totalMercExonerada;
    }

    public void setTotalMercExonerada(Double totalMercExonerada) {
        this.totalMercExonerada = totalMercExonerada;
    }

    public Double getTotalGravado() {
        return totalGravado;
    }

    public void setTotalGravado(Double totalGravado) {
        this.totalGravado = totalGravado;
    }

    public Double getTotalExento() {
        return totalExento;
    }

    public void setTotalExento(Double totalExento) {
        this.totalExento = totalExento;
    }

    public Double getTotalExonerado() {
        return totalExonerado;
    }

    public void setTotalExonerado(Double totalExonerado) {
        this.totalExonerado = totalExonerado;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public Double getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(Double totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public Double getTotalVentaNeta() {
        return totalVentaNeta;
    }

    public void setTotalVentaNeta(Double totalVentaNeta) {
        this.totalVentaNeta = totalVentaNeta;
    }

    public Double getTotalImpuesto() {
        return totalImpuesto;
    }

    public void setTotalImpuesto(Double totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    public Double getTotalIVADevuelto() {
        return totalIVADevuelto;
    }

    public void setTotalIVADevuelto(Double totalIVADevuelto) {
        this.totalIVADevuelto = totalIVADevuelto;
    }

    public Double getTotalOtrosCargos() {
        return totalOtrosCargos;
    }

    public void setTotalOtrosCargos(Double totalOtrosCargos) {
        this.totalOtrosCargos = totalOtrosCargos;
    }

    public Double getTotalComprobante() {
        return totalComprobante;
    }

    public void setTotalComprobante(Double totalComprobante) {
        this.totalComprobante = totalComprobante;
    }

    public String getTipoIdReceptor() {
        return tipoIdReceptor;
    }

    public void setTipoIdReceptor(String tipoIdReceptor) {
        this.tipoIdReceptor = tipoIdReceptor;
    }

    public String getNombreReceptor() {
        return nombreReceptor;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }

    public String getNumeroReceptor() {
        return numeroReceptor;
    }

    public void setNumeroReceptor(String numeroReceptor) {
        this.numeroReceptor = numeroReceptor;
    }

    public String getCodigoActividad() {
        return codigoActividad;
    }

    public void setCodigoActividad(String codigoActividad) {
        this.codigoActividad = codigoActividad;
    }

    public String getCorreoElectronicoEmisor() {
        return correoElectronicoEmisor;
    }

    public void setCorreoElectronicoEmisor(String correoElectronicoEmisor) {
        this.correoElectronicoEmisor = correoElectronicoEmisor;
    }

    
    
}
