package ui;

import dao.*;
import model.producto;
import java.util.*;

public class main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ProductoDAO dao = new ProductoDAOArchivo();

    public static void main(String[] args) {
        int op;
        do {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Registrar  2. Listar  3. Buscar");
            System.out.println("4. Actualizar 5. Eliminar 0. Salir");
            op = leerInt("Opción: ");
            switch (op) {
                case 1 -> registrar();
                case 2 -> dao.listar().forEach(System.out::println);
                case 3 -> buscar();
                case 4 -> actualizar();
                case 5 -> eliminar();
                case 0 -> System.out.println("¡Hasta pronto!");
                default -> System.out.println("Opción inválida.");
            }
        } while (op != 0);
    }

    private static void registrar() {
        try {
            String cod = leer("Código: ");
            String nom = leer("Nombre: ");
            String desc = leer("Descripción: ");
            double precio = Double.parseDouble(leer("Precio: "));
            int cant = leerInt("Cantidad: ");
            System.out.println(dao.crear(new producto(cod, nom, desc, precio, cant))
                    ? "Producto registrado." : "Ya existe ese código.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void buscar() {
        String t = leer("Código o nombre: ");
        producto p = dao.buscarPorCodigo(t);
        if (p != null) { System.out.println(p); return; }
        List<producto> r = dao.buscarPorNombre(t);
        if (r.isEmpty()) System.out.println("Sin resultados.");
        else r.forEach(System.out::println);
    }

    private static void actualizar() {
        producto p = dao.buscarPorCodigo(leer("Código a actualizar: "));
        if (p == null) { System.out.println("No existe."); return; }
        try {
            p.setprecio(Double.parseDouble(leer("Nuevo precio: ")));
            p.setdescripcion(leer("Nueva descripción: "));
            p.setcantidad(leerInt("Nueva cantidad: "));
            dao.actualizar(p);
            System.out.println("Producto actualizado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminar() {
        String cod = leer("Código a eliminar: ");
        if (dao.buscarPorCodigo(cod) == null) { System.out.println("No existe."); return; }
        if (leer("¿Confirmar (s/n)? ").equalsIgnoreCase("s"))
            System.out.println(dao.eliminar(cod) ? "Eliminado." : "Error.");
        else System.out.println("Cancelado.");
    }

    private static String leer(String msg) { System.out.print(msg); return sc.nextLine().trim(); }

    private static int leerInt(String msg) {
        while (true) {
            try { return Integer.parseInt(leer(msg)); }
            catch (NumberFormatException e) { System.out.println("Ingresa un número entero."); }
        }
    }
}