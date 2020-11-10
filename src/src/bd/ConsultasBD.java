package bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ConsultasBD {

    public static String buscarTitularBD(String nroDoc, String tipoDoc) throws SQLException {
        String retornoBD = "";
        ConectarBD conexion = new ConectarBD();
        Statement stmt = (conexion.getStmt());
        String SQL = "SELECT * FROM Titular " +
                "WHERE (tipoDeDocumento="+"'"+tipoDoc+"'"+
                "AND numeroDeDocumento="+nroDoc+")";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("nombre")+","+rs.getString("apellido")+","+rs.getString("cuil")+","+rs.getString("direccion")+","+rs.getString("fechaDeNacimiento")+","+rs.getString("grupoSanguineo")+","+rs.getString("donante")+","+rs.getString("codigoPostal");
        }
        conexion.getCon().close();
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

    public static void emitirLicenciaBD(){

    }

    public static ArrayList<String> getClaseByTitularBD(String nroDoc, String tipoDoc) throws SQLException {
            ArrayList<String> retornoBD = new ArrayList<>();
            ConectarBD conectar = new ConectarBD();
            Statement stmt = conectar.getStmt();
            String SQL = "SELECT Clase.tipo" +
                    " FROM Clase " +
                    "INNER JOIN Licencia ON Clase.idLicencia = Licencia.idLicencia " +
                    "INNER JOIN Titular ON Licencia.titular = Titular.idTitular " +
                    "WHERE Titular.numeroDeDocumento ="+nroDoc+
                    " AND Titular.tipoDeDocumento ="+"'"+tipoDoc+"'";
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()){
                retornoBD.add(rs.getString("tipo"));
            }
            conectar.getCon().close();
            return retornoBD;
        }
    }
