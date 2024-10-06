package com.example.grupo1_tp4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grupo1_tp4.conexion.DataArticuloMainActivity;
import com.example.grupo1_tp4.entidad.Articulo;

import java.util.ArrayList;
import java.util.List;

public class fragmentoAlta extends Fragment {

    private Spinner spCategorias;
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
        return inflater.inflate(R.layout.fragment_fragmento_alta, container, false);
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
        dataArticuloMainActivity = new DataArticuloMainActivity(null,getContext());

        // Llama al método cargarCategorias() de la actividad para cargar las categorías
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            // Cargar las categorías en el Spinner (lógica temporal con lista vacía por ahora)
            List<String> listaCategorias = new ArrayList<>();

            // Adapter para el Spinner
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    getContext(),
                    android.R.layout.simple_spinner_item,
                    listaCategorias
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCategorias.setAdapter(adapter);
        }

        // Asignar el evento onClick al botón Agregar
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Llamar al método para insertar el artículo
                insertarArticulo();
            }
        });
    }

    // Método para insertar un artículo
    private void insertarArticulo() {
        // Obtener los valores de los EditText
        String id = ptID.getText().toString();
        String nombreProducto = ptNombreProducto.getText().toString();
        String stock = ptStock.getText().toString();

        // Harcodear la categoría del Spinner (por ahora)
        int categoriaId = 1; // Categoría harcodeada

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
        articulo.setIdCategoria(categoriaId); // Usamos la categoría harcodeada

        // Llamar al método insertar del DataArticuloMainActivity
        dataArticuloMainActivity.insertar(articulo, new DataArticuloMainActivity.ArticuloCallback() {
            @Override
            public void onArticuloObtenido(Articulo articulo) {
                // Mostrar un mensaje de éxito si es necesario
                Toast.makeText(getContext(), "Artículo agregado con éxito", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                // Mostrar mensaje de error
                Toast.makeText(getContext(), "Error al agregar artículo: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
