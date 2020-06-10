# Instalar Tomcat 9
El sistema codefac actualmente funciona con la versión 9 de tomcato que puede descargar del sitio oficial :  [Tomcat 9](https://tomcat.apache.org/download-90.cgi)

## Cambiar el archivo de inicio y el archivo de detener
Para que el sistema codefac pueda interactuar con Tomcat se tiene que cambiar los scripts de **startup.bat y shutdown.bat** ubicados en la carpeta bin y agregar la siguiente linea de código:

`IF "%1%" NEQ "" (`
`	cd "%1" `
`)`
Este código se debe agregar despues de la linea ***setlocal***

## Agregar directorio de librerias codefac al Tomcat
Para que tomcat pueda agregar librerias externas necesarias para ejecutar codefac web se tiene que editar el archivo **tomcat9\conf\catalina.properties** y agregar la siguiente linea de código:
`shared.loader="${catalina.home}/../lib","${catalina.home}/../lib*.jar"`
**Nota:** Para configurar en modo de desarrollo es necesario agregar el **directorio target antes de lib.**


## Crear usuarios y roles para administrar desde la web
Se tiene que editar el archivo **tomcat9\conf\tomcat-users.xml** y agregar las siguiente linea de código:
`<role rolename="manager-gui"/>`
`<role rolename="admin-gui"/>`
`<user username="usuario" password="clave" roles="manager-gui,admin-gui" />`

## Configurar Codefac como el proyecto inicial
Se debe editar el achivo **tomcat9\conf\server.xml** y agregar las siguiente etiquetas dentro de la etiqueta Host.
```
<Context docBase="codefac" path="" />
```
## Iniciar el proyecto Tomcat
Si se desea iniciar de forma manual Tomcat para codefac se tiene que ir al directorio principal de codefac y ejecutar la siguiente linea de código en una terminal:
 `.\tomcat9\bin\startup.bat "tomcat9"`

### Configuración adicional en la terminal para evitar pausar
Por defecto cuando se selecciona la ventana de la terminal que esta ejecutando el servicio de Tomcat el proceso se pausa , podemos quitar este comportamiento de la siguiente manera:
1. Click derecho en la barra de titulo de la terminal y seleccionar propiedades
2. Seleccionar la pestaña de opciones
3. En opciones de edición se tiene que quitar el check donde dice **Modalidad de edición rápida**

### Cambiar de puerto 8080 a puerto 80
Se debe editar el achivo **tomcat9\conf\server.xml** y cambiar en la siguiente lineas de código por el puerto 80:
```
<Connector port="80" protocol="HTTP/1.1"
               connectionTimeout="20000"
               redirectPort="8443" />
```



