package bga.com.fe;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bosco
 */
@XmlType(propOrder = {"codigo", "codigoTarifa", "tarifa", "factorIVA", "monto"})
public class Impuesto {
    private String codigo;
    private String codigoTarifa;    // Julio 2019
    private float tarifa;
    private float factorIVA;        // Julio 2019
    private double monto;
    
    
    public Impuesto(){
        this.codigo = "";
        this.codigoTarifa = "";
        this.factorIVA = 0f;
        this.monto = 0.0;
    } // end empty constructor
    
    
    public String getCodigo() {
        return codigo;
    }

    @XmlElement(name = "Codigo")
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @XmlElement(name = "CodigoTarifa")
    public void setCodigoTarifa(String codigoTarifa) {
        this.codigoTarifa = codigoTarifa;
    }

    public String getCodigoTarifa() {
        return codigoTarifa;
    }
    
    public float getTarifa() {
        return tarifa;
    }

    @XmlElement(name = "Tarifa")
    public void setTarifa(float tarifa) {
        this.tarifa = tarifa;
    }

    public float getFactorIVA() {
        return factorIVA;
    }

    @XmlElement(name = "FactorIVA")
    public void setFactorIVA(float factorIVA) {
        this.factorIVA = factorIVA;
    }

    
    public double getMonto() {
        return monto;
    }

    @XmlElement(name = "Monto")
    public void setMonto(double monto) {
        this.monto = monto;
    }
    
} // end class
