import java.awt.Color;
import java.awt.Graphics2D;

public class Projectile {
    private double x;
    private double y;
    private final Enemy objetivo;
    private final int danio;
    private final double velocidad = 7;

    public Projectile(double x, double y, Enemy objetivo, int danio) {
        this.x = x;
        this.y = y;
        this.objetivo = objetivo;
        this.danio = danio;
    }

    public boolean actualizar() {
        if (!objetivo.isVivo()) {
            return false;
        }

        double distancia = Utils.distancia(x, y, objetivo.getX(), objetivo.getY());

        if (distancia <= velocidad) {
            objetivo.recibirDanio(danio);
            return false;
        }

        x += (objetivo.getX() - x) / distancia * velocidad;
        y += (objetivo.getY() - y) / distancia * velocidad;

        return true;
    }

    public void dibujar(Graphics2D g2) {
        g2.setColor(Color.YELLOW);
        g2.fillOval((int) x - 4, (int) y - 4, 8, 8);
    }
}