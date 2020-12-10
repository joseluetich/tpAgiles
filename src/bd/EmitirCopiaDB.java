package src.bd;

import src.clases.CopiaLicencia;
import src.interfaces.ColumnasTabla;

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
        String SQL = "SELECT idCopiaLicencia FROM CopiaLicencia " +
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
        String SQL = "SELECT idLicencia, titular, numeroDeLicencia FROM Licencia " +
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
        String SQL = "SELECT tipoDeDocumento, numeroDeDocumento, apellido, nombre FROM Titular " +
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
        String SQL = "SELECT tipo FROM Clase " +
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
        String SQL = "SELECT idLicencia, titular, numeroDeLicencia FROM Licencia " +
                "WHERE (titular = "+ idTitular + ")";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD.add(rs.getString("idLicencia")+","+ rs.getString("titular") + "," + rs.getString("numeroDeLicencia"));
        }
        conectar.getCon().close();
        return retornoBD;
    }

    public static String[][] getInformacionTabla() throws SQLException {

        String informacion[][] = new String[100000][6];

        ArrayList<String> retornoBD = new ArrayList<String>();
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT t.apellido, t.nombre, t.tipoDeDocumento, t.numeroDeDocumento, l.numeroDeLicencia, c.tipo" +
                " FROM Licencia l, Titular t, Clase c" +
                " WHERE l.titular = t.idTitular " +
                " AND l.idLicencia = c.idLicencia" +
                " AND l.enVigencia = 1";

        ResultSet rs = stmt.executeQuery(SQL);

        Object[] licencias = new Object[6];
        int fila = 0;
        while (rs.next()){
            licencias[0] = rs.getString(1);
            licencias[1] = rs.getString(2);
            licencias[2] = rs.getString(3);
            licencias[3] = rs.getString(4);
            licencias[4] = rs.getString(5);
            licencias[5] = rs.getString(6);

            informacion[fila][ColumnasTabla.APELLIDO] = (String) licencias[0];
            informacion[fila][ColumnasTabla.NOMBRE] = (String) licencias[1];
            informacion[fila][ColumnasTabla.TIPO_DOCUMENTO] = (String) licencias[2];
            informacion[fila][ColumnasTabla.DOCUMENTO] = (String) licencias[3];
            informacion[fila][ColumnasTabla.NUM_LICENCIA] = (String) licencias[4];
            informacion[fila][ColumnasTabla.CLASE_LICENCIA] = (String) licencias[5];

            fila++;
        }
        stmt = null;
        rs = null;
        conectar.getCon().close();
        String resultado[][] = new String[fila][6];
        for(int i=0; i<fila; i++){
            resultado[i][0] = informacion[i][0];
            resultado[i][1] = informacion[i][1];
            resultado[i][2] = informacion[i][2];
            resultado[i][3] = informacion[i][3];
            resultado[i][4] = informacion[i][4];
            resultado[i][5] = informacion[i][5];
        }
        return resultado;
    }

}