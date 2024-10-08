package com.example.grupo1_tp4.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.grupo1_tp4.R;
import com.example.grupo1_tp4.entidad.Categoria;

import java.util.List;

public class CategoriaSpinnerAdapter extends ArrayAdapter<Categoria> {
    public CategoriaSpinnerAdapter(@NonNull Context context, @NonNull List<Categoria> objects) {
        super(context, R.layout.spiner_template_categoria, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.spiner_template_categoria, null);

        TextView tv_id_spiner_categoria = view.findViewById(R.id.tv_id_spiner_categoria);
        TextView tv_nombre_spiner_categoria = view.findViewById(R.id.tv_decripcion_spiner_categoria);

        tv_id_spiner_categoria.setText(getItem(position).getId().toString());
        tv_nombre_spiner_categoria.setText(getItem(position).getDescripcion());

        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.spiner_template_categoria, null);

        TextView tv_id_spiner_categoria = view.findViewById(R.id.tv_id_spiner_categoria);
        TextView tv_decripcion_spiner_categoria = view.findViewById(R.id.tv_decripcion_spiner_categoria);

        tv_id_spiner_categoria.setText(getItem(position).getId().toString());
        tv_decripcion_spiner_categoria.setText(getItem(position).getDescripcion());

        return view;
    }
}
