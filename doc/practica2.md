play-todolist! v0.2
===================
El objetivo principal de la práctica es entender el funcionamiento de las pruebas en Play framework. Play utiliza el framework de pruebas Scala specs2.

Tareas a realizar
===================

Pruebas de la aplicación desarrollada hasta ahora
--------------------------

En primer lugar se deben añadir los test para que los features añadidas hasta el momento los superen. Para esto en primer lugar realizaremos las pruebas de los modelos de manera individual, intentando en un primer momento eliminar todas las dependencias de las que dispongan.

En segundo lugar probaremos la traza completa de las peticiones rest que se puedan realizar sobre el sistema. Para eso utilizaremos las herramientas que nos ofrece Scala y Specs2, las cuales puede generar solicitudes "Fake", emulando una peticion externa que pueda realizar un usuario.


Nueva característica (feature) a desarrollar usando TDD
--------------------------

En esta tarea debemos añadir categorias a la aplicación utilizando la metodologia TDD. ¿Y que se entiende por metodologia TDD?

```
Desarrollo guiado por pruebas de software, o Test-driven development (TDD) es una práctica de programación orientada a objetos que involucra otras dos prácticas: Escribir las pruebas primero (Test First Development) y Refactorización (Refactoring).
Para que funcione el desarrollo guiado por pruebas, el sistema que se programa tiene que ser lo suficientemente flexible como para permitir que sea probado automáticamente. Cada prueba será suficientemente pequeña como para que permita determinar unívocamente si el código probado pasa o no la verificación que ésta le impone. El diseño se ve favorecido ya que se evita el indeseado "sobre diseño" de las aplicaciones y se logran interfaces más claras y un código más cohesivo
```

#### Aplicacion de TDD en esta practica

Los pasos a seguir para la correcta aplicacion de TDD han sido los siguientes:

- Diseñar lo que deseamos que realice nuestra aplicacion.
- Diseñar e implementar los test que aseguren que nuestra funcionalidad es correcta.
- Refactorizar el codigo para que los test se vayan completando con exito.
- Comprobar que los test pasan correctamente con lo que ya podemos integrar en la rama master para la puesta en producción.

#### Test añadidos

Para la implementacion de la nueva funcionalidad se han implementado los siguientes test:

- get category (obtiene una categoria)
- get categories by users_id (obtiene las categorias de un usuario)
- get categories by invalid users_id (error por usuario invalido)
- create category (crear una categoria)
- listing categories (lista todas las categorias)
- delete category (elimina una categoria)
- delete fail category (genera error de id de categoria invalida)

Ademas de esta funcionalidad en el modelo de categorias se han añadido nuevas funcionalidades a las tareas:

- all categories from user (lista las categorias de un usuario)
- all tasks from category (lista las tareas de una categoria)


Ejecutar las pruebas diseñadas
===================

Una vez hemos diseñado las pruebas se debe proceder a implementarlas. Ej:

```
class ModelTaskSpec extends Specification {
	"Task model" should{
		"get task" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val Some(task) = Task.getTask(1)
				task.label must equalTo("User 1 - Tarea 1 - Id 1")
				task.users_id must equalTo(1)
			}
		}
	}
}
```

En el ejemplo podemos ver como para que se ejecute la prueba la clase debe extender de Specification. Ademas se deben indicar las pruebas que esta clase debera pasar, que para el caso es que nos devuelva una tarea con el id requerido. Si la prueba pasa con exito se nos indicara con un "+" en verde en la consola.

#### Importante
> Al ejecutar los test Scala inicializa la base de datos con la configuracion indicada en las evoluciones.
> Esto es asi para cada uno de los test, por lo que se puede incializar la base de datos con datos de prueba para realizar los test.

En el siguiente ejemplo se muestra una prueba al controlador de la aplicacion como si de una peticion externa se tratase, esta nos devolvera una serie de datos en JSON (la serializacion de una tarea en concreto que ya de antemano sabemos que existe y queremos que la devuelva de manera correcta en formato JSON), Ej:

```
class ApplicationSpec extends Specification {
  "Application" should {
	    "get task in json" in { 
	      running(FakeApplication()) {
	        val id_task = 1
	        val Some(home) = route(FakeRequest(GET, "/tasks/"+id_task))
	        status(home) must equalTo(OK)  
	        contentType(home) must beSome.which(_ == "application/json")
	        contentAsString(home) must contain ("\"id\":1,\"label\":\"User 1 - Tarea 1 - Id 1\"")
	      }
	    }
	}
}
```

Para ejecutar las pruebas solo debemos usar el siguiente comando en la consola de comandos:

```
$ activator test
```

Automaticamente el framework realizara las pruebas necesarias y nos indicara si ha ocurrido un error de compilacion o si por el contrario no se ha realizado correctamente el test.

Para finalizar...
===================

Siendo tedioso el tener que generar las pruebas necesarias, Play + Specs2 nos aportan todas las herramientas necesarias para facilitarnos la tarea. Aun habiendo trabajado con otros frameworks de prueba en otros lenguajes como JUnit o PHPUnit es muy interesante el poder realizar peticiones de prueba para comprobar que la respuesta es la correcta. Esto consigue que se pueda cumplir la integración continua con un minimo esfuerzo y añade la tranquilidad de saber que tu aplicación funciona correctamente.