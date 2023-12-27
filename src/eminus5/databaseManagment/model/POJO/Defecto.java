package eminus5.databaseManagment.model.POJO;

public class Defecto {
    int idDefecto;
    String nombre;
    String descripcion;
    String Estado;
    int Esfuerzo;
    String fechaEncontrado;
    String fechaSolucionado;
    String tipo;
    int IdProyecto;
    int IdDesarrollador;

    public Defecto() {
    }

    public Defecto(int idDefecto, String nombre, String descripcion, String Estado, int Esfuerzo, String fechaEncontrado, String fechaSolucionado, String tipo, int IdProyecto, int IdDesarrollador) {
        this.idDefecto = idDefecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.Estado = Estado;
        this.Esfuerzo = Esfuerzo;
        this.fechaEncontrado = fechaEncontrado;
        this.fechaSolucionado = fechaSolucionado;
        this.tipo = tipo;
        this.IdProyecto = IdProyecto;
        this.IdDesarrollador = IdDesarrollador;
    }

    public int getIdDefecto() {
        return idDefecto;
    }

    public void setIdDefecto(int idDefecto) {
        this.idDefecto = idDefecto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public int getEsfuerzo() {
        return Esfuerzo;
    }

    public void setEsfuerzo(int Esfuerzo) {
        this.Esfuerzo = Esfuerzo;
    }

    public String getFechaEncontrado() {
        return fechaEncontrado;
    }

    public void setFechaEncontrado(String fechaEncontrado) {
        this.fechaEncontrado = fechaEncontrado;
    }

    public String getFechaSolucionado() {
        return fechaSolucionado;
    }

    public void setFechaSolucionado(String fechaSolucionado) {
        this.fechaSolucionado = fechaSolucionado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdProyecto() {
        return IdProyecto;
    }

    public void setIdProyecto(int IdProyecto) {
        this.IdProyecto = IdProyecto;
    }

    public int getIdDesarrollador() {
        return IdDesarrollador;
    }

    public void setIdDesarrollador(int IdDesarrollador) {
        this.IdDesarrollador = IdDesarrollador;
    }

    
}