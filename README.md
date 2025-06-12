## Índice archivo README.md

- [Descripción](#akihabara-market)
- [Autoría](#desarrollado-por)
- [Funcionalidades](#funcionalidades-implementadas)
- [Tecnologías y requisitos previos](#requisitos-previos)
- [Repositorio Git Hub](#repositorio-git-hub)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Preparación previa requerida](#preparacion-previa-requerida)
- [Archivo config.properties](#archivo-config-properties)
- [API Key / Asistente IA LLM](#api-key-de-llm-para-asistente-ia)
- [Ejecutables entregados](#ejecutables-entregados)
- [Estructura para la ejecución de los jar y bat](#estructura-para-poder-ejecutar-los-jar-y-bat)
- [Guía de ejecución](#guia-para-ejecutar)
- [Licencía](#licencia)


---

# Akihabara Market

Se trata de una aplicación de gestión de inventario y clientes, desarrollada en Java con una interfaz gráfica en Swing, conexión a base de datos MySQL y funcionalidades CRUD completas tanto para productos como para clientes. 
Incluye integración con un modelo de lenguaje (LLM) como asistente de IA.

---
## Desarrollado por:

- **Autora:** Tamara Martínez Vargas  
- **Curso:** 1º Curso del Grado Superior en Desarrollo de Aplicaciones Multiplataforma  
- **Centro:** Campus FP Alcalá de Henares

---
## Funcionalidades Implementadas

- CRUD completo de productos y clientes
- Interfaz gráfica Swing con validación visual
- Interfaz de consola
- Simulación de asistente IA (para generación de descripción de productos y sugerencias de categorías)
- Pruebas unitarias en DAO de productos
- Script SQL para base de datos inicial

---
## Requisitos previos

- Java 17
- Swing Java (interfaz gráfica)
- MySQL 8.x
- JDBC
- Librerias Apache Commons
- LLM Service (modelo de lenguaje local/integrado)
- Eclipse IDE

---
## Repositorio Git Hub:

- Este proyecto se encuentra versionado en el siguiente enlace de GitHub:
[https://github.com/maracampusfp/Akihabara-Market.git](https://github.com/maracampusfp/Akihabara-Market.git)

---
## Estructura del Proyecto
- Se han implementado los siguientes archivos en el proyecto Java Maven:

```
akihabara/
├── src/
│ └── main/
│   ├── java/
│   │ ├── akihabaramarketlogo.png #Logo de la aplicación
│   │ ├── config/
│   │ │ └── ConfigLoader.java #Carga de propiedades de config. de BD y API
│   │ ├── controller/
│   │ │ ├── MainApp.java #Lanzador de consola
│   │ │ ├── MainGrafica.java #Lanzador de la interfaz gráfica
│   │ │ └── MainDual.java #Opción adicional para elegir modo de arranque
│   │ ├── dao/
│   │ │ ├── ClienteDAOImpl.java #Implementación DAO para clientes
│   │ │ ├── ClienteDAOInterface.java #Interfaz DAO de clientes
│   │ │ ├── ProductoDAOImpl.java #Implementación DAO para productos
│   │ │ ├── ProductoDAOInterface.java #Interfaz DAO de productos
│   │ │ └── DatabaseConnection.java #Conexión a BD MySQL
│   │ ├── model/
│   │ │ ├── ClienteOtaku.java #Clase modelo para Cliente
│   │ │ └── ProductoOtaku.java #Clase modelo para Producto
│   │ ├── service/
│   │ │ └── LlmService.java #Simulación del asistente IA (API de OpenRouter)
│   │ └── view/
│   │ ├── InterfazConsola.java #Interfaz de texto para consola
│   │ └── InterfazGrafica.java #Interfaz gráfica con Swing
│   └── sql/
│        └── crear_akihabara_db.sql #Script SQL para crear la base de datos
├── test/
│ └── java/
│   └── test/
│     └── ProductoDAOTest.java #Test unitario para DAO de productos
├── target/ # Carpeta generada al compilar
├── config.properties # Configuración de conexión (host, usuario...)
├── pom.xml # Archivo Maven con dependencias
└── README.md # Documentación del proyecto (este archivo)
```

---
## Preparacion previa requerida

### Base de Datos

- **Motor** `MySQL`  
- **Nombre BD** `akihabara_db`  
- **Ubicación del script de creación de la BD** `src/main/sql/crear_akihabara_db.sql`  
- **Tablas requeridas INCLUIDAS EN EL SCRIPT**
  - `productos`  
    - `id`, `nombre`, `categoria`, `precio`, `stock`
  - `clientes`  
    - `id`, `nombre`, `email`, `telefono`, `fecha_registro`
- **Datos de prueba INCLUIDOS EN EL SCRIPT**    
  - Se han incluido INSERTS para incluir datos de prueba, tanto para productos como para clientes. (Si no se insertan al ejecutar el .sql completo y se desea usarlos, deberán ejecutarse manualmente para su correcta insercción)

⚠️**Importante**⚠️: En el script SQL hay incluida la creación de un usuario de prueba, con permisos de CRUD llamado "kenji", cuya contraseña es "akihabara". Puedes personalizar estos datos, pero asegurate de que coincidan con los datos que incluyas en el archivo config.properties.
  
Se ha incluido la dependencia Maven del conector mysql en el `pom.xml`:
- `mysql-connector-j-9.3.0.jar`
---

# Archivo config properties

- Este archivo debe contener la configuración sensible de la base de datos (usuario, contraseña, etc.) y **no se incluye en el repositorio** por seguridad

- Debe crearse manualmente un archivo llamado `config.properties` con el siguiente contenido

```
db.url=jdbc:mysql://localhost:3306/akihabara_db # Verifica el puerto correcto en tu caso.
db.user=tu_usuario_de_la_BD # El usuario debe tener permisos de CRUD sobre la BD
db.password=contraseña_del_usuario
apiKey=tu-apiKey-del-LLM-OpenRouter #Como se explica en el siguiente punto.
```
- El usuario debe tener permisos de lectura y escritura sobre la base de datos. 


# API Key de LLM para Asistente IA 
La aplicación incluye una integración opcional con un modelo de lenguaje (LLM) externo para generar descripciones de productos o sugerencias de categoría, usando la API de OpenRouter y el modelo openai/gpt-4.1.

- **Seguridad**
La clave de API (apiKey) del LLM no se versiona por motivos de seguridad. Debe configurarse manualmente en el archivo config.properties, que debe colocarse en el mismo directorio que los `.jar`

- **Funcionalidades del asistente IA**
    - Genera descripciones breves y atractivas de productos a partir de su ID. 
    - Sugiere la categoría adecuada de productos nuevos a partir de un nombre de producto.
    - Disponible tanto desde la versión de consola como la versión gráfica.


- **Configuración**
    - Tu archivo config.properties debe tener la siguiente línea:
`apiKey=tu-apiKey-del-LLM-OpenRouter`
    - Puedes generar una clave gratuita desde [OpenRouter](https://openrouter.ai) 
    - El modelo utilizado por defecto es: [`openai/gpt-4.1`](https://openrouter.ai/docs#models)
    - La API utilizada es: [`https://openrouter.ai/api/v1/chat/completions`](https://openrouter.ai/docs#chat-completions-api)

---
## Ejecutables Entregados

Se incluyen tres archivos `.jar` junto con sus respectivos `.bat` para facilitar la ejecución:

- **`akihabara_consola.jar`**  
  Ejecuta la versión de consola del sistema.

- **`akihabara_grafica.jar`**  
  Ejecuta la versión con interfaz gráfica (Swing).

- **`akihabara_dual.jar`**  
  Muestra un menú en consola para que el usuario elija si lanzar la versión gráfica o la de consola.

Cada uno va acompañado de su archivo `.bat` correspondiente para facilitar su ejecución en Windows.
  
⚠️**IMPORTANTE**⚠️ 
- Estos ejecutables deben colocarse en la misma carpeta que el archivo `config.properties`.

---

## Estructura para poder ejecutar los jar y bat


Estructura de carpetas recomendada para la ejecución:

```
/AkihabaraMarket/ # Carpeta del proyecto
├── akihabara_consola.jar
├── akihabara_grafica.jar
├── akihabara_dual.jar
├── AkihabaraMarket_consola.bat
├── AkihabaraMarket_grafica.bat
├── AkihabaraMarket_dual.bat
├── config.properties # *(no se incluye en el repositorio))
````
- Los `.bat` deben estar en la **misma carpeta** que los `.jar` correspondientes.
- El archivo `config.properties` debe estar en **el mismo directorio raíz** que los `.jar`, ya que se carga desde ahí en tiempo de ejecución.

⚠️**IMPORTANTE**⚠️  
- Si no se encuentra el archivo `config.properties` en la carpeta de ejecución, la aplicación no podrá conectarse a la base de datos y mostrará un mensaje de error.
- Tampoco podrá hacerse uso del LLM Service externo, ya que los datos sensibles de la Api se deben incluir en dicho archivo, como se ha detallado anteriormente.

---

## Guia para ejecutar:
**1. Preparar la base de datos MySQL**

1. Abre tu gestor de base de datos MySQL (como MySQL Workbench, DBeaver, etc.).
2. Ejecuta el script SQL que se encuentra en:  
   `src/main/sql/crear_akihabara_db.sql`
3. Asegúrate de que se han creado correctamente:
   - La base de datos `akihabara_db`
   - Las tablas `productos` y `clientes`
   - Tu usuario y contraseña (con los datos que hayas personalizado en el script)

---

**2. Crear el archivo `config.properties`**

1. En el **mismo directorio** donde se colocan los `.jar` y `.bat`, crea un archivo llamado:  
   `config.properties`

2. Copia este contenido y **ajústalo según tus datos** (explicado en los apartados "Archivo `config.properties` (no versionado)" y "API Key / Asistente IA (LLM)".

**3. Verifica tu entorno Java**

- Asegúrate de tener **Java 17** instalado en tu sistema.
- Ejecuta en la terminal `java -version` para comprobar que está correctamente instalado.
- Si no lo tienes, puedes descargarlo desde:  
  [https://www.oracle.com/java/technologies/downloads/archive/](https://www.oracle.com/java/technologies/downloads/archive/)

**4. Ejecutar la aplicación**

1. Copia los siguientes archivos al mismo directorio (a la misma altura):

   - `akihabara_consola.jar`
   - `akihabara_grafica.jar`
   - `akihabara_menu.jar`
   - `AkihabaraMarket_consola.bat`
   - `AkihabaraMarket_grafica.bat`
   - `AkihabaraMarket_dual.bat`
   - `config.properties`

2. Ejecuta haciendo doble clic en el archivo `.bat` deseado:

   - `AkihabaraMarket_consola.bat` → modo consola  
   - `AkihabaraMarket_grafica.bat` → interfaz gráfica  
   - `AkihabaraMarket_dual.bat` → selecciona modo al iniciar  
   
**Posibles errores al ejecutar la aplicación:**

- Si ves un mensaje de error sobre conexión:
    - Revisa que el archivo `config.properties` esté en la misma carpeta.
    - Comprueba que el usuario y contraseña de la base de datos sean correctos.

- Si no funciona el asistente IA:
    - Revisa si la API Key es válida y está bien copiada.
    - Asegúrate de tener acceso a internet.
 
**Alternativa de ejecución: Ejecutar desde terminal (sin archivos `.bat`)**

Si no estás en Windows o prefieres lanzar los `.jar` manualmente desde la terminal o consola, puedes hacerlo así, teniendo cmd en la ubicación donde se encuentran los jar:

- **Versión consola:** `java -jar akihabara_consola.jar`
- **Version gráfica (Swing):** `java -jar akihabara_grafica.jar`
- **Versión menú de arranque dual** (para elegir modo de arranque): `java -jar akihabara_dual.jar`

⚠️**Recuerda**
El archivo config.properties debe estar en el mismo directorio desde donde ejecutas el comando, ya que el programa lo carga desde ahí.
---

# Licencia
Únicamente de uso educativo y sin fines comerciales.

---

**🔓🧠🤝 Código abierto y uso libre 🤝🧠🔓**

Este humilde proyecto ha sido desarrollado como proyecto durante mis prácticas formativas del 1er curso del Grado Superior de Desarrollo de Aplicaciones, con el mero objetivo de seguir aprendiendo y aplicar de forma práctica los conocimientos adquiridos durante dicho curso.

Siéntete libre de explorar el código, modificarlo, reutilizarlo, mejorarlo o ampliarlo para fines educativos, personales o colaborativos.

El software libre no es solo una forma de programar, sino también una forma de entender el conocimiento como algo que se construye entre todas las personas de la comunidad.

Mara ❤
