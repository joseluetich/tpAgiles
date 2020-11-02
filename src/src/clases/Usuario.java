package src.src.clases;

import src.src.clases.Rol;
import src.src.clases.tipoDeDocumento;

import java.util.Date;

public class Usuario {
    private String idUsuario, numeroLegajo, apellido, nombre, direccion, numeroDeDocumento;
    private Date fechaDeNacimiento;
    private Rol rol;
    private tipoDeDocumento tipoDoc;

    public Usuario(String idUsuario, String numeroLegajo, String apellido, String nombre, String direccion, String numeroDeDocumento, Date fechaDeNacimiento, Rol rol, tipoDeDocumento tipoDoc) {
        this.idUsuario = idUsuario;
        this.numeroLegajo = numeroLegajo;
        this.apellido = apellido;
        this.nombre = nombre;
        this.direccion = direccion;
        this.numeroDeDocumento = numeroDeDocumento;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.rol = rol;
        this.tipoDoc = tipoDoc;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNumeroLegajo() {
        return numeroLegajo;
    }

    public void setNumeroLegajo(String numeroLegajo) {
        this.numeroLegajo = numeroLegajo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNumeroDeDocumento() {
        return numeroDeDocumento;
    }

    public void setNumeroDeDocumento(String numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public tipoDeDocumento getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(tipoDeDocumento tipoDoc) {
        this.tipoDoc = tipoDoc;
    }
}
