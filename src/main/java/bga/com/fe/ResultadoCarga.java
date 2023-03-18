package bga.com.fe;

/**
 *
 * @author bgarita, 10/12/2022
 */
public class ResultadoCarga {
    public static final String EXITO = "EXITO";
    public static final String ADEVERTENCIA = "ADEVERTENCIA";
    public static final String ERROR = "ERROR";
    public static final String FACTURA = "FAC";
    public static final String NOTA_CREDITO = "NCR";
    public static final String NOTA_DEBITO = "NDB";
    public static final String NO_DEFINIDO = "NDF";
    
    private String archivo;
    private String tipoArchivo;
    private String emisor;
    private String estado;
    private String mensaje;

    public ResultadoCarga() {
        this.archivo = "";
        this.tipoArchivo = FACTURA;
        this.emisor = "";
        this.estado = ERROR;
        this.mensaje = "Error indeterminado";
    }
    public ResultadoCarga(String archivo, String tipoArchivo, String emisor, String estado, String mensaje) {
        this.archivo = archivo;
        this.tipoArchivo = tipoArchivo;
        this.emisor = emisor;
        this.estado = estado;
        this.mensaje = mensaje;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    @Override
    public String toString() {
        return "ResultadoCarga{" + "archivo=" + archivo + ", tipoArchivo=" + tipoArchivo + ", emisor=" + emisor + ", estado=" + estado + ", mensaje=" + mensaje + '}';
    }

}
