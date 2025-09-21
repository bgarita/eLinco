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
    "nombre", "identificacion", "nombreComercial", "correoElectronico", "codigoActividadReceptor"
})
public class Receptor {

    @XmlElement(name = "Nombre")
    private String nombre;

    @XmlElement(name = "Identificacion")
    private Identificacion identificacion;

    @XmlElement(name = "NombreComercial")
    private String nombreComercial;

    @XmlElement(name = "CorreoElectronico")
    private String correoElectronico;

    @XmlElement(name = "CodigoActividadReceptor")
    private String codigoActividadReceptor;

    public Receptor() {
        this.nombre = "";
        this.identificacion = new Identificacion();
        this.nombreComercial = "";
        this.correoElectronico = "";
        this.codigoActividadReceptor = "";
    }

    // Getters/Setters (sin anotaciones JAXB)
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Identificacion getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Identificacion identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCodigoActividadReceptor() {
        return codigoActividadReceptor;
    }

    public void setCodigoActividadReceptor(String codigoActividadReceptor) {
        this.codigoActividadReceptor = codigoActividadReceptor;
    }
}
