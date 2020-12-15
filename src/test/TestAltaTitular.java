package src.test;

import org.junit.Test;
import src.bd.DarDeAltaTitularBD;
import src.bd.EmitirCopiaDB;
import src.clases.Titular;
import src.clases.tipoDeDocumento;
import src.interfaces.DarDeAltaTitular;

import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class TestAltaTitular {

    @Test
    public void testEmitirTitular() throws SQLException {

        Titular titular = new Titular();
        tipoDeDocumento tipo;
        Date fechaNacimientoTitular = new GregorianCalendar(1997,07,17).getTime();

        titular.setTipoDoc(tipoDeDocumento.DNI);
        titular.setNumeroDeDocumento("40115993");
        titular.setNombre("Diego");
        titular.setApellido("Acosta");
        titular.setCuil("20401159933");
        titular.setGrupoSanguineo("A+");
        titular.setDireccion("Aguado 6933");
        titular.setCodigoPostal("3000");
        titular.setDonante(true);
        titular.setFechaDeNacimiento(fechaNacimientoTitular);

        DarDeAltaTitularBD.darDeAltaTitular(titular);

        int id = DarDeAltaTitularBD.getIdTitularBD_int();
        String titularBase = EmitirCopiaDB.getTitularByID(String.valueOf(id));

        String titularBase_datos[] = titularBase.split(",");

        assertEquals(titular.getTipoDoc().toString(), titularBase_datos[0]);
        assertEquals(titular.getNumeroDeDocumento(), titularBase_datos[1]);
        assertEquals(titular.getApellido(), titularBase_datos[2]);
        assertEquals(titular.getNombre(), titularBase_datos[3]);

    }

}
