package bga.com.fe;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author bosco
 * 
 */
public class DetalleFactura {

    private List<LineaDetalle> linea;

    public DetalleFactura() {
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
