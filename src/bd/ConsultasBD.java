package src.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConsultasBD{

    public static String buscarTitularBD(String nroDoc, String tipoDoc) throws SQLException {
        String retornoBD = "";
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT * FROM Titular " +
                "WHERE (tipoDeDocumento="+"'"+tipoDoc+"'"+
                "AND numeroDeDocumento="+nroDoc+")";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("nombre")+","+rs.getString("apellido")+","+rs.getString("cuil")+","+rs.getString("direccion")+","+rs.getString("fechaDeNacimiento")+","+rs.getString("grupoSanguineo")+","+rs.getString("donante")+","+rs.getString("codigoPostal");
        }

        conectar.getCon().close();
        return retornoBD;
    }

    public static String getIdLicenciaBD() throws SQLException {
        String retornoBD = "";
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT * FROM Licencia " +
                "ORDER BY idLicencia " +
                "DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("idLicencia");
        }
        conectar.getCon().close();
        return retornoBD;
    }

    public static ArrayList<String> getLicenciasVigentes() throws SQLException {
        ArrayList<String> retornoBD = new ArrayList<String>();
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT * FROM Licencia " +
                "WHERE (enVigencia = 1)";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD.add(rs.getString("idLicencia")+","+ rs.getString("titular") + "," + rs.getString("numeroDeLicencia"));
        }
        conectar.getCon().close();
        return retornoBD;
    }

    public static String getTitularByID(String idTitular) throws SQLException {
        String retornoBD = "";
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT * FROM Titular " +
                "WHERE (idTitular = "+ idTitular + ")";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("tipoDeDocumento") + "," + rs.getString("numeroDeDocumento") + "," + rs.getString("apellido") + "," + rs.getString("nombre");
        }
        conectar.getCon().close();
        return retornoBD;
    }

    public static ArrayList<String> getClaseByID(String idLicencia) throws SQLException {
        ArrayList<String> retornoBD = new ArrayList<String>();
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT * FROM Clase " +
                "WHERE (idLicencia = "+ idLicencia + ")";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD.add(rs.getString("tipo"));
        }
        conectar.getCon().close();
        return retornoBD;
    }

    public static ArrayList<String> getLicenciasTitular(String idTitular) throws SQLException {
        ArrayList<String> retornoBD = new ArrayList<String>();
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT * FROM Licencia" +
                "WHERE (titular = "+ idTitular + ")";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD.add(rs.getString("idLicencia")+","+ rs.getString("titular") + "," + rs.getString("numeroDeLicencia"));
        }
        conectar.getCon().close();
        return retornoBD;
    }

}