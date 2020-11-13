package clases;

public class Clase {
    private Integer idClase, edadMinima;
    private tipoClase tipo;
    private Licencia licencia;

    public Clase(Integer idClase, Integer edadMinima, tipoClase tipo, Licencia licencia) {
        this.idClase = idClase;
        this.edadMinima = edadMinima;
        this.tipo = tipo;
        this.licencia = licencia;
    }

    public Clase() {

    }

    public Integer getIdClase() {
        return idClase;
    }

    public void setIdClase(Integer idClase) {
        this.idClase = idClase;
    }

    public Integer getEdadMinima() {
        return edadMinima;
    }

    public void setEdadMinima(Integer edadMinima) {
        this.edadMinima = edadMinima;
    }

    public tipoClase getTipo() {
        return tipo;
    }

    public void setTipo(tipoClase tipo) {
        this.tipo = tipo;
    }

    public Licencia getLicencia() {
        return licencia;
    }

    public void setLicencia(Licencia licencia) {
        this.licencia = licencia;
    }
}
