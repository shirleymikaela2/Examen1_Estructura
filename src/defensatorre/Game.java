import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class Game {
    // Cola FIFO para que los enemigos se mantengan en orden.
    private final Queue<Enemy> colaEnemigos = new ArrayDeque<>(

    );

    private final List<Tower> torres = new ArrayList<>();
    private final List<Projectile> proyectiles = new ArrayList<>();

    // Pilas LIFO para deshacer y rehacer.
    private final Deque<Tower> pilaDeshacer = new ArrayDeque<>();
    private final Deque<Tower> pilaRehacer = new ArrayDeque<>();

    private int monedas;
    private int vidaBase;
    private int oleada;
    private int enemigosGenerados;
    private int enemigosPorOleada;
    private long ultimoEnemigoCreado;
    private boolean terminado;
    private String mensaje;

    public Game() {
        reiniciar();
    }

    public void reiniciar() {
        colaEnemigos.clear();
        torres.clear();
        proyectiles.clear();
        pilaDeshacer.clear();
        pilaRehacer.clear();

        monedas = Config.MONEDAS_INICIALES;
        vidaBase = Config.VIDA_BASE_INICIAL;
        oleada = 1;
        terminado = false;

        iniciarOleada();
    }

    private void iniciarOleada() {
        enemigosGenerados = 0;
        enemigosPorOleada = 3 + oleada * 2;
        ultimoEnemigoCreado = 0;
        mensaje = "Oleada " + oleada + " iniciada.";
    }

    public void actualizar() {
        if (terminado) {
            return;
        }

        long tiempoActual = System.currentTimeMillis();

        if (enemigosGenerados < enemigosPorOleada
                && tiempoActual - ultimoEnemigoCreado >= 850) {

            int vida = 10 + oleada * 3;
            double velocidad = 0.9 + oleada * 0.15;

            colaEnemigos.offer(new Enemy(vida, velocidad));
            enemigosGenerados++;
            ultimoEnemigoCreado = tiempoActual;
        }

        Iterator<Enemy> recorridoEnemigos = colaEnemigos.iterator();

        while (recorridoEnemigos.hasNext()) {
            Enemy enemigo = recorridoEnemigos.next();
            enemigo.actualizar();

            if (!enemigo.isVivo()) {
                recorridoEnemigos.remove();
                monedas += 5;
                mensaje = "Enemigo eliminado. +5 monedas.";
            } else if (enemigo.llegoALaBase()) {
                recorridoEnemigos.remove();
                vidaBase--;
                mensaje = "Un enemigo llegó a la base.";

                if (vidaBase <= 0) {
                    vidaBase = 0;
                    terminado = true;
                    mensaje = "Derrota: la base se quedó sin vida.";
                }
            }
        }

        for (Tower torre : torres) {
            Projectile proyectil = torre.intentarDisparar(colaEnemigos, tiempoActual);

            if (proyectil != null) {
                proyectiles.add(proyectil);
            }
        }

        Iterator<Projectile> recorridoProyectiles = proyectiles.iterator();

        while (recorridoProyectiles.hasNext()) {
            Projectile proyectil = recorridoProyectiles.next();

            if (!proyectil.actualizar()) {
                recorridoProyectiles.remove();
            }
        }

        if (enemigosGenerados == enemigosPorOleada
                && colaEnemigos.isEmpty()
                && proyectiles.isEmpty()) {

            if (oleada == Config.MAX_OLEADAS) {
                terminado = true;
                mensaje = "¡Victoria! Defendiste la base.";
            } else {
                oleada++;
                iniciarOleada();
            }
        }
    }

    public boolean colocarTorre(int mouseX, int mouseY) {
        if (terminado || mouseX < 0 || mouseX >= Config.ANCHO_TABLERO
                || mouseY < 0 || mouseY >= Config.ALTO_TABLERO) {
            return false;
        }

        int columna = mouseX / Config.TAM_CELDA;
        int fila = mouseY / Config.TAM_CELDA;

        if (fila == Config.FILA_CAMINO) {
            mensaje = "No puedes colocar torres en el camino.";
            return false;
        }

        if (monedas < Config.COSTO_TORRE) {
            mensaje = "No tienes monedas suficientes.";
            return false;
        }

        for (Tower torre : torres) {
            if (torre.getFila() == fila && torre.getColumna() == columna) {
                mensaje = "Ya existe una torre en esta celda.";
                return false;
            }
        }

        Tower torreNueva = new Tower(fila, columna);
        torres.add(torreNueva);
        pilaDeshacer.push(torreNueva);
        pilaRehacer.clear();

        monedas -= Config.COSTO_TORRE;
        mensaje = "Torre colocada.";
        return true;
    }

    public void deshacer() {
        if (pilaDeshacer.isEmpty()) {
            mensaje = "No hay acciones para deshacer.";
            return;
        }

        Tower ultimaTorre = pilaDeshacer.pop();
        torres.remove(ultimaTorre);
        pilaRehacer.push(ultimaTorre);
        monedas += Config.COSTO_TORRE;
        mensaje = "Última torre eliminada.";
    }

    public void rehacer() {
        if (pilaRehacer.isEmpty()) {
            mensaje = "No hay acciones para rehacer.";
            return;
        }

        if (monedas < Config.COSTO_TORRE) {
            mensaje = "No tienes monedas para rehacer.";
            return;
        }

        Tower torre = pilaRehacer.pop();
        torres.add(torre);
        pilaDeshacer.push(torre);
        monedas -= Config.COSTO_TORRE;
        mensaje = "Torre recuperada.";
    }

    public Collection<Enemy> getEnemigos() {
        return new ArrayList<>(colaEnemigos);
    }

    public List<Tower> getTorres() {
        return torres;
    }

    public List<Projectile> getProyectiles() {
        return proyectiles;
    }

    public int getMonedas() {
        return monedas;
    }

    public int getVidaBase() {
        return vidaBase;
    }

    public int getOleada() {
        return oleada;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isTerminado() {
        return terminado;
    }
}
// Modificación para registrar segundo commit