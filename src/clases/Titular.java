package src.clases;

import java.util.ArrayList;
import java.util.Date;

public class Titular {
    private Integer idTitular;
    private tipoDeDocumento tipoDoc;
    private String numeroDeDocumento, apellido, nombre, cuil, grupoSanguineo, direccion, codigoPostal;
    private Boolean donante;
    private Date fechaDeNacimiento;
    private ArrayList<Licencia> licencias;
    private Usuario creadoPor;

    public Usuario getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(Usuario creadoPor) {
        this.creadoPor = creadoPor;
    }

    public Titular() {
        this.licencias = new ArrayList<Licencia>();
    }

    public Titular(Integer idTitular, tipoDeDocumento tipoDoc, String numeroDeDocumento, String apellido, String nombre, String cuil, String grupoSanguineo, String factorSanguineo, String direccion, String codigoPostal, Boolean donante, Date fechaDeNacimiento, ArrayList<Licencia> licencias, Usuario creadoPor) {
        this.idTitular = idTitular;
        this.tipoDoc = tipoDoc;
        this.numeroDeDocumento = numeroDeDocumento;
        this.apellido = apellido;
        this.nombre = nombre;
        this.cuil = cuil;
        this.grupoSanguineo = grupoSanguineo;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.donante = donante;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.licencias = licencias;
        this.creadoPor = creadoPor;
    }

    public Integer getIdTitular() {
        return idTitular;
    }

    public void setIdTitular(Integer idTitular) {
        this.idTitular = idTitular;
    }

    public tipoDeDocumento getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(tipoDeDocumento tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public String getNumeroDeDocumento() {
        return numeroDeDocumento;
    }

    public void setNumeroDeDocumento(String numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
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

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public String getGrupoSanguineo() {
        return grupoSanguineo;
    }

    public void setGrupoSanguineo(String grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Boolean getDonante() {
        return donante;
    }

    public void setDonante(Boolean donante) {
        this.donante = donante;
    }

    public Date getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Date fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public ArrayList<Licencia> getLicencias() {
        return licencias;
    }

    public void setLicencias(ArrayList<Licencia> licencias) {
        this.licencias = licencias;
    }
}
