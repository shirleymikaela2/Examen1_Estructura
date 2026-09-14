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

