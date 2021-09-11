# th-assessment

## Despliegue

Para poder desplegar la aplicación se puede seguir dos caminos: 1. Directamente en stand-alone desde el equipo local mediante el wrapper de maven. 2. Ejecutando los contenedores con docker-compose, el cual permite aislar las dependencias para desplegar la aplicación. Tenga en cuenta que debe realizar los siguientes pasos estando dentro de la carpeta del repositorio.

### MVN Wrapper

Esta opción tiene como premisa que el usuario tiene instalada una base de datos mysql por el puerto 3306 (defecto) y que se tiene creado el usuario **fonyou** con sus respectivos permisos para crear base de datos. Primero proceda a crear la tabla correspondiente:

```
mysql -u fonyou -p fonyou < ./deploy/schema.sql
```

Siguiente a esto se procede por medio del wrapper (no hay necesidad de descargar el maven ni pasar a la versión con la que se compiló) a ejecutar el aplicativo:

Cambiar al directorio core:

```
cd core
```

Linux/Mac Ox:
```
./mvnw spring-run:boot
```

Windows:
```
./mvnw.cmd spring-run:boot
```

Una vez ejecutado puede proceder por medio de la colección Postman a realizar las diferentes peticiones correspondiente al servicio que quiera ejecutar.

### Docker Compose

Esta opción tiene como premisa que la aplicación se puede desplegar sin necesidad de instalar ninguna dependencia en el equipo local. Por lo que solo se necesita el motor de Docker para poder ejecutarlo. Para proceder solo es necesario ejecutar el siguiente comando:

Comandos para limpiar caché (Opcional si tiene caché inservible):

```
docker-compose stop
docker-compose rm
```

Ejecutar docker-compose (levantar los servicios):

```
docker-compose up --build
```

Finalmente puede acceder a los servicios por medio de la colección Postman para verificar los servicios. Recuerde que en caso de que hayan conflictos con los puertos definidos acceda directamente al archivo docker compose y proceda a cambiarlo a un puerto libre. **Nota:** Tiene que esperar unos minutos a que levanten ambos servicios para proceder a consumirlos (esto se puede optimizar utilizando una imagen ya creada y optimizando MySQL, pero se sale del alcance de este desarrollo).

```
vim docker-compose.yml

...
 ports:
      - [cambiar el primero]:3306
...

```

## Postman

Las colecciones de postman se dividen en [local](https://github.com/DavidPDP/th-assessment/blob/main/docs/api/Employees.postman_collection.json) cuando se ejecuta el proyecto con el maven wrapper y [producción](https://github.com/DavidPDP/th-assessment/blob/main/docs/api/Employees%20Prod.postman_collection.json) cuando se ejecuta por medio del docker compose.
