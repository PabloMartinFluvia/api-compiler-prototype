1. El request body es lo que más puede dar problemas, debido a que cada API puede pedir un schema distinto.
2. Ídem con el response body, pero se soluciona con un ApiResponseBody que accepte todos los posibles campos + al deserializar deja null / 0 los no usados + como el servicio sabe a que API se ha llamado sabrá "que campos le interesa mirar" (pendiente de implementar y diseñar: según aparezca la necesidad).
3. En el proxy lo que puede variar son los headers, pero ya está medio montado para que se coloquen solo los requeridos.

### Request body:
1. Al proxy no le importa el tipo de objecto (se le pasa como parámetro y lo mete directamente en el body del client).
2. Incluso podría acceptar un tipo Object o T, y funciona igual (probado + pasa los test).
3. Pero esto, en teoría, permitiría que el servicio le pasara el objeto "que le diera la gana" -> alguien podría cometer algun error.
4. Por esto, se podría montar algun tipo de padre abstracto para todas las posibles clases de ApiRequestBody (solamente en caso que se necesiten varias, para forzar al servicio que solo puede proporcionar al proxy un objeto que herede/implemente ese tipo).
5. De esa manera: quien inicialize el body lo devuelve al servicio upcasteado a esa clase -> se lo pasa al proxy -> lo mete directamente en el body del client -> se serializa correctamente (incluso si la clase padre no tubiese metodos / o ningún get, debido a que al serializar jackson mira la instancia concreta -> se ha hecho la prueba montando la jerarquía con ambas de este paquete + han pasado los tests).