package src.logica;

import src.clases.*;
import src.bd.EmitirLicenciaBD;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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

    public static Titular getTitularByIdLicencia(String idLicencia) throws SQLException {
        return EmitirLicenciaBD.getTitularByIdLicencia(idLicencia);
    }

    public static String getTipoClaseByIdLicencia(String idLicencia) throws SQLException {
        return EmitirLicenciaBD.getTipoClaseByIdLicencia(idLicencia);
    }

    public static Titular buscarTitularAll(String nroDoc, String tipoDoc) throws SQLException {
        return EmitirLicenciaBD.buscarTitularAll(nroDoc,tipoDoc);
    }

    public static int getIdTitular(String nroDoc, String tipoDoc) throws SQLException{
        return EmitirLicenciaBD.getIdTitularBD(nroDoc,tipoDoc);
    }

    public static Licencia getLicenciaByIdLicencia(String idLicencia) throws SQLException{
        return EmitirLicenciaBD.getLicenciaByIdLicencia(idLicencia);
    }

    public static int renovarLicencia(Licencia lic, Clase cla, Titular titular) throws SQLException {
        return EmitirLicenciaBD.renovarLicencia(lic, cla, titular);
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

    public static int emitirLicencia(Licencia lic, Clase cla, boolean donante) throws SQLException {
        return emitirLicenciaBD(lic,cla,donante);
    }

}
