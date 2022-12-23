package bga.com.fe;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author bosco
 */
@XmlType(propOrder = {"nombre", "identificacion", "correoElectronico"})
public class Receptor {

    private String nombre;
    private Identificacion identificacion;
    private String correoElectronico;

    public Receptor() {
        this.nombre = "";
        this.identificacion = new Identificacion();
        this.correoElectronico = "";
    } // end empty constructor

    public String getNombre() {
        return nombre;
    }

    @XmlElement(name = "Nombre")
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    @XmlElement(name = "CorreoElectronico")
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Identificacion getIdentificacion() {
        return identificacion;
    }

    @XmlElement(name = "Identificacion")
    public void setIdentificacion(Identificacion identificacion) {
        this.identificacion = identificacion;
    }

} // end class
