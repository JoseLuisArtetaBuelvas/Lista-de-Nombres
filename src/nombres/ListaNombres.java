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
