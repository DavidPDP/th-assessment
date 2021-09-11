# th-assessment

## Despliegue

Para poder desplegar la aplicación se puede seguir dos caminos: 1. Directamente en stand-alone desde el equipo local mediante el wrapper de maven. 2. Ejecutando los contenedores con docker-compose, el cual permite aislar las dependencias para desplegar la aplicación. Tenga en cuenta que debe realizar los siguientes pasos estando dentro de la carpeta del repositorio.

### MVN Wrapper

Esta opción tiene como premisa que el usuario tiene instalada una base de datos mysql por el puerto 3306 (defecto) y que se tiene creado el usuario **fonyou**. Primero proceda a crear la tabla correspondiente:

```
mysql -u fonyou -p fonyou < ./deploy/payroll-ddl.sql
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

```
docker-compose build --no-cache
docker-compose up
```

Finalmente puede acceder a los servicios por medio de la colección Postman para verificar los servicios. Recuerde que en caso de que hayan conflictos con los puertos definidos acceda directamente al archivo docker compose y proceda a cambiarlo a un puerto libre.

```
vim docker-compose.yml

...
 ports:
      - [cambiar el primero]:3306
...

```
