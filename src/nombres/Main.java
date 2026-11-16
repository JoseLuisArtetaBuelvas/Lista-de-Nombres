package nombres;

import java.util.Scanner;

/**
 * Programa principal de la Lista de nombres.
 *
 * Uso:
 *   java -cp out nombres.Main         -> menu interactivo
 *   java -cp out nombres.Main demo    -> ejecuta los casos de ejemplo sin menu
 */
public class Main {

    /**
     * Nombres de ejemplo. Hay mayusculas y minusculas mezcladas ("Camila" y "camila",
     * "andres", "mateo") y un nombre repetido ("Daniel"), a proposito.
     */
    private static final String[] NOMBRES_EJEMPLO = {
        "Valentina", "andres", "Camila", "Sofia", "mateo", "Angela",
        "Daniel", "camila", "Lucia", "Sebastian", "Isabella", "Daniel"
    };

    private static ListaNombres lista = new ListaNombres(NOMBRES_EJEMPLO);
    private static final Scanner entrada = new Scanner(System.in, "UTF-8");

    /** Segundo caso de prueba: un arreglo casi ordenado (solo "Gabriel" y "Fernando" estan intercambiados). */
    private static final String[] NOMBRES_CASI_ORDENADOS = {
        "Ana", "beatriz", "Carlos", "Daniel", "Elena", "Gabriel", "Fernando", "hugo", "Ines", "Juan"
    };

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("demo")) {
            ejecutarDemo();
            return;
        }

        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Seleccione una opcion: ");
            System.out.println();
            switch (opcion) {
                case 1:
                    mostrarLista();
                    break;
                case 2:
                    ordenar(true);
                    break;
                case 3:
                    ordenar(false);
                    break;
                case 4:
                    compararCasos();
                    break;
                case 5:
                    cargarNombres();
                    break;
                case 6:
                    lista = new ListaNombres(NOMBRES_EJEMPLO);
                    System.out.println("Se restauro la lista de ejemplo.");
                    mostrarLista();
                    break;
                case 0:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opcion no valida. Intente de nuevo.");
            }
            System.out.println();
        } while (opcion != 0);
    }

    /** Ejecuta los casos de ejemplo uno tras otro, sin pedir datos. */
    private static void ejecutarDemo() {
        System.out.println("===== DEMO: LISTA DE NOMBRES - INSERTION SORT =====");
        System.out.println();
        System.out.println("CASO 1: lista de ejemplo desordenada, con mayusculas, minusculas y repetidos");
        mostrarLista();
        System.out.println();
        ordenar(true);
        System.out.println();
        ordenar(false);
        System.out.println();

        System.out.println("CASO 2: arreglo casi ordenado (solo dos nombres fuera de lugar)");
        lista = new ListaNombres(NOMBRES_CASI_ORDENADOS);
        mostrarLista();
        System.out.println();
        ordenar(true);
        System.out.println();

        System.out.println("CASO 3: comparacion segun el estado inicial (lista de ejemplo)");
        lista = new ListaNombres(NOMBRES_EJEMPLO);
        compararCasos();
    }

    private static void mostrarMenu() {
        System.out.println("===== LISTA DE NOMBRES - INSERTION SORT =====");
        System.out.println("1. Mostrar lista actual");
        System.out.println("2. Ordenar de A a Z (paso a paso)");
        System.out.println("3. Ordenar de Z a A (paso a paso)");
        System.out.println("4. Comportamiento con arreglo casi ordenado");
        System.out.println("5. Cargar una nueva lista de nombres");
        System.out.println("6. Restaurar lista de ejemplo");
        System.out.println("0. Salir");
    }

    private static void mostrarLista() {
        System.out.println("Lista actual (" + lista.tamanio() + " nombres):");
        System.out.println("  " + lista.formatear());
    }

    /** Ordena la lista actual mostrando el antes, cada insercion significativa y el despues. */
    private static void ordenar(boolean ascendente) {
        String sentido = ascendente ? "A-Z" : "Z-A";
        System.out.println("--- Insertion Sort " + sentido + " ---");
        System.out.println("Antes:   " + lista.formatear());
        System.out.println();

        if (ascendente) {
            lista.ordenarAZ();
        } else {
            lista.ordenarZA();
        }

        System.out.println("Inserciones significativas (nombre, posicion origen -> destino, arreglo):");
        if (lista.getPasos().isEmpty()) {
            System.out.println("  Ninguna: todos los nombres ya estaban en su lugar.");
        }
        int numero = 1;
        for (String paso : lista.getPasos()) {
            System.out.printf("  %2d. %s%n", numero++, paso);
        }

        System.out.println();
        System.out.println("Despues: " + lista.formatear());
        mostrarContadores(lista);
    }

    private static void mostrarContadores(ListaNombres l) {
        int n = l.tamanio();
        int peorCaso = n * (n - 1) / 2;
        System.out.println("Comparaciones: " + l.getComparaciones()
                + "  |  Desplazamientos: " + l.getDesplazamientos()
                + "  |  Inserciones significativas: " + l.getInsercionesSignificativas()
                + " de " + Math.max(n - 1, 0));
        System.out.println("(Referencia con n = " + n + ": mejor caso " + Math.max(n - 1, 0)
                + " comparaciones, peor caso " + peorCaso + ")");
    }

    /**
     * Ordena de A a Z cuatro versiones de los mismos nombres para ver como cambia el
     * trabajo de Insertion Sort segun que tan ordenado este el arreglo de entrada.
     */
    private static void compararCasos() {
        ListaNombres base = new ListaNombres(lista.comoArreglo());
        base.ordenarAZ();
        String[] ordenado = base.comoArreglo();

        String[] casiOrdenado = base.comoArreglo();
        // Casi ordenado: se intercambian solo dos pares de nombres vecinos.
        intercambiar(casiOrdenado, 1, 2);
        if (casiOrdenado.length > 5) {
            intercambiar(casiOrdenado, casiOrdenado.length - 3, casiOrdenado.length - 2);
        }

        ListaNombres inverso = new ListaNombres(ordenado);
        inverso.ordenarZA();

        System.out.println("--- Insertion Sort A-Z segun el estado inicial (n = " + ordenado.length + ") ---");
        System.out.printf("%-17s %13s %16s%n", "Estado inicial", "Comparaciones", "Desplazamientos");
        filaCaso("Ya ordenado", ordenado);
        filaCaso("Casi ordenado", casiOrdenado);
        filaCaso("Lista actual", lista.comoArreglo());
        filaCaso("Orden inverso", inverso.comoArreglo());
        System.out.println();
        System.out.println("Casi ordenado: cada nombre se compara con su vecino de la izquierda y casi");
        System.out.println("siempre ya esta en su lugar, asi que el while termina enseguida. El trabajo");
        System.out.println("crece como O(n + d), donde d es la cantidad de nombres fuera de lugar:");
        System.out.println("practicamente lineal. En orden inverso cada nombre recorre toda la parte");
        System.out.println("ordenada: peor caso O(n^2).");
    }

    private static void filaCaso(String titulo, String[] datos) {
        ListaNombres l = new ListaNombres(datos);
        l.ordenarAZ();
        System.out.printf("%-17s %13d %16d%n", titulo, l.getComparaciones(), l.getDesplazamientos());
    }

    private static void intercambiar(String[] a, int i, int j) {
        if (i >= 0 && j < a.length) {
            String temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    /** Lee nombres separados por comas. Ignora los vacios. */
    private static void cargarNombres() {
        System.out.println("Escriba los nombres separados por comas (ej: Ana, luis, Sofia):");
        String linea = entrada.nextLine();
        String[] partes = linea.split(",");

        int validos = 0;
        for (String parte : partes) {
            if (!parte.trim().isEmpty()) {
                validos++;
            }
        }
        if (validos == 0) {
            System.out.println("No se ingreso ningun nombre. La lista no cambio.");
            return;
        }

        String[] nuevos = new String[validos];
        int k = 0;
        for (String parte : partes) {
            if (!parte.trim().isEmpty()) {
                nuevos[k++] = parte.trim();
            }
        }
        lista = new ListaNombres(nuevos);
        System.out.println("Lista cargada.");
        mostrarLista();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = entrada.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Debe escribir un numero entero.");
            }
        }
    }
}
