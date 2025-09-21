package bga.com.fe.factura;

import bga.com.fe.DetalleFactura;
import bga.com.fe.Resumen;
import bga.com.fe.Emisor;
import bga.com.fe.InformacionReferencia;
import bga.com.fe.MedioPago;
import bga.com.fe.Normativa;
import bga.com.fe.Otros;
import bga.com.fe.Receptor;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author bgarita, 28/11/2022
 */
@XmlRootElement(name = "FacturaElectronica")
@XmlAccessorType(XmlAccessType.FIELD)
public class FacturaElectronica {

    @XmlElement(name = "Clave")
    private String clave;
    
    @XmlElement(name = "ProveedorSistemas")
    private String proveedorSistemas;
    
    @XmlElement(name = "CodigoActividadEmisor")
    private String codigoActividadEmisor;
    
    @XmlElement(name = "NumeroConsecutivo")
    private String numeroConsecutivo;
    
    @XmlElement(name = "FechaEmision")
    private String fechaEmision;
    
    @XmlElement(name = "Emisor")
    private Emisor emisor;
    
    @XmlElement(name = "Receptor")
    private Receptor receptor;
    
    @XmlElement(name = "CondicionVenta")
    private String condicionVenta;
    
    @XmlElement(name = "PlazoCredito")
    private Integer plazoCredito;
    
    @XmlElement(name = "MedioPago")
    private MedioPago medioPago;
    
    @XmlElement(name = "DetalleServicio")
    private DetalleFactura detalle;
    
    @XmlElement(name = "ResumenFactura")
    private Resumen resumen;
    
    @XmlElement(name = "InformacionReferencia")
    private List<InformacionReferencia> nota;
    
    @XmlElement(name = "Normativa")
    private Normativa normativa;
    
    @XmlElement(name = "Otros")
    private Otros otros;
    
    public FacturaElectronica() {
        
    } // empty constructor
    
    
    public void setCodigoActividadEmisor(String codigoActividadEmisor){
        this.codigoActividadEmisor = codigoActividadEmisor;
    }
    
    public String getCodigoActividadEmisor() {
        return codigoActividadEmisor;
    }
    
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getProveedorSistemas() {
        return proveedorSistemas;
    }

    public void setProveedorSistemas(String proveedorSistemas) {
        this.proveedorSistemas = proveedorSistemas;
    }
    
    public String getNumeroConsecutivo() {
        return numeroConsecutivo;
    }
    
    public void setNumeroConsecutivo(String numeroConsecutivo) {
        this.numeroConsecutivo = numeroConsecutivo;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }
    
    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    public Emisor getEmisor() {
        return emisor;
    }

    public void setEmisor(Emisor emisor) {
        this.emisor = emisor;
    }
    
    public DetalleFactura getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleFactura detalle) {
        this.detalle = detalle;
    }

    public Receptor getReceptor() {
        return receptor;
    }

    public void setReceptor(Receptor receptor) {
        this.receptor = receptor;
    }
    
    public String getCondicionVenta() {
        return condicionVenta;
    }
    
    public void setCondicionVenta(String condicionVenta) {
        this.condicionVenta = condicionVenta;
    }

    public Integer getPlazoCredito() {
        return plazoCredito;
    }
    
    public void setPlazoCredito(Integer plazoCredito) {
        this.plazoCredito = plazoCredito;
    }

    public MedioPago getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(MedioPago medioPago) {
        this.medioPago = medioPago;
    }
    
    public Resumen getResumen() {
        return resumen;
    }

    public void setResumen(Resumen resumen) {
        this.resumen = resumen;
    }
    
    public List<InformacionReferencia> getNota() {
        return nota;
    }

    public void setNota(List<InformacionReferencia> nota) {
        this.nota = nota;
    }

    public Normativa getNormativa() {
        return normativa;
    }

    public void setNormativa(Normativa normativa) {
        this.normativa = normativa;
    }

    public Otros getOtros() {
        return otros;
    }

    public void setOtros(Otros otros) {
        this.otros = otros;
    }
    
}
