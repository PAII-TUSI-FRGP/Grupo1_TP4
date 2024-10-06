package com.example.grupo1_tp4.conexion;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.example.grupo1_tp4.entidad.Articulo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataArticuloMainActivity {
    private Object object;
    private static Context context;

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

    public interface ArticuloCallback {
        void onArticuloObtenido(Articulo articulo);

        void onError(String mensaje);
    }

    public static void obtenerPorId(Integer id, ArticuloCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Articulo articulo = null;  // Inicializar como null
            try {
                // Crear la sentencia SQL
                String sql = "SELECT * FROM " + DataDB.TABLE_ARTICULO + " WHERE " + DataDB.COL_ARTICULO_ID + " = " + id;
                Log.d("SENTENCIASQL", sql);

                // Cargar el driver y conectar con la base de datos
                Class.forName(DataDB.driver);
                Connection connection = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);

                // Procesar el resultado
                if (resultSet.next()) {
                    articulo = new Articulo();
                    articulo.setId(resultSet.getInt("id"));
                    articulo.setNombre(resultSet.getString("nombre"));
                    articulo.setStock(resultSet.getInt("stock"));
                    articulo.setIdCategoria(resultSet.getInt("idCategoria"));
                }

                // Cerrar la conexión
                resultSet.close();
                statement.close();
                connection.close();
            } catch (Exception e) {
                Log.d("SENTENCIASQL", "ALGO SALIO MAL");
                e.printStackTrace();

                // Devolver el error al callback
                new android.os.Handler(Looper.getMainLooper()).post(() -> {
                    callback.onError("Error al obtener el artículo.");
                });
                return;
            }

            // Actualizar la UI en el hilo principal
            Articulo finalArticulo = articulo;
            new android.os.Handler(Looper.getMainLooper()).post(() -> {
                if (finalArticulo != null) {
                    callback.onArticuloObtenido(finalArticulo);  // Enviar el artículo al callback
                } else {
                    callback.onError("Artículo no encontrado.");
                }
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



    // Método para actualizar un artículo en la base de datos
    public static void actualizarPorId(Articulo articulo, ArticuloCallback callback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                // Crear la sentencia SQL de actualización
                String sql = "UPDATE " + DataDB.TABLE_ARTICULO + " SET nombre = ?, stock = ?, idCategoria = ? WHERE " + DataDB.COL_ARTICULO_ID + " = ?";
                Log.d("SENTENCIASQL", sql);

                // Cargar el driver y conectar con la base de datos
                Class.forName(DataDB.driver);
                Connection connection = DriverManager.getConnection(DataDB.urlMySQL, DataDB.user, DataDB.pass);
                PreparedStatement statement = connection.prepareStatement(sql);

                // Asignar valores a la sentencia SQL
                statement.setString(1, articulo.getNombre());
                statement.setInt(2, articulo.getStock());
                statement.setInt(3, articulo.getIdCategoria());
                statement.setInt(4, articulo.getId());

                int rowsAffected = statement.executeUpdate();

                // Cerrar la conexión
                statement.close();
                connection.close();

                // Si la actualización es exitosa
                if (rowsAffected > 0) {
                    new android.os.Handler(Looper.getMainLooper()).post(() -> callback.onArticuloObtenido(articulo));
                } else {
                    new android.os.Handler(Looper.getMainLooper()).post(() -> callback.onError("No se encontró el artículo."));
                }

            } catch (Exception e) {
                Log.d("SENTENCIASQL", "ALGO SALIÓ MAL");
                e.printStackTrace();
                new android.os.Handler(Looper.getMainLooper()).post(() -> callback.onError("Error al actualizar el artículo."));
            }
        });
    }
}
