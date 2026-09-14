import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Collection;

public class Tower {
    private final int fila;
    private final int columna;
    private final int x;
    private final int y;
    private long ultimoDisparo;

    public Tower(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.x = columna * Config.TAM_CELDA + Config.TAM_CELDA / 2;
        this.y = fila * Config.TAM_CELDA + Config.TAM_CELDA / 2;
    }

    public Projectile intentarDisparar(Collection<Enemy> enemigos, long tiempoActual) {
        if (tiempoActual - ultimoDisparo < Config.RECARGA_TORRE_MS) {
            return null;
        }

        Enemy objetivo = null;
        double menorDistancia = Double.MAX_VALUE;

        for (Enemy enemigo : enemigos) {
            if (!enemigo.isVivo()) {
                continue;
            }

            double distancia = Utils.distancia(x, y, enemigo.getX(), enemigo.getY());

            if (distancia <= Config.ALCANCE_TORRE && distancia < menorDistancia) {
                objetivo = enemigo;
                menorDistancia = distancia;
            }
        }

        if (objetivo != null) {
            ultimoDisparo = tiempoActual;
            return new Projectile(x, y, objetivo, Config.DANIO_TORRE);
        }

        return null;
    }

    public void dibujar(Graphics2D g2) {
        g2.setColor(new Color(54, 105, 190));
        g2.fillOval(x - 17, y - 17, 34, 34);

        g2.setColor(Color.WHITE);
        g2.drawString("T", x - 4, y + 5);
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}