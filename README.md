# Entregas semanales:

## Semana (30-12 marzo/abril)
¡Gran actualización!
Hemos trabajado en los siguientes puntos:

### Login

- Corrección de errores en el login.
- Botones para que que el usuario pueda borrar su cuenta, cambiar su contraseña y cerrar su sesión (Irán implementados
  dentro de la pantalla de actividades).

| Login  | Botones |
| ------------- | ------------- |
| ![image](https://user-images.githubusercontent.com/67373943/227145365-9bdbf207-92ee-4ee9-9c25-2e88351d223f.png)  | ![image](https://user-images.githubusercontent.com/67373943/229128894-36b6a31b-1f82-434f-8921-22e7b7e91e23.png)  |

### Actividades

- Implementación de una primera estructura para las actividades en FireBase RT DB.
- Implementación de la persistencia mediante la tecnología ROOM para guardar actividades a nivel local.
- Conexión de la BD ROOM con FireBase RTDB para la obtención de los datos de las actividades asignadas a 
  los usuarios en tiempo real.
- Mejoras gráficas en la UI.
- Adición del botón de ajustes: Desde aquí el usuario elegirá los diferentes settings que requiera para la aplicación.
- Adición del botón de cuenta personal: Desde este botón se podrán acceder a los ajustes y detalles sobre la cuenta de usuario actual.
- Solución de errores menores.

| Actividades  | Detalles de una Actividad |
| ------------- | ------------- |
| ![Screenshot_2023-04-05-01-43-08-312_es aleph_tea teabuddy](https://user-images.githubusercontent.com/99476958/229946329-669d5c95-df61-454c-9cad-8506e035a3d5.jpg) | ![Screenshot_2023-04-05-01-43-12-535_es aleph_tea teabuddy](https://user-images.githubusercontent.com/99476958/229946373-685ce006-665b-48d8-a139-7a02363a9638.jpg) |


## Semana (23-29 marzo)
Hemos mejorado la parte de login y registro de los usuarios. 
En el registro de usuarios hemos añadido persistencia en firebase para almacenar los datos (nombre, apellidos, fecha de nacimiento...). 
Hemos añadido un botón para que el usuario tenga la opción de guardar su acceso y no tenga que realizar login si así lo prefiere. 

En el futuro lo cambiaremos a RealTime Database e incluiremos otro con las actividades almacenadas.
Hemos pensado en la parte de las actividades, cómo estructurar los mockups y hemos corregido errores en las pantallas de login y registro...

Se ha creado la primera versión de prueba de las vistas de las listas de actividades: con 2 tabs y el AppBar correspondiente generados para
la vista principal de las actividades. Este sigue principalmente el esquema de actividades que debería encontrarse el usuario "Voluntario"
dentro del mapa mental que hemos creado para la aplicación.

| Negro  | Blanco |
| ------------- | ------------- |
| ![image](https://user-images.githubusercontent.com/67373943/227145365-9bdbf207-92ee-4ee9-9c25-2e88351d223f.png)  | ![image](https://user-images.githubusercontent.com/67373943/227145394-4f1fc9a8-b13a-48d0-aab0-3c386b44a828.png)  |


![image](https://user-images.githubusercontent.com/67373943/227145450-d985847f-19ea-4ce4-a74e-d56f75223060.png)


## Semana (16-22 marzo)
Hemos hecho un login y un registro totalmente funcionales. Con Auth en Firebase.
Hemos puesto colores corporativos y hemos puesto los logos en formato svg.


| Negro  | Blanco |
| ------------- | ------------- |
| ![image](https://user-images.githubusercontent.com/67373943/227150758-ccadf856-66b1-4574-8908-3de049fc524c.png)  | ![image](https://user-images.githubusercontent.com/67373943/227150938-841a8477-9748-454e-a05f-eb0768693561.png)  |

## Semana (9-15 marzo)
Publicamos nuestro anteproyecto.

# Mapa mental de la aplicación

![WhatsApp Image 2023-04-05 at 18 57 34](https://user-images.githubusercontent.com/99476958/231225667-97de7930-ad00-49bd-a38d-b3f39bc6aa39.jpeg)

## Posibles combinaciones de usuarios

- Voluntario
- Monitor
- Administrador
- Administrador-Voluntario
- Administrador-Monitor
