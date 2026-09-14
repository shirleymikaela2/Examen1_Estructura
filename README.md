# Examen1_Estructura
# Tower Defense en Java

Proyecto desarrollado para la asignatura de **Estructura de Datos**.  
Consiste en un juego básico de defensa de torres: los enemigos avanzan por un camino y el jugador coloca torres para impedir que lleguen a la base.

## Integrantes

- Shirley Mikaela
- Andrés
- Josue Torres
- Cris
- Lenin
- Héctor

## Objetivo

Aplicar estructuras de datos en Java mediante un juego Tower Defense, utilizando colas para gestionar enemigos y pilas para las acciones de deshacer y rehacer.

## Específicos:

Implementar una cola circular, una cola FIFO y pilas para controlar el recorrido de los enemigos (Cozy), el orden de las oleadas y las operaciones de deshacer y rehacer.

Aplicar estructuras de datos secuenciales para gestionar el estado del juego: enemigos activos, torres colocadas, proyectiles en vuelo y el historial de acciones.

Integrar las estructuras de datos desarrolladas en una interfaz gráfica funcional que permita simular, visualizar y comprobar el comportamiento del juego.

## Funcionalidades

- Colocar torres en el tablero.
- Detectar enemigos dentro del alcance de una torre.
- Disparar proyectiles a los enemigos.
- Controlar vida de enemigos, monedas y vida de la base.
- Crear oleadas de enemigos.
- Deshacer y rehacer la colocación de torres.
- Mostrar el juego mediante una interfaz gráfica en Java Swing.

## Estructuras de datos utilizadas

| Estructura | Uso en el proyecto |
|---|---|
| Cola FIFO | Organiza los enemigos que aparecen en cada oleada. |
| Pila LIFO | Permite deshacer y rehacer acciones de colocar torres. |
| Lista | Guarda las torres y proyectiles activos durante el juego. |

## Clases del proyecto

| Clase | Responsable | Descripción |
|---|---|---|
| `Main.java` | Shirley | Es el punto de inicio del programa. Ejecuta la aplicación y crea la interfaz gráfica. |
| `Config.java` | Shirley | Contiene las constantes del juego: tamaño del tablero, monedas iniciales, costo, daño, alcance y vida de la base. |
| `Enemy.java` | Andrés | Representa a cada enemigo. Controla su posición, velocidad, vida, movimiento y daño recibido. |
| `ColaEnemigos.java` | Andrés | Implementa una cola para organizar el orden en que aparecen los enemigos durante las oleadas. |
| `Tower.java` | Josue | Representa una torre colocada por el jugador. Busca enemigos dentro de su alcance y crea proyectiles para atacarlos. |
| `Projectile.java` | Josue | Representa el disparo de una torre. Se mueve hacia un enemigo y le aplica daño al alcanzarlo. |
| `UI.java` | Cris | Construye la interfaz gráfica con Java Swing. Muestra el tablero, botones, información de monedas y vida. |
| `Accion.java` | Lenin | Guarda la información de una acción realizada por el jugador, por ejemplo, colocar una torre. |
| `PilaAcciones.java` | Lenin | Implementa una pila enlazada para guardar acciones y permitir las opciones de deshacer y rehacer. |
| `Game.java` | Héctor | Controla la lógica principal del juego: oleadas, enemigos, torres, proyectiles, monedas, vida y acciones. |
| `Utils.java` | Héctor | Incluye métodos auxiliares, como el cálculo de distancia entre una torre y un enemigo. |

## Organización del proyecto

```text
Examen1_Estructura/
├── src/
│   └── defensatorres/
│       ├── Main.java
│       ├── Config.java
│       ├── Enemy.java
│       ├── ColaEnemigos.java
│       ├── Tower.java
│       ├── Projectile.java
│       ├── UI.java
│       ├── Accion.java
│       ├── PilaAcciones.java
│       ├── Game.java
│       └── Utils.java
├── .gitignore
└── README.md
```

## Tecnologías utilizadas

- Java 17 o superior
- Java Swing
- Visual Studio Code
- Git y GitHub

# Examen1_Estructura - Tower Defense (Java)

Este proyecto implementa un simulador de defensa de base (Tower Defense) en **Java**, donde el jugador debe proteger una base contra oleadas de enemigos llamados **Cozy**. El sistema utiliza estructuras de datos personalizadas (colas circulares, colas FIFO, pilas LIFO) para gestionar el combate, las oleadas y el historial de acciones.

