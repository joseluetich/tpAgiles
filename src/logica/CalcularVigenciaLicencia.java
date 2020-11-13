package src.logica;

import clases.tipoDeDocumento;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.time.*;


public class CalcularVigenciaLicencia {


    public static Date calcularVigencia( Date fechaCumpleanios, String dniTitular, String idTitular) throws SQLException, ParseException {

        //Los meses en date empiezan en 1 y en time en 0
        fechaCumpleanios.setMonth(fechaCumpleanios.getMonth()-1);
        //convierto de Date a Time
        LocalDate fechaConvertida = fechaCumpleanios.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(fechaConvertida);

        LocalDate fechaCumple = LocalDate.of(fechaConvertida.getYear(),fechaConvertida.getMonth(),fechaConvertida.getDayOfMonth());
        LocalDate fechaActual = LocalDate.now();

        LocalDate fechaOtorgamiento = LocalDate.of(fechaActual.getYear(),fechaConvertida.getMonth(),fechaConvertida.getDayOfMonth());
        System.out.println("Fecha cumpleaños: "+fechaCumple+ " Fecha actual: "+fechaActual);
        int anios= (fechaActual.getYear()-fechaCumple.getYear());
        int mes = (fechaCumple.getMonthValue()- fechaActual.getMonthValue());
        int dias= (fechaCumple.getDayOfMonth()-fechaActual.getDayOfMonth());

        System.out.println("edad: "+anios+ " Meses: "+mes+ " Dias: "+ dias);

        String retornoBD = buscarLicencia(dniTitular,idTitular);
        System.out.println("base de datos retorna:  "+retornoBD);

        if(anios<17) {
            System.out.println("No se le puede otorgar una licencia, edad minima 17 años 1");
            return fechaCumpleanios;
        }
        if(anios>=17 && mes>0 && anios <21){
            System.out.println("No se le puede otorgar una licencia, edad minima 17 años 2");
            return fechaCumpleanios;
        }

        //si no tuvo licencia y es menor a 21 => licencia por 1 años
        if(anios<=21 && retornoBD.isEmpty()){
            fechaOtorgamiento=fechaOtorgamiento.plusYears(1);
            System.out.println("FECHA FINAL DEVUELTA "+fechaOtorgamiento);
            Date fechaOtorgDate = new Date(fechaOtorgamiento.getYear(),fechaOtorgamiento.getMonthValue(), fechaOtorgamiento.getDayOfMonth());
            return fechaOtorgDate;
        }

        //si tuvo licencia y es menor de 21 => licencia por 3 año
        if(anios<=21 && !(retornoBD.isEmpty())){
            fechaOtorgamiento=fechaOtorgamiento.plusYears(3);
            System.out.println("FECHA FINAL DEVUELTA "+fechaOtorgamiento);
            Date fechaOtorgDate = new Date(fechaOtorgamiento.getYear(),fechaOtorgamiento.getMonthValue(), fechaOtorgamiento.getDayOfMonth());
            return fechaOtorgDate;
        }

        //entre 21 y 46=> licencia por 5 años
        if(anios>21 && anios<46){
            fechaOtorgamiento=fechaOtorgamiento.plusYears(5);
            System.out.println("FECHA FINAL DEVUELTA "+fechaOtorgamiento);
            Date fechaOtorgDate = new Date(fechaOtorgamiento.getYear(),fechaOtorgamiento.getMonthValue(), fechaOtorgamiento.getDayOfMonth());
            return fechaOtorgDate;
        }
        //entre 46 y 60 => licencia por 4 años
        if(anios>=46 && anios<60){
            fechaOtorgamiento=fechaOtorgamiento.plusYears(4);
            System.out.println("FECHA FINAL DEVUELTA "+fechaOtorgamiento);
            Date fechaOtorgDate = new Date(fechaOtorgamiento.getYear(),fechaOtorgamiento.getMonthValue(), fechaOtorgamiento.getDayOfMonth());
            return fechaOtorgDate;
        }
        //entre 60 y 70 => licencia por 1 año
        if(anios>=60 && anios<70){
            fechaOtorgamiento=fechaOtorgamiento.plusYears(1);
            System.out.println("FECHA FINAL DEVUELTA "+fechaOtorgamiento);
            Date fechaOtorgDate = new Date(fechaOtorgamiento.getYear(),fechaOtorgamiento.getMonthValue(), fechaOtorgamiento.getDayOfMonth());
            return fechaOtorgDate;
        }

        return fechaCumpleanios; //hubo un error
    }



  /*  public static void main(String[] args) throws SQLException, ParseException {

        Calendar calendar = new GregorianCalendar();
        calendar.set(1999, 12,10);
        Date fechaCumpl = calendar.getTime();

        Date resultado = calcularVigencia(fechaCumpl,"40258746","5");
       // System.out.println("fecha devuelta: "+resultado);

    }
*/

    public static String buscarLicencia(String dniTitular, String idTitular) throws SQLException {
        String retornoBD = "";
        ConectarBD conexion =new ConectarBD();
        Statement stmt = conexion.getStmt();
        String SQL = "SELECT * FROM Licencia " +
                "WHERE (numeroDeLicencia="+"'"+dniTitular+"'"+
                "AND titular="+idTitular+")";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("numeroDeLicencia")+","+rs.getString("titular");
        }

        conexion.getCon().close();
        return retornoBD;
    }


}
