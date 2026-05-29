package ProyectoFinal;
public class Estudiante {
// Datos del estudiante
    private String codigo;
    private String nombre;
    private double promedio;

    // Horas acumuladas por cada categoría
    private double horasDeporte;
    private double horasCultura;
    private double horasSalud;

    // Total de horas (incluye bonos y descuentos por penalización)
    private double horasTotal;

    // Contador de actividades aprobadas por categoría
    // (sirve para el bono de "ciclo de bienestar")
    private int actividadesDeporte;
    private int actividadesCultura;
    private int actividadesSalud;

    // Cuántos bonos de ciclo ya se dieron por categoría
    // (para no dar el mismo bono dos veces)
    private int bonosDeporte;
    private int bonosCultura;
    private int bonosSalud;

    // Constructor
    public Estudiante(String codigo, String nombre, double promedio) {
        this.codigo   = codigo;
        this.nombre   = nombre;
        this.promedio = promedio;

        // Valores iniciales
        this.horasDeporte      = 0;
        this.horasCultura      = 0;
        this.horasSalud        = 0;
        this.horasTotal        = 0;
        this.actividadesDeporte = 0;
        this.actividadesCultura = 0;
        this.actividadesSalud   = 0;
        this.bonosDeporte      = 0;
        this.bonosCultura      = 0;
        this.bonosSalud        = 0;
    }

    // ---- Getters (solo lectura desde afuera) ----

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPromedio() {
        return promedio;
    }

    public double getHorasDeporte() {
        return horasDeporte;
    }

    public double getHorasCultura() {
        return horasCultura;
    }

    public double getHorasSalud() {
        return horasSalud;
    }

    public double getHorasTotal() {
        return horasTotal;
    }

    // ---- Métodos principales ----

    // Suma horas al estudiante según la categoría de la actividad completada.
    // También verifica si hay bono de ciclo de bienestar (cada 3 actividades = +5 horas).
    public void sumarHoras(String categoria, double horas) {

        if (categoria.equalsIgnoreCase("Deporte")) {
            horasDeporte += horas;
            actividadesDeporte++;

            // ¿Completó un nuevo ciclo en Deporte?
            int nuevosBonosDeporte = actividadesDeporte / 3;
            if (nuevosBonosDeporte > bonosDeporte) {
                horasTotal += 5;
                bonosDeporte++;
                System.out.println("  *** BONO +5h: completaste un ciclo de Deporte! ***");
            }

        } else if (categoria.equalsIgnoreCase("Cultura")) {
            horasCultura += horas;
            actividadesCultura++;

            int nuevosBonosCultura = actividadesCultura / 3;
            if (nuevosBonosCultura > bonosCultura) {
                horasTotal += 5;
                bonosCultura++;
                System.out.println("  *** BONO +5h: completaste un ciclo de Cultura! ***");
            }

        } else if (categoria.equalsIgnoreCase("Salud")) {
            horasSalud += horas;
            actividadesSalud++;

            int nuevosBonosSalud = actividadesSalud / 3;
            if (nuevosBonosSalud > bonosSalud) {
                horasTotal += 5;
                bonosSalud++;
                System.out.println("  *** BONO +5h: completaste un ciclo de Salud! ***");
            }
        }

        // Siempre sumamos las horas de la actividad al total
        horasTotal += horas;
    }

    // Resta 2 horas del total por cancelación tardía
    public void aplicarPenalizacion() {
        horasTotal -= 2;
        System.out.println("  [PENALIZACION] Se restaron 2 horas. Total ahora: " + horasTotal);
    }

    // Muestra la info del estudiante en consola de forma resumida
    public void mostrarInfo() {
        System.out.println("  Codigo  : " + codigo);
        System.out.println("  Nombre  : " + nombre);
        System.out.println("  Promedio: " + promedio);
        System.out.printf("  Horas   -> Deporte: %.1f | Cultura: %.1f | Salud: %.1f | Total: %.1f%n",
                horasDeporte, horasCultura, horasSalud, horasTotal);
    }
}
