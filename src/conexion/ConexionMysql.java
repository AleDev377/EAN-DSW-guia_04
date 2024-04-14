package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConexionMysql {

    private Connection connection;

    private String db_user = "root";
    private String db_pass = "";
    private String server = "localhost";
    private String port = "3306";
    private String db_name = "alcancia_db";
    private String url = "";
    private String timeZone = "?serverTimezone=UTC";
    private String driver = "com.mysql.cj.jdbc.Driver";

    public ConexionMysql() {

        this.url = "jdbc:mysql://" + server + ":" + port + "/" + db_name + timeZone;
    }

    private boolean conectar() {
        desconectar();
        try {
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url, db_user, db_pass);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean desconectar() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet ejecutarConsulta(String query) {
        ResultSet resultSet = null;
        conectar();
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public int ejecutarActualizacion(String query) {
        int rowsAffected = 0;
        conectar();
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        desconectar();
        return rowsAffected;
    }
}
