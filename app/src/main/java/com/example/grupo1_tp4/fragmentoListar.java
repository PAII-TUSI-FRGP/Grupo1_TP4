package com.example.grupo1_tp4;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class fragmentoListar extends Fragment {

    private ListView listViewProductos;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> productosList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragmento_listar, container, false);

        // Inicializar el ListView
        listViewProductos = view.findViewById(R.id.listViewProductos);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, productosList);
        listViewProductos.setAdapter(adapter);

        // Ejecutar la tarea asincrónica para conectarse a la base de datos y traer productos
        new ObtenerProductosTask().execute();

        return view;
    }

    // Tarea asincrónica para obtener productos de la base de datos
    private class ObtenerProductosTask extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            ArrayList<String> productos = new ArrayList<>();
            try {
                // Configurar la conexión a la base de datos
                Class.forName("com.mysql.jdbc.Driver");
                Connection conn = DriverManager.getConnection(
                        "jdbc:mysql://sql10.freesqldatabase.com/sql10735795",
                        "sql10735795",
                        "Q3yn35fQte");

                // Realizar la consulta
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nombre, stock FROM articulo");

                // Agregar los productos a la lista
                while (rs.next()) {
                    String producto = "Nombre: " + rs.getString("nombre") + " - Stock: " + rs.getInt("stock");
                    productos.add(producto);
                }

                // Cerrar la conexión
                rs.close();
                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return productos;
        }

        @Override
        protected void onPostExecute(ArrayList<String> productos) {
            super.onPostExecute(productos);
            if (productos.isEmpty()) {
                Toast.makeText(getContext(), "No se encontraron productos", Toast.LENGTH_SHORT).show();
            } else {
                productosList.clear();
                productosList.addAll(productos);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
