package src.bd;

import src.clases.CopiaLicencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EmitirCopiaDB {

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

    public static String getIdCopiaLicenciaBD() throws SQLException {
        String retornoBD = "";
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT * FROM CopiaLicencia " +
                "ORDER BY idCopiaLicencia " +
                "DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("idCopiaLicencia");
        }
        conectar.getCon().close();
        return retornoBD;
    }

    public static void insertCopiaLicencia(CopiaLicencia copiaLicencia, String idLicencia, String fechaEmision) throws SQLException {
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        stmt.executeUpdate("INSERT INTO CopiaLicencia " +
                "VALUES ('"+copiaLicencia.getIdCopiaLicencia()+"','"+copiaLicencia.getNumeroDeCopia()+"','" +
                fechaEmision+"','"+ copiaLicencia.getMotivos().toString()+"','"+idLicencia+"')");
        System.out.println("insertada");
        /*ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("idCopiaLicencia");
        }*/
        conectar.getCon().close();
    }

    public static String getIdLicencia(String numDoc, String claseSolicitada) throws SQLException {
        String retornoBD = "";
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT l.idLicencia "+
        "FROM Clase c, Licencia l "+
        "WHERE l.numeroDeLicencia="+numDoc+
        " AND l.idLicencia = c.idLicencia"+
        " AND c.tipo = '"+claseSolicitada+"'";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("l.idLicencia");
        }
        conectar.getCon().close();
        return retornoBD;
    }

    public static String getNumCopiasDeLicencia(String idLicencia) throws SQLException {
        String retornoBD = "";
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT COUNT(idLicencia) "+
                "FROM CopiaLicencia " +
                "WHERE idLicencia=" + idLicencia;
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("COUNT(idLicencia)");
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
        String SQL = "SELECT * FROM Licencia " +
                "WHERE (titular = "+ idTitular + ")";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD.add(rs.getString("idLicencia")+","+ rs.getString("titular") + "," + rs.getString("numeroDeLicencia"));
        }
        conectar.getCon().close();
        return retornoBD;
    }

}