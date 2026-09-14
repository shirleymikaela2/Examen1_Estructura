import java.awt.Color;
import java.awt.Graphics2D;

public class Enemy {

    private double x;
    private final double y;

    private final double velocidad;

    private int vida;
    private final int vidaMaxima;

    private boolean vivo;

    public Enemy(int vida, double velocidad) {

        this.x = -20;

        this.y = Config.FILA_CAMINO
                * Config.TAM_CELDA
                + Config.TAM_CELDA / 2.0;

        this.vida = vida;
        this.vidaMaxima = vida;
        this.velocidad = velocidad;

        this.vivo = true;
    }

    public void actualizar() {

        if (vivo) {
            x += velocidad;
        }
    }

    public void recibirDanio(int danio) {

        if (!vivo) {
            return;
        }

        vida -= danio;

        if (vida <= 0) {
            vida = 0;
            vivo = false;
        }
    }

    public boolean llegoALaBase() {

        return x >= Config.ANCHO_TABLERO
                - Config.TAM_CELDA / 2.0;
    }

    public void dibujar(Graphics2D g2) {

        int tamano = 28;

        int posicionX = (int) x - tamano / 2;
        int posicionY = (int) y - tamano / 2;

        // Enemigo
        g2.setColor(new Color(205, 70, 70));

        g2.fillOval(
                posicionX,
                posicionY,
                tamano,
                tamano);

        // Vida
        g2.setColor(Color.WHITE);

        g2.drawString(
                vida + "/" + vidaMaxima,
                posicionX - 3,
                posicionY - 5);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getVida() {
        return vida;
    }

    public boolean isVivo() {
        return vivo;
    }
}