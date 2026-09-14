public class Accion {
    private final Tower torre;

    public Accion(Tower torre) {
        if (torre == null) {
            throw new IllegalArgumentException("La torre no puede ser nula.");
        }

        this.torre = torre;
    }

    public Tower getTorre() {
        return torre;
    }
}