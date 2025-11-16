package bga.com.fe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bosco
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    propOrder = {"codigo", "codigoTarifaIVA", "tarifa", "factorCalculoIVA", "monto"}
)
public class Impuesto {

    @XmlElement(name = "Codigo")
    private String codigo;

    @XmlElement(name = "CodigoTarifa")
    private String codigoTarifaIVA;

    @XmlElement(name = "Tarifa")
    private float tarifa;

    private float factorCalculoIVA; // Este campo no parece estarse enviando a la base de datos 12/11/2026 (pendiente implementación)

    @XmlElement(name = "Monto")
    private double monto;
    
    
    public Impuesto(){
        this.codigo = "";
        this.codigoTarifaIVA = "";
        this.factorCalculoIVA = 0f;
        this.monto = 0.0;
    } // end empty constructor
    
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigoTarifaIVA() {
        return codigoTarifaIVA;
    }

    public void setCodigoTarifaIVA(String codigoTarifaIVA) {
        this.codigoTarifaIVA = codigoTarifaIVA;
    }
    
    public float getTarifa() {
        return tarifa;
    }

    public void setTarifa(float tarifa) {
        this.tarifa = tarifa;
    }

    public float getFactorCalculoIVA() {
        return factorCalculoIVA;
    }

    public void setFactorCalculoIVA(float factorCalculoIVA) {
        this.factorCalculoIVA = factorCalculoIVA;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    
} // end class
