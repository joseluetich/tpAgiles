package logica;

import clases.tipoDeDocumento;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CalcularVigenciaLicencia {

    public Date hoy = new Date();
//tipoDeDocumento tipoDoc
    public Date calcularVigencia(Date fechaOtorgamiento, Date fechaCumpleanios, Integer idTitular, String tipoDoc){
        int anios = hoy.getYear()-fechaCumpleanios.getYear();
        Date fechaReturn = new Date();
        //si tuvo licencia y es menor a 21 => licencia por 3 años
        if(anios<21 && !tuvoLicencia(idTitular, tipoDoc)){
            fechaReturn.setDate(fechaCumpleanios.getDate()+1095);
            return  fechaReturn;
        }
        //si no tuvo licencia y es menor de 21 => licencia por 1 año
        if(anios<21 && tuvoLicencia(idTitular, tipoDoc)){
            fechaReturn.setDate(fechaCumpleanios.getDate()+365);
            return fechaReturn;
        }

        //entre 21 y 46=> licencia por 5 años
        if(anios>21 && anios<46){
            fechaReturn.setDate(fechaCumpleanios.getDate()+1825);
            return fechaReturn;
        }
        //entre 47 y 60 => licencia por 4 años
        if(anios>47 && anios<60){
            fechaReturn.setDate(fechaCumpleanios.getDate()+1460);
            return fechaReturn;
        }
        //entre 61 y 70 => licencia por 1 año
        if(anios>61 && anios<70){
            fechaReturn.setDate(fechaCumpleanios.getDate()+365);
            return fechaReturn;
        }

        return fechaOtorgamiento; //hubo un error
    }

    public boolean tuvoLicencia(Integer idTitular, String tipoDoc){
        //consulta
        //if((Select * from licencia where tipo = @tipo and dni =@ dni) != "null"){
        //      return true;
        //else return false;
        return false;
    }


    public static void main(String[] args) {
        Date fechaOtor = new Date(2020 , 11 , 8);
        Date fechaCumpl = new Date(1998 , 10 , 1);
        Date fechaPrueba = new Date();
        //fechaPrueba = calcularVigencia(fechaOtor, fechaCumpl, 41012558, "hola");


    }

}
