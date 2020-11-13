package src.clases;

public class Clase {
    private Integer idClase, edadMinima;
    private String tipo;
    private Licencia licencia;

    public Clase(Integer idClase, Integer edadMinima, String tipo, Licencia licencia) {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Licencia getLicencia() {
        return licencia;
    }

    public void setLicencia(Licencia licencia) {
        this.licencia = licencia;
    }
}

