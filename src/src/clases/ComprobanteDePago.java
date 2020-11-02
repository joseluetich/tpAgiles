package src.src.clases;

import java.util.ArrayList;
import java.util.Date;

public class ComprobanteDePago {
    private Integer idComprobante, numeroDeFactura;
    private Date fechaEmision, fechaVencimiento;
    private String condicionDePago, observacionesPago;
    private ArrayList<String> descripcionElemento, cantidadDescripcion;
    private Double importeNeto, importeBruto;
    private Licencia licenciaAsociada;
    private CopiaLicencia copiaAsociada; // TODO verificar

    public ComprobanteDePago(Integer idComprobante, Integer numeroDeFactura, Date fechaEmision, Date fechaVencimiento, String condicionDePago, String observacionesPago, ArrayList<String> descripcionElemento, ArrayList<String> cantidadDescripcion, Double importeNeto, Double importeBruto, Licencia licenciaAsociada, CopiaLicencia copiaAsociada) {
        this.idComprobante = idComprobante;
        this.numeroDeFactura = numeroDeFactura;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.condicionDePago = condicionDePago;
        this.observacionesPago = observacionesPago;
        this.descripcionElemento = descripcionElemento;
        this.cantidadDescripcion = cantidadDescripcion;
        this.importeNeto = importeNeto;
        this.importeBruto = importeBruto;
        this.licenciaAsociada = licenciaAsociada;
        this.copiaAsociada = copiaAsociada;
    }

    public Integer getNumeroDeFactura() {
        return numeroDeFactura;
    }

    public void setNumeroDeFactura(Integer numeroDeFactura) {
        this.numeroDeFactura = numeroDeFactura;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getCondicionDePago() {
        return condicionDePago;
    }

    public void setCondicionDePago(String condicionDePago) {
        this.condicionDePago = condicionDePago;
    }

    public String getObservacionesPago() {
        return observacionesPago;
    }

    public void setObservacionesPago(String observacionesPago) {
        this.observacionesPago = observacionesPago;
    }

    public ArrayList<String> getDescripcionElemento() {
        return descripcionElemento;
    }

    public void setDescripcionElemento(ArrayList<String> descripcionElemento) {
        this.descripcionElemento = descripcionElemento;
    }

    public ArrayList<String> getCantidadDescripcion() {
        return cantidadDescripcion;
    }

    public void setCantidadDescripcion(ArrayList<String> cantidadDescripcion) {
        this.cantidadDescripcion = cantidadDescripcion;
    }

    public Double getImporteNeto() {
        return importeNeto;
    }

    public void setImporteNeto(Double importeNeto) {
        this.importeNeto = importeNeto;
    }

    public Double getImporteBruto() {
        return importeBruto;
    }

    public void setImporteBruto(Double importeBruto) {
        this.importeBruto = importeBruto;
    }

    public Licencia getLicenciaAsociada() {
        return licenciaAsociada;
    }

    public void setLicenciaAsociada(Licencia licenciaAsociada) {
        this.licenciaAsociada = licenciaAsociada;
    }

    public Integer getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Integer idComprobante) {
        this.idComprobante = idComprobante;
    }

    public CopiaLicencia getCopiaAsociada() {
        return copiaAsociada;
    }

    public void setCopiaAsociada(CopiaLicencia copiaAsociada) {
        this.copiaAsociada = copiaAsociada;
    }
}
