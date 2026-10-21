package model;

public class producto{
    private String codigo, nombre, descripcion;
    private double precio;
    private int cantidad;

    public producto( String codigo, String nombre, String descripcion, double precio, int cantidad){
        this.codigo= codigo;
        this.nombre= nombre;
        this.descripcion= descripcion;
        setprecio(precio);
        setcantidad(cantidad);
    }
    public void setprecio(double p){
        if(p < 0) throw new IllegalArgumentException("el precio no puede ser negativo");
        this.precio= p;
    }
    public void setcantidad(int c){
        if(c<0) throw new IllegalArgumentException("no puede haber stock negativo");
        this.cantidad = c;
    }

    public void setdescripcion (String d){
    this.descripcion=d;
    }

    public String getcodigo (){return codigo;}
    public String getnombre (){return nombre;}
    public String getdescripcion(){return descripcion;}
    public double getprecio(){return precio;}
    public int getcantidad(){return cantidad;}

     public static producto fromCsv(String linea) {
        String[] p = linea.split("\\|");
        return new producto(p[0], p[1], p[2],
                Double.parseDouble(p[3]), Integer.parseInt(p[4]));
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | $%.2f | Stock: %d",
                codigo, nombre, descripcion, precio, cantidad);
    }
}