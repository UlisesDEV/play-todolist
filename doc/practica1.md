play-todolist!
===================
El proposito de la práctica es la familiarización con el framework Play para el lenguaje Scala. Para ello se debía implementar el tutorial You're first Application de la página del framework. Además, tambien se solicitaba el aprendizaje de las herramientas Git usando como repositorio remoto Bitbucket y la herramienta de despliegue Heroku para publicar la aplicacion. La aplicación la tengo desplegada en esta dirección. También se pide el realizar esta memoria con los conceptos adquiridos durante el desarrollo de la práctica.

----------

Comenzamos
-------------

Para crear una nueva aplicacion en Play tenemos que utilizar este comando:
```
$ play new <nombre app>
```
Ese comando se encarga de crear un directorio con el nombre de la aplicacion que contine todo el arbol de directorios de un proyecto Play. Estos directorios son los siguientes:
```
app/: contiene el codigo de la aplicacion en si, dividido en los subdirectorios models, views y controllers (Siguiendo la arquitectura modelo-vista-controlador).
conf/: contiene los ficheros de configuración del proyecto, entre ellos el main (application.conf), el fichero routes con las direcciones de la aplicación y messages para traducciones.
project/: con los scripts de construcción de la aplicación.
public/: contiene los recursos publicos de la aplicacion como ficheros Javascript, hojas de estilo, imagenes...
test/: contiene los tests de la aplicacion en formato Specs2.
```

Para iniciar la aplicación:
```
$ activator run
```

Tareas
===================

#### Task 3 [RESOLVED]

>**[RESOLVED]** TODO Create API REST functions using scala timing functions

> - **[RESOLVED] 3.1** ADD endTime to tasks
> - **[RESOLVED] 3.2** ADD function end to API Rest
> - **[RESOLVED] 3.3** ADD function ended user task to API Rest



#### Task 2 [RESOLVED]

>**[RESOLVED]** TODO Create API REST with users

> - **[RESOLVED] 2.1** GET /{login}/tasks /*Lista todas las tareas de un usuario. Por ejemplo GET /domingogallardo/tasks/.*/
> - **[RESOLVED] 2.2** POST /{login}/tasks /*Añade una nueva tarea a un usuario.*/

#### Task 1 [RESOLVED]

>**[RESOLVED]** TODO Create API REST

> - **[RESOLVED] 1.1** GET /tasks/{id}
> - **[RESOLVED] 1.2** POST /tasks
> - **[RESOLVED] 1.3** GET /tasks
> - **[RESOLVED] 1.4** DELETE /tasks/{id}


#### Task 0 [RESOLVED]

>**[RESOLVED]** http://www.playframework.com/documentation/2.1.x/ScalaTodoList

> - Etiquetamos el último commit con v0.0
> - git tag -a v0.0 -m "Versión inicial"
> - git push origin --tags

#API REST
Routes
: Todas las tareas o las terminadas antes de esa fecha.
> GET 	/tasks 					controllers.Application.tasks(end: Option[String] ?= None)

: Retorna la tarea con ese ID
> Ej: localhost:9000/tasks
> GET 	/tasks/:id 				controllers.Application.getTask(id: Long)

: Añade una nueva tarea
> Ej: POST localhost:9000/tasks 
> POST    /tasks                  controllers.Application.newTask

: Elimina una tarea
> Ej: DELETE localhost:9000/tasks/10
> DELETE 	/tasks/:id 				controllers.Application.deleteTask(id: Long)

: Devuelve todas las tareas de un usuario si existe.
> Ej: localhost:9000/user1/tasks
> GET 	/:login/tasks 			controllers.Application.userTasks(login: String)

: Añade una nueva tarea a un usuario
> Ej: localhost:9000/user1/tasks
> POST 	/:login/tasks 			controllers.Application.newUserTask(login: String)

: Todas las tareas completadas de un usuario.
> Ej: localhost:9000/user1/tasks/completed
> GET 	/:login/tasks/completed 			controllers.Application.userTasksEnded(login: String)
>> ![enter image description here](http://ulisesdev.com/img/tareas_usuario1_completadas.PNG)

#FORMULARIOS
He creado formularios para comprobar la potencia de Play y las ayudas que este nos puede ofrecer a la hora de crear templates y prototipos. Entre los mismos se encuentran los listados de usuarios, tareas (junto con operaciones CRUD) y las tareas pertenecientes a un usuario en concreto.

### URLs
> http://localhost:9000
> http://localhost:9000/tasksForms
>> ![Formulario para añadir tareas](http://ulisesdev.com/img/tareas_listado.PNG)
>
>> ![Formulario para añadir tareas](http://ulisesdev.com/img/tareas_add.PNG)
>
> http://localhost:9000/usersForms
>> ![enter image description here](http://ulisesdev.com/img/usuarios_listado.PNG)
>
> http://localhost:9000/user1/tasksForms
>> ![enter image description here](http://ulisesdev.com/img/tareas_listado_usuario1.PNG)


#PARA FINALIZAR...
Con una curva de aprendizaje suave, considero a Scala junto con Play, un conjunto de herramientas potentes, con las que realizar un prototipado de manera rápida no es complicado. Ha sido un poco frustrante la falta de documentación, para lo que en algunos casos llegaba a ser tedioso para realizar alguna tarea que requería salirse un poco de la norma.