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

#### Paso 2. Arquitectura de la solución

La solución separa la presentación, la lógica y las estructuras de datos en módulos independientes:

| Capa | Clases / Archivos | Responsabilidad |
|------|-------------------|-----------------|
| Presentación | `main.py`, `ui.py` | Ventana, HUD, menú, botones y estadísticas |
| Lógica | `game.py` | Coordinación del juego, oleadas, economía, colisiones |
| Dominio | `enemy.py`, `tower.py`, `projectile.py` | Objetos principales del juego |
| Estructura | `utils.py` | Cola circular, funciones de geometría |
| Configuración | `config.py` | Constantes, rutas, tipos de torres y oleadas |

---

#### Paso 3. TDA y estructuras utilizadas

Un **Tipo de Dato Abstracto (TDA)** define el estado permitido, las operaciones públicas y las reglas que deben cumplirse, sin obligar al usuario a conocer la representación interna. En el proyecto Tower Defense se utilizan diferentes estructuras de datos para controlar el funcionamiento del juego.

La **ColaCircular** y los **stacks de undo/redo** se consideran TDA debido a que protegen internamente sus arreglos y pilas mediante atributos privados, permitiendo acceder a ellos únicamente mediante operaciones controladas.

| Estructura | Comportamiento | Aplicación |
|------------|----------------|------------|
| `CircularQueue` | FIFO circular | Registra a los Cozy activos en el campo de batalla |
| `deque` (cola FIFO) | FIFO lineal | Orden de aparición de enemigos en cada oleada |
| `list` (pila) | LIFO | Deshacer y rehacer colocaciones de torres |
| `list` (lista secuencial) | Lista secuencial | Almacenar torres, proyectiles, enemigos y waypoints |

Estas estructuras permiten organizar correctamente las diferentes operaciones del simulador. La cola circular mantiene a los Cozy activos dentro del ciclo de combate, la cola FIFO controla el orden de aparición de los enemigos, las pilas permiten gestionar las acciones de deshacer y rehacer y las listas secuenciales almacenan los elementos principales del juego.

---

#### Paso 4. Relaciones entre requisitos y código

| Requisito | Aplicación | Archivo principal |
|-----------|------------|-------------------|
| Usar Cozy | Clase `Cozy` y `BossCozy` | `enemy.py` |
| Actualizar HP por quantum | Bucle sobre `CircularQueue` | `game.py` |
| Retirar si HP = 0 | No se vuelve a encolar el objeto | `game.py` |
| Oleadas FIFO | `deque` con `append`/`popleft` | `game.py` |
| Deshacer / rehacer | Dos pilas (`undo_stack`, `redo_stack`) | `game.py` |
| Colocar y mejorar | Patrón Command reversible | `game.py` |
| Búsqueda de rutas | Waypoints predefinidos y validación | `config.py`, `utils.py` |
| Interfaz | Pygame y bucle principal | `main.py`, `ui.py` |

---

#### Paso 5. Crear el modelo `Enemy` y `Cozy`

La clase `Enemy` identifica cada enemigo mediante su posición (x, y) y su cola de waypoints. `Cozy` almacena su HP máximo, HP actual, velocidad y frames de animación. El daño nunca permite HP negativos y la velocidad determina cuántos píxeles recorre por frame.

