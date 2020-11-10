package logica;

import bd.ConsultasBD;

import java.sql.SQLException;

public class EmitirLicencia {

    public static String getIdLicencia() throws SQLException {
        String nroLicenciaBD = ConsultasBD.getIdLicenciaBD();
        int nroLicencia = Integer.parseInt(nroLicenciaBD)+1;
        return String.format("%06d" , nroLicencia);
    }

    public static String buscarTitular(String nroDoc, String tipoDoc) throws SQLException {
    return ConsultasBD.buscarTitularBD(nroDoc,tipoDoc);
    }

}
