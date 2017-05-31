+ Resumen sistema: 
C.O.S.O está compuesto por una aplicación movíl Android (desarrollo nativo) y un modulo de hardware prototipado con Arduino, intercomunicados a través de una conexión bluetooth. 

+ Aplicación movíl (software): 
Es un desarrollo en JAVA. Implementa el framework de googlemaps -donde se define una tasa de refresco para extraer latitud y longitud del usuario- ,así como a la vez toma datos del acelerometro (m/s^2) incorporado en el smartphone. Ésta data es procesada de tal forma que se extrae un diferencial entre cada medida tomada en el eje Z, para así ser subida a tiempo real a una base de datos Firebase (no-referenciada). Paralelamente a éste proceso, la app valida los diferenciales en 4 intervalos definidos (obtenidos en el proceso de analís de data HAY QUE REFERENCIAR A ESTO EN ESTA DOC) para enviar una señal de control via bluetooth al modulo de hardware.


+ Modulo Arduino(hardware): 
Se hace uso de los siguientes componentes: Cinta LED RGB direccionable (que tenga el driver SK6812/WS2812B), Modulo bluetooth HC-06, 1 resistencia de 10k ohm y 1 resistencia de 20k ohm.

  A continuación una imagen del montaje:

	![Montaje](http://danielrosero.com/stuff/coso.png)