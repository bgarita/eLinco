package bga.com.fe.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author bgarita, 07/01/2023
 */
@Entity
public class Emisor implements Serializable {
    @Id
    @Column(name="numero_emisor")
    private String numeroEmisor; // Por lo general es la cédula física o jurídica
    
    @Column(name="nombre_emisor")
    private String nombreEmisor;
    
    public Emisor() {
        this.numeroEmisor = "";
        this.nombreEmisor = "";
    }

    public Emisor(String numeroReceptor, String nombreReceptor) {
        this.numeroEmisor = numeroReceptor;
        this.nombreEmisor = nombreReceptor;
    }

    public String getNumeroEmisor() {
        return numeroEmisor;
    }

    public void setNumeroEmisor(String numeroEmisor) {
        this.numeroEmisor = numeroEmisor;
    }

    public String getNombreEmisor() {
        return nombreEmisor;
    }

    public void setNombreEmisor(String nombreEmisor) {
        this.nombreEmisor = nombreEmisor;
    }

    @Override
    public String toString() {
        return "Company{" + "numeroReceptor=" + numeroEmisor + ", nombreReceptor=" + nombreEmisor + '}';
    }
    
    
}
