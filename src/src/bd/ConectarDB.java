package bd;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TestBD {

    public static void main(String[] args) throws SQLException {
        ConexionDefault conectar = new ConexionDefault();
        Connection conexion = conectar.openConnection();
        Statement stmt = conexion.createStatement();
        String SQL = "SELECT * FROM Clase";
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()) {
            System.out.println("ID CLASE: "+rs.getString("idClase") + " EDAD: " + rs.getString("edadMinima") + " TIPO: " +rs.getString("tipo")+" ID LIC: "+rs.getString("idLicencia")  );
        }
    }
}
