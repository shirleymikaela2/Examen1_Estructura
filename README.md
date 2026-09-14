# Examen1_Estructura

### Módulo de Enemigos y Cola de Gestión (`Enemy` y `ColaEnemigos`)

Este módulo implementa la entidad individual de combate de los enemigos y la estructura de datos lineal tipo cola (`Queue`) basada en `ArrayDeque` para administrar su flujo de aparición y desplazamiento a lo largo del mapa.

---

### 1. Clase `Enemy`

La clase `Enemy` modela a una unidad enemiga individual dentro del juego, encapsulando su posición en coordenadas continuas, atributos dinámicos de salud, velocidad de avance y su representación gráfica en pantalla.

#### Atributos Principales

* `x`: Coordenada horizontal actual del enemigo en el tablero (inicia fuera de la pantalla en `-20`).
* `y`: Coordenada vertical fija calculada en función de la fila del camino establecida en la configuración.
* `velocidad`: Magnitud de avance por cada actualización de turno.
* `vida`: Puntos de salud actuales del enemigo.
* `vidaMaxima`: Límite máximo de salud con el que se genera el enemigo.
* `vivo`: Bandera booleana que indica si el enemigo sigue activo o ha sido derrotado.

#### Métodos y Lógica Implementada

* **Constructor (`Enemy(int vida, double velocidad)`)**: Inicializa la posición inicial de entrada ($x = -20$), calcula la altura centrada en la celda del camino, asigna la vida máxima, la velocidad y establece el estado inicial como vivo.
* **`actualizar()`**: Incrementa la coordenada horizontal $x$ sumándole la velocidad asignada, siempre y cuando la bandera `vivo` sea verdadera.
* **`recibirDanio(int danio)`**: Aplica daño reduciendo la salud actual del enemigo. Si la vida desciende a cero o menos, ajusta la vida a `0` y cambia el estado de `vivo` a falso.
* **`llegoALaBase()`**: Evalúa si el enemigo ha alcanzado o superado la coordenada horizontal límite correspondiente a la base defensiva al final del tablero.
* **`dibujar(Graphics2D g2)`**: Renderiza de forma gráfica al enemigo en el panel utilizando un círculo de color rojo suave e imprime encima el texto con su barra de estado de vida (`vida/vidaMaxima`).
* **Getters (`getX`, `getY`, `getVida`, `isVivo`)**: Métodos de acceso público para consultar de manera segura los estados y coordenadas actuales de la unidad.

### 2. Clase `ColaEnemigos`

La clase `ColaEnemigos` implementa una estructura de datos lineal de tipo **Cola (Queue)** utilizando internamente un `ArrayDeque<Enemy>`, lo que permite gestionar las unidades enemigas bajo una disciplina FIFO (*First In, First Out*).

#### Atributos Principales

* `enemigos`: Instancia de `Queue<Enemy>` que almacena de manera secuencial los objetos de tipo enemigo activos en la estructura.

#### Operaciones y Métodos Implementados

* **Constructor (`ColaEnemigos()`)**: Inicializa la cola utilizando la implementación eficiente de doble cola `ArrayDeque`.
* **`agregarEnemigo(Enemy enemigo)`**: Inserta un nuevo enemigo al final de la cola (`offer`) tras validar que el objeto no sea nulo.
* **`obtenerPrimero()`**: Consulta y retorna el primer enemigo de la cola sin removerlo de la estructura (`peek`).
* **`eliminarPrimero()`**: Extrae y retorna el primer enemigo de la cola, removiéndolo de la estructura (`poll`).
* **`estaVacia()`**: Retorna un valor booleano indicando si la cola se encuentra sin elementos (`isEmpty`).
* **`cantidad()`**: Retorna el número total de enemigos que se encuentran actualmente en la cola (`size`).
* **`limpiar()`**: Vacía por completo la estructura eliminando todos los elementos almacenados (`clear`).
* **`obtenerEnemigos()`**: Genera y retorna una copia de los elementos en forma de `Collection<Enemy>` utilizando un `ArrayList`, útil para consultas y recorridos seguros sin comprometer la integridad interna de la cola.
* **`iterator()`**: Retorna un iterador seguro para recorrer y permitir la eliminación de elementos de forma controlada sobre la cola.

**Archivos desarrollados:** `Enemy.java`, `ColaEnemigos.java`.

**Estructura utilizada:** Cola (`Queue`) basada en `ArrayDeque` para la gestión de elementos y atributos coordenados para la entidad.
