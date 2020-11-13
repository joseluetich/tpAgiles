package bd;
import clases.Titular;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
public class darDeAltaTitularBD {

    public static void darDeAltaTitular(Titular titular) throws SQLException {
        ConectarBD conectar = new ConectarBD();
        int idTitular = getIdTitularBD_int()+1;
        Statement stmt = conectar.getStmt();

        titular.setIdTitular(idTitular);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaNac_string = sdf.format(titular.getFechaDeNacimiento());

        int donante = 0;
        if(titular.getDonante()) {
            donante = 1;
        }

        String SQLLicencia = "INSERT INTO " +
                "Titular(idTitular, tipoDeDocumento, numeroDeDocumento, apellido, nombre, cuil, fechaDeNacimiento, direccion, codigoPostal, donante, grupoSanguineo)" +
                "VALUES ("+"'"+titular.getIdTitular()+"'"+", "+
                            "'"+titular.getTipoDoc()+"'"+", "+
                            "'"+titular.getNumeroDeDocumento()+"'"+", "+
                            "'"+titular.getApellido()+"'"+", "+
                            "'"+titular.getNombre()+"'"+", "+
                            "'"+"00"+"'" + ", "+
                            "'"+fechaNac_string+"'"+", "+
                            "'"+titular.getDireccion()+"'"+", " +
                            "'"+"00"+"'"+", "+
                            "'"+donante+"'"+", "+
                            "'"+titular.getGrupoSanguineo()+"'"+") ";
        stmt.execute(SQLLicencia);


        conectar.getCon().close();
    }

    public static int getIdTitularBD_int() throws SQLException {
        int retornoBD = 0;
        Statement stmt = (new ConectarBD()).getStmt();
        String SQL = "SELECT * FROM Titular " +
                "ORDER BY idTitular " +
                "DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getInt("idTitular");
        }
        return retornoBD;
    }
}
