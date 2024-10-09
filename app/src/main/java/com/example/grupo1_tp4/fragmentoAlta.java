package com.example.grupo1_tp4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo1_tp4.adapter.CategoriaSpinnerAdapter;
import com.example.grupo1_tp4.conexion.DataArticuloMainActivity;
import com.example.grupo1_tp4.conexion.DataCategoriaMainActivity;
import com.example.grupo1_tp4.entidad.Articulo;
import com.example.grupo1_tp4.entidad.Categoria;

import java.util.ArrayList;
import java.util.List;

public class fragmentoAlta extends Fragment {

    private Spinner spCategorias;
    private List<Categoria> categorias;
    private EditText ptID, ptNombreProducto, ptStock;
    private Button btnAgregar;
    private DataArticuloMainActivity dataArticuloMainActivity;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public fragmentoAlta() {
        // Required empty public constructor
    }

    public static fragmentoAlta newInstance(String param1, String param2) {
        fragmentoAlta fragment = new fragmentoAlta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_fragmento_alta, container, false);
        // Inicializa el Spinner
        spCategorias = view.findViewById(R.id.spCategorias); // Asegúrate de que este ID coincida con tu XML

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Referencia a los campos de la interfaz
        spCategorias = view.findViewById(R.id.spCategorias);
        ptID = view.findViewById(R.id.ptID);
        ptNombreProducto = view.findViewById(R.id.ptNombreProducto);
        ptStock = view.findViewById(R.id.ptStock);
        btnAgregar = view.findViewById(R.id.btnAgregar);

        // Inicializa la clase que contiene el método insertar (DataArticuloMainActivity)
        dataArticuloMainActivity = new DataArticuloMainActivity(null, getContext());

        // Cargar las categorías en el Spinner
        cargarCategorias();

        // Asignar el evento onClick al botón Agregar
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertarArticulo();}
        });
    }


    private void cargarCategorias() {
        DataCategoriaMainActivity dataCategoriaMainActivity = new DataCategoriaMainActivity(null, getActivity());
        dataCategoriaMainActivity.obtenerTodos(new DataCategoriaMainActivity.CategoriaCallback() {
            @Override
            public void onResponse(Object response) {
                fragmentoAlta.this.categorias = (List<Categoria>) response;

                CategoriaSpinnerAdapter categoriaSpinnerAdapter = new CategoriaSpinnerAdapter(getContext(), categorias);
                spCategorias.setAdapter(categoriaSpinnerAdapter);

                // Manejar la selección del Spinner
                spCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Categoria categoriaSeleccionada = (Categoria) spCategorias.getSelectedItem(); // Obtiene la categoría seleccionada
                        // Aquí puedes hacer lo que necesites con la categoria seleccionada
                        Toast.makeText(getContext(), "Categoría seleccionada: " + categoriaSeleccionada.getDescripcion(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // No haces nada si no se selecciona nada
                    }
                });
            }

            @Override
            public void onError(String mensaje) {
                // Manejo de errores
                Toast.makeText(getContext(), "Error al obtener categorías: " + mensaje, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para insertar un artículo
    private void insertarArticulo() {
        // Obtener los valores de los EditText
        String id = ptID.getText().toString();
        String nombreProducto = ptNombreProducto.getText().toString();
        String stock = ptStock.getText().toString();

        // Verificar si hay una categoría seleccionada válida
        int posicionSeleccionada = spCategorias.getSelectedItemPosition();

        if (posicionSeleccionada >= 0) {
            // Obtener la categoría seleccionada desde la lista de categorías
            Categoria categoriaSeleccionada = (Categoria) spCategorias.getSelectedItem(); // Obtiene la categoría seleccionada
            int categoriaId = categoriaSeleccionada.getId(); // Obtener el ID de la categoría seleccionada

            // Verificar si los campos obligatorios están completos
            if (nombreProducto.isEmpty() || stock.isEmpty()) {
                Toast.makeText(getContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear el artículo con los datos obtenidos
            Articulo articulo = new Articulo();
            articulo.setId(Integer.parseInt(id)); // Suponiendo que el ID es un número
            articulo.setNombre(nombreProducto);
            articulo.setStock(Integer.parseInt(stock)); // Suponiendo que el stock es un número
            articulo.setIdCategoria(categoriaId); // Usamos el ID de la categoría seleccionada

            // Llamar al método insertar del DataArticuloMainActivity
            dataArticuloMainActivity.insertar(articulo, new DataArticuloMainActivity.ArticuloCallback() {
                @Override
                public void onArticuloObtenido(Articulo articulo) {
                    ptID.setText("");
                    ptNombreProducto.setText("");
                    ptStock.setText("");
                    spCategorias.setSelection(0);

                    // Mostrar un mensaje de éxito si es necesario
                    Toast.makeText(getContext(), "Artículo agregado con éxito", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String error) {
                    // Mostrar mensaje de error
                    Toast.makeText(getContext(), "Error al agregar artículo: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Por favor, seleccione una categoría válida", Toast.LENGTH_SHORT).show();
        }
    }

}
