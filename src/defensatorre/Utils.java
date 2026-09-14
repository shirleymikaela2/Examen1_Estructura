public final class Utils {
    private Utils() {
    }

    public static double distancia(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
// Segundo commit: actualización de comentarios
