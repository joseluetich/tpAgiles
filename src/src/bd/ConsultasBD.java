package bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultasBD {

    public static String buscarTitularBD(String nroDoc, String tipoDoc) throws SQLException {
        String retornoBD = "";
        Statement stmt = (new ConectarBD()).getStmt();
        String SQL = "SELECT * FROM Titular " +
                "WHERE (tipoDeDocumento="+"'"+tipoDoc+"'"+
                "AND numeroDeDocumento="+nroDoc+")";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("nombre")+","+rs.getString("apellido")+","+rs.getString("cuil")+","+rs.getString("direccion")+","+rs.getString("fechaDeNacimiento")+","+rs.getString("grupoSanguineo")+","+rs.getString("donante")+","+rs.getString("codigoPostal");
        }
        return retornoBD;
    }

    public static String getIdLicenciaBD() throws SQLException {
        String retornoBD = "";
        Statement stmt = (new ConectarBD()).getStmt();
        String SQL = "SELECT * FROM Licencia " +
                "ORDER BY idLicencia " +
                "DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("idLicencia");
        }
        return retornoBD;
    }
}