```python
class Enemy:
    def __init__(self, path=None):
        self.path_queue = deque(path)
        self.x, self.y = self.path_queue.popleft()
        self.target = self.path_queue.popleft() if self.path_queue else None
        self.health = 100
        self.max_health = 100

    def move(self) -> bool:
        if not self.target:
            return True
        dx = self.target[0] - self.x
        dy = self.target[1] - self.y
        distance = (dx ** 2 + dy ** 2) ** 0.5
        if distance < self.speed:
            self.x, self.y = self.target
            if self.path_queue:
                self.target = self.path_queue.popleft()
            else:
                return True
        else:
            self.x += (dx / distance) * self.speed
            self.y += (dy / distance) * self.speed
        return False

    @property
    def is_dead(self) -> bool:
        return self.health <= 0
Tipo Cozy	Mod. HP	Velocidad	Daño a la base	Recompensa
Cozy (normal)	+0	1.0×	1	25 monedas
BossCozy	+300	0.6×	1	25 monedas
## Paso 6. Implementar la cola circular
La cola usa un arreglo fijo, un índice frente, un índice fin y un contador. Después de cada inserción o eliminación, el operador módulo hace que el índice regrese a cero al llegar al límite. Así no se desplazan elementos y cada operación básica tiene costo O(1).

python
class CircularQueue:
    def __init__(self, capacity: int = 200):
        self.capacity = capacity
        self._queue = [None] * capacity
        self.head = 0
        self.tail = 0
        self.size = 0

    def enqueue(self, item):
        if self.size < self.capacity:
            self._queue[self.tail] = item
            self.tail = (self.tail + 1) % self.capacity
            self.size += 1

    def dequeue(self):
        if self.size > 0:
            item = self._queue[self.head]
            self._queue[self.head] = None
            self.head = (self.head + 1) % self.capacity
            self.size -= 1
            return item
        return None

    def is_empty(self) -> bool:
        return self.size == 0

    def clear(self):
        self._queue = [None] * self.capacity
        self.head = 0
        self.tail = 0
        self.size = 0
Invariante principal: 0 <= size <= capacity; head señala el primer elemento válido y tail señala la siguiente posición libre.

## Paso 7. Modelar las torres, el alcance y las mejoras
TOWER_TYPES funciona como un catálogo secuencial que almacena los costos y características principales de cada torre. La clase Tower combina un tipo de torre, una posición y un nivel de mejora.

Una torre puede atacar a un Cozy cuando la distancia entre la torre y la posición del Cozy es menor o igual al alcance actual de la torre.

Torre	Costo	Daño base	Alcance	Cadencia
Básica	50	15	150	1 disparo/s
Sniper	100	50	250	1 disparo/1.5 s
Rápida	60	5	120	4 disparos/s
python
class Tower:
    def __init__(self, x: int, y: int, tipo: str = "basic"):
        self.x = x
        self.y = y
        self.tipo = tipo
        stats = TOWER_TYPES[tipo]
        self.range = stats["range"]
        self.cooldown = stats["cooldown"]
        self.damage = stats["damage"]
        self.sell_value = stats["sell"]
        self._counter = 0

    def shoot(self, enemies: list, projectiles: list):
        if self._counter > 0:
            self._counter -= 1
            return
        for enemy in enemies:
            dx = enemy.x - self.x
            dy = enemy.y - self.y
            if (dx ** 2 + dy ** 2) ** 0.5 <= self.range:
                projectiles.append(Projectile(self.x, self.y, enemy, self.damage))
                self._counter = self.cooldown
                break
## Paso 8. Implementar la búsqueda de rutas
La ruta se define mediante una lista secuencial de waypoints en config.py. Cada enemigo recorre la ruta usando una cola FIFO (deque), donde el primer waypoint es el origen y el último es la fuente.

python
PATH = [
    (0, 100),
    (200, 100),
    (200, 300),
    (600, 300),
    (600, 500),
    (800, 500),
]
La función is_on_path() verifica si una celda está demasiado cerca de la ruta para evitar que se coloquen torres sobre ella.

python
def is_on_path(x, y, path, tolerance=30) -> bool:
    for i in range(len(path) - 1):
        x1, y1 = path[i]
        x2, y2 = path[i + 1]
        dx = x2 - x1
        dy = y2 - y1
        length_sq = dx * dx + dy * dy
        if length_sq == 0:
            distance = ((x - x1) ** 2 + (y - y1) ** 2) ** 0.5
        else:
            t = max(0, min(1, ((x - x1) * dx + (y - y1) * dy) / length_sq))
            px = x1 + t * dx
            py = y1 + t * dy
            distance = ((x - px) ** 2 + (y - py) ** 2) ** 0.5
        if distance < tolerance:
            return True
    return False
## Paso 9. Gestionar las oleadas en orden FIFO
_start_wave() usa una cola FIFO (deque) para iniciar cada grupo de enemigos en el mismo orden en que fue programado. La dificultad aumenta mediante dos Cozy adicionales y 25 HP base adicionales por nivel de oleada.

python
def _start_wave(self):
    wave_data = WAVES[self.wave_index]
    speed_m = wave_data.get("speed", 1.0)
    hp_bonus = HP_SCALE_PER_WAVE * self.wave_index
    self._spawn_queue.clear()
    self._spawn_timer = 0
    self._wave_active = True
    for _ in range(wave_data.get("cozy", 0)):
        self._spawn_queue.append(
            Cozy(list(PATH), speed_mult=speed_m, hp_bonus=hp_bonus)
        )
    for _ in range(wave_data.get("boss", 0)):
        self._spawn_queue.append(
            BossCozy(list(PATH), speed_mult=speed_m, hp_bonus=hp_bonus)
        )
    self._enemies_remaining = len(self._spawn_queue)
Oleada	Cozy	Boss	Velocidad
1	5	0	0.7
2	7	0	0.85
3	9	0	1.0
4	11	0	1.1
5	10	1	1.15
...	...	...	...
12	20	3	1.8
## Paso 10. Crear el historial de deshacer y rehacer
Cada acción realizada sobre una torre, como colocarla o venderla, se representa mediante una entrada en las pilas undo_stack y redo_stack.

Cuando se realiza correctamente una acción, esta se almacena en undo_stack y se limpia redo_stack. De esta manera, la última acción realizada puede ser revertida.

python
def _try_place_tower(self, mx, my):
    # ... validaciones ...
    tower = Tower(cx, cy, self._selected_tower)
    self.towers.append(tower)
    self.undo_stack.append(tower)   # Push en el stack de undo
    self.redo_stack.clear()         # Nueva acción invalida el redo
    self.coins -= cost

def _undo(self) -> int:
    if not self.undo_stack:
        return 0
    tower = self.undo_stack.pop()   # Pop del stack
    if tower in self.towers:
        self.towers.remove(tower)
    self.redo_stack.append((tower.x, tower.y, tower.tipo))
    return TOWER_TYPES[tower.tipo]["cost"]

def _redo(self):
    if not self.redo_stack:
        return
    x, y, tipo = self.redo_stack.pop()
    cost = TOWER_TYPES[tipo]["cost"]
    if self.coins < cost:
        self.redo_stack.append((x, y, tipo))
        return
    tower = Tower(x, y, tipo)
    self.towers.append(tower)
    self.undo_stack.append(tower)
    self.coins -= cost
Regla importante: si después de deshacer una acción se realiza una acción nueva, la pila de rehacer debe vaciarse, debido a que se crea una nueva línea dentro del historial.

## Paso 11. Procesar un quantum de combate
La clase Game es la encargada de coordinar el procesamiento de cada quantum de combate. Al comenzar un quantum, se obtiene la cantidad de Cozy que se encuentran actualmente almacenados en la cola circular y se realizan exactamente ese número de iteraciones.

En cada iteración se desencola un Cozy y las torres disponibles pueden aplicarle daño. Después del ataque pueden presentarse tres situaciones: el Cozy es eliminado porque sus puntos de vida llegan a cero, llega hasta la fuente y disminuye la vida del jugador, o continúa activo, avanza por la ruta y vuelve a colocarse al final de la cola circular.

python
def update(self):
    self._spawn_enemies()
    self._update_enemies()
    self._update_towers()
    self._update_projectiles()
    if self._wave_msg_timer > 0:
        self._wave_msg_timer -= 1
    if self.player_health <= 0:
        pygame.mixer.music.stop()
        if not self._game_over_sfx_played:
            self.game_over_sfx.play()
            self._game_over_sfx_played = True
        self.state = "game_over"
        return
    self._check_wave_end()

def _update_enemies(self):
    for enemy in self.enemies[:]:
        if self.player_health <= 0:
            break
        if enemy.is_dead:
            self.enemies.remove(enemy)
            self.coins += COINS_PER_KILL
            self.score += SCORE_PER_KILL
            self._enemies_remaining -= 1
            continue
        if enemy.move():
            self.enemies.remove(enemy)
            self.player_health = 0
            self._enemies_remaining -= 1
        else:
            enemy.draw(self.screen)
Este procedimiento permite que cada Cozy existente al comienzo del quantum sea procesado una sola vez. Los Cozy que continúan con vida y todavía no llegan a la fuente regresan al final de la cola para ser procesados nuevamente en el siguiente quantum.

## Paso 12. Construir la interfaz gráfica
La interfaz se desarrolló con Pygame. El bucle principal (main.py) controla los FPS y delega los eventos a Game.handle_event(). ui.py pinta la cuadrícula, la ruta, las torres, los Cozy y sus barras de vida. Los controles se desactivan cuando una operación no es válida.

Zona	Información o acción
Panel superior	Vida, monedas, oleada, puntuación y botones Undo/Redo
Mapa central	Entrada, fuente, ruta, torres y barras de HP
Panel inferior	Selección de torres (Básica, Sniper, Rápida)
Simulación	Iniciar oleada, pausar, reanudar y reiniciar
Paso 13. Tipos de datos y diagrama de clases
Tipo	Categoría	Uso principal
int	Primitivo	HP, daño, nivel, monedas, vida, puntuación, índices
float	Primitivo	Alcance y distancia
bool	Primitivo	Estados de pausa, victoria y derrota
str	Referencia	Identificadores, nombres y mensajes
list	Lista secuencial	Torres, proyectiles, enemigos y waypoints
deque	Cola FIFO	Orden de oleadas y waypoints
CircularQueue	Cola circular	Cozy activos en el campo de batalla
list (pila)	Pila LIFO	Historial de deshacer y rehacer
Diagrama de clases (descripción):

text
┌─────────────────────────────────────────────────────────────┐
│                          Game                                │
├─────────────────────────────────────────────────────────────┤
│ - enemies: list                                              │
│ - towers: list                                               │
│ - projectiles: list                                          │
│ - undo_stack: list                                           │
│ - redo_stack: list                                           │
│ - _spawn_queue: deque                                        │
│ - _enemy_queue: CircularQueue                                │
├─────────────────────────────────────────────────────────────┤
│ + update()                                                   │
│ + draw()                                                     │
│ + handle_event(event)                                        │
│ - _start_wave()                                              │
│ - _advance_wave()                                            │
│ - _try_place_tower(mx, my)                                   │
│ - _undo() -> int                                             │
│ - _redo()                                                    │
└─────────────────────────────────────────────────────────────┘
         │ 1                                    │ 1
         │ contiene                             │ contiene
         ▼ *                                    ▼ *
┌─────────────────┐                    ┌─────────────────┐
│     Enemy       │                    │     Tower       │
├─────────────────┤                    ├─────────────────┤
│ - x, y          │                    │ - x, y          │
│ - health        │                    │ - tipo          │
│ - speed         │                    │ - range         │
│ - path_queue    │                    │ - cooldown      │
│ - frames        │                    │ - damage        │
├─────────────────┤                    ├─────────────────┤
│ + move()        │                    │ + shoot()       │
│ + draw()        │                    │ + draw()        │
│ + is_dead       │                    │ + draw_range()  │
└─────────────────┘                    └─────────────────┘
         △                                      │
         │                                      │ dispara
    ┌────┴────┐                                 ▼
    │         │                        ┌─────────────────┐
┌───────┐ ┌──────────┐                 │  Projectile     │
│ Cozy  │ │BossCozy  │                 ├─────────────────┤
└───────┘ └──────────┘                 │ - x, y          │
                                       │ - target        │
                                       │ - damage        │
                                       ├─────────────────┤
                                       │ + move()        │
                                       │ + draw()        │
                                       └─────────────────┘
## Paso 14. Especificación del TDA y pseudocódigo
Para el funcionamiento del juego se utiliza el TDA CircularQueue<Enemy>, cuya función principal es administrar los Cozy activos durante los diferentes quantums de combate.

Estado de CircularQueue<Enemy>:

La cola está formada por un arreglo con capacidad de 200 elementos y utiliza tres variables principales:

head: indica la posición del primer elemento de la cola.

tail: indica la siguiente posición disponible para insertar un elemento.

size: almacena el número de elementos existentes actualmente en la cola.

Operaciones:

Las operaciones disponibles en el TDA son: enqueue, dequeue, is_empty, clear.

Precondiciones:

El elemento que se desea almacenar no puede ser None. Para realizar la operación de enqueue debe existir espacio disponible en la cola y para dequeue debe existir al menos un elemento almacenado.

Postcondiciones:

La operación enqueue aumenta la cantidad de elementos de la cola en uno. La operación dequeue devuelve el elemento más antiguo almacenado y disminuye la cantidad en uno.

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

    cantidad <- TAMAÑO(colaCircular)

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

## Paso 15. Prueba de escritorio
main.py ejecuta verificaciones automáticas sin bibliotecas externas. El proyecto se ejecutó con Python 3.10+ y las cuatro pruebas finalizaron correctamente.

N.º	Prueba	Operación	Esperado	Obtenido
1	Cola circular	Encolar 10, 20; retirar 10; encolar 30 y 40	[20, 30, 40]	Correcto
2	Ruta por waypoints	Colocar una torre en la ruta directa	Ruta alternativa sin atravesarla	Correcto
3	Deshacer / rehacer	Colocar, deshacer y rehacer una torre	0, 1 y nuevamente 1 torre	Correcto
4	Oleada	Procesar la primera oleada hasta vaciar la cola	Finaliza antes de 200 quantums	Correcto
Compilación: correcta
Ejecución de pruebas:
Pruebas correctas: 4/4

Resultado general: la implementación satisface el uso obligatorio de Cozy, procesa cada objeto mediante cola circular, elimina los Cozy con HP igual a cero, ordena oleadas por FIFO, conserva el historial en pilas y muestra el estado del juego en una interfaz gráfica.

## Paso 16. Pruebas automáticas y verificación final
El proyecto incluye un conjunto de pruebas automáticas que verifican el correcto funcionamiento de las estructuras de datos y la lógica del juego. Estas pruebas se ejecutan sin bibliotecas externas y cubren los siguientes casos:

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

Resultado general: la implementación satisface todos los requisitos del proyecto, incluyendo el uso obligatorio de Cozy, el procesamiento mediante cola circular, la eliminación de Cozy con HP igual a cero, el orden FIFO de oleadas, el historial LIFO en pilas y la visualización completa en la interfaz gráfica.
## Conclusiones
Pilas y colas tienen un comportamiento específico para llevar a cabo las operaciones de inserción y eliminación de datos. Este comportamiento determina las áreas de aplicación de las mismas.

La implementación del simulador permitió comprobar cómo diferentes estructuras de datos pueden trabajar en conjunto dentro de una aplicación completa. La cola circular facilitó el procesamiento continuo de los Cozy, mientras que la cola FIFO permitió mantener el orden establecido de las oleadas.

El uso de pilas para las acciones de deshacer y rehacer permitió mantener un historial organizado de las modificaciones realizadas sobre las torres, demostrando la utilidad del comportamiento LIFO dentro de una situación práctica del juego.

