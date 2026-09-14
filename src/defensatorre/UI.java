package defensatorre;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class UI extends JFrame {
    private final Game juego = new Game();
    private final JLabel datos = new JLabel();
    private final JLabel mensaje = new JLabel();
    private final PanelJuego panelJuego = new PanelJuego();

    public UI() {
        setTitle("Tower Defense - Estructura de Datos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controles = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton nuevaPartida = new JButton("Nueva partida");
        JButton deshacer = new JButton("Deshacer");
        JButton rehacer = new JButton("Rehacer");

        controles.add(nuevaPartida);
        controles.add(deshacer);
        controles.add(rehacer);

        datos.setHorizontalAlignment(SwingConstants.CENTER);
        datos.setBorder(BorderFactory.createEmptyBorder(8, 10, 3, 10));

        mensaje.setHorizontalAlignment(SwingConstants.CENTER);
        mensaje.setBorder(BorderFactory.createEmptyBorder(3, 10, 8, 10));

        add(controles, BorderLayout.NORTH);
        add(panelJuego, BorderLayout.CENTER);
        add(datos, BorderLayout.SOUTH);

        nuevaPartida.addActionListener(e -> {
            juego.reiniciar();
            actualizarVista();
        });

        deshacer.addActionListener(e -> {
            juego.deshacer();
            actualizarVista();
        });

        rehacer.addActionListener(e -> {
            juego.rehacer();
            actualizarVista();
        });

        Timer temporizador = new Timer(30, e -> {
            juego.actualizar();
            actualizarVista();
        });

        temporizador.start();

        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        actualizarVista();
    }

    private void actualizarVista() {
        datos.setText(
                "Monedas: " + juego.getMonedas()
                + "     Vida de base: " + juego.getVidaBase()
                + "     Oleada: " + juego.getOleada()
        );

        mensaje.setText(juego.getMensaje());
        panelJuego.repaint();
    }

    private class PanelJuego extends JPanel {
        public PanelJuego() {
            setPreferredSize(new Dimension(Config.ANCHO_TABLERO, Config.ALTO_TABLERO));
            setBackground(new Color(150, 205, 135));

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    juego.colocarTorre(e.getX(), e.getY());
                    actualizarVista();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);
            Graphics2D g2 = (Graphics2D) graphics.create();

            dibujarTablero(g2);

            for (Tower torre : juego.getTorres()) {
                torre.dibujar(g2);
            }

            for (Enemy enemigo : juego.getEnemigos()) {
                enemigo.dibujar(g2);
            }

            for (Projectile proyectil : juego.getProyectiles()) {
                proyectil.dibujar(g2);
            }

            dibujarBase(g2);

            if (juego.isTerminado()) {
                g2.setColor(new Color(0, 0, 0, 150));
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 28));
                g2.drawString(juego.getMensaje(), 210, 230);
            }

            g2.dispose();
        }

        private void dibujarTablero(Graphics2D g2) {
            for (int fila = 0; fila < Config.FILAS; fila++) {
                for (int columna = 0; columna < Config.COLUMNAS; columna++) {
                    if (fila == Config.FILA_CAMINO) {
                        g2.setColor(new Color(220, 185, 120));
                    } else {
                        g2.setColor(new Color(150, 205, 135));
                    }

                    int x = columna * Config.TAM_CELDA;
                    int y = fila * Config.TAM_CELDA;

                    g2.fillRect(x, y, Config.TAM_CELDA, Config.TAM_CELDA);
                    g2.setColor(new Color(90, 125, 80));
                    g2.drawRect(x, y, Config.TAM_CELDA, Config.TAM_CELDA);
                }
            }
        }

        private void dibujarBase(Graphics2D g2) {
            int x = Config.ANCHO_TABLERO - Config.TAM_CELDA + 5;
            int y = Config.FILA_CAMINO * Config.TAM_CELDA + 5;

            g2.setColor(new Color(90, 65, 150));
            g2.fillRoundRect(x, y, 40, 40, 10, 10);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 10));
            g2.drawString("BASE", x + 4, y + 24);
        }
    }
}