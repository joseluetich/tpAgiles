package logica;

import clases.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import bd.darDeAltaTitularBD;

public class logicaAltaTitular {

    public static String validar(String tipoDoc, String numeroDoc, String apellido, String nombre, String direccion, Date fechaNac) {
        if(tipoDoc.equals("Seleccionar")) {
            return "errorTipoDoc";
        }
        if(numeroDoc.length() != 8) {
            return "errorNumeroDoc";
        }
        if(apellido.isEmpty() || apellido.length() > 50) {
            return "errorApellido";
        }
        if(nombre.isEmpty() || nombre.length() > 50) {
            return "errorNombre";
        }
        if(direccion.isEmpty() || direccion.length() > 100) {
            return "errorDireccion";
        }
        LocalDate fechaNuevaNac = fechaNac.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate ahora = LocalDate.now();
        Period periodo = Period.between(fechaNuevaNac, ahora);

        if(periodo.getYears() < 17) {
            return "errorEdad17";
        }

        return "Validado";
    }

    public static void guardarDatos(String tipoDoc, String numeroDoc, String apellido, String nombre, String direccion, String grupoS, Boolean donante, Date fechaNac) throws SQLException {
        Titular nuevoTitular = new Titular();
        if(tipoDoc == "DNI") {
            nuevoTitular.setTipoDoc(tipoDeDocumento.DNI);
        }
        else if(tipoDoc == "Libreta Cívica") {
            nuevoTitular.setTipoDoc(tipoDeDocumento.LIBRETA_CIVICA);
        }
        else if(tipoDoc == "Libreta de Enrolamiento") {
            nuevoTitular.setTipoDoc(tipoDeDocumento.LIBRETA_ENROLAMIENTO);
        }
        nuevoTitular.setNumeroDeDocumento(numeroDoc);
        nuevoTitular.setApellido(apellido);
        nuevoTitular.setNombre(nombre);
        nuevoTitular.setDireccion(direccion);
        nuevoTitular.setGrupoSanguineo(grupoS);
        nuevoTitular.setDonante(donante);
        nuevoTitular.setFechaDeNacimiento(fechaNac);

        darDeAltaTitularBD.darDeAltaTitular(nuevoTitular);
    }

}
