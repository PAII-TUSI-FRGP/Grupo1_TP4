package com.example.grupo1_tp4.conexion;

public class DataDB {
    /* BD */
    //Informacion de la BD
    public static String host = "sql10.freesqldatabase.com";
    public static String port = "3306";
    public static String nameBD = "sql10735795";
    public static String user = "sql10735795";
    public static String pass = "Q3yn35fQte";

    //Informacion para la conexion
    public static String urlMySQL = "jdbc:mysql://" + host + ":" + port + "/" + nameBD;
    public static String driver = "com.mysql.jdbc.Driver";

    /* TABLAS */
    //categoria
    public static final String TABLE_CATEGORIA = "categoria";
    public static final String COL_CATEGORIA_ID = "id";
    public static final String COL_CATEGORIA_DESCRIPCION = "descripcion";

    //articulo
    public static final String TABLE_ARTICULO = "articulo";
    public static final String COL_ARTICULO_ID = "id";
    public static final String COL_ARTICULO_NOMBRE = "nombre";
    public static final String COL_ARTICULO_STOCK = "stock";
    public static final String COL_ARTICULO_ID_CATEGORIA = "idCategoria";
}
