package bga.com.fe;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author bosco
 */
@XmlType(propOrder = {"nombre", "correoElectronico", "identificacion"})
public class Emisor {

    private String nombre;
    private String correoElectronico;
    private Identificacion identificacion;


    public Emisor() {

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
   
    
}
