package defensatorre;

public final class Config {

    // Tamaño del tablero
    public static final int FILAS = 10;
    public static final int COLUMNAS = 16;
    public static final int TAM_CELDA = 50;

    public static final int ANCHO_TABLERO = COLUMNAS * TAM_CELDA;
    public static final int ALTO_TABLERO = FILAS * TAM_CELDA;

    // Fila por donde se mueven los enemigos
    public static final int FILA_CAMINO = 5;

    // Valores iniciales del juego
    public static final int MONEDAS_INICIALES = 60;
    public static final int VIDA_BASE_INICIAL = 5;
    public static final int COSTO_TORRE = 20;

    // Valores de las torres
    public static final int DANIO_TORRE = 4;
    public static final int ALCANCE_TORRE = 125;
    public static final long RECARGA_TORRE_MS = 650;

    // Número máximo de oleadas
    public static final int MAX_OLEADAS = 3;

    private Config() {
        // Esta clase solo guarda constantes.
    }
}