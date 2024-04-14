package conexion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlcanciaCRUD {

    private   ConexionMysql conexion = new ConexionMysql();

    public  void createRegistro(String nombre,
                               int moneda0050,
                               int moneda0100,
                               int moneda0200,
                               int moneda0500,
                               int moneda1000) {
        String query = String.format("INSERT INTO alcancia " +
                        "(nombre, " + "moneda_0050, moneda_0100, moneda_0200, moneda_0500, moneda_1000) " +
                        "VALUES ('%s', %d, %d, %d, %d, %d)",
                nombre, moneda0050, moneda0100, moneda0200, moneda0500, moneda1000);

        int rowsAffected = conexion.ejecutarActualizacion(query);
        if (rowsAffected > 0) {
            System.out.println("Registro creado exitosamente.");
        } else {
            System.out.println("Error al crear el registro.");
        }
    }

    public  void updateRegistro(String nombre, int moneda0050, int moneda0100,
                                int moneda0200, int moneda0500, int moneda1000) {
        String query = String.format("UPDATE alcancia " +
                        "SET moneda_0050 = %d, moneda_0100 = %d, " +
                        "moneda_0200 = %d, moneda_0500 = %d, moneda_1000 = %d WHERE nombre = '%s'",
                moneda0050, moneda0100, moneda0200, moneda0500, moneda1000, nombre);

        int rowsAffected = conexion.ejecutarActualizacion(query);
        if (rowsAffected > 0) {
            System.out.println("Registro actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar el registro.");
        }
    }


    public ArrayList<Object> devuelveRegistro(String nombre) {
        ArrayList<Object> listDatos = new ArrayList<>();
        String query = "SELECT * FROM alcancia WHERE nombre = '" + nombre + "'";

        try (ResultSet resultSet = conexion.ejecutarConsulta(query)) {
            while (resultSet.next()) {
                listDatos.add(resultSet.getInt("id"));
                listDatos.add(resultSet.getString("nombre"));
                listDatos.add(resultSet.getInt("moneda_0050"));
                listDatos.add(resultSet.getInt("moneda_0100"));
                listDatos.add(resultSet.getInt("moneda_0200"));
                listDatos.add(resultSet.getInt("moneda_0500"));
                listDatos.add(resultSet.getInt("moneda_1000"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listDatos;
    }

    public  void deleteRegistro(String nombre) {
        String query = "DELETE FROM alcancia WHERE nombre = '" + nombre +"'";

        int rowsAffected = conexion.ejecutarActualizacion(query);
        if (rowsAffected > 0) {
            System.out.println("Registro eliminado exitosamente.");
        } else {
            System.out.println("Error al eliminar el registro.");
        }
    }
}

