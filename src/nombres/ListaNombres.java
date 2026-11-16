package nombres;

/**
 * Arreglo de nombres de personas que se ordena con Insertion Sort implementado manualmente
 * (sin Arrays.sort, Collections.sort ni List.sort).
 */
public class ListaNombres {

    /** Arreglo de nombres. La clase trabaja sobre una copia propia. */
    private final String[] nombres;

    public ListaNombres(String[] nombres) {
        this.nombres = copiar(nombres);
    }

    /** Cantidad de nombres en la lista. */
    public int tamanio() {
        return nombres.length;
    }

    /** Nombre guardado en la posicion indicada. */
    public String obtener(int posicion) {
        return nombres[posicion];
    }

    /** Copia del arreglo, para que nadie modifique la lista desde afuera. */
    public String[] comoArreglo() {
        return copiar(nombres);
    }

    /** Lista en el formato [Ana, Luis, Sofia]. */
    public String formatear() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < nombres.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(nombres[i]);
        }
        return sb.append("]").toString();
    }

    /**
     * Compara dos nombres ignorando mayusculas y minusculas (y tildes).
     *
     * @return negativo si a va antes que b, 0 si son iguales, positivo si a va despues
     */
    public static int compararNombres(String a, String b) {
        return normalizar(a).compareTo(normalizar(b));
    }

    /**
     * Version del nombre que se usa SOLO para comparar: en minusculas y sin tildes,
     * para que "ana", "Ana" y "ANA" sean iguales y "Angela" quede junto a "Andres".
     * La enie se reemplaza por "n~" para que quede entre la n y la o, como en espanol.
     * El nombre guardado en el arreglo no se modifica.
     */
    static String normalizar(String nombre) {
        if (nombre == null) {
            return "";
        }
        String minusculas = nombre.trim().toLowerCase(java.util.Locale.ROOT).replace("ñ", "n~");
        String separado = java.text.Normalizer.normalize(minusculas, java.text.Normalizer.Form.NFD);
        return separado.replaceAll("\\p{M}", "");
    }

    /** Copia manual del arreglo. Un arreglo null se trata como lista vacia. */
    private static String[] copiar(String[] origen) {
        if (origen == null) {
            return new String[0];
        }
        String[] copia = new String[origen.length];
        for (int i = 0; i < origen.length; i++) {
            copia[i] = origen[i];
        }
        return copia;
    }
}
