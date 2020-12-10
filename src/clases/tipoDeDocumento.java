package src.clases;

public enum tipoDeDocumento {
    DNI, LIBRETA_ENROLAMIENTO, LIBRETA_CIVICA;

    public static tipoDeDocumento stringToTipoDoc(String tipo){
        if(tipo.equals("DNI")){
            return DNI;
        }
        else if(tipo.equals("LIBRETA_ENROLAMIENTO")){
            return LIBRETA_ENROLAMIENTO;
        }
        else if(tipo.equals("LIBRETA_CIVICA")){
            return LIBRETA_CIVICA;
        }
        return null;
    }
}
