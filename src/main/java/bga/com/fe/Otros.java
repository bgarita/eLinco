package bga.com.fe;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bosco
 */
@XmlType(propOrder = {"otroTexto"})
public class Otros {

    private List<OtroTexto> otroTexto;

    public Otros() {

    } // end empty constructor

    public List<OtroTexto> getOtroTexto() {
        return otroTexto;
    }

    @XmlElement(name = "OtroTexto")
    public void setOtroTexto(List<OtroTexto> otroTexto) {
        this.otroTexto = otroTexto;
    }

} // end class
