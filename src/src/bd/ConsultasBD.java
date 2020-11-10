package bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import static java.sql.JDBCType.NULL;

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

    public static int getIdTitularBD(String nroDoc, String tipoDoc) throws SQLException {
        int retornoBD = 0;
        ConectarBD conexion = new ConectarBD();
        Statement stmt = (conexion.getStmt());
        String SQL = "SELECT * FROM Titular " +
                "WHERE (tipoDeDocumento="+"'"+tipoDoc+"'"+
                "AND numeroDeDocumento="+nroDoc+")";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getInt("idTitular");
        }
        conexion.getCon().close();
        return retornoBD;
    }

    public static String getIdLicenciaBD() throws SQLException {
        ConectarBD conectar = new ConectarBD();
        String retornoBD = "";
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

    public static int getIdLicenciaBD_int() throws SQLException {
        int retornoBD = 0;
        Statement stmt = (new ConectarBD()).getStmt();
        String SQL = "SELECT * FROM Licencia " +
                "ORDER BY idLicencia " +
                "DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getInt("idLicencia");
        }
        return retornoBD;
    }

    public static void emitirLicenciaBD(Integer numeroDeLicencia, String tipo, String fechaDeModificacion, String fechaDeOtorgamiento, String fechaDeVencimiento, boolean enVigencia, double costo, String observaciones, Integer idTitular) throws SQLException {
        ConectarBD conectar = new ConectarBD();
        int idLicencia = getIdLicenciaBD_int()+1;
        Statement stmt = conectar.getStmt();
        //Relación licencia con su clase (idLicencia FK en clase) INSERTAR EN CLASE LOS ATRIBUTOS
        //Relación licencia con el atributo enVigencia (quitar enVigencia de la anterior)
        String SQL = "INSERT INTO " +
                "Licencia(idLicencia, numeroDeLicencia, tipo, fechaDeModificacion, fechaDeOtorgamiento, fechaDeVencimiento, enVigencia, costo, observaciones, titular) " +
                "VALUES ("+idLicencia+", "+numeroDeLicencia+", "+"'"+tipo+"'"+", "+"'"+fechaDeModificacion+"'"+", "+"'"+fechaDeOtorgamiento+"'"+", "+"'"+fechaDeVencimiento+"'"+", "+enVigencia+", "+costo+", "+"'"+observaciones+"'"+", "+idTitular+ ") ";
        stmt.execute(SQL);
        conectar.getCon().close();
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
