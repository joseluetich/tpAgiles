package logica;

import bd.ConsultasBD;
import clases.tipoLicencia;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import static bd.ConsultasBD.emitirLicenciaBD;

public class EmitirLicencia {

    public static String getIdLicencia() throws SQLException {
        String nroLicenciaBD = ConsultasBD.getIdLicenciaBD();
        int nroLicencia = Integer.parseInt(nroLicenciaBD)+1;
        return String.format("%06d" , nroLicencia);
    }

    public static String buscarTitular(String nroDoc, String tipoDoc) throws SQLException {
    return ConsultasBD.buscarTitularBD(nroDoc,tipoDoc);
    }

    public static ArrayList<String> getClaseByTitular(String nroDoc, String tipoDoc) throws SQLException{
        return ConsultasBD.getClaseByTitularBD(nroDoc,tipoDoc);
    }

    public static Date calcularVigencia(Date fechaNac, String nroDoc, String tipDoc){
        return null;
    }

    public static boolean validarEmisionPorClase(String nroDoc, String tipoDoc) throws SQLException {
        return getClaseByTitular(nroDoc, tipoDoc).contains("B");
    }

    public static int calcularEdad(String fechaNacimiento){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaNac = LocalDate.parse(fechaNacimiento, fmt);
        LocalDate ahora = LocalDate.now();

        Period periodo = Period.between(fechaNac, ahora);
        return periodo.getYears();
    }

    public static void emitirLicencia(Integer numeroDeLicencia, tipoLicencia tipo, Date fechaDeModificacion, Date fechaDeOtorgamiento, Date fechaDeVencimiento, boolean enVigencia, double costo, String observaciones, Integer idTitular){
        emitirLicenciaBD();
    }

}
