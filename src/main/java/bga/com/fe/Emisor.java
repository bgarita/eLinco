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
@XmlType(propOrder = {
    "nombre", "correoElectronico", "identificacion", "registroFiscal8707", "nombreComercial"
})
public class Emisor {

    @XmlElement(name = "Nombre")
    private String nombre;

    @XmlElement(name = "CorreoElectronico")
    private String correoElectronico;

    @XmlElement(name = "Identificacion")
    private Identificacion identificacion;

    // Esto parece ser un error en la documentación.  La F debería esta en mayúsculas.
    // Habrá que esperar a ver cómo llegan los comprobantes (15/09/2025)
    @XmlElement(name = "Registrofiscal8707")
    private String registroFiscal8707;

    @XmlElement(name = "NombreComercial")
    private String nombreComercial;

    public Emisor() {
    }

    // Getters/Setters SIN anotaciones JAXB
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Identificacion getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Identificacion identificacion) {
        this.identificacion = identificacion;
    }

    public String getRegistroFiscal8707() {
        return registroFiscal8707;
    }

    public void setRegistroFiscal8707(String registroFiscal8707) {
        this.registroFiscal8707 = registroFiscal8707;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }
}
