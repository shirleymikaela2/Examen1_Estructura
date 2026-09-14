public class PilaAcciones {

    private static class Nodo {
        private final Accion accion;
        private Nodo siguiente;

        public Nodo(Accion accion) {
            this.accion = accion;
        }
    }

    private Nodo cima;
    private int tamanio;

    // Agrega una acción arriba de la pila.
    public void apilar(Accion accion) {
        if (accion == null) {
            return;
        }

        Nodo nuevo = new Nodo(accion);
        nuevo.siguiente = cima;
        cima = nuevo;
        tamanio++;
    }

    // Quita y devuelve la última acción registrada.
    public Accion desapilar() {
        if (estaVacia()) {
            return null;
        }

        Accion accion = cima.accion;
        cima = cima.siguiente;
        tamanio--;

        return accion;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void vaciar() {
        cima = null;
        tamanio = 0;
    }
}