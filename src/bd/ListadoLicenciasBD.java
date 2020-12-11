package src.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ListadoLicenciasBD {

    public static ArrayList<String> getLicenciasNoVigentes() throws SQLException {
        ArrayList<String> retornoBD = new ArrayList<String>();
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT * FROM Licencia " +
                "WHERE (enVigencia = 0)";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD.add(rs.getString("idLicencia")+","+ rs.getString("titular") + "," + rs.getString("fechaDeVencimiento"));
        }
        conectar.getCon().close();
        return retornoBD;
    }
}
