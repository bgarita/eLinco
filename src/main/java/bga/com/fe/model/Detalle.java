package bga.com.fe.model;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author bosco
 */
@Entity
public class Detalle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String clave;
    @Column(name="numero_linea")
    private Integer numeroLinea;
    private String codigo;  // Código del producto o servicio
    @Column(name="codigo_comercial")
    private String codigoComercial;
    @Column(name="tipo_codigo_comercial")
    private String tipoCodigoComercial;
    private Double cantidad;
    @Column(name="unidad_medida")
    private String unidadMedida;
    private String descrip;
    @Column(name="precio_unitario")
    private Double precioUnitario;
    @Column(name="monto_total")
    private Double montoTotal;
    @Column(name="monto_descuento")
    private Double montoDescuento;
    @Column(name="naturaleza_descuento")
    private String naturalezaDescuento;
    @Column(name="sub_total")
    private Double subTotal;
    @Column(name="base_imponible")
    private Double baseImponible;
    @Column(name="impuesto_neto")
    private Double impuestoNeto;
    /*
    @Column(name="codigo_impuesto")
    private String codigoImpuesto;
    @Column(name="codigo_tarifa")
    private String codigoTarifa;
    private Float tarifa;
    private Float factorIVA;
    private Double montoIVA;
*/
    @Column(name="monto_total_linea")
    private Double montoTotalLinea; 
    

    public Detalle() {

    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(Integer numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Double getSubTotal() {
        return subTotal;
    }
    
    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(Double baseImponible) {
        this.baseImponible = baseImponible;
    }
    
    public Double getMontoTotalLinea() {
        return montoTotalLinea;
    }

    public void setMontoTotalLinea(Double montoTotalLinea) {
        this.montoTotalLinea = montoTotalLinea;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoComercial() {
        return codigoComercial;
    }

    public void setCodigoComercial(String codigoComercial) {
        this.codigoComercial = codigoComercial;
    }

    public String getTipoCodigoComercial() {
        return tipoCodigoComercial;
    }

    public void setTipoCodigoComercial(String tipoCodigoComercial) {
        this.tipoCodigoComercial = tipoCodigoComercial;
    }

    public Double getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(Double montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    public String getNaturalezaDescuento() {
        return naturalezaDescuento;
    }

    public void setNaturalezaDescuento(String naturalezaDescuento) {
        this.naturalezaDescuento = naturalezaDescuento;
    }

    public Double getImpuestoNeto() {
        return impuestoNeto;
    }

    public void setImpuestoNeto(Double impuestoNeto) {
        this.impuestoNeto = impuestoNeto;
    }
    
    

//    public String getCodigoImpuesto() {
//        return codigoImpuesto;
//    }
//
//    public void setCodigoImpuesto(String codigoImpuesto) {
//        this.codigoImpuesto = codigoImpuesto;
//    }
//
//    public String getCodigoTarifa() {
//        return codigoTarifa;
//    }
//
//    public void setCodigoTarifa(String codigoTarifa) {
//        this.codigoTarifa = codigoTarifa;
//    }
//
//    public Float getTarifa() {
//        return tarifa;
//    }
//
//    public void setTarifa(Float tarifa) {
//        this.tarifa = tarifa;
//    }
//
//    public Float getFactorIVA() {
//        return factorIVA;
//    }
//
//    public void setFactorIVA(Float factorIVA) {
//        this.factorIVA = factorIVA;
//    }
//
//    public Double getMontoIVA() {
//        return montoIVA;
//    }
//
//    public void setMontoIVA(Double montoIVA) {
//        this.montoIVA = montoIVA;
//    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    
} // end class