---

## Paso 1. Análisis del problema y decisiones de diseño

El sistema simula la defensa de una base contra oleadas de enemigos llamados **Cozy**. Los Cozy aparecen en oleadas, recorren una ruta predefinida a través de waypoints y pierden HP cuando una torre dentro de su alcance les dispara un proyectil. Si un Cozy llega al final de la ruta, disminuye la vida del jugador.

| Decisión | Valor adoptado |
|----------|----------------|
| Mapa | 800×600 píxeles, cuadrícula de 40×40 (20 columnas × 15 filas) |
| Entrada / Fuente | (0, 100) / (800, 500) |
| Torres | Básica, Sniper y Rápida |
| Tipos de Cozy | Cozy (normal) y BossCozy (jefe) |
| Oleadas | 12, con cantidad y HP crecientes |
| Quantum | 1 frame = 1/60 s (FPS = 60) |
| Ruta | Camino predefinido por waypoints (lista secuencial) |

---

## Paso 2. Arquitectura de la solución

La solución separa la presentación, la lógica y las estructuras de datos en módulos independientes, con una clara división de responsabilidades por clase:

| Capa | Clases / Archivos | Responsabilidad |
|------|-------------------|-----------------|
| Presentación | `Main.java`, `UI.java` | Ventana, HUD, menú, botones, clic para poner torres y dibujo del juego. |
| Lógica | `Game.java` | Coordinación del juego, oleadas, economía, colisiones, victoria/derrota. |
| Dominio | `Enemy.java`, `Tower.java`, `Projectile.java` | Objetos principales del juego: enemigos, torres y proyectiles. |
| Estructuras | `ColaEnemigos.java`, `PilaAcciones.java`, `Accion.java`, `Utils.java` | Cola FIFO para enemigos, pilas LIFO para undo/redo, y utilidades geométricas. |
| Configuración | `Config.java` | Constantes, rutas, tipos de torres y oleadas. |
| Documentación | `PRUEBAS_MANUALES.md` | Registro de pruebas manuales y verificación. |

---

## Paso 3. TDA y estructuras utilizadas

Un **Tipo de Dato Abstracto (TDA)** define el estado permitido, las operaciones públicas y las reglas que deben cumplirse, sin obligar al usuario a conocer la representación interna.

| Estructura | Comportamiento | Aplicación | Clase Java |
|------------|----------------|------------|------------|
| `CircularQueue` | FIFO circular | Registra a los Cozy activos en el campo de batalla | (Integrada en `Game.java` o `ColaEnemigos.java`) |
| `ColaEnemigos` | FIFO lineal | Orden de aparición de enemigos en cada oleada | `ColaEnemigos.java` |
| `PilaAcciones` | LIFO | Deshacer y rehacer colocaciones de torres | `PilaAcciones.java` |
| `ArrayList` | Lista secuencial | Almacenar torres, proyectiles, enemigos y waypoints | `java.util.ArrayList` |

Estas estructuras permiten organizar correctamente las diferentes operaciones del simulador. La cola circular mantiene a los Cozy activos dentro del ciclo de combate, la cola FIFO controla el orden de aparición de los enemigos, las pilas permiten gestionar las acciones de deshacer y rehacer y las listas secuenciales almacenan los elementos principales del juego.

---

## Paso 4. Relaciones entre requisitos y código

| Requisito | Aplicación | Archivo principal |
|-----------|------------|-------------------|
| Usar Cozy | Clase `Enemy` (con atributos de Cozy y BossCozy) | `Enemy.java` |
| Actualizar HP por quantum | Bucle sobre `ColaEnemigos` o `CircularQueue` | `Game.java` |
| Retirar si HP = 0 | No se vuelve a encolar el objeto | `Game.java` |
| Oleadas FIFO | `ColaEnemigos` con `encolar`/`desencolar` | `ColaEnemigos.java`, `Game.java` |
| Deshacer / rehacer | Dos pilas (`PilaAcciones` para undo y redo) | `PilaAcciones.java`, `Game.java` |
| Colocar y mejorar | Patrón Command reversible (`Accion.java`) | `Accion.java`, `Game.java` |
| Búsqueda de rutas | Waypoints predefinidos y validación | `Config.java`, `Utils.java` |
| Interfaz | Java Swing / AWT y bucle principal | `Main.java`, `UI.java` |

---

## Paso 5. Crear el modelo `Enemy` y `Cozy`

