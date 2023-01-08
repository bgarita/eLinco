package bga.com.fe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author bosco, 07/01/2023
 * 
 */
public class DetalleNotaDebito {

    private List<LineaDetalle> linea;

    public DetalleNotaDebito() {
        this.linea = new ArrayList<>();
    }

    public List<LineaDetalle> getLinea() {
        return linea;
    }

    @XmlElement(name = "LineaDetalle")
    public void setLinea(List<LineaDetalle> linea) {
        this.linea = linea;
    }

} // end class
