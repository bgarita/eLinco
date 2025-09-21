package bga.com.fe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author bosco, 15/06/2019
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CodigoTipoMoneda {

    // v4.3
    @XmlElement(name = "CodigoTipoMoneda")
    private String codigoTipoMoneda;

    // v4.4 (alias)
    @XmlElement(name = "CodigoMoneda")
    private String codigoMoneda;

    @XmlElement(name = "TipoCambio")
    private Double tipoCambio;

    /** Devuelve el código de moneda (soporta v4.3 y v4.4). */
    public String getCodigoTipoMoneda() {
        return codigoTipoMoneda != null ? codigoTipoMoneda : codigoMoneda;
    }

    /** Setea ambos campos para mantener consistencia. */
    public void setCodigoTipoMoneda(String codigo) {
        this.codigoTipoMoneda = codigo;
        this.codigoMoneda = codigo;
    }

    public Double getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    // Opcional: acceso directo al alias
    public String getCodigoMoneda() { return codigoMoneda; }
    public void setCodigoMoneda(String codigo) {
        this.codigoMoneda = codigo;
        this.codigoTipoMoneda = codigo;
    }
}
