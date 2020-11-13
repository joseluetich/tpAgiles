package logica;

import bd.EmitirLicenciaBD;
import clases.Clase;
import clases.Licencia;
import clases.Titular;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import java.time.Period;
import java.time.format.DateTimeFormatter;

import static bd.EmitirLicenciaBD.emitirLicenciaBD;

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

    public static ArrayList<String> getClaseByTitular(String nroDoc, String tipoDoc) throws SQLException{
        return EmitirLicenciaBD.getClaseByTitularBD(nroDoc,tipoDoc);
    }

    public static Date calcularVigencia(Date fechaNac, String nroDoc, String tipDoc){
        return null;
    }

    public static boolean validarEmisionPorClase(String nroDoc, String tipoDoc, String clase) throws SQLException {
        return getClaseByTitular(nroDoc, tipoDoc).contains(clase);
    }

    public static int calcularEdad(String fechaNacimiento){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);
        return periodo.getYears();
    }

    public static void emitirLicencia(Licencia lic, Clase cla) throws SQLException {
        emitirLicenciaBD(lic,cla);
    }

}
