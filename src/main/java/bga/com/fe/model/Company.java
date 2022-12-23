package bga.com.fe.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author bgarita, 15/12/2022
 */
@Entity
public class Company {
    @Id
    @Column(name="numero_receptor")
    private String numeroReceptor; // Por lo general es la cédula física o jurídica
    
    @Column(name="nombre_receptor")
    private String nombreReceptor;
    
    public Company() {
        this.numeroReceptor = "";
        this.nombreReceptor = "";
    }

    public Company(String numeroReceptor, String nombreReceptor) {
        this.numeroReceptor = numeroReceptor;
        this.nombreReceptor = nombreReceptor;
    }

    public String getNumeroReceptor() {
        return numeroReceptor;
    }

    public void setNumeroReceptor(String numeroReceptor) {
        this.numeroReceptor = numeroReceptor;
    }

    public String getNombreReceptor() {
        return nombreReceptor;
    }

    public void setNombreReceptor(String nombreReceptor) {
        this.nombreReceptor = nombreReceptor;
    }

    @Override
    public String toString() {
        return "Company{" + "numeroReceptor=" + numeroReceptor + ", nombreReceptor=" + nombreReceptor + '}';
    }
    
    
}
