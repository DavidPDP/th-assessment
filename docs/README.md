# th-assessment
La aplicación se desarrolla bajo una metodología prescriptiva (cascada). Aunque pueda parecer contraproducente no lo es. Esto se debe a que es un problema cuya naturaleza es conocida. Un API REST sobre protocolo HTTP presenta problemas ampliamente estudiados a lo largo de la historia de la ingeniería de software, por lo que optar por un modelo donde se procede a realizar cada fase teniendo en cuenta el conocimiento requerido y considerable para desarrollarla de manera óptima, vuelve este modelo de desarrollo uno de los más sensatos. Cabe resaltar que el proyecto tiene un limite por ser una prueba por lo que su evolución queda coartada a la finalización del mismo.

## Analisis

DDD ofrece una filosófia para pensar desde el negocio. Teniendo en cuenta que la premisa de la ingenería de software con respecto a la sociedad es soportar el cumplimiento de la misión del cliente de la manera más efectiva. Es aquí donde esta filosofía nos permite explorar los maticez de negocio que próximamente se convertiran en conceptos para modelar el mismo sistema. A continuación se presenta el análisis DDD con respecto a la parte técnica deducida de las necesidades estipuladas.

![DDD Analysis](https://github.com/DavidPDP/th-assessment/blob/main/docs/analysis/employees-ddd-dorfman.drawio.png)

Se puede observar que en la parte técnica se han extraídos los conceptos de Employee como la entidad root que rige y que se convierte en un Aggregation Root debido a que compende otros conceptos dentro de él. Entre esos encontramos un Value Object que representará el cálculo de salario mensual. Por último estos son gestionados por el Repositorio y el Servicio expuesto como un API. Aunque conciso y reducido este análisis nos permite guiar hacía los límites de la aplicación, como también la cohesión entre los diferentes conceptos. Particionar estos conceptos de una manera más granular a nivel de negocio perdería el sentido. Por lo que este análisis establece las bases de cohesión sobre las que se trabajarán.

## Diseño

El diseño se ve fuertemente influenciado por los atributos de calidad como por las metas del negocio. En este ejercicio queda muy reducido ambos alcances por lo que se procede a explorar basado en la experiencia histórica de este tipo de aplicaciones. Los drivers arquitectónicos más importantes son: Performance, Interoperabilidad (Modificabilidad), Disponibilidad (Escalabilidad). A continuación se puede observar la arquitectura propuesta para la solución de los requerimientos.

![Architecture](https://github.com/DavidPDP/th-assessment/blob/main/docs/design/Employees-Architecture.jpg)

* Performance: Los requerimientos establecidos se basan en cálculos state-less, són sencillos y transaccionales. Esto permite discernir que el performance no será un punto a tener en cuenta (recordar que la evolución del sistema finaliza con la entrega de la app).
* Interoperabilidad: Dado por la naturaleza de los requerimientos podemos enfocarnos en realizar un esfuerzo por que la aplicación se pueda ejecutar en un entorno interoperable. Inicialmente podemos ganar interoperabilidad mediante el protocolo de comunicación del API Rest. Segundo tenemos el lenguaje que intrísicamente incrementa la interoperabilidad mediante la JVM. Finalmente se utilizará Docker para aislar las dependencias de una configuración local. Cabe resaltar que el alcance nuevamente es corto por lo que pensar en un Broker u otros patrones arquitectónicos no es sensato.
* Disponibilidad: Por falta de recursos no se puedo desarrollar plenamente este inciso. Aunque la aplicación está diseñada para poder multi-instanciarla no se tienen a mano credenciales para alguna cloud (todas ya las gasté). Pero dado el diseño se debería proceder a proponer un proxy balanceador de carga que tenga funciones de confiabilidad (como HAproxy) para implementar una estrategia de balanceo de carga con verificación de salud de las instancias y recuperación de las mismas en caso de que hayan fallos.

Por último, la arquitectura sigue un modelo de acceso de memoria NORMA y un modelo de proceso SISD según la taxonomía de Flynn con un grano medio a nivel de la transacción que ofrece el API.

## Consideraciones

1. Analizando las necesidades se puede notar que lo necesario por parte del back-end sería el CRUD de Employee. El cálculo del salario se debería pasar al front-end quitandole carga innecesaria al servidor. Los datos suministrados para este cálculo tienen la misma sensibilidad que el usuario que lo obtiene. Por lo que no es un impedimento para estar en el front-end distribuyendo la carga entre los usuarios que consulten.
2. Hay que recordar que según la programación orientada a objetos y sus principios, cada objeto debe ser responsable de su propio estado. Esto no solo va en vía con los principios S.O.L.I.D sino que permite aumentar la cohesión y disminuir el acomplamiento por lo que es una buena práctica. Si es extraño que los cálculos del propio estado del objeto lo realice la propia entidad del modelo puede consultar a [Matin Fowler](https://martinfowler.com/bliki/AnemicDomainModel.html).
3. Se utilizó la libraría Lombok para evitar el boilerplate utilizando las anotaciones que proceden en tiempo de compilación.

## Time record

A continuación puede observar el tiempo dedicado a las tareas realizadas. [Time Record](https://github.com/DavidPDP/th-assessment/blob/main/docs/Toggl_Track_summary_report_2021-09-10_2021-09-10.pdf).

## Git Flow Tree

A continuación se puede observar el árbol del git flow llevado a cabo.

![Git flow tree](https://github.com/DavidPDP/th-assessment/blob/main/docs/git-flow-tree.png)
