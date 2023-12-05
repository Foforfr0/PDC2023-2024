package eminus5.databaseManagment.model.POJO;

/**
 *
 * @author abrah
 */
public class Cambio {
    private int idCambio;
    private String nombre;
    private String descripcionCambio;
    private String tipo;
    private int idProyecto;
    private int idDesarrollador;
    private int idDefecto;
    private int idSolicitud;

    public Cambio() {
    }

    public Cambio(int idCambio, String nombre, String descripcionCambio, String tipo, int idProyecto, int idDesarrollador, int idDefecto, int idSolicitud) {
        this.idCambio = idCambio;
        this.nombre = nombre;
        this.descripcionCambio = descripcionCambio;
        this.tipo = tipo;
        this.idProyecto = idProyecto;
        this.idDesarrollador = idDesarrollador;
        this.idDefecto = idDefecto;
        this.idSolicitud = idSolicitud;
    }

    public int getIdCambio() {
        return idCambio;
    }

    public void setIdCambio(int idCambio) {
        this.idCambio = idCambio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionCambio() {
        return descripcionCambio;
    }

    public void setDescripcionCambio(String descripcionCambio) {
        this.descripcionCambio = descripcionCambio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getIdDesarrollador() {
        return idDesarrollador;
    }

    public void setIdDesarrollador(int idDesarrollador) {
        this.idDesarrollador = idDesarrollador;
    }

    public int getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(int idDefecto) {
        this.idDefecto = idDefecto;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    
    
}
