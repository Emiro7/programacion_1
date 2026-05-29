package ProyectoFinal;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

        // Listas globales donde guardamos todos los datos en memoria
        static ArrayList<Estudiante>  estudiantes  = new ArrayList<>();
    static ArrayList<Actividad>   actividades  = new ArrayList<>();
    static ArrayList<Inscripcion> inscripciones = new ArrayList<>();

    // Scanner
    static Scanner sc = new Scanner(System.in);

        // Main
        public static void main(String[] args) {

        
        System.out.println("   Sistema de Bienestar");
        System.out.println("   ");
        

        // Cargamos datos de prueba para poder probar el sistema
        // sin tener que escribir todo manualmente cada vez
        cargarDatosDePrueba();

        int opcion = -1;

        while (opcion != 9) {
            mostrarMenu();
            opcion = leerEntero();

            switch (opcion) {
                case 1: menuEstudiantes();   break;
                case 2: menuActividades();   break;
                case 3: menuInscripciones(); break;
                case 4: menuReportes();      break;
                case 9:
                    System.out.println("\nHasta luego!\n");
                    break;
                default:
                    System.out.println("[!] Opcion no valida.\n");
            }
        }

        sc.close();
    }

        // MENÚ PRINCIPAL
        static void mostrarMenu() {
        System.out.println();
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║        MENU PRINCIPAL            ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.println("║  1. Gestion de Estudiantes       ║");
        System.out.println("║  2. Gestion de Actividades       ║");
        System.out.println("║  3. Inscripciones                ║");
        System.out.println("║  4. Reportes                     ║");
        System.out.println("║  9. Salir                        ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.print("Opcion: ");
    }

        // SUBMENÚ ESTUDIANTES
        static void menuEstudiantes() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n-- ESTUDIANTES --");
            System.out.println("1. Registrar estudiante");
            System.out.println("2. Ver lista de estudiantes");
            System.out.println("3. Ver inscripciones de un estudiante");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();

            switch (op) {
                case 1: registrarEstudiante();             break;
                case 2: listarEstudiantes();               break;
                case 3:
                    System.out.print("Codigo del estudiante: ");
                    String cod = sc.nextLine().trim();
                    verInscripcionesEstudiante(cod);
                    break;
                case 0: break;
                default: System.out.println("[!] Opcion no valida.");
            }
        }
    }

    // Registra un estudiante nuevo pidiendo datos al usuario
    static void registrarEstudiante() {
        System.out.println("\n-- REGISTRAR ESTUDIANTE --");

        System.out.print("Codigo: ");
        String codigo = sc.nextLine().trim();

        // Verificamos que no exista ya ese código
        if (buscarEstudiante(codigo) != null) {
            System.out.println("[!] Ya existe un estudiante con ese codigo.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();

        System.out.print("Promedio academico (ej: 3.8): ");
        double promedio = leerDouble();

        // Creamos el objeto y lo agregamos a la lista
        Estudiante nuevo = new Estudiante(codigo, nombre, promedio);
        estudiantes.add(nuevo);

        System.out.println("[OK] Estudiante " + nombre + " registrado.");
    }

    // Muestra todos los estudiantes registrados
    static void listarEstudiantes() {
        System.out.println("\n-- LISTA DE ESTUDIANTES --");

        if (estudiantes.size() == 0) {
            System.out.println("No hay estudiantes registrados.");
            return;
        }

        for (int i = 0; i < estudiantes.size(); i++) {
            System.out.println("\n  " + (i + 1) + ".");
            estudiantes.get(i).mostrarInfo();
        }
    }

    // Muestra todas las inscripciones de un estudiante específico
    static void verInscripcionesEstudiante(String codigoEstudiante) {
        Estudiante e = buscarEstudiante(codigoEstudiante);

        if (e == null) {
            System.out.println("[!] Estudiante no encontrado.");
            return;
        }

        System.out.println("\n-- INSCRIPCIONES DE " + e.getNombre() + " --");
        boolean hayAlguna = false;

        for (int i = 0; i < inscripciones.size(); i++) {
            Inscripcion ins = inscripciones.get(i);
            if (ins.getCodigoEstudiante().equalsIgnoreCase(codigoEstudiante)) {
                Actividad act = buscarActividad(ins.getCodigoActividad());
                String nombreAct = (act != null) ? act.getNombre() : ins.getCodigoActividad();
                System.out.println("  - " + nombreAct + " | Estado: " + ins.getEstado());
                hayAlguna = true;
            }
        }

        if (!hayAlguna) {
            System.out.println("  Sin inscripciones.");
        }
    }

        // SUBMENÚ ACTIVIDADES
        static void menuActividades() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n-- ACTIVIDADES --");
            System.out.println("1. Registrar actividad");
            System.out.println("2. Ver lista de actividades");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();

            switch (op) {
                case 1: registrarActividad(); break;
                case 2: listarActividades();  break;
                case 0: break;
                default: System.out.println("[!] Opcion no valida.");
            }
        }
    }

    // Registra una actividad nueva pidiendo datos al usuario
    static void registrarActividad() {
        System.out.println("\n-- REGISTRAR ACTIVIDAD --");

        System.out.print("Codigo: ");
        String codigo = sc.nextLine().trim();

        if (buscarActividad(codigo) != null) {
            System.out.println("[!] Ya existe una actividad con ese codigo.");
            return;
        }

        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();

        System.out.print("Categoria (Deporte / Cultura / Salud): ");
        String categoria = sc.nextLine().trim();

        // Validamos que la categoría sea una de las tres permitidas
        if (!categoria.equalsIgnoreCase("Deporte")
                && !categoria.equalsIgnoreCase("Cultura")
                && !categoria.equalsIgnoreCase("Salud")) {
            System.out.println("[!] Categoria invalida. Debe ser Deporte, Cultura o Salud.");
            return;
        }

        System.out.print("Horas que vale la actividad: ");
        double horas = leerDouble();

        System.out.print("Cupo maximo: ");
        int cupo = leerEntero();

        System.out.print("Hora de inicio (ej: 8 para 8am): ");
        int horaInicio = leerEntero();

        System.out.print("Hora de fin (ej: 10 para 10am): ");
        int horaFin = leerEntero();

        System.out.print("¿Es de alto impacto? requiere promedio >= 4.0 (s/n): ");
        String resp = sc.nextLine().trim();
        boolean esAltoImpacto = resp.equalsIgnoreCase("s");

        Actividad nueva = new Actividad(codigo, nombre, categoria, horas,
                cupo, horaInicio, horaFin, esAltoImpacto);
        actividades.add(nueva);

        System.out.println("[OK] Actividad '" + nombre + "' registrada.");
    }

    // Lista todas las actividades disponibles
    static void listarActividades() {
        System.out.println("\n-- LISTA DE ACTIVIDADES --");

        if (actividades.size() == 0) {
            System.out.println("No hay actividades registradas.");
            return;
        }

        for (int i = 0; i < actividades.size(); i++) {
            actividades.get(i).mostrarInfo();
        }
    }

        // SUBMENÚ INSCRIPCIONES
        static void menuInscripciones() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n-- INSCRIPCIONES --");
            System.out.println("1. Inscribir estudiante en actividad");
            System.out.println("2. Cancelar inscripcion");
            System.out.println("3. Completar actividad (sumar horas)");
            System.out.println("4. Calificar actividad");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();

            switch (op) {
                case 1: inscribirEstudiante();  break;
                case 2: cancelarInscripcion();  break;
                case 3: completarActividad();   break;
                case 4: calificarActividad();   break;
                case 0: break;
                default: System.out.println("[!] Opcion no valida.");
            }
        }
    }

    // Inscribe a un estudiante en una actividad.
    // Valida: existencia, alto impacto, choque de horario, cupo.
    // Si no hay cupo, lo pone en lista de espera.
    static void inscribirEstudiante() {
        System.out.println("\n-- INSCRIBIR ESTUDIANTE --");

        System.out.print("Codigo del estudiante: ");
        String codEst = sc.nextLine().trim();

        System.out.print("Codigo de la actividad: ");
        String codAct = sc.nextLine().trim();

        // 1. Buscar ambos
        Estudiante est = buscarEstudiante(codEst);
        Actividad  act = buscarActividad(codAct);

        if (est == null) {
            System.out.println("[!] Estudiante no encontrado.");
            return;
        }
        if (act == null) {
            System.out.println("[!] Actividad no encontrada.");
            return;
        }

        // 2. ¿Ya está inscrito en esta actividad?
        if (yaEstaInscrito(codEst, codAct)) {
            System.out.println("[!] El estudiante ya esta inscrito en esa actividad.");
            return;
        }

        // 3. ¿Actividad de alto impacto? → necesita promedio >= 4.0
        if (act.isEsAltoImpacto() && est.getPromedio() < 4.0) {
            System.out.println("[!] Esta actividad es de ALTO IMPACTO.");
            System.out.println("    Se requiere promedio >= 4.0");
            System.out.println("    El estudiante tiene: " + est.getPromedio());
            return;
        }

        // 4. ¿Hay choque de horario con otra inscripción activa?
        if (hayChoqueDeHorario(codEst, act)) {
            // El mensaje del choque ya se imprimió dentro del método
            return;
        }

        // 5. ¿Hay cupo?
        if (!act.hayCupo()) {
            // Sin cupo: intentamos agregar a lista de espera
            if (act.estaEnEspera(codEst)) {
                System.out.println("[!] El estudiante ya esta en la lista de espera.");
            } else {
                boolean agregado = act.agregarAEspera(codEst);
                if (agregado) {
                    System.out.println("[!] No hay cupo. " + est.getNombre()
                            + " fue agregado a la lista de espera. Posicion: "
                            + act.getTotalEnEspera());
                } else {
                    System.out.println("[!] No hay cupo ni espacio en lista de espera.");
                }
            }
            return;
        }

        // 6. Todo bien: creamos la inscripción
        Inscripcion nueva = new Inscripcion(codEst, codAct);
        inscripciones.add(nueva);
        act.ocuparCupo();

        System.out.println("[OK] " + est.getNombre()
                + " inscrito en '" + act.getNombre() + "' exitosamente.");
    }

    // Cancela la inscripción de un estudiante en una actividad.
    // Si fue tardía (< 2 días), aplica penalización de -2 horas.
    // Al liberar cupo, inscribe automáticamente al primero de la lista de espera.
    static void cancelarInscripcion() {
        System.out.println("\n-- CANCELAR INSCRIPCION --");

        System.out.print("Codigo del estudiante: ");
        String codEst = sc.nextLine().trim();

        System.out.print("Codigo de la actividad: ");
        String codAct = sc.nextLine().trim();

        System.out.print("¿Con cuantos dias de antelacion cancela? (0 = hoy mismo): ");
        int dias = leerEntero();

        Estudiante est = buscarEstudiante(codEst);
        Actividad  act = buscarActividad(codAct);

        if (est == null || act == null) {
            System.out.println("[!] Estudiante o actividad no encontrada.");
            return;
        }

        // Buscamos la inscripción activa
        Inscripcion ins = buscarInscripcionActiva(codEst, codAct);

        if (ins == null) {
            System.out.println("[!] No hay inscripcion activa para cancelar.");
            return;
        }

        // Cancelamos y liberamos cupo
        ins.cancelar(dias);
        act.liberarCupo();

        System.out.println("[OK] Inscripcion cancelada para " + est.getNombre()
                + " en '" + act.getNombre() + "'.");

        // ¿La cancelación fue tardía? → penalización
        if (ins.fueCancelacionTardia()) {
            System.out.println("[!] Cancelacion con solo " + dias + " dia(s). Penalizacion aplicada.");
            est.aplicarPenalizacion();
        } else {
            System.out.println("[OK] Cancelacion a tiempo (" + dias + " dias). Sin penalizacion.");
        }

        // ¿Hay alguien en lista de espera? → lo inscribimos automáticamente
        String siguienteCod = act.sacarPrimeroDeEspera();
        if (siguienteCod != null) {
            Estudiante enEspera = buscarEstudiante(siguienteCod);
            if (enEspera != null) {
                Inscripcion nuevaIns = new Inscripcion(siguienteCod, codAct);
                inscripciones.add(nuevaIns);
                act.ocuparCupo();
                System.out.println("[AUTO] " + enEspera.getNombre()
                        + " fue inscrito desde la lista de espera.");
            }
        }
    }

    // Marca una actividad como completada y suma las horas al estudiante.
    static void completarActividad() {
        System.out.println("\n-- COMPLETAR ACTIVIDAD --");

        System.out.print("Codigo del estudiante: ");
        String codEst = sc.nextLine().trim();

        System.out.print("Codigo de la actividad: ");
        String codAct = sc.nextLine().trim();

        Estudiante est = buscarEstudiante(codEst);
        Actividad  act = buscarActividad(codAct);

        if (est == null || act == null) {
            System.out.println("[!] Estudiante o actividad no encontrada.");
            return;
        }

        Inscripcion ins = buscarInscripcionActiva(codEst, codAct);

        if (ins == null) {
            System.out.println("[!] No hay inscripcion activa para completar.");
            return;
        }

        // Marcamos como completada y sumamos horas
        ins.completar();
        est.sumarHoras(act.getCategoria(), act.getHoras());

        System.out.println("[OK] Actividad completada. Se sumaron " + act.getHoras()
                + "h de " + act.getCategoria() + " a " + est.getNombre() + ".");
        System.out.printf("     Horas totales: %.1f%n", est.getHorasTotal());
    }

    // Permite a un estudiante calificar una actividad completada (1 a 5).
    static void calificarActividad() {
        System.out.println("\n-- CALIFICAR ACTIVIDAD --");

        System.out.print("Codigo del estudiante: ");
        String codEst = sc.nextLine().trim();

        System.out.print("Codigo de la actividad a calificar: ");
        String codAct = sc.nextLine().trim();

        System.out.print("Calificacion (1 a 5): ");
        int cal = leerEntero();

        if (cal < 1 || cal > 5) {
            System.out.println("[!] La calificacion debe estar entre 1 y 5.");
            return;
        }

        Actividad act = buscarActividad(codAct);
        if (act == null) {
            System.out.println("[!] Actividad no encontrada.");
            return;
        }

        // Solo puede calificar si la completó
        boolean completo = false;
        for (int i = 0; i < inscripciones.size(); i++) {
            Inscripcion ins = inscripciones.get(i);
            if (ins.getCodigoEstudiante().equalsIgnoreCase(codEst)
                    && ins.getCodigoActividad().equalsIgnoreCase(codAct)
                    && ins.estaCompletada()) {
                completo = true;
                break;
            }
        }

        if (!completo) {
            System.out.println("[!] Solo puedes calificar actividades que hayas completado.");
            return;
        }

        act.registrarCalificacion(cal);
        System.out.printf("[OK] Calificacion %d/5 registrada. Promedio actual: %.2f%n",
                cal, act.getPromedioSatisfaccion());
    }

        // SUBMENÚ REPORTES
        static void menuReportes() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n-- REPORTES --");
            System.out.println("1. Certificacion de grado de un estudiante");
            System.out.println("2. Satisfaccion de actividades");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();

            switch (op) {
                case 1:
                    System.out.print("Codigo del estudiante: ");
                    String cod = sc.nextLine().trim();
                    generarReporteGrado(cod);
                    break;
                case 2:
                    reporteSatisfaccion();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("[!] Opcion no valida.");
            }
        }
    }

    // Genera el reporte final de certificación de grado.
    // El estudiante es APTO si tiene >= 10h en cada categoría.
    static void generarReporteGrado(String codigoEstudiante) {
        Estudiante est = buscarEstudiante(codigoEstudiante);

        if (est == null) {
            System.out.println("[!] Estudiante no encontrado.");
            return;
        }

        System.out.println();
        System.out.println("============================================");
        System.out.println("     CERTIFICACION DE GRADO - BIENESTAR");
        System.out.println("============================================");
        System.out.println("  Estudiante : " + est.getNombre());
        System.out.println("  Codigo     : " + est.getCodigo());
        System.out.println("  Promedio   : " + est.getPromedio());
        System.out.println("--------------------------------------------");
        System.out.printf("  Horas Deporte : %.1f  (minimo 10)%n", est.getHorasDeporte());
        System.out.printf("  Horas Cultura : %.1f  (minimo 10)%n", est.getHorasCultura());
        System.out.printf("  Horas Salud   : %.1f  (minimo 10)%n", est.getHorasSalud());
        System.out.printf("  Horas TOTAL   : %.1f%n",              est.getHorasTotal());
        System.out.println("--------------------------------------------");

        // Verificamos cada condición
        boolean cumpleDeporte = est.getHorasDeporte() >= 10;
        boolean cumpleCultura = est.getHorasCultura() >= 10;
        boolean cumpleSalud   = est.getHorasSalud()   >= 10;

        System.out.println("  Deporte >= 10h : " + (cumpleDeporte ? "CUMPLE ✓" : "NO CUMPLE ✗"));
        System.out.println("  Cultura >= 10h : " + (cumpleCultura ? "CUMPLE ✓" : "NO CUMPLE ✗"));
        System.out.println("  Salud   >= 10h : " + (cumpleSalud   ? "CUMPLE ✓" : "NO CUMPLE ✗"));
        System.out.println("--------------------------------------------");

        // Resultado final
        if (cumpleDeporte && cumpleCultura && cumpleSalud) {
            System.out.println("  RESULTADO: *** APTO PARA GRADO ***");
        } else {
            System.out.println("  RESULTADO: *** NO APTO PARA GRADO ***");
            System.out.println("  Debe completar los requisitos faltantes.");
        }
        System.out.println("============================================");
        System.out.println();
    }

    // Muestra el promedio de satisfacción de todas las actividades
    static void reporteSatisfaccion() {
        System.out.println("\n-- SATISFACCION DE ACTIVIDADES --");

        if (actividades.size() == 0) {
            System.out.println("No hay actividades registradas.");
            return;
        }

        for (int i = 0; i < actividades.size(); i++) {
            Actividad act = actividades.get(i);
            if (act.getPromedioSatisfaccion() > 0) {
                System.out.printf("  [%s] %s -> Promedio: %.2f/5%n",
                        act.getCodigo(), act.getNombre(), act.getPromedioSatisfaccion());
            } else {
                System.out.println("  [" + act.getCodigo() + "] "
                        + act.getNombre() + " -> Sin calificaciones aun.");
            }
        }
    }

        // MÉTODOS DE BÚSQUEDA
    // Recorren los ArrayList buscando por código
    
    // Busca un estudiante por su código. Devuelve null si no existe.
    static Estudiante buscarEstudiante(String codigo) {
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                return estudiantes.get(i);
            }
        }
        return null;
    }

    // Busca una actividad por su código. Devuelve null si no existe.
    static Actividad buscarActividad(String codigo) {
        for (int i = 0; i < actividades.size(); i++) {
            if (actividades.get(i).getCodigo().equalsIgnoreCase(codigo)) {
                return actividades.get(i);
            }
        }
        return null;
    }

    // Busca una inscripción que esté activa entre un estudiante y una actividad.
    static Inscripcion buscarInscripcionActiva(String codEst, String codAct) {
        for (int i = 0; i < inscripciones.size(); i++) {
            Inscripcion ins = inscripciones.get(i);
            if (ins.getCodigoEstudiante().equalsIgnoreCase(codEst)
                    && ins.getCodigoActividad().equalsIgnoreCase(codAct)
                    && ins.estaActiva()) {
                return ins;
            }
        }
        return null;
    }

        // MÉTODOS DE VALIDACIÓN
    
    // Verifica si el estudiante ya tiene una inscripción activa en esa actividad
    static boolean yaEstaInscrito(String codEst, String codAct) {
        return buscarInscripcionActiva(codEst, codAct) != null;
    }

    // Verifica si la nueva actividad choca de horario con alguna inscripción
    // activa del estudiante. Imprime en consola con qué actividad choca.
    static boolean hayChoqueDeHorario(String codEst, Actividad nuevaAct) {
        for (int i = 0; i < inscripciones.size(); i++) {
            Inscripcion ins = inscripciones.get(i);

            // Solo revisamos inscripciones activas del mismo estudiante
            if (ins.getCodigoEstudiante().equalsIgnoreCase(codEst) && ins.estaActiva()) {

                Actividad actExistente = buscarActividad(ins.getCodigoActividad());

                if (actExistente != null) {
                    if (actExistente.chocanHorarios(nuevaAct.getHoraInicio(), nuevaAct.getHoraFin())) {
                        System.out.println("[!] Choque de horario con: "
                                + actExistente.getNombre()
                                + " (" + actExistente.getHoraInicio()
                                + ":00 - " + actExistente.getHoraFin() + ":00)");
                        return true;
                    }
                }
            }
        }
        return false;
    }

        // DATOS DE PRUEBA
    // Se cargan al inicio para poder probar el sistema rápido
        static void cargarDatosDePrueba() {
        System.out.println("\n[INFO] Cargando datos de prueba...");

        // Estudiantes
        estudiantes.add(new Estudiante("EST001", "Laura Gomez",    4.2));
        estudiantes.add(new Estudiante("EST002", "Carlos Ruiz",    3.5));
        estudiantes.add(new Estudiante("EST003", "Maria Torres",   4.5));

        // Actividades: (codigo, nombre, categoria, horas, cupo, horaInicio, horaFin, altoImpacto)
        actividades.add(new Actividad("DEP001", "Futbol sala",             "Deporte", 4, 2, 8,  10, false));
        actividades.add(new Actividad("DEP002", "Natacion",                "Deporte", 5, 5, 14, 16, false));
        actividades.add(new Actividad("DEP003", "Atletismo",               "Deporte", 4, 5, 10, 12, false));
        actividades.add(new Actividad("CUL001", "Danza folklorica",        "Cultura", 4, 5, 8,  10, false));
        actividades.add(new Actividad("CUL002", "Taller de teatro",        "Cultura", 5, 5, 16, 18, false));
        actividades.add(new Actividad("CUL003", "Viaje cultural Bogota",   "Cultura", 8, 3, 6,  20, true));
        actividades.add(new Actividad("SAL001", "Yoga",                    "Salud",   4, 5, 12, 14, false));
        actividades.add(new Actividad("SAL002", "Primeros auxilios",       "Salud",   5, 5, 8,  10, false));
        actividades.add(new Actividad("SAL003", "Nutricion",               "Salud",   4, 5, 14, 16, false));

        System.out.println("[INFO] 3 estudiantes y 9 actividades cargadas.\n");
    }

        // MÉTODOS DE LECTURA SEGUROS (evitan que el programa truene
    // si el usuario escribe letras en vez de números)
    
    // Lee un número entero de forma segura
    static int leerEntero() {
        while (true) {
            try {
                String linea = sc.nextLine().trim();
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.print("[!] Ingrese un numero valido: ");
            }
        }
    }

    // Lee un número decimal de forma segura (acepta coma o punto)
    static double leerDouble() {
        while (true) {
            try {
                String linea = sc.nextLine().trim();
                linea = linea.replace(",", ".");
                return Double.parseDouble(linea);
            } catch (NumberFormatException e) {
                System.out.print("[!] Ingrese un numero valido (ej: 3.8): ");
            }
        }
    }
}
