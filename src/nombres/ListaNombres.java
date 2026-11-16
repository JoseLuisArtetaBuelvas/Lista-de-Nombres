package nombres;

import java.text.Normalizer;
import java.util.Locale;

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

    /** Ordena la lista alfabeticamente de A a Z con Insertion Sort. */
    public void ordenarAZ() {
        // Se recorre desde la segunda posicion: la parte izquierda [0..i-1] ya esta ordenada.
        for (int i = 1; i < nombres.length; i++) {
            String actual = nombres[i];   // el nombre que se va a insertar
            int j = i - 1;

            // Mientras el nombre de la izquierda vaya DESPUES del actual, se corre una posicion
            // a la derecha para abrir espacio.
            while (j >= 0 && compararNombres(actual, nombres[j]) < 0) {
                nombres[j + 1] = nombres[j];
                j--;
            }

            // Se inserta el nombre en el hueco que quedo.
            nombres[j + 1] = actual;
        }
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
        String minusculas = nombre.trim().toLowerCase(Locale.ROOT).replace("ñ", "n~");
        String separado = Normalizer.normalize(minusculas, Normalizer.Form.NFD);
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
