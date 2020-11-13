package bd;

import clases.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EmitirLicenciaBD {

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

    public static void insertClase(Clase cla, int idLicencia) throws SQLException {
        ConectarBD conectar = new ConectarBD();
        int idClase = getIdClaseBD_int()+1;
        Statement stmt = conectar.getStmt();

        int edadMinima = cla.getEdadMinima();
        tipoClase clase = cla.getTipo();
        int idLicenciaFK = idLicencia;

        String SQLClase = "INSERT INTO " +
                "Clase(idClase, edadMinima, tipo, idLicencia) " +
                "VALUES ("+idClase+", "+edadMinima+", "+"'"+clase+"'"+", "+idLicenciaFK+") ";
        stmt.execute(SQLClase);

        conectar.getCon().close();
    }

    public static void updateVigenciaLicenciaTitular(Integer nroLicencia) throws SQLException {
        ConectarBD conexion = new ConectarBD();
        Statement stmt = (conexion.getStmt());
        String SQLUpdate = "UPDATE Licencia SET enVigencia = 0 WHERE numeroDeLicencia = "+nroLicencia+"";
        stmt.execute(SQLUpdate);
        conexion.getCon().close();
    }

    public static String getIdLicenciaBD() throws SQLException {
        ConectarBD conectar = new ConectarBD();
        String retornoBD = "";
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT * FROM Licencia " +
                    "ORDER BY convert(idLicencia, decimal) " +
                    "DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()){
            retornoBD = rs.getString("idLicencia");
        }
        conectar.getCon().close();
        return retornoBD;
    }

    public static int getIdClaseBD_int() throws SQLException {
        int retornoBD = 0;
        Statement stmt = (new ConectarBD()).getStmt();
        String SQL = "SELECT * FROM Clase " +
                "ORDER BY idClase " +
                "DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getInt("idClase");
        }
        return retornoBD;
    }

    public static int getIdLicenciaBD_int() throws SQLException {
        int retornoBD = 0;
        Statement stmt = (new ConectarBD()).getStmt();
        String SQL = "SELECT * FROM Licencia " +
                "ORDER BY convert(idLicencia, decimal) " +
                "DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getInt("idLicencia");
        }
        return retornoBD;
    }

    public static void emitirLicenciaBD(Licencia lic, Clase cla) throws SQLException {
        ConectarBD conectar = new ConectarBD();
        int idLicencia = getIdLicenciaBD_int()+1;
        Statement stmt = conectar.getStmt();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Integer numeroDeLicencia = lic.getNumeroDeLicencia();
        tipoLicencia tipo = lic.getTipoLicencia();
        Date fechaDeModificacion = lic.getFechaDeModificacion();
        Date fechaDeOtorgamiento = lic.getFechaDeOtorgamiento();
        Date fechaDeVencimiento = lic.getFechaDeVencimiento();
        int enVigencia = lic.getEnVigencia();
        double costo = lic.getCosto();
        String observaciones = lic.getObservaciones();
        Titular titularLic = lic.getTitular();
        Integer idTitular = titularLic.getIdTitular();

        String fechaDeModificacion_string = sdf.format(fechaDeModificacion);
        String fechaDeOtorgamiento_string = sdf.format(fechaDeOtorgamiento);
        String fechaDeVencimiento_string = sdf.format(fechaDeVencimiento);

        updateVigenciaLicenciaTitular(numeroDeLicencia);

        String SQLLicencia = "INSERT INTO " +
                "Licencia(idLicencia, numeroDeLicencia, tipo, fechaDeModificacion, fechaDeOtorgamiento, fechaDeVencimiento, enVigencia, costo, observaciones, titular) " +
                "VALUES ("+idLicencia+", "+numeroDeLicencia+", "+"'"+tipo+"'"+", "+"'"+fechaDeModificacion_string+"'"+", "+"'"+fechaDeOtorgamiento_string+"'"+", "+"'"+fechaDeVencimiento_string+"'"+", "+enVigencia+", "+costo+", "+"'"+observaciones+"'"+", "+idTitular+ ") ";
        stmt.execute(SQLLicencia);

        insertClase(cla,idLicencia);

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

    public static Titular buscarTitularAll(String nroDoc, String tipoDoc) throws SQLException {
        Titular titularBD = new Titular();
        ConectarBD conexion = new ConectarBD();
        Statement stmt = (conexion.getStmt());
        String SQL = "SELECT * FROM Titular " +
                "WHERE (tipoDeDocumento="+"'"+tipoDoc+"'"+
                "AND numeroDeDocumento="+nroDoc+")";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            titularBD.setNombre(rs.getString("nombre"));
            titularBD.setApellido(rs.getString("apellido"));
            titularBD.setIdTitular(rs.getInt("idTitular"));
            titularBD.setDireccion(rs.getString("direccion"));
            titularBD.setFechaDeNacimiento(rs.getDate("fechaDeNacimiento"));
            titularBD.setGrupoSanguineo(rs.getString("grupoSanguineo"));
            titularBD.setDonante(rs.getBoolean("donante"));
            titularBD.setCodigoPostal(rs.getString("codigoPostal"));
        }
        conexion.getCon().close();
        return titularBD;
    }

    }
