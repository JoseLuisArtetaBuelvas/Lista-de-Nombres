package nombres;

/**
 * Casos de prueba de la Lista de nombres (sin librerias externas).
 *
 * Ejecutar:
 *   java -cp out nombres.PruebasListaNombres
 */
public class PruebasListaNombres {

    private static int aprobadas = 0;
    private static int fallidas = 0;

    public static void main(String[] args) {
        System.out.println("== Comparacion ignorando mayusculas y minusculas ==");
        verificar("Ana == ana", ListaNombres.compararNombres("Ana", "ana") == 0, true);
        verificar("ANA == ana", ListaNombres.compararNombres("ANA", "ana") == 0, true);
        verificar("ana va antes que Beatriz", ListaNombres.compararNombres("ana", "Beatriz") < 0, true);
        verificar("zoe va despues que Mateo", ListaNombres.compararNombres("zoe", "Mateo") > 0, true);
        verificar("con tilde: Angela == angela con tilde",
                ListaNombres.compararNombres("Angela", "Ángela") == 0, true);
        verificar("enie: Nubia < Nunez con enie < Oscar",
                ListaNombres.compararNombres("Nubia", "Nuñez") < 0
                        && ListaNombres.compararNombres("Nuñez", "Oscar") < 0, true);

        System.out.println("== Orden A-Z ==");
        String[] ejemplo = {"Valentina", "andres", "Camila", "Sofia", "mateo", "Angela",
                            "Daniel", "camila", "Lucia", "Sebastian", "Isabella", "Daniel"};
        ListaNombres lista = new ListaNombres(ejemplo);
        lista.ordenarAZ();
        verificarArreglo("resultado A-Z", lista.comoArreglo(), new String[] {
            "andres", "Angela", "Camila", "camila", "Daniel", "Daniel",
            "Isabella", "Lucia", "mateo", "Sebastian", "Sofia", "Valentina"});
        verificar("estaOrdenadaAZ", lista.estaOrdenadaAZ(), true);
        verificar("arreglo original intacto", ejemplo[0], "Valentina");

        System.out.println("== Orden Z-A (despues de A-Z) ==");
        lista.ordenarZA();
        verificarArreglo("resultado Z-A", lista.comoArreglo(), new String[] {
            "Valentina", "Sofia", "Sebastian", "mateo", "Lucia", "Isabella",
            "Daniel", "Daniel", "Camila", "camila", "Angela", "andres"});
        verificar("estaOrdenadaZA", lista.estaOrdenadaZA(), true);
        verificar("venia en orden inverso: peor caso n(n-1)/2", lista.getComparaciones(), 66);

        System.out.println("== Estabilidad con nombres iguales ==");
        ListaNombres iguales = new ListaNombres(new String[] {"camila", "Beto", "Camila", "CAMILA"});
        iguales.ordenarAZ();
        verificarArreglo("conserva el orden original de los iguales", iguales.comoArreglo(),
                new String[] {"Beto", "camila", "Camila", "CAMILA"});

        System.out.println("== Mejor caso: ya ordenado ==");
        ListaNombres ordenada = new ListaNombres(new String[] {"Ana", "Beto", "Carla", "Diana", "Elsa"});
        ordenada.ordenarAZ();
        verificar("comparaciones = n - 1", ordenada.getComparaciones(), 4);
        verificar("desplazamientos = 0", ordenada.getDesplazamientos(), 0);
        verificar("ninguna insercion significativa", ordenada.getInsercionesSignificativas(), 0);

        System.out.println("== Casi ordenado ==");
        ListaNombres casi = new ListaNombres(new String[] {
            "Ana", "beatriz", "Carlos", "Daniel", "Elena", "Gabriel", "Fernando", "hugo", "Ines", "Juan"});
        casi.ordenarAZ();
        verificar("queda ordenado", casi.estaOrdenadaAZ(), true);
        verificar("comparaciones (n - 1) + 1", casi.getComparaciones(), 10);
        verificar("un solo desplazamiento", casi.getDesplazamientos(), 1);
        verificar("una insercion significativa", casi.getInsercionesSignificativas(), 1);

        System.out.println("== Peor caso: orden inverso ==");
        ListaNombres inversa = new ListaNombres(new String[] {"Elsa", "Diana", "Carla", "Beto", "Ana"});
        inversa.ordenarAZ();
        verificar("comparaciones = n(n-1)/2", inversa.getComparaciones(), 10);
        verificar("desplazamientos = n(n-1)/2", inversa.getDesplazamientos(), 10);

        System.out.println("== Casos borde ==");
        ListaNombres vacia = new ListaNombres(new String[0]);
        vacia.ordenarAZ();
        verificar("lista vacia", vacia.tamanio(), 0);
        ListaNombres nula = new ListaNombres(null);
        nula.ordenarZA();
        verificar("arreglo null = lista vacia", nula.tamanio(), 0);
        ListaNombres uno = new ListaNombres(new String[] {"Ana"});
        uno.ordenarAZ();
        verificar("un solo nombre: 0 comparaciones", uno.getComparaciones(), 0);
        ListaNombres espacios = new ListaNombres(new String[] {"  luis", "Ana"});
        espacios.ordenarAZ();
        verificar("ignora espacios al inicio", espacios.obtener(0), "Ana");

        System.out.println();
        System.out.println("Aprobadas: " + aprobadas + "  |  Fallidas: " + fallidas);
        if (fallidas > 0) {
            System.exit(1);
        }
    }

    private static void verificar(String descripcion, Object obtenido, Object esperado) {
        if (esperado.equals(obtenido)) {
            aprobadas++;
            System.out.println("  OK    " + descripcion);
        } else {
            fallidas++;
            System.out.println("  FALLA " + descripcion + " -> esperado: " + esperado + ", obtenido: " + obtenido);
        }
    }

    private static void verificarArreglo(String descripcion, String[] obtenido, String[] esperado) {
        boolean iguales = obtenido.length == esperado.length;
        for (int i = 0; iguales && i < esperado.length; i++) {
            iguales = esperado[i].equals(obtenido[i]);
        }
        verificar(descripcion, iguales, true);
        if (!iguales) {
            System.out.println("        obtenido: " + String.join(", ", obtenido));
        }
    }
}
