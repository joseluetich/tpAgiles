package src.clases;

import java.util.Date;

public class CopiaLicencia {
    private Integer idCopiaLicencia, numeroDeCopia;
    private Usuario usuario;
    private Licencia licencia;
    private Date fechaDeEmision;
    private motivosCopia motivos;
    private ComprobanteDePago comprobante;

    public CopiaLicencia(Integer idCopiaLicencia, Usuario usuario, Licencia licencia, Integer numeroDeCopia, Date fechaDeEmision, motivosCopia motivos, ComprobanteDePago comprobante) {
        this.idCopiaLicencia = idCopiaLicencia;
        this.usuario = usuario;
        this.licencia = licencia;
        this.numeroDeCopia = numeroDeCopia;
        this.fechaDeEmision = fechaDeEmision;
        this.motivos = motivos;
        this.comprobante = comprobante;
    }

    public Integer getIdCopiaLicencia() {
        return idCopiaLicencia;
    }

    public void setIdCopiaLicencia(Integer idCopiaLicencia) {
        this.idCopiaLicencia = idCopiaLicencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Licencia getLicencia() {
        return licencia;
    }

    public void setLicencia(Licencia licencia) {
        this.licencia = licencia;
    }

    public Integer getNumeroDeCopia() {
        return numeroDeCopia;
    }

    public void setNumeroDeCopia(Integer numeroDeCopia) {
        this.numeroDeCopia = numeroDeCopia;
    }

    public Date getFechaDeEmision() {
        return fechaDeEmision;
    }

    public void setFechaDeEmision(Date fechaDeEmision) {
        this.fechaDeEmision = fechaDeEmision;
    }

    public motivosCopia getMotivos() {
        return motivos;
    }

    public void setMotivos(motivosCopia motivos) {
        this.motivos = motivos;
    }

    public ComprobanteDePago getComprobante() {
        return comprobante;
    }

    public void setComprobante(ComprobanteDePago comprobante) {
        this.comprobante = comprobante;
    }
}
