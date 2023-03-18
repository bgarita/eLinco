package bga.com.fe.model;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author bosco, 15/01/2023
 */
@Entity
public class Impuesto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="detalle_id")
    private Integer detalleId;
    @Column(name="codigo_impuesto")
    private String codigoImpuesto;
    @Column(name="codigo_tarifa")
    private String codigoTarifa;
    private Float tarifa;
    private Double monto;
    

    public Impuesto() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(Integer detalleId) {
        this.detalleId = detalleId;
    }

    public String getCodigoImpuesto() {
        return codigoImpuesto;
    }

    public void setCodigoImpuesto(String codigoImpuesto) {
        this.codigoImpuesto = codigoImpuesto;
    }

    public String getCodigoTarifa() {
        return codigoTarifa;
    }

    public void setCodigoTarifa(String codigoTarifa) {
        this.codigoTarifa = codigoTarifa;
    }

    public Float getTarifa() {
        return tarifa;
    }

    public void setTarifa(Float tarifa) {
        this.tarifa = tarifa;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    
} // end class
