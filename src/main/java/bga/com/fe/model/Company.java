package bga.com.fe.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author bgarita, 15/12/2022
 */
@Entity
public class Company implements Serializable {
    @Id
    @Column(name="numero_receptor")
    private String numeroReceptor; // Por lo general es la cédula física o jurídica
    
    @Column(name="nombre_receptor")
    private String nombreReceptor;
    @Column(name="nombre_comercial_receptor")
    private String nombreComercial;
    
    public Company() {
        this.numeroReceptor = "";
        this.nombreReceptor = "";
        this.nombreComercial = "";
    }

    public Company(String numeroReceptor, String nombreReceptor, String nombreComercial) {
        this.numeroReceptor = numeroReceptor;
        this.nombreReceptor = nombreReceptor;
        this.nombreComercial = nombreComercial;
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

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    @Override
    public String toString() {
        return "Company{" + "numeroReceptor=" + numeroReceptor + ", nombreReceptor=" + nombreReceptor + ", nombreComercial=" + nombreComercial + '}';
    }

    
}
