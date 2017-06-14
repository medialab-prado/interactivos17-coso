# C.O.S.O · Interactivos?'17 

+ [C.O.S.O Web](http://coso.ga)


### Resúmen
Pensar en movilidad implica reflexionar cómo el entorno de la ciudad implica la existencia de barreras o facilitadores que afectan la forma en que circulamos por la urbe. 

C.O.S.O. es resultado del interés por experimentar con la microrelaciones que afectan la movilidad en las grandes ciudades. Específicamente, es una herramienta tecnológica que visibiliza la experiencia de andar en bicicleta que se altera conforme a las distintas texturas del terreno. Para ello, C.O.S.O genera dos experiencias visuales que plasman la interacción entre la bicicleta y el terreno: mapa de datos y fotografías de larga exposición. 

Es un dispositivo tecnológico que por medio de un móvil que captura las vibraciones y las asocia con un punto espacial en concreto. C.O.S.O está conectado a una guía de luces LED que cambian de color con base en las vibraciones que registra el acelerómetro del móvil y por medio de una conexión a internet es capaz de registrar y emitir datos en tiempo real.  

C.O.S.O es un dispositivo que convierte una bicicleta en una herramienta cazadora de datos, un artilugio de diagnóstico para el análisis del entorno, capaz de cuantificar el suelo por el que se traslada.


## Concepto

### C.O.S.O en la bicicleta 
C.O.SO va fijado al cuadro de la bicicleta con el objetivo de registrar las vibraciones que ésta sufre cuando circula por distintos terrenos. En términos simples, C.O.S.O. convierte a la bicicleta en una herramienta de medición que registra las vibraciones y las convierte en un dato que es posible visualizar a través de dos formas: las luces LED y en un mapa. 

### C.O.S.O como sensor 
Dispositivo tecnológico y de composición minimalista que por medio de un móvil que captura las vibraciones y las asocia con un punto espacial en concreto. C.O.S.O está conectado a una guía de luces LED que cambian de color con base en las vibraciones que registra el acelerómetro del móvil y por medio de una conexión a internet es capaz de registrar y emitir datos en tiempo real.  

### C.O.S.O y los datos 
C.O.S.O. almacena y procesa los datos que registra a su paso para determinar el estado de la calzada con base en intervalos de vibración que clasifican el terreno en cuatro niveles.  A su vez, estas categorías están asociadas a un código de color de semáforo que visualiza el terreno. 
En tiempo real, C.O.S.O procesa la información y las luces LED integradas cambian de color con base en las categorías establecidas. Simultáneamente, transmite la información georreferenciada de las vibraciones y con base en las categorías establecidas realiza un mapeo del estado de las calzadas en la ciudad. 


## Proceso

### Prototipo
Contenedor diseñado digitalmente y elaborado con cortado láser a partir de una placa de MDF de 3mm de espesor. En el interior contiene un móvil Android con acelerómetro y GPS que está conectado vía Bluetooth con un microcontrolador Arduino. El microcontrolador está conectado directamente a una guía de LEDs de alta intensidad que están alimentados por una batería externa de larga duración. Finalmente, el dispositivo está fijo a una bicicleta por medio de un sistema de bridas. 
 
### Código
C.O.S.O. se conforma de un sistema software de dos componentes. Primero, se desarrolló una app móvil del sistema Android que recolecta los registros del acelerómetro y GPS del móvil, y utiliza el framework de Google Maps para la georreferenciación de los valores. La app distribuye la información al microcontrolador que está programado para que las luces LED se comporten de forma diferenciada dependiendo de las distintas vibraciones. 
A su vez, la app envía los valores a una base de datos en Firebase que alimenta un mapa elaborado con el framework de Google Maps en el que se visualizan los recorridos. La ventaja de este sistema es que, en tiempo real, se puede procesar y visualizar el mapeo en la página web. 
 
### Visualización 
Las fotografías de larga exposición capturan el rastro de luz que deja C.O.S.O. a su paso. Se utiliza esta técnica para poder capturar los cambios de luz que tienen los LEDs a su paso por distintas texturas en el terreno como baches, alcantarillas y empedrados. 
El mapa interactivo refleja en tiempo real los valores que el C.O.S.O registra mientras circula la bicicleta. El mapa es el compilado de todos los registros que se capturan y permite visualizar las observaciones en conjunto y en cualquier localización. 

# Promotores y colaboradores: 
### Equipo: 
+ Pablo Fernández Vallejo // https://twitter.com/pablitofv
+ Mariana Robles
+ Roberto Rodríguez // https://www.behance.net/robertoerre
+ Daniel Rosero // http://danielrosero.com/
+ Michael Sandoval

# Instrucciones
### Instrucciones de montaje y desmontaje
+ Qué material se necesita
+ Cómo se ensambla todo
+ En qué puntos hay que tener cuidado
### Instrucciones de funcionamiento
+ ¿Cómo se enciende? ¿Cómo se apaga?
Ver en Instrucciones de Mantenimiento. 
+ ¿Qué hace exactamente? ¿Cómo interactuar con el dispositivo? (pensar que la gente que lo ve no ha estado en el taller). 
Ver las fotos, la bici, y que la tira de LEDS reacciona con la vibración de la bici.

### Instrucciones de Expo
+ Mostrar la web de COSO:  http://coso.ga
+ Prototipo en bici ( ver instrucciones de mantenimiento )

### Instrucciones de mantenimiento
+ Si deja de funcionar cuál sería la lista de errores más corrientes: de los más comunes a los más raros. ¿Cómo arreglar cada uno?
   1. Apagar Arduino con el cargador ( quitar y volver a poner el enchufe)
   2. Cerra el App COSO del movil, cerrandolo bien ( no minimizando )
   3. Volvemos a conectar el Arduino ( verificar que el modulo bluetooh este titilando )
   4. Arrancamos el APpp COSO y el modulo bluetooh deberia dejar de titilar para quedarse estático ( ha conectado bien )
   5. Le damos al botón registrar para que el sensor y los LEDS esten activos y respondiendo a la vibración.

Que pasa si el bluetooh se despareja? ... pues que hay que aparejarlo de nuevo: 
   1. settings de smartphone
   2. bluetooh -> COSO ( dispositivos vinculados ) --> settings --> olvidar
   3. finalmente emparejarlo de nuevo con contraseña "1234"

Que pasa si quieres provar la instalacion para hacerle la foto de larga exposición?
   1. Quitar el candado con la llave que dejamos a cargo de mediacin con un cartelito de COSO! ( hacer )
   2. Conectar el powerBank en vez de cargador al movil, reiniciar el App. 

# Recursos: 
### Repositorios del proyecto:
+ [Fotos de Interactivos?'17:1](https://www.flickr.com/photos/medialab-prado/albums/72157681254355863/with/34081752643/)
+ [Fotos de Interactivos?'17:2](https://www.flickr.com/photos/medialab-prado/sets/72157681434594913)
+ [Fotos de Interactivos?'17:3](https://www.flickr.com/photos/medialab-prado/sets/72157681468757274)
+ [Fotos del Proyecto](https://flic.kr/s/aHskW7TnQj)
### Inspiración
+ http://www.yourban.no/2011/02/22/immaterials-light-painting-wifi/
### Bibliografía, otros repositorios y links: 
+ https://www.thingiverse.com/thing:17240
+ http://openframeworks.cc/
+ https://libcinder.org/

# Diario del proceso
In progress

