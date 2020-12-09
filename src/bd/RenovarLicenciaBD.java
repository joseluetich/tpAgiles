package src.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RenovarLicenciaBD {

    public static ArrayList<String> getLicencias() throws SQLException {
        ArrayList<String> retornoBD = new ArrayList<String>();
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT * FROM Licencia ";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD.add(rs.getString("idLicencia")+","+ rs.getString("titular") + "," + rs.getString("numeroDeLicencia"));
        }
        conectar.getCon().close();
        return retornoBD;
    }

    public static String getIdLicencia(String numLicencia, String claseLicencia) throws SQLException {
        String retornoBD = "";
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT *"+
                "FROM Clase c, Licencia l "+
                "WHERE l.numeroDeLicencia="+numLicencia+
                " AND l.idLicencia = c.idLicencia"+
                " AND c.tipo = '"+claseLicencia+"'";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("l.idLicencia")+","+ rs.getString("l.fechaDeVencimiento");
        }
        conectar.getCon().close();
        return retornoBD;
    }

}
