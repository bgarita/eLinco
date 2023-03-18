package bga.com.fe.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author bgarita, 22/01/2023
 */
@Entity
public class Empresa implements Serializable {
    
    @Id
    private int id;
    
    @Column(name="nombre_comercial")
    private String nombreComercial;
    
    public Empresa() {
        this.id = 0;
        this.nombreComercial = "";
    }

    public Empresa(int id, String nombreComercial) {
        this.id = id;
        this.nombreComercial = nombreComercial;
    }


    
    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Empresa{" + "id=" + id + ", nombreComercial=" + nombreComercial + '}';
    }

}
