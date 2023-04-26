## Prototipado de invocaciones:

### Objetivo:
1. Realizar llamadas a una API de terceros (y gratuita) que ejecute un codigo escrito en una determinada tecnologia.
2. Dados unos determinados inputs, que nos devuelva el resultado al ejecutarse el código.

### Riesgos:
1. Al ser de terceros no controlamos la API. Podría dejar de dar el servicio "inesperadamente y sin previo aviso", debido a mútliples causas.
2. No efectuar ningun pago implica una limitación en las peticiones (por día, o por mes, o por cada x tiempo, etc...).

### Solución viable:
1. Tener siempre disponibles un número X (**cantidad a decidir**) de APIS "de backup" a las que el sistema se pueda conectar.

### Requerimientos para implementación:
1. Un diseño flexible y modular, para permitir la automatización de canvio de API en caso de necesidad.
2. A más cantidad de de apis de backup, más generalista tendrá que ser el diseño y, por tanto, más abstracto. Quizás incluso requiera el uso de patrones, para aconseguir el princio Open/Close.

## Situación actual:
### Objetivo: 
1. Realizar peticiones funcionales a una api + tests de prueba.
2. Diseño debería permitir que, modificando el valor de un enum (api target), la petición se realize correctamente (si el body de la petición es coherente).


### Proxy:
1. Client pendiente de configurar. Solo se ha implementado lo mínimo necesario para poder realizar las peticiones correctamente.
2. Desconoce **todos** los parámetros requeridos para realizar la petición.
   1. Asociado a un objeto WorkingApisSpecs: encapsula un mapa que relaciona cada una de las apis que tengamos localizadas como disponibles (**enum**) con un objeto ApiSpec. Es este último quien debe poder proporcionar al client los datos para realizar la conexion (headers, url, secrets, etc...).
   2. El método que se invoca en el proxy recibe como parámetros el enum de la API a usar (para así solicitar los specs al map) y el body (que tiene que ser "coherente" con la API indicada).
   3. Devuelve un ApiResponseBody (mono). Este tiene que poder ser mapeable para **cualquier respuesta de cualquer api**. Si la cosa se complica también se podría pasar un .class adecuado como parametro del método.

### DemoService
1. Método implementado solo para poder hacer las pruebas. Pasa al Proxy el enum de la api + el ApiRequestBody.
2. Para los bodys "tira" de jsons almacenados en resources que simulan varios ApiRequestBody.
3. La dificultad principal que tendrá este servicio será **como mapear el código que introduzca el usuario a: un ejecutable + en formato String***, y así poderlo poner como atributo en un ApiRequestBody. Todas las apis que he visto lo piden así. **ver jsons.requestBodys**
4. Esto dependerá de como quiere el cliente que se introduzca la propuesta + como el front nos pase los datos.

### APIXXX
1. ApiCompiler: enum representando las posibles apis disponibles.
2. Request + Response: los dtos. Los que pueden dar problemas si hay mucha variabilidad entre apis.
3. ApiSpec: Encapsula los detalles de como se debe conectar el proxy a una api. Lo más generalista y homogenio posible.
4. WorkingApisSpecs: bean. Por cada posible api que tengamos, tendría que inizializar el correspondiente ApiSpec y asociarlos como clave : valor.

