package src.bd;
import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConectarBD {

    private Statement stmt;
    private Connection con;

    public ConectarBD() throws SQLException {
        ConexionDefault conectar = new ConexionDefault();
        con = conectar.openConnection();
        stmt = con.createStatement();
    }

    public Statement getStmt() {
        return stmt;
    }

    public Connection getCon() {
        return con;
    }

}
