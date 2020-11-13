package clases.*;

import java.util.ArrayList;
import java.util.Date;

public class Licencia {
    private String idLicencia;
    private String observaciones;
    private src.clases.tipoLicencia tipoLicencia;
    private Integer numeroDeLicencia;
    private Date fechaDeModificacion, fechaDeOtorgamiento, fechaDeVencimienot;
    private Boolean enVigencia;
    private Double costo;
    private ArrayList<Clase> clases;
    private ComprobanteDePago comprobanteDePago;
    private Titular titular;
    private Usuario usuario;
    private ArrayList<CopiaLicencia> copias;

    public Licencia(){

    }

    public Licencia(String id, String observaciones, tipoLicencia tipoLicencia, Integer numeroDeLicencia, Integer numeroDuplicado, Date fechaDeModificacion, Date fechaDeOtorgamiento, Date fechaDeVencimienot, Boolean enVigencia, Double costo, ArrayList<Clase> clases, ComprobanteDePago comprobanteDePago, Titular titular, Usuario usuario, ArrayList<CopiaLicencia> copias) {
        this.idLicencia = id;
        this.observaciones = observaciones;
        this.tipoLicencia = tipoLicencia;
        this.numeroDeLicencia = numeroDeLicencia;
        this.fechaDeModificacion = fechaDeModificacion;
        this.fechaDeOtorgamiento = fechaDeOtorgamiento;
        this.fechaDeVencimienot = fechaDeVencimienot;
        this.enVigencia = enVigencia;
        this.costo = costo;
        this.clases = clases;
        this.comprobanteDePago = comprobanteDePago;
        this.titular = titular;
        this.usuario = usuario;
        this.copias = copias;
    }

    public String getIdLicencia() {
        return idLicencia;
    }

    public void setIdLicencia(String idLicencia) {
        this.idLicencia = idLicencia;
    }

    public clases.tipoLicencia getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(clases.tipoLicencia tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Integer getNumeroDeLicencia() {
        return numeroDeLicencia;
    }

    public void setNumeroDeLicencia(Integer numeroDeLicencia) {
        this.numeroDeLicencia = numeroDeLicencia;
    }

    public Date getFechaDeModificacion() {
        return fechaDeModificacion;
    }

    public void setFechaDeModificacion(Date fechaDeModificacion) {
        this.fechaDeModificacion = fechaDeModificacion;
    }

    public Date getFechaDeOtorgamiento() {
        return fechaDeOtorgamiento;
    }

    public void setFechaDeOtorgamiento(Date fechaDeOtorgamiento) {
        this.fechaDeOtorgamiento = fechaDeOtorgamiento;
    }

    public Date getFechaDeVencimienot() {
        return fechaDeVencimienot;
    }

    public void setFechaDeVencimienot(Date fechaDeVencimienot) {
        this.fechaDeVencimienot = fechaDeVencimienot;
    }

    public Boolean getEnVigencia() {
        return enVigencia;
    }

    public void setEnVigencia(Boolean enVigencia) {
        this.enVigencia = enVigencia;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public ArrayList<Clase> getClases() {
        return clases;
    }

    public void setClases(ArrayList<Clase> clases) {
        this.clases = clases;
    }

    public ComprobanteDePago getComprobanteDePago() {
        return comprobanteDePago;
    }

    public void setComprobanteDePago(ComprobanteDePago comprobanteDePago) {
        this.comprobanteDePago = comprobanteDePago;
    }

    public Titular getTitular() {
        return titular;
    }

    public void setTitular(Titular titular) {
        this.titular = titular;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ArrayList<CopiaLicencia> getCopias() {
        return copias;
    }

    public void setCopias(ArrayList<CopiaLicencia> copias) {
        this.copias = copias;
    }
}
