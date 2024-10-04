package com.example.grupo1_tp4.conexion;

import android.content.Context;
import android.util.Log;

import com.example.grupo1_tp4.entidad.Categoria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataCategoriaMainActivity {
    private Object object;
    private Context context;

    public DataCategoriaMainActivity(Object object, Context context) {
        this.context = context;
    }

    public void insertar(Categoria categoria) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                String sql = "INSERT INTO " + DataDB.TABLE_CATEGORIA + "(" + DataDB.COL_CATEGORIA_DESCRIPCION + ")" + " VALUES ('" + categoria.getDescripcion() + "')";
                Log.d("SENTENCIASQL", sql);
                Class.forName(DataDB.driver);
                Connection connection = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                Statement statement = connection.createStatement();
                /*statement.executeUpdate("INSERT INTO " + DataDB.TABLE_CATEGORIA + "(" + DataDB.COL_CATEGORIA_DESCRIPCION + ")" + " VALUES ('" + categoria.getDescripcion() + "')");*/
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
            });
        });
    }

    public void obtenerPorId(Integer id) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Categoria categoria = new Categoria();
            try {
                String sql = "SELECT * FROM " + DataDB.TABLE_CATEGORIA + " WHERE " + DataDB.COL_CATEGORIA_ID + " = " + id;
                Log.d("SENTENCIASQL", sql);
                Class.forName(DataDB.driver);
                Connection connection = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                resultSet.next();
                categoria.setId(resultSet.getInt("id"));
                categoria.setDescripcion(resultSet.getString("descripcion"));
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
                Log.d("SENTENCIASQL", "CATEGORIA: " + categoria.toString());
            });
        });
    }

    public void obtenerTodos() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Categoria> listaCategorias = new ArrayList<>();
            try {
                String sql = "SELECT * FROM " + DataDB.TABLE_CATEGORIA;
                Log.d("SENTENCIASQL", sql);
                Class.forName(DataDB.driver);
                Log.d("SENTENCIASQL", DataDB.urlMySQL);
                Connection connection = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                while (resultSet.next()) {
                    Categoria categoria = new Categoria();
                    categoria.setId(resultSet.getInt("id"));
                    categoria.setDescripcion(resultSet.getString("descripcion"));
                    listaCategorias.add(categoria);
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
                Log.d("SENTENCIASQL", "CATEGORIAS: " + listaCategorias.size());
            });

        });
    }

    public void actualizarPorId(Categoria categoria) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                String sql = "UPDATE " + DataDB.TABLE_CATEGORIA + " SET " + DataDB.COL_CATEGORIA_DESCRIPCION + " = '" + categoria.getDescripcion() + "' WHERE " + DataDB.COL_CATEGORIA_ID + " = " + categoria.getId() + ";";
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
                Log.d("SENTENCIASQL", "CATEGORIA ACTUALIZADA");
            });
        });
    }
}
