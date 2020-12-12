package src.bd;
import src.clases.Licencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmitirComprobanteBD {

    public static void insertarComprobanteDePago(Licencia lic, String concepto, String importeBruto, String importeNeto, String idLicencia) throws SQLException {
        int idComprobante = getIdComprobanteBD_int()+1;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date fechaDeOtorgamiento = lic.getFechaDeOtorgamiento();
        Date fechaDeVencimiento = lic.getFechaDeVencimiento();

        String fechaDeOtorgamiento_string = sdf.format(fechaDeOtorgamiento);
        String fechaDeVencimiento_string = sdf.format(fechaDeVencimiento);

        ConectarBD conectar = new ConectarBD();
        Statement stmt = conectar.getStmt();
        String SQL =  "INSERT INTO ComprobanteDePago " +
                "(idComprobante, numeroDeFactura, fechaEmision, fechaVencimiento, descripcionElementos, cantidadDescripcion, importeNeto, importeBruto, idLicencia) " +
                "VALUES ("+idComprobante+", "+"'"+idComprobante+"'"+", "+"'"+fechaDeOtorgamiento_string+"'"+", "+"'"+fechaDeVencimiento_string+"'"+", "+"'"+concepto+"'"+", "+"1"+", "+importeNeto+", "+importeBruto+", "+idLicencia+ ")";
        stmt.execute(SQL);
        conectar.getCon().close();
    }

    public static int getIdComprobanteBD_int() throws SQLException {
        int retornoBD = 0;
        Statement stmt = (new ConectarBD()).getStmt();
        String SQL = "SELECT * FROM ComprobanteDePago " +
                "ORDER BY convert(idComprobante, decimal) " +
                "DESC LIMIT 1";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            retornoBD = rs.getInt("idComprobante");
        }
        return retornoBD;
    }

}