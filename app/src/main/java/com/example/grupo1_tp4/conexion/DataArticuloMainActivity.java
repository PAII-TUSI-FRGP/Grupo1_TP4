package com.example.grupo1_tp4.conexion;

import android.content.Context;
import android.util.Log;

import com.example.grupo1_tp4.entidad.Articulo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataArticuloMainActivity {
    private Object object;
    private Context context;

    public DataArticuloMainActivity(Object object, Context context) {
        this.context = context;
    }

    public void insertar(Articulo articulo) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                String sql = "INSERT INTO " + DataDB.TABLE_ARTICULO + "(" + DataDB.COL_ARTICULO_ID + ", " + DataDB.COL_ARTICULO_NOMBRE + "," + DataDB.COL_ARTICULO_STOCK + "," + DataDB.COL_ARTICULO_ID_CATEGORIA + ")" + " VALUES (" + articulo.getId() + "," + "'" + articulo.getNombre() + "'," + articulo.getStock() + "," + articulo.getIdCategoria() + ")";
                Log.d("SENTENCIASQL", sql);
                Class.forName(DataDB.driver);
                Connection connection = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
                connection.close();
            } catch (Exception e) {
                Log.d("SENTENCIASQL", "ALGO SALIO MAL");
                e.printStackTrace();
            }
            // Actualiza la UI en el hilo principal
            new android.os.Handler(context.getMainLooper()).post(() -> {
                // Actualiza la UI aquí
                Log.d("SENTENCIASQL", "ARTICULO INSERTADO");
            });
        });
    }

    public void obtenerPorId(Integer id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Articulo articulo = new Articulo();
            try {
                String sql = "SELECT * FROM " + DataDB.TABLE_ARTICULO + " WHERE " + DataDB.COL_ARTICULO_ID + " = " + id;
                Log.d("SENTENCIASQL", sql);
                Class.forName(DataDB.driver);
                Connection connection = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                articulo.setId(resultSet.getInt("id"));
                articulo.setNombre(resultSet.getString("nombre"));
                articulo.setStock(resultSet.getInt("stock"));
                articulo.setIdCategoria(resultSet.getInt("idCategoria"));
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                Log.d("SENTENCIASQL", "ALGO SALIO MAL");
                e.printStackTrace();
            }
            // Actualiza la UI en el hilo principal
            new android.os.Handler(context.getMainLooper()).post(() -> {
                // Actualiza la UI aquí
                Log.d("SENTENCIASQL", "ARTICULO: " + articulo.toString());
            });
        });
    }

    public void obtenerTodos() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Articulo> listaArticulos = new ArrayList<>();
            try {
                String sql = "SELECT * FROM " + DataDB.TABLE_ARTICULO;
                Log.d("SENTENCIASQL", sql);
                Class.forName(DataDB.driver);
                Connection connection = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    Articulo articulo = new Articulo();
                    articulo.setId(resultSet.getInt("id"));
                    articulo.setNombre(resultSet.getString("nombre"));
                    articulo.setStock(resultSet.getInt("stock"));
                    articulo.setIdCategoria(resultSet.getInt("idCategoria"));
                    listaArticulos.add(articulo);
                }
                resultSet.close();
                statement.close();
                connection.close();

            } catch (Exception e) {
                Log.d("SENTENCIASQL", "ALGO SALIO MAL");
                e.printStackTrace();
            }
            // Actualiza la UI en el hilo principal
            new android.os.Handler(context.getMainLooper()).post(() -> {
                // Actualiza la UI aquí
                Log.d("SENTENCIASQL", "ARTICULOS: " + listaArticulos.size());
            });

        });
    }

    public void actualizarPorId(Articulo articulo) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                String sql = "UPDATE " + DataDB.TABLE_ARTICULO + " SET " + DataDB.COL_ARTICULO_NOMBRE + " = '" + articulo.getNombre() + "', " + DataDB.COL_ARTICULO_STOCK + " = " + articulo.getStock() + ", " + DataDB.COL_ARTICULO_ID_CATEGORIA + " = " + articulo.getIdCategoria() + " WHERE " + DataDB.COL_ARTICULO_ID + " = " + articulo.getId() + ";";
                Log.d("SENTENCIASQL", sql);
                Class.forName(DataDB.driver);
                Connection connection = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql);
                statement.close();
                connection.close();
            } catch (Exception e) {
                Log.d("SENTENCIASQL", "ALGO SALIO MAL");
            }
            // Actualiza la UI en el hilo principal
            new android.os.Handler(context.getMainLooper()).post(() -> {
                // Actualiza la UI aquí
                Log.d("SENTENCIASQL", "ARTICULO ACTUALIZADO");
            });
        });
    }
}
