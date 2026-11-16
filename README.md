# Lista de nombres

Ejercicio 3 de la actividad **Algoritmos de ordenamiento** (Estructuras de Datos, Unidad 2 –
Universidad de Cartagena).

**Autor:** José Luis Arteta Buelvas

## Descripción

El programa guarda en un arreglo `String[]` los nombres de un grupo de personas y los ordena
con **Insertion Sort implementado manualmente** (sin `Arrays.sort()`, `Collections.sort()`,
`List.sort()` ni similares):

- Orden alfabético de **A a Z**.
- Orden posterior de **Z a A**, con el mismo algoritmo.
- La comparación **ignora mayúsculas y minúsculas** (`"camila"` y `"Camila"` son iguales).
  También ignora tildes y ubica la ñ entre la n y la o.
- Muestra el arreglo **antes**, **después de cada inserción significativa** y **después**.
- Cuenta **comparaciones** y **desplazamientos**.
- Compara el comportamiento con un arreglo **ya ordenado, casi ordenado, desordenado e inverso**.

## Estructura

```
src/nombres/
  ListaNombres.java   Arreglo de nombres, Insertion Sort, comparación y contadores
  Main.java           Menú interactivo y modo demo
test/nombres/
  PruebasListaNombres.java   Casos de prueba (sin librerías externas)
```

## Cómo funciona Insertion Sort

Se recorre el arreglo desde la segunda posición. La parte izquierda `[0..i-1]` siempre está
ordenada; el nombre de la posición `i` se guarda en `actual` y los nombres de la izquierda que
deben ir después de él se corren una posición a la derecha. Cuando aparece uno que no debe
correrse, `actual` se inserta en el hueco.

```java
for (int i = 1; i < nombres.length; i++) {
    String actual = nombres[i];
    int j = i - 1;
    while (j >= 0 && debeIrAntes(actual, nombres[j], ascendente)) {
        nombres[j + 1] = nombres[j];   // desplazamiento
        j--;
    }
    nombres[j + 1] = actual;           // insercion
}
```

Para A-Z, `debeIrAntes` es verdadero si `actual` es alfabéticamente **menor**; para Z-A, si es
**mayor**. Es el único cambio entre los dos sentidos.

**Inserción significativa:** aquella en la que el nombre sí cambió de posición (`j + 1 != i`).
Si el nombre ya estaba en su lugar, el arreglo no cambia y no se muestra.

**Valores repetidos:** con nombres iguales `debeIrAntes` retorna `false`, así que el repetido
no salta por encima del otro. Insertion Sort es **estable**: los iguales conservan su orden
original (por ejemplo, `Camila` sigue antes que `camila`).

**Criterio de comparación:** cada nombre se convierte a minúsculas y sin tildes solo para
comparar (`compararNombres`); el arreglo guarda los nombres tal como se escribieron.

## Complejidad

| Caso | Comparaciones | Complejidad |
|---|---|---|
| Mejor: ya ordenado | n − 1 | O(n) |
| Casi ordenado | n − 1 + d (d = nombres fuera de lugar) | ≈ O(n) |
| Promedio: desordenado | ≈ n² / 4 | O(n²) |
| Peor: orden inverso | n(n − 1) / 2 | O(n²) |

Espacio adicional O(1): ordena dentro del mismo arreglo.

**Arreglo casi ordenado:** casi todos los nombres ya son mayores que su vecino de la
izquierda, así que el `while` termina en la primera comparación y no hay desplazamientos.
Solo los pocos nombres fuera de lugar se mueven, y se mueven poco. Por eso Insertion Sort es
casi lineal en este caso y es de los mejores algoritmos para datos casi ordenados.

Resultados del programa con n = 12 (opción 4):

| Estado inicial | Comparaciones | Desplazamientos |
|---|---|---|
| Ya ordenado | 11 | 0 |
| Casi ordenado | 13 | 2 |
| Lista de ejemplo | 42 | 32 |
| Orden inverso | 66 | 64 |

Por eso ordenar de Z a A justo después de A a Z es el **peor caso**: la lista llega en orden
exactamente contrario y hay 66 comparaciones = 12·11/2.

## Cómo ejecutar

Requisitos: JDK 11 o superior (probado con JDK 17).

En PowerShell, desde la carpeta del proyecto:

```powershell
# Si java no está en el PATH, apuntar a un JDK 17
$env:JAVA_HOME="$env:USERPROFILE\.jdks\ms-17.0.20.1"
$env:Path="$env:JAVA_HOME\bin;$env:Path"

# Compilar
javac -encoding UTF-8 -d out src\nombres\*.java test\nombres\*.java

# Menú interactivo
java -cp out nombres.Main

# Modo demo (ejecuta los casos de ejemplo sin menú)
java -cp out nombres.Main demo

# Casos de prueba
java -cp out nombres.PruebasListaNombres
```

En Linux/macOS los comandos son iguales cambiando `\` por `/`.

## Menú

```
1. Mostrar lista actual
2. Ordenar de A a Z (paso a paso)
3. Ordenar de Z a A (paso a paso)
4. Comportamiento con arreglo casi ordenado
5. Cargar una nueva lista de nombres
6. Restaurar lista de ejemplo
0. Salir
```

## Datos de ejemplo

```
Antes: [Valentina, andres, Camila, Sofia, mateo, Angela, Daniel, camila, Lucia, Sebastian, Isabella, Daniel]
A-Z:   [andres, Angela, Camila, camila, Daniel, Daniel, Isabella, Lucia, mateo, Sebastian, Sofia, Valentina]
Z-A:   [Valentina, Sofia, Sebastian, mateo, Lucia, Isabella, Daniel, Daniel, Camila, camila, Angela, andres]
```

Segundo caso (casi ordenado, solo `Gabriel` y `Fernando` intercambiados):

```
Antes: [Ana, beatriz, Carlos, Daniel, Elena, Gabriel, Fernando, hugo, Ines, Juan]
A-Z:   [Ana, beatriz, Carlos, Daniel, Elena, Fernando, Gabriel, hugo, Ines, Juan]
       10 comparaciones, 1 desplazamiento, 1 inserción significativa de 9
```
