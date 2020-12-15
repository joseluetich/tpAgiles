package src.test;

import org.junit.Test;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;
import src.logica.AltaTitular.*;

public class AltaTitularTest {

    @Test
    public void testValidar() {
        String tipoDoc = "DNI";
        String numeroDoc = "41610949";
        String apellido = "Guiter";
        String nombre = "Alejandro";
        String direccion = "Pasaje Ingenieros 22";
        Date fechaNac = new GregorianCalendar(1997,07,17).getTime();

        String tipoDoc_mal = "Seleccionar";
        String numeroDoc_mal = "123";
        String apellido_mal = "ABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDE";
        String nombre_mal = "ABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDE";
        String direccion_mal = "ABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDEABCDE";
        Date fechaNac_mal = new Date();

        String validacion_tipoDoc = src.logica.AltaTitular.validar(tipoDoc_mal, numeroDoc,  apellido, nombre, direccion, fechaNac);
        String validacion_nroDoc = src.logica.AltaTitular.validar(tipoDoc, numeroDoc_mal,  apellido, nombre, direccion, fechaNac);
        String validacion_ap = src.logica.AltaTitular.validar(tipoDoc, numeroDoc,  apellido_mal, nombre, direccion, fechaNac);
        String validacion_nom = src.logica.AltaTitular.validar(tipoDoc, numeroDoc,  apellido, nombre_mal, direccion, fechaNac);
        String validacion_direc = src.logica.AltaTitular.validar(tipoDoc, numeroDoc,  apellido, nombre, direccion_mal, fechaNac);
        String validacion_fecha = src.logica.AltaTitular.validar(tipoDoc, numeroDoc,  apellido, nombre, direccion, fechaNac_mal);
        String validacion = src.logica.AltaTitular.validar(tipoDoc, numeroDoc,  apellido, nombre, direccion, fechaNac);

        assertEquals("errorTipoDoc", validacion_tipoDoc);
        assertEquals("errorNumeroDoc", validacion_nroDoc);
        assertEquals("errorApellido", validacion_ap);
        assertEquals("errorNombre", validacion_nom);
        assertEquals("errorDireccion", validacion_direc);
        assertEquals("errorEdad17", validacion_fecha);
        assertEquals("Validado", validacion);

    }
}