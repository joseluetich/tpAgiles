package src.bd;

import src.interfaces.ColumnasTabla;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RenovarLicenciaBD {

    public static ArrayList<String> getLicencias() throws SQLException {
        ArrayList<String> retornoBD = new ArrayList<String>();
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT idLicencia, titular, numeroDeLicencia FROM Licencia ";
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
        String SQL = "SELECT l.idLicencia, l.fechaDeVencimiento"+
                " FROM Clase c, Licencia l "+
                " WHERE l.numeroDeLicencia="+numLicencia+
                " AND l.idLicencia = c.idLicencia"+
                " AND c.tipo = '"+claseLicencia+"'";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("l.idLicencia")+","+ rs.getString("l.fechaDeVencimiento");
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
                " AND l.idLicencia = c.idLicencia";

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
