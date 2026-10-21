package dao;

import model.producto;
import java.util.List;

public interface ProductoDAO {
    boolean crear(producto p);
    List<producto> listar();
    producto buscarPorCodigo(String codigo);
    List<producto> buscarPorNombre(String texto);
    boolean actualizar(producto p);
    boolean eliminar(String codigo);
}