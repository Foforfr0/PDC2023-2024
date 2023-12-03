package eminus5.databaseManagment.model.POJO;

public class Defecto {
    int idDefecto;
    String nombre;
    String descripcion;
    int Estado;
    int Esfuerzo;
    String fechaEncontrado;
    String fechaSolucionado;
    int tipo;
    int idActividad;

    public Defecto() {
    }

    public Defecto(int idDefecto, String nombre, String descripcion, int Estado, int Esfuerzo, String fechaEncontrado, String fechaSolucionado, int tipo, int idActividad) {
        this.idDefecto = idDefecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.Estado = Estado;
        this.Esfuerzo = Esfuerzo;
        this.fechaEncontrado = fechaEncontrado;
        this.fechaSolucionado = fechaSolucionado;
        this.tipo = tipo;
        this.idActividad = idActividad;
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

    public int getEstado() {
        return Estado;
    }

    public void setEstado(int Estado) {
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

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }
    
}