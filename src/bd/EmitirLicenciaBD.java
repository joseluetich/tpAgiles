package src.bd;

import src.clases.*;

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
        String clase = cla.getTipo().toString();
        int idLicenciaFK = idLicencia;

        String SQLClase = "INSERT INTO " +
                "Clase(idClase, edadMinima, tipo, idLicencia) " +
                "VALUES ("+idClase+", "+edadMinima+", "+"'"+clase+"'"+", "+idLicenciaFK+") ";
        stmt.execute(SQLClase);

        conectar.getCon().close();
    }

    public static void updateVigenciaLicenciaTitular(Integer idLicencia) throws SQLException {
        ConectarBD conexion = new ConectarBD();
        Statement stmt = conexion.getStmt();
        String SQLUpdate = "UPDATE Licencia SET enVigencia = 0 WHERE idLicencia = "+idLicencia+"";
        stmt.execute(SQLUpdate);
        conexion.getCon().close();
    }

    private static void updateVigenciaLicenciaTitularAll(String numeroDeDocumento) throws SQLException {
        ConectarBD conexion = new ConectarBD();
        Statement stmt = conexion.getStmt();
        String SQLUpdate = "UPDATE Licencia SET enVigencia = 0 WHERE numeroDeLicencia = "+numeroDeDocumento+"";
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
        ConectarBD conectar = new ConectarBD();
        int retornoBD = 0;
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT * FROM Clase " +
                "ORDER BY idClase " +
                "DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getInt("idClase");
        }
        conectar.getCon().close();
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
        ArrayList<String> tipoClases = new ArrayList<>();

        Integer numeroDeLicencia = lic.getNumeroDeLicencia();
        tipoLicencia tipo = lic.getTipoLicencia();
        Date fechaDeModificacion = lic.getFechaDeModificacion();
        Date fechaDeOtorgamiento = lic.getFechaDeOtorgamiento();
        Date fechaDeVencimiento = lic.getFechaDeVencimiento();
        String enVigencia = String.valueOf(lic.getEnVigencia());

        double costo = lic.getCosto();
        String observaciones = lic.getObservaciones();
        Titular titularLic = lic.getTitular();
        Integer idTitular = titularLic.getIdTitular();

        for(Clase clases : lic.getClases())
        {
          tipoClases.add(clases.getTipo().toString());
        }

        if (cla.getTipo().toString().equals("F")){
            for(Clase clases : lic.getClases()){
                updateVigenciaLicenciaTitularAll(titularLic.getNumeroDeDocumento());
            }
        }

        if(cla.getTipo().toString().equals("C")){
            if (tipoClases.contains("B")){
                updateVigenciaLicenciaTitular(Integer.parseInt(getIdLicenciaByClase("B", titularLic.getNumeroDeDocumento())));
            }
        }

        if(cla.getTipo().toString().equals("D")){
             if(tipoClases.contains("B")){
                updateVigenciaLicenciaTitular(Integer.parseInt(getIdLicenciaByClase("B", titularLic.getNumeroDeDocumento())));
            }

            if (tipoClases.contains("C")){
                updateVigenciaLicenciaTitular(Integer.parseInt(getIdLicenciaByClase("C", titularLic.getNumeroDeDocumento())));
            }
        }

        if(cla.getTipo().toString().equals("E")){
            if (tipoClases.contains("B")){
                updateVigenciaLicenciaTitular(Integer.parseInt(getIdLicenciaByClase("B", titularLic.getNumeroDeDocumento())));
            }
            if (tipoClases.contains("C")){
                updateVigenciaLicenciaTitular(Integer.parseInt(getIdLicenciaByClase("C", titularLic.getNumeroDeDocumento())));
            }
            if (tipoClases.contains("D")){
                updateVigenciaLicenciaTitular(Integer.parseInt(getIdLicenciaByClase("D", titularLic.getNumeroDeDocumento())));
            }
        }

        String fechaDeModificacion_string = sdf.format(fechaDeModificacion);
        String fechaDeOtorgamiento_string = sdf.format(fechaDeOtorgamiento);
        String fechaDeVencimiento_string = sdf.format(fechaDeVencimiento);

       String SQLLicencia = "INSERT INTO " +
                "Licencia(idLicencia, numeroDeLicencia, tipo, fechaDeModificacion, fechaDeOtorgamiento, fechaDeVencimiento, enVigencia, costo, observaciones, titular) " +
                "VALUES ("+idLicencia+", "+numeroDeLicencia+", "+"'"+tipo+"'"+", "+"'"+fechaDeModificacion_string+"'"+", "+"'"+fechaDeOtorgamiento_string+"'"+", "+"'"+fechaDeVencimiento_string+"'"+", "+enVigencia+", "+costo+", "+"'"+observaciones+"'"+", "+idTitular+ ") ";
        stmt.execute(SQLLicencia);

        insertClase(cla,idLicencia);

        conectar.getCon().close();
    }

    private static String getIdLicenciaByClase(String tipoClase, String nroDoc) throws SQLException {
        String retornoBD = null;
        ConectarBD conexion = new ConectarBD();
        Statement stmt = conexion.getStmt();

        String SQL = "SELECT Licencia.idLicencia " +
                "FROM Licencia " +
                "INNER JOIN Clase on Clase.idLicencia = Licencia.idLicencia " +
                "WHERE Clase.tipo = "+"'"+tipoClase+"'"+" and Licencia.numeroDeLicencia = "+"'"+nroDoc+"'";

        ResultSet rs = stmt.executeQuery(SQL);

        while (rs.next()){
            retornoBD = rs.getString("idLicencia");
        }

        conexion.getCon().close();
        return retornoBD;
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

    public static boolean validarVigenciaClaseBD(String numDoc, String claseSolicitada) throws SQLException {
        boolean retornoBD = false;
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT l.enVigencia "+
                "FROM Clase c, Licencia l "+
                "WHERE l.numeroDeLicencia="+numDoc+
                " AND l.idLicencia = c.idLicencia"+
                " AND l.enVigencia = "+1+
                " AND c.tipo = '"+claseSolicitada+"'";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getBoolean("l.enVigencia");
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
            titularBD.setNumeroDeDocumento(rs.getString("numeroDeDocumento"));
        }
        conexion.getCon().close();
        return titularBD;
    }

    public static String getFechaOtorgamientoBD(String nroDoc, String clase) throws SQLException {
        String retornoBD = null;
        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL = "SELECT l.fechaDeOtorgamiento "+
                "FROM Clase c, Licencia l "+
                "WHERE l.numeroDeLicencia="+nroDoc+
                " AND l.idLicencia = c.idLicencia"+
                " AND c.tipo = '"+clase+"'";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getString("l.fechaDeOtorgamiento");
        }
        conectar.getCon().close();
        return retornoBD;
    }

    }
