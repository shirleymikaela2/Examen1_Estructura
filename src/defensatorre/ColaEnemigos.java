import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class ColaEnemigos {

    private final Queue<Enemy> enemigos;

    public ColaEnemigos() {

        enemigos = new ArrayDeque<>();
    }

    // INSERTAR AL FINAL DE LA COLA
    public void agregarEnemigo(Enemy enemigo) {

        if (enemigo != null) {
            enemigos.offer(enemigo);
        }
    }

    // CONSULTAR EL PRIMER ELEMENTO
    public Enemy obtenerPrimero() {

        return enemigos.peek();
    }

    // ELIMINAR EL PRIMER ELEMENTO
    public Enemy eliminarPrimero() {

        return enemigos.poll();
    }

    // COMPROBAR SI ESTÁ VACÍA
    public boolean estaVacia() {

        return enemigos.isEmpty();
    }

    // TAMAÑO DE LA COLA
    public int cantidad() {

        return enemigos.size();
    }

    // ELIMINAR TODOS LOS ELEMENTOS
    public void limpiar() {

        enemigos.clear();
    }

    // DEVUELVE UNA COPIA PARA CONSULTAR LOS ENEMIGOS
    public Collection<Enemy> obtenerEnemigos() {

        return new ArrayList<>(enemigos);
    }

    // ITERADOR PARA RECORRER Y ELIMINAR DE FORMA SEGURA
    public Iterator<Enemy> iterator() {

        return enemigos.iterator();
    }
}