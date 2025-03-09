# Movimientos


## Installation
Realizar un git clone del repositorio actual con la rama MAIN en la ruta de su preferencia


```sh
git clone https://github.com/jhtc5898/Movimientos.git
```
Abri el proyecto en su IDE de preferencia (Desarrollado en IntelliJ IDEA)

Levantar la base de datos y el proyecto:
## Opcion

Levantar la base de datos en un contenedor:
En la ubicacion donde esta el fichero encontrara el documento 
```sh
docker-compose.yml
```
Levante el contenedor con el siguiente comando:
```sh
docker-compose -f up
```
El contenedor esta configurado para que inicie ejecutando el init.sql que esta en la carpeta [sql]  de esta manera ya tendra generado los clientes.

El contendero levanta el proyecto spring boot en la ruta http://localhost:8109

## Recomendaciones
En el caso de tener alguna error con las dependencias ejecutar:
```sh
mvn clean install
```

En el caso que no se levante el contenedor Docker puede que tenga levantado otro PostGres en su maquina por lo cual seria recomendable cambiar el puerto en el archivo [postgres.ymlv]

##  Clientes definidos
Estos Clientes Fueron Generados de prueba.

| clientidentification (Identificacion cliente)          |     Numero Cuenta                                   |
|------------------|-------------------------------------------|
| SilviaPatricia          | 15 (A - Ahorro) , 16 ( C - Corriente)        |
| JohnTenesaca           | 17 (A - Ahorro) , 18 ( C - Corriente)         |

## Documentacion

https://documenter.getpostman.com/view/13910567/VUxYohUv
