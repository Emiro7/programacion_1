package ProyectoFinal;
public class Inscripcion {

    // Datos de la inscripción
    private String codigoEstudiante;
    private String codigoActividad;

    // Estado posible: "ACTIVA", "CANCELADA", "COMPLETADA"
    private String estado;

    // Días con los que se canceló (para saber si fue tardío)
    // -1 significa que aún no fue cancelada
    private int diasAntelacion;

    // Constructor
    public Inscripcion(String codigoEstudiante, String codigoActividad) {
        this.codigoEstudiante = codigoEstudiante;
        this.codigoActividad  = codigoActividad;
        this.estado           = "ACTIVA";
        this.diasAntelacion   = -1;
    }

    // ---- Getters ----

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public String getCodigoActividad() {
        return codigoActividad;
    }

    public String getEstado() {
        return estado;
    }

    // ---- Métodos de estado ----

    public void cancelar(int dias) {
        this.estado         = "CANCELADA";
        this.diasAntelacion = dias;
    }

    public void completar() {
        this.estado = "COMPLETADA";
    }

    // Devuelve true si está activa (no cancelada ni completada)
    public boolean estaActiva() {
        return estado.equals("ACTIVA");
    }

    // Devuelve true si fue completada exitosamente
    public boolean estaCompletada() {
        return estado.equals("COMPLETADA");
    }

    // Devuelve true si la cancelación fue tardía (menos de 2 días)
    // → se debe aplicar penalización
    public boolean fueCancelacionTardia() {
        return diasAntelacion >= 0 && diasAntelacion < 2;
    }
}
