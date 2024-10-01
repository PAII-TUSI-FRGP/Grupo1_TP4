package com.example.grupo1_tp4.entidad;

public class Articulo {
    private Integer id;
    private String nombre;
    private Integer stock;
    private Integer idCategoria;

    public Articulo() {
    }

    public Articulo(Integer id, String nombre, Integer stock, Integer idCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.idCategoria = idCategoria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Integer idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", stock=" + stock +
                ", idCategoria=" + idCategoria +
                '}';
    }
}
