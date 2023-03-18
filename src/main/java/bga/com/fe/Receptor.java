package bga.com.fe;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author bosco
 */
public class Receptor {

    private String nombre;
    private Identificacion identificacion;
    private String nombreComercial;
    private String correoElectronico;

    public Receptor() {
        this.nombre = "";
        this.identificacion = new Identificacion();
        this.nombreComercial = "";
        this.correoElectronico = "";
    } // end empty constructor

    public String getNombre() {
        return nombre;
    }

    @XmlElement(name = "Nombre")
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Identificacion getIdentificacion() {
        return identificacion;
    }

    @XmlElement(name = "Identificacion")
    public void setIdentificacion(Identificacion identificacion) {
        this.identificacion = identificacion;
    }
    
    public String getNombreComercial() {
        return nombreComercial;
    }

    @XmlElement(name = "NombreComercial")
    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    @XmlElement(name = "CorreoElectronico")
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
} // end class
