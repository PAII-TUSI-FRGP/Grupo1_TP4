package com.example.grupo1_tp4;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grupo1_tp4.conexion.DataArticuloMainActivity;
import com.example.grupo1_tp4.entidad.Articulo;

import org.jetbrains.annotations.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentoModificar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentoModificar extends Fragment {

    // Variables para los inputs
    private EditText articleIdInput, nameInput, stockInput, categoryInput;
    private Button searchButton, saveButton;
    private int articleId;

    public fragmentoModificar() {
        // Constructor público vacío requerido para fragmentos
    }

    public static fragmentoModificar newInstance() {
        return new fragmentoModificar();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_fragmento_modificar_articulo, container, false);

        // Inicializar las vistas
        articleIdInput = view.findViewById(R.id.article_id_input);
        nameInput = view.findViewById(R.id.name_input);
        stockInput = view.findViewById(R.id.stock_input);
        categoryInput = view.findViewById(R.id.category_input);
        searchButton = view.findViewById(R.id.search_button);
        saveButton = view.findViewById(R.id.save_button);

        // Hacer los campos de edición invisibles al inicio
        nameInput.setVisibility(View.GONE);
        stockInput.setVisibility(View.GONE);
        categoryInput.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);

        // Configurar el botón de búsqueda
        searchButton.setOnClickListener(v -> {
            String articleIdText = articleIdInput.getText().toString();
            if (!articleIdText.isEmpty()) {
                int articleId = Integer.parseInt(articleIdText);

                // Llamar al método obtenerPorId con el Callback
                DataArticuloMainActivity.obtenerPorId(articleId, new DataArticuloMainActivity.ArticuloCallback() {
                    @Override
                    public void onArticuloObtenido(Articulo articulo) {
                        // Aquí recibes el objeto Articulo y actualizas los campos
                        nameInput.setVisibility(View.VISIBLE);
                        stockInput.setVisibility(View.VISIBLE);
                        categoryInput.setVisibility(View.VISIBLE);
                        saveButton.setVisibility(View.VISIBLE);

                        // Actualizamos los EditText con los datos del artículo
                        nameInput.setText(articulo.getNombre());
                        stockInput.setText(String.valueOf(articulo.getStock()));
                        categoryInput.setText(String.valueOf(articulo.getIdCategoria()));
                    }

                    @Override
                    public void onError(String mensaje) {
                        // Muestra un mensaje de error si algo sale mal
                        Toast.makeText(getActivity(), mensaje, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                // Si el ID no está presente, muestra un mensaje de advertencia
                Toast.makeText(getActivity(), "Ingrese un ID", Toast.LENGTH_SHORT).show();
            }
        });
        // Configurar el botón de guardar
        saveButton.setOnClickListener(v -> updateArticle());

        return view;
    }

    // Método para guardar la actualización
    private void updateArticle() {
        // Verificar que los campos no estén vacíos
        if (nameInput.getText().toString().isEmpty() || stockInput.getText().toString().isEmpty() || categoryInput.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Capturar los datos del formulario
            articleId = Integer.parseInt(articleIdInput.getText().toString());
            String nuevoNombre = nameInput.getText().toString();
            int nuevoStock = Integer.parseInt(stockInput.getText().toString());
            int nuevaCategoria = Integer.parseInt(categoryInput.getText().toString());

            // Crear un nuevo objeto Articulo con los valores actualizados
            Articulo articuloActualizado = new Articulo(articleId, nuevoNombre, nuevoStock, nuevaCategoria);

            // Llamar al método actualizarPorId con el artículo actualizado
            DataArticuloMainActivity.actualizarPorId(articuloActualizado, new DataArticuloMainActivity.ArticuloCallback() {
                @Override
                public void onArticuloObtenido(Articulo articulo) {
                    // Mostrar un mensaje de éxito
                    Toast.makeText(getActivity(), "Artículo actualizado correctamente.", Toast.LENGTH_SHORT).show();
                    // Hacer los campos invisibles nuevamente después de actualizar
                    nameInput.setVisibility(View.GONE);
                    stockInput.setVisibility(View.GONE);
                    categoryInput.setVisibility(View.GONE);
                    saveButton.setVisibility(View.GONE);

                    // Limpiar los campos de texto después de la actualización
                    articleIdInput.setText("");
                    nameInput.setText("");
                    stockInput.setText("");
                    categoryInput.setText("");

                }

                @Override
                public void onError(String mensaje) {
                    // Manejar error en la actualización
                    Toast.makeText(getActivity(), "Error al actualizar el artículo: " + mensaje, Toast.LENGTH_SHORT).show();
                }
            });

        } catch (NumberFormatException e) {
            // Manejar error si el stock o categoría no son números válidos
            Toast.makeText(getActivity(), "Stock y categoría deben ser números válidos.", Toast.LENGTH_SHORT).show();
        }
    }
}