La clase `Enemy` identifica cada enemigo mediante su posición (x, y) y su cola de waypoints. Almacena su HP máximo, HP actual, velocidad y frames de animación. El daño nunca permite HP negativos y la velocidad determina cuántos píxeles recorre por frame.

```java
public class Enemy {
    private Queue<int[]> pathQueue;
    private int x, y;
    private int[] target;
    private int health;
    private int maxHealth;
    private double speed;

    public Enemy(List<int[]> path) {
        this.pathQueue = new LinkedList<>(path);
        int[] first = pathQueue.poll();
        this.x = first[0];
        this.y = first[1];
        this.target = pathQueue.poll();
        this.health = 100;
        this.maxHealth = 100;
        this.speed = 1.0;
    }

    public boolean move() {
        if (target == null) return true;
        double dx = target[0] - x;
        double dy = target[1] - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        if (distance < speed) {
            x = target[0];
            y = target[1];
            if (!pathQueue.isEmpty()) {
                target = pathQueue.poll();
            } else {
                return true;
            }
        } else {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
        return false;
    }

    public boolean isDead() {
        return health <= 0;
    }
    
    // Getters y setters omitidos por brevedad
}
Paso 6. Implementar la cola circular (o cola FIFO)
La cola usa un arreglo fijo, un índice frente, un índice fin y un contador. Después de cada inserción o eliminación, el operador módulo hace que el índice regrese a cero al llegar al límite. Así no se desplazan elementos y cada operación básica tiene costo O(1).

java
public class ColaEnemigos {
    private Enemy[] cola;
    private int head;
    private int tail;
    private int size;
    private int capacity;

    public ColaEnemigos(int capacity) {
        this.capacity = capacity;
        this.cola = new Enemy[capacity];
        this.head = 0;
        this.tail = 0;
        this.size = 0;
    }

    public void encolar(Enemy e) {
        if (size < capacity) {
            cola[tail] = e;
            tail = (tail + 1) % capacity;
            size++;
        }
    }

    public Enemy desencolar() {
        if (size > 0) {
            Enemy e = cola[head];
            cola[head] = null;
            head = (head + 1) % capacity;
            size--;
            return e;
        }
        return null;
    }

    public boolean estaVacia() {
        return size == 0;
    }

    public void limpiar() {
        cola = new Enemy[capacity];
        head = 0;
        tail = 0;
        size = 0;
    }
}
Invariante principal: 0 <= size <= capacity; head señala el primer elemento válido y tail señala la siguiente posición libre.

Paso 7. Modelar las torres, el alcance y las mejoras
Config.java funciona como un catálogo secuencial que almacena los costos y características principales de cada torre. La clase Tower combina un tipo de torre, una posición y un nivel de mejora.

Una torre puede atacar a un Cozy cuando la distancia entre la torre y la posición del Cozy es menor o igual al alcance actual de la torre.

Torre	Costo	Daño base	Alcance	Cadencia
Básica	50	15	150	1 disparo/s
Sniper	100	50	250	1 disparo/1.5 s
Rápida	60	5	120	4 disparos/s
java
public class Tower {
    private int x, y;
    private String tipo;
    private int range;
    private int cooldown;
    private int damage;
    private int sellValue;
    private int counter;

    public Tower(int x, int y, String tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        // Cargar stats desde Config según el tipo
        this.range = Config.TOWER_RANGES.get(tipo);
        this.cooldown = Config.TOWER_COOLDOWNS.get(tipo);
        this.damage = Config.TOWER_DAMAGES.get(tipo);
        this.sellValue = Config.TOWER_SELL_VALUES.get(tipo);
        this.counter = 0;
    }

    public void shoot(List<Enemy> enemies, List<Projectile> projectiles) {
        if (counter > 0) {
            counter--;
            return;
        }
        for (Enemy enemy : enemies) {
            double dx = enemy.getX() - x;
            double dy = enemy.getY() - y;
            if (Math.sqrt(dx * dx + dy * dy) <= range) {
                projectiles.add(new Projectile(x, y, enemy, damage));
                counter = cooldown;
                break;
            }
        }
    }
}
Paso 8. Implementar la búsqueda de rutas
La ruta se define mediante una lista secuencial de waypoints en Config.java. Cada enemigo recorre la ruta usando una cola FIFO, donde el primer waypoint es el origen y el último es la fuente.

java
public class Config {
    public static final List<int[]> PATH = List.of(
        new int[]{0, 100},
        new int[]{200, 100},
        new int[]{200, 300},
        new int[]{600, 300},
        new int[]{600, 500},
        new int[]{800, 500}
    );
}
La función isOnPath() en Utils.java verifica si una celda está demasiado cerca de la ruta para evitar que se coloquen torres sobre ella.

java
public static boolean isOnPath(int x, int y, List<int[]> path, int tolerance) {
    for (int i = 0; i < path.size() - 1; i++) {
        int[] p1 = path.get(i);
        int[] p2 = path.get(i + 1);
        double x1 = p1[0], y1 = p1[1];
        double x2 = p2[0], y2 = p2[1];
        double dx = x2 - x1, dy = y2 - y1;
        double lengthSq = dx * dx + dy * dy;
        double distance;
        if (lengthSq == 0) {
            distance = Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
        } else {
            double t = Math.max(0, Math.min(1, ((x - x1) * dx + (y - y1) * dy) / lengthSq));
            double px = x1 + t * dx;
            double py = y1 + t * dy;
            distance = Math.sqrt(Math.pow(x - px, 2) + Math.pow(y - py, 2));
        }
        if (distance < tolerance) return true;
    }
    return false;
}
Paso 9. Gestionar las oleadas en orden FIFO
Game.java usa ColaEnemigos para iniciar cada grupo de enemigos en el mismo orden en que fue programado. La dificultad aumenta mediante dos Cozy adicionales y 25 HP base adicionales por nivel de oleada.

java
private void startWave() {
    Map<String, Object> waveData = Config.WAVES.get(waveIndex);
    double speedM = (double) waveData.getOrDefault("speed", 1.0);
    int hpBonus = Config.HP_SCALE_PER_WAVE * waveIndex;
    
    this.spawnQueue.limpiar();
    this.spawnTimer = 0;
    this.waveActive = true;
    
    int cozyCount = (int) waveData.getOrDefault("cozy", 0);
    for (int i = 0; i < cozyCount; i++) {
        spawnQueue.encolar(new Enemy(Config.PATH, speedM, hpBonus));
    }
    int bossCount = (int) waveData.getOrDefault("boss", 0);
    for (int i = 0; i < bossCount; i++) {
        spawnQueue.encolar(new Enemy(Config.PATH, speedM * 0.6, hpBonus + 300)); // BossCozy
    }
    this.enemiesRemaining = spawnQueue.size();
}
Oleada	Cozy	Boss	Velocidad
1	5	0	0.7
2	7	0	0.85
3	9	0	1.0
4	11	0	1.1
5	10	1	1.15
...	...	...	...
12	20	3	1.8
Paso 10. Crear el historial de deshacer y rehacer
Cada acción realizada sobre una torre, como colocarla o venderla, se representa mediante una entrada en las pilas PilaAcciones (undo y redo).

Cuando se realiza correctamente una acción, esta se almacena en la pila de undo y se limpia la pila de redo. De esta manera, la última acción realizada puede ser revertida.

java
public class PilaAcciones {
    private Stack<Accion> pila = new Stack<>();

    public void push(Accion a) { pila.push(a); }
    public Accion pop() { return pila.isEmpty() ? null : pila.pop(); }
    public boolean isEmpty() { return pila.isEmpty(); }
    public void clear() { pila.clear(); }
}

// En Game.java
private void tryPlaceTower(int mx, int my) {
    // ... validaciones ...
    Tower tower = new Tower(cx, cy, selectedTower);
    towers.add(tower);
    undoStack.push(new Accion(tower, "place")); // Push en el stack de undo
    redoStack.clear();                           // Nueva acción invalida el redo
    coins -= cost;
}

private int undo() {
    if (undoStack.isEmpty()) return 0;
    Accion accion = undoStack.pop(); // Pop del stack
    if (accion.getTipo().equals("place")) {
        towers.remove(accion.getTower());
        redoStack.push(new Accion(accion.getTower(), "place"));
    }
    return Config.TOWER_COSTS.get(accion.getTower().getTipo());
}

private void redo() {
    if (redoStack.isEmpty()) return;
    Accion accion = redoStack.pop();
    int cost = Config.TOWER_COSTS.get(accion.getTower().getTipo());
    if (coins < cost) {
        redoStack.push(accion);
        return;
    }
    Tower tower = accion.getTower();
    towers.add(tower);
    undoStack.push(new Accion(tower, "place"));
    coins -= cost;
}
Regla importante: si después de deshacer una acción se realiza una acción nueva, la pila de rehacer debe vaciarse, debido a que se crea una nueva línea dentro del historial.

Paso 11. Procesar un quantum de combate
La clase Game es la encargada de coordinar el procesamiento de cada quantum de combate. Al comenzar un quantum, se obtiene la cantidad de Cozy que se encuentran actualmente almacenados en la cola circular y se realizan exactamente ese número de iteraciones.

En cada iteración se desencola un Cozy y las torres disponibles pueden aplicarle daño. Después del ataque pueden presentarse tres situaciones: el Cozy es eliminado porque sus puntos de vida llegan a cero, llega hasta la fuente y disminuye la vida del jugador, o continúa activo, avanza por la ruta y vuelve a colocarse al final de la cola circular.

java
public void update() {
    spawnEnemies();
    updateEnemies();
    updateTowers();
    updateProjectiles();
    
    if (waveMsgTimer > 0) waveMsgTimer--;
    
    if (playerHealth <= 0) {
        // Detener música y sonido de game over
        state = "game_over";
        return;
    }
    checkWaveEnd();
}

private void updateEnemies() {
    for (Enemy enemy : new ArrayList<>(enemies)) {
        if (playerHealth <= 0) break;
        if (enemy.isDead()) {
            enemies.remove(enemy);
            coins += Config.COINS_PER_KILL;
            score += Config.SCORE_PER_KILL;
            enemiesRemaining--;
            continue;
        }
        if (enemy.move()) {
            enemies.remove(enemy);
            playerHealth = 0;
            enemiesRemaining--;
        } else {
            // Dibujar enemigo (delegado a UI)
        }
    }
}
Este procedimiento permite que cada Cozy existente al comienzo del quantum sea procesado una sola vez. Los Cozy que continúan con vida y todavía no llegan a la fuente regresan al final de la cola para ser procesados nuevamente en el siguiente quantum.

Paso 12. Construir la interfaz gráfica
La interfaz se desarrolló con Java Swing / AWT. El bucle principal (Main.java) controla los FPS y delega los eventos a Game. UI.java pinta la cuadrícula, la ruta, las torres, los Cozy y sus barras de vida. Los controles se desactivan cuando una operación no es válida.

Zona	Información o acción
Panel superior	Vida, monedas, oleada, puntuación y botones Undo/Redo
Mapa central	Entrada, fuente, ruta, torres y barras de HP
Panel inferior	Selección de torres (Básica, Sniper, Rápida)
Simulación	Iniciar oleada, pausar, reanudar y reiniciar
Paso 13. Tipos de datos y diagrama de clases
Tipo	Categoría	Uso principal
int	Primitivo	HP, daño, nivel, monedas, vida, puntuación, índices
double	Primitivo	Alcance y distancia
boolean	Primitivo	Estados de pausa, victoria y derrota
String	Referencia	Identificadores, nombres y mensajes
ArrayList	Lista secuencial	Torres, proyectiles, enemigos y waypoints
Queue / LinkedList	Cola FIFO	Orden de oleadas y waypoints
ColaEnemigos	Cola circular	Cozy activos en el campo de batalla
Stack / PilaAcciones	Pila LIFO	Historial de deshacer y rehacer
Paso 14. Especificación del TDA y pseudocódigo
Para el funcionamiento del juego se utiliza el TDA ColaEnemigos, cuya función principal es administrar los Cozy activos durante los diferentes quantums de combate.

Estado de ColaEnemigos:

La cola está formada por un arreglo con capacidad de 200 elementos y utiliza tres variables principales:

head: indica la posición del primer elemento de la cola.

tail: indica la siguiente posición disponible para insertar un elemento.

size: almacena el número de elementos existentes actualmente en la cola.

Operaciones:

Las operaciones disponibles en el TDA son: encolar, desencolar, estaVacia, limpiar.

Precondiciones:

El elemento que se desea almacenar no puede ser null. Para realizar la operación de encolar debe existir espacio disponible en la cola y para desencolar debe existir al menos un elemento almacenado.

Postcondiciones:

La operación encolar aumenta la cantidad de elementos de la cola en uno. La operación desencolar devuelve el elemento más antiguo almacenado y disminuye la cantidad en uno.

Invariantes del juego:

Durante la ejecución del simulador se deben mantener las siguientes condiciones:

La vida del jugador nunca puede ser negativa.

Los HP de un Cozy nunca pueden ser negativos.

Una celda puede contener como máximo una torre.

El nivel de una torre debe mantenerse entre 1 y 3.

Un Cozy derrotado no puede permanecer almacenado en la cola circular.

Pseudocódigo del procesamiento de un quantum:

text
INICIO QUANTUM

    SI existe Cozy pendiente ENTONCES
        crear Cozy y ENCOLAR
    FIN SI

    cantidad <- TAMAÑO(colaEnemigos)

    REPETIR cantidad VECES

        cozy <- DESENCOLAR

        aplicar DAÑO de una torre disponible

        SI HP(cozy) = 0 ENTONCES
            sumar puntos y recompensa
        SINO SI cozy llega a la fuente ENTONCES
            restar vida al jugador
        SINO
            mover cozy y ENCOLAR nuevamente
        FIN SI

    FIN REPETIR

FIN QUANTUM
El pseudocódigo representa el ciclo principal del procesamiento de los Cozy. Primero se agrega un nuevo Cozy cuando existe uno pendiente de la oleada. Posteriormente se procesa únicamente la cantidad de Cozy existentes en la cola al comenzar el quantum. Cada Cozy puede ser eliminado, llegar a la fuente o regresar al final de la cola para continuar participando en los siguientes ciclos.

Paso 15. Prueba de escritorio
Main.java ejecuta verificaciones automáticas sin bibliotecas externas. El proyecto se ejecutó con Java 11+ y las pruebas finalizaron correctamente.

N.º	Prueba	Operación	Esperado	Obtenido
1	Cola circular	Encolar 10, 20; retirar 10; encolar 30 y 40	[20, 30, 40]	Correcto
2	Ruta por waypoints	Colocar una torre en la ruta directa	Ruta alternativa sin atravesarla	Correcto
3	Deshacer / rehacer	Colocar, deshacer y rehacer una torre	0, 1 y nuevamente 1 torre	Correcto
4	Oleada	Procesar la primera oleada hasta vaciar la cola	Finaliza antes de 200 quantums	Correcto
Compilación: correcta
Ejecución de pruebas:
Pruebas correctas: 4/4

Resultado general: la implementación satisface el uso obligatorio de Cozy, procesa cada objeto mediante cola circular, elimina los Cozy con HP igual a cero, ordena oleadas por FIFO, conserva el historial en pilas y muestra el estado del juego en una interfaz gráfica.

Paso 16. Pruebas automáticas y verificación final
El proyecto incluye un conjunto de pruebas automáticas en PRUEBAS_MANUALES.md que verifican el correcto funcionamiento de las estructuras de datos y la lógica del juego. Estas pruebas se ejecutan sin bibliotecas externas y cubren los siguientes casos:

N.º	Prueba	Operación	Esperado	Obtenido
1	Cola circular	Encolar 10, 20; retirar 10; encolar 30 y 40	[20, 30, 40]	Correcto
2	Ruta por waypoints	Colocar una torre en la ruta directa	Ruta alternativa sin atravesarla	Correcto
3	Deshacer / rehacer	Colocar, deshacer y rehacer una torre	0, 1 y nuevamente 1 torre	Correcto
4	Oleada	Procesar la primera oleada hasta vaciar la cola	Finaliza antes de 200 quantums	Correcto
5	Undo/Redo múltiple	Colocar 3 torres, deshacer 2 y rehacer 1	2 torres activas	Correcto
6	Cola circular llena	Encolar 200 elementos y encolar uno más	Ignora el excedente	Correcto
7	Venta de torre	Colocar torre y venderla	Reembolso correcto	Correcto
8	Game over	Dejar que un Cozy llegue a la fuente	Vida = 0 y estado game_over	Correcto
Compilación: correcta
Ejecución de pruebas:
Pruebas correctas: 8/8
## Conclusiones
Pilas y colas tienen un comportamiento específico para llevar a cabo las operaciones de inserción y eliminación de datos. Este comportamiento determina las áreas de aplicación de las mismas.

La implementación del simulador permitió comprobar cómo diferentes estructuras de datos pueden trabajar en conjunto dentro de una aplicación completa. La cola circular facilitó el procesamiento continuo de los Cozy, mientras que la cola FIFO permitió mantener el orden establecido de las oleadas.

El uso de pilas para las acciones de deshacer y rehacer permitió mantener un historial organizado de las modificaciones realizadas sobre las torres, demostrando la utilidad del comportamiento LIFO dentro de una situación práctica del juego.

