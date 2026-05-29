package ProyectoFinal;
public class Actividad {

    // Datos de la actividad
    private String codigo;
    private String nombre;
    private String categoria;     // "Deporte", "Cultura" o "Salud"
    private double horas;         // Horas que vale al completarla
    private int cupoMaximo;       // Límite de participantes
    private int cupoActual;       // Cuántos están inscritos ahora

    // Horario para detectar choques
    private int horaInicio;       // Ej: 8  → 8:00 am
    private int horaFin;          // Ej: 10 → 10:00 am

    // Si es alto impacto, se necesita promedio >= 4.0 para inscribirse
    private boolean esAltoImpacto;

    // Lista de espera: guardamos los códigos de estudiantes en cola
    // Usamos un arreglo simple, máximo 20 personas esperando
    private String[] listaEspera;
    private int totalEnEspera;

    // Para calcular el promedio de satisfacción (calificaciones 1 a 5)
    private double sumaCalificaciones;
    private int totalCalificaciones;

    // Constructor
    public Actividad(String codigo, String nombre, String categoria,
                     double horas, int cupoMaximo,
                     int horaInicio, int horaFin, boolean esAltoImpacto) {

        this.codigo        = codigo;
        this.nombre        = nombre;
        this.categoria     = categoria;
        this.horas         = horas;
        this.cupoMaximo    = cupoMaximo;
        this.cupoActual    = 0;
        this.horaInicio    = horaInicio;
        this.horaFin       = horaFin;
        this.esAltoImpacto = esAltoImpacto;

        this.sumaCalificaciones  = 0;
        this.totalCalificaciones = 0;

        // Lista de espera vacía al inicio
        this.listaEspera   = new String[20];
        this.totalEnEspera = 0;
    }

    // ---- Getters ----

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getHoras() {
        return horas;
    }

    public int getCupoActual() {
        return cupoActual;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public int getHoraFin() {
        return horaFin;
    }

    public boolean isEsAltoImpacto() {
        return esAltoImpacto;
    }

    public int getTotalEnEspera() {
        return totalEnEspera;
    }

    // ---- Métodos de cupo ----

    // Devuelve true si todavía hay cupo disponible
    public boolean hayCupo() {
        return cupoActual < cupoMaximo;
    }

    // Ocupa un cupo (cuando alguien se inscribe exitosamente)
    public void ocuparCupo() {
        cupoActual++;
    }

    // Libera un cupo (cuando alguien cancela)
    public void liberarCupo() {
        if (cupoActual > 0) {
            cupoActual--;
        }
    }

    // ---- Métodos de lista de espera ----

    // Agrega un estudiante al final de la cola de espera
    public boolean agregarAEspera(String codigoEstudiante) {
        if (totalEnEspera < listaEspera.length) {
            listaEspera[totalEnEspera] = codigoEstudiante;
            totalEnEspera++;
            return true;
        }
        return false; // La lista de espera está llena
    }

    // Sacar primero de la lista
    public String sacarPrimeroDeEspera() {
        if (totalEnEspera == 0) {
            return null; // Nadie esperando
        }

        String primero = listaEspera[0];

        // Corremos todos una posición hacia adelante
        for (int i = 0; i < totalEnEspera - 1; i++) {
            listaEspera[i] = listaEspera[i + 1];
        }
        listaEspera[totalEnEspera - 1] = null;
        totalEnEspera--;

        return primero;
    }

    // Verifica si un estudiante ya está en la lista de espera
    public boolean estaEnEspera(String codigoEstudiante) {
        for (int i = 0; i < totalEnEspera; i++) {
            if (listaEspera[i].equals(codigoEstudiante)) {
                return true;
            }
        }
        return false;
    }

    // ---- Método de validación de horario ----

    // Devuelve true si el horario de esta actividad choca con otro horario dado.
    // Hay choque si los rangos se superponen (no si solo se tocan en un extremo).
    public boolean chocanHorarios(int otroInicio, int otroFin) {
        return !(otroFin <= horaInicio || otroInicio >= horaFin);
    }

    // ---- Métodos de calificación ----

    // Registra una calificación de satisfacción (1 a 5)
    public void registrarCalificacion(int calificacion) {
        sumaCalificaciones += calificacion;
        totalCalificaciones++;
    }

    // Calcula y devuelve el promedio de satisfacción
    public double getPromedioSatisfaccion() {
        if (totalCalificaciones == 0) {
            return 0;
        }
        return sumaCalificaciones / totalCalificaciones;
    }

    // Muestra los datos de la actividad en consola
    public void mostrarInfo() {
        String impacto = esAltoImpacto ? " [ALTO IMPACTO - promedio >= 4.0]" : "";
        System.out.println("  [" + codigo + "] " + nombre
                + " | " + categoria
                + " | " + horas + "h"
                + " | Horario: " + horaInicio + ":00 - " + horaFin + ":00"
                + " | Cupo: " + cupoActual + "/" + cupoMaximo
                + impacto);
    }
}
