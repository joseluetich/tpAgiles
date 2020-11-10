package logica;

import bd.ConectarBD;
import clases.tipoDeDocumento;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import bd.ConexionDefault;


public class CalcularVigenciaLicencia {


    public static Date calcularVigencia(Date fechaOtorgamiento, Date fechaCumpleanios, String dniTitular, String idTitular) throws SQLException {

        Calendar calActual = Calendar.getInstance();
        Date fechaActual = new Date();

        System.out.println(fechaCumpleanios.getYear());
        System.out.println("que chucha imprime esto: "+fechaActual);

        int anios = fechaActual.getYear()-fechaCumpleanios.getYear();
        fechaCumpleanios.setYear(fechaActual.getYear());
        calActual.setTime(fechaCumpleanios);
        String retornoBD = buscarLicencia(dniTitular,idTitular);
        System.out.println(retornoBD);
        System.out.println(anios);


        //si no tuvo licencia y es menor a 21 => licencia por 1 años
        if(anios<21 && retornoBD== ""){
            calActual.add(Calendar.YEAR, 1);
            return calActual.getTime();
        }

        //si tuvo licencia y es menor de 21 => licencia por 3 año
        if(anios<21 && !(retornoBD== "")){
            calActual.add(Calendar.YEAR, 3);
            return calActual.getTime();
        }

        //entre 21 y 46=> licencia por 5 años
        if(anios>21 && anios<46){
            calActual.add(Calendar.YEAR, 5);
            return calActual.getTime();
        }
        //entre 47 y 60 => licencia por 4 años
        if(anios>47 && anios<60){
            calActual.add(Calendar.YEAR, 4);
            return calActual.getTime();
        }
        //entre 61 y 70 => licencia por 1 año
        if(anios>61 && anios<70){
            calActual.add(Calendar.YEAR, 1);
            return calActual.getTime();
        }

        return fechaActual; //hubo un error
    }



    public static void main(String[] args) throws SQLException {
        Date fechaOtor = new Date(2020 , 11 , 8);
        Date fechaCumpl = new Date(1998 , 10 , 1);
        Date fechaPrueba = new Date();
        //fechaPrueba = calcularVigencia(fechaOtor, fechaCumpl, 41012558, "hola");

        Date resultado = calcularVigencia(fechaOtor,fechaCumpl,"40258746","5");
        System.out.println("fecha devuelta: "+resultado);


      /*  String retornoBD = buscarLicencia("40258746","5");
        System.out.println(retornoBD);

        //consulta
        if(retornoBD == "") {
            System.out.println("true");
            // return true;
        }
        else System.out.println("false");
            //return false;
        */
    }


    public static String buscarLicencia(String dniTitular, String idTitular) throws SQLException {
        String retornoBD = "";
        Statement stmt = (new ConectarBD()).getStmt();
        String SQL = "SELECT * FROM Licencia " +
                "WHERE (numeroDeLicencia="+"'"+dniTitular+"'"+
                "AND titular="+idTitular+")";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("numeroDeLicencia")+","+rs.getString("titular");
        }

        return retornoBD;
    }

}
