package logica;

import clases.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class logicaAltaTitular {

    public static String validar(String tipoDoc, String numeroDoc, String apellido, String nombre, String direccion, String clase, Date fechaNac) {
        if(tipoDoc.equals("Seleccionar")) {
            return "errorTipoDoc";
        }
        else if(numeroDoc.length() != 8) {
            return "errorNumeroDoc";
        }
        else if(apellido.isEmpty() || apellido.length() > 50) {
            return "errorApellido";
        }
        else if(nombre.isEmpty() || nombre.length() > 50) {
            return "errorNombre";
        }
        else if(direccion.isEmpty() || direccion.length() > 100) {
            return "errorDireccion";
        }
        else if(clase.equals("Seleccionar")) {
            return "errorClase";
        }
        else if(clase.equals("C") || clase.equals("D") || clase.equals("E")) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaNuevaNac = fechaNac.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate ahora = LocalDate.now();
            Period periodo = Period.between(fechaNuevaNac, ahora);

            if(periodo.getYears() < 21) {
                return "errorEdad21";
            }
            if(periodo.getYears() > 65) {
                return "errorEdad65";
            }
        }
        else if(clase.equals("A") || clase.equals("B") || clase.equals("F") || clase.equals("G")) {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaNuevaNac = fechaNac.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate ahora = LocalDate.now();
            Period periodo = Period.between(fechaNuevaNac, ahora);

            if(periodo.getYears() < 17) {
                return "errorEdad17";
            }
        }
        return "Validado";
    }

    public static void guardarDatos(String tipoDoc, String numeroDoc, String apellido, String nombre, String direccion, String clase, String grupoS, Boolean donante, Date fechaNac) {
        Titular nuevoTitular = new Titular();
        nuevoTitular.setTipoDoc(tipoDeDocumento.valueOf(tipoDoc.toUpperCase()));
        nuevoTitular.setNumeroDeDocumento(numeroDoc);
        nuevoTitular.setApellido(apellido);
        nuevoTitular.setNombre(nombre);
        nuevoTitular.setDireccion(direccion);
        nuevoTitular.setGrupoSanguineo(grupoS);
        nuevoTitular.setDonante(donante);
        nuevoTitular.setFechaDeNacimiento(fechaNac);
    }

}
