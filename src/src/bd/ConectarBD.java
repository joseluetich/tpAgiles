package bd;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
