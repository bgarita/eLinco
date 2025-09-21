package bga.com.fe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bosco, 15/09/2025
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"tipoMedioPago", "medioPagoOtros", "totalMedioPago"})
public class MedioPago {

    @XmlElement(name = "TipoMedioPago", required = true)
    private String tipoMedioPago;
    @XmlElement(name = "MedioPagoOtros")
    private String medioPagoOtros;
    @XmlElement(name = "TotalMedioPago")
    private double totalMedioPago;

    public MedioPago() {

    } // end empty constructor

    public String getTipoMedioPago() {
        return tipoMedioPago;
    }

    public void setTipoMedioPago(String tipoMedioPago) {
        this.tipoMedioPago = tipoMedioPago;
    }

    public String getMedioPagoOtros() {
        return medioPagoOtros;
    }

    public void setMedioPagoOtros(String medioPagoOtros) {
        this.medioPagoOtros = medioPagoOtros;
    }
    
    public double getTotalMedioPago() {
        return totalMedioPago;
    }
    
    public void setTotalMedioPago(double totalMedioPago) {
        this.totalMedioPago = totalMedioPago;
    }

} // end class
