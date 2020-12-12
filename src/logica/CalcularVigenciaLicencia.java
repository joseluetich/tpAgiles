package src.logica;

import src.bd.ConectarBD;
import src.clases.tipoDeDocumento;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.time.*;


public class CalcularVigenciaLicencia {


    public static Date calcularVigencia( Date fechaCumpleanios, String dniTitular, String idTitular) throws SQLException, ParseException {
        ZoneId defaultZoneId = ZoneId.systemDefault();

        //Los meses en date empiezan en 1 y en time en 0
        fechaCumpleanios.setMonth(fechaCumpleanios.getMonth());
        //convierto de Date a Time
        LocalDate fechaConvertida = fechaCumpleanios.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaCumple = LocalDate.of(fechaConvertida.getYear(),fechaConvertida.getMonth(),fechaConvertida.getDayOfMonth());
        LocalDate fechaActual = LocalDate.now();

        LocalDate fechaOtorgamiento = LocalDate.of(fechaActual.getYear(),fechaConvertida.getMonth(),fechaConvertida.getDayOfMonth());
        int anios= (fechaActual.getYear()-fechaCumple.getYear());
        int mes = (fechaCumple.getMonthValue()- fechaActual.getMonthValue());
        int dias= (fechaCumple.getDayOfMonth()-fechaActual.getDayOfMonth());
        String retornoBD = buscarLicencia(dniTitular,idTitular);

        if(anios<17) {
            return fechaCumpleanios;
        }
        if(anios>=17 && mes>0 && anios <21){
            return fechaCumpleanios;
        }

        //si no tuvo licencia y es menor a 21 => licencia por 1 años
        if(anios<=21 && retornoBD.isEmpty()){
            fechaOtorgamiento=fechaOtorgamiento.plusYears(1);
            Date date = Date.from(fechaOtorgamiento.atStartOfDay(defaultZoneId).toInstant());
            return date;
        }

        //si tuvo licencia y es menor de 21 => licencia por 3 año
        if(anios<=21 && !(retornoBD.isEmpty())){
            fechaOtorgamiento=fechaOtorgamiento.plusYears(3);
            Date date = Date.from(fechaOtorgamiento.atStartOfDay(defaultZoneId).toInstant());
            return date;
        }

        //entre 21 y 46=> licencia por 5 años
        if(anios>21 && anios<46){
            fechaOtorgamiento=fechaOtorgamiento.plusYears(5);
            Date date = Date.from(fechaOtorgamiento.atStartOfDay(defaultZoneId).toInstant());
            return date;
        }
        //entre 46 y 60 => licencia por 4 años
        if(anios>=46 && anios<60){
            fechaOtorgamiento=fechaOtorgamiento.plusYears(4);
            Date date = Date.from(fechaOtorgamiento.atStartOfDay(defaultZoneId).toInstant());
            return date;
        }
        //entre 60 y 70 => licencia por 1 año
        if(anios>=60 && anios<70){
            fechaOtorgamiento=fechaOtorgamiento.plusYears(1);
            Date date = Date.from(fechaOtorgamiento.atStartOfDay(defaultZoneId).toInstant());
            return date;
        }

        return fechaCumpleanios; //hubo un error
    }

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
