package dao;

import model.producto;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class ProductoDAOArchivo implements ProductoDAO {
    private static final Path ARCHIVO = Paths.get("data", "productos.csv");
    private final Map<String, producto> productos = new LinkedHashMap<>();

    public ProductoDAOArchivo() { cargar(); }

    private void cargar() {
        if (!Files.exists(ARCHIVO)) return;
        try (BufferedReader br = Files.newBufferedReader(ARCHIVO)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                producto p = producto.fromCsv(linea);
                productos.put(p.getcodigo(), p);
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
        }
    }

    private void guardar() {
        try {
            Files.createDirectories(ARCHIVO.getParent());
            try (BufferedWriter bw = Files.newBufferedWriter(ARCHIVO)) {
                for (producto p : productos.values()) {
                    bw.write(p.toCsv());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

    public boolean crear(producto p) {
        if (productos.containsKey(p.getcodigo())) return false;
        productos.put(p.getcodigo(), p);
        guardar();
        return true;
    }

    public List<producto> listar() { return new ArrayList<>(productos.values()); }

    public producto buscarPorCodigo(String codigo) { return productos.get(codigo); }

    public List<producto> buscarPorNombre(String texto) {
        List<producto> res = new ArrayList<>();
        for (producto p : productos.values())
            if (p.getnombre().toLowerCase().contains(texto.toLowerCase())) res.add(p);
        return res;
    }

    public boolean actualizar(producto p) {
        if (!productos.containsKey(p.getcodigo())) return false;
        productos.put(p.getcodigo(), p);
        guardar();
        return true;
    }

    public boolean eliminar(String codigo) {
        if (productos.remove(codigo) == null) return false;
        guardar();
        return true;
    }
}