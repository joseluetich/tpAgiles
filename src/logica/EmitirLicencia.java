package src.logica;

import src.clases.*;
import src.bd.EmitirLicenciaBD;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static src.bd.EmitirLicenciaBD.*;

public class EmitirLicencia {

    public static String getIdLicencia() throws SQLException {
        String nroLicenciaBD = EmitirLicenciaBD.getIdLicenciaBD();
        int nroLicencia = Integer.parseInt(nroLicenciaBD)+1;
        return String.format("%06d" , nroLicencia);
    }

    public static String buscarTitular(String nroDoc, String tipoDoc) throws SQLException {
        return EmitirLicenciaBD.buscarTitularBD(nroDoc,tipoDoc);
    }

    public static Titular buscarTitularAll(String nroDoc, String tipoDoc) throws SQLException {
        return EmitirLicenciaBD.buscarTitularAll(nroDoc,tipoDoc);
    }

    public static int getIdTitular(String nroDoc, String tipoDoc) throws SQLException{
        return EmitirLicenciaBD.getIdTitularBD(nroDoc,tipoDoc);
    }

    public static String getFechaOtorgamiento(String nroDoc, String clase) throws SQLException {
        return getFechaOtorgamientoBD(nroDoc,clase);
    }

    public static ArrayList<String> getClaseByTitular(String nroDoc, String tipoDoc) throws SQLException{
        return EmitirLicenciaBD.getClaseByTitularBD(nroDoc,tipoDoc);
    }

    public static int calcularEdad(String fechaNacimiento){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);
        return periodo.getYears();
    }

    public static void emitirLicencia(Licencia lic, Clase cla, boolean donante) throws SQLException {
        emitirLicenciaBD(lic,cla,donante);
    }

}
