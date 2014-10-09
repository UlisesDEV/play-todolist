This is your new Play application
=================================

This file will be packaged with your application, when using `activator dist`.

Heroku APP URL: http://desolate-savannah-9377.herokuapp.com/
Practica 0: http://www.dccia.ua.es/dccia/inf/asignaturas/MADS/practicas/Practica0.html

#TASK 3
---------------------------------
>**[WIP]** TODO Create API REST functions using scala timing functions

> - **[WIP] 3.1** ADD endTime to tasks
> - **3.2** ADD function today to API Rest



#TASK 2
---------------------------------

>**[RESOLVED]** TODO Create API REST with users

> - **[RESOLVED] 2.1** GET /{login}/tasks /*Lista todas las tareas de un usuario. Por ejemplo GET /domingogallardo/tasks/.*/
> - **[RESOLVED] 2.2** POST /{login}/tasks /*Añade una nueva tarea a un usuario.*/

#TASK 1
---------------------------------

>**[RESOLVED]** TODO Create API REST

> - **[RESOLVED] 1.1** GET /tasks/{id}
> - **[RESOLVED] 1.2** POST /tasks
> - **[RESOLVED] 1.3** GET /tasks
> - **[RESOLVED] 1.4** DELETE /tasks/{id}


#TASK 0 [RESOLVED]
---------------------------------

>**[RESOLVED]** http://www.playframework.com/documentation/2.1.x/ScalaTodoList

> - Etiquetamos el último commit con v0.0
> - git tag -a v0.0 -m "Versión inicial"
> - git push origin --tags


#GIT help
---------------------------------

/*Create branch with changes*/

```
git checkout -b todo-issueX
```

/*Merge conservating all branches*/

```
git merge --no-ff todo-issueX
```

/*Beautiful logging*/

```
git log --graph --oneline --all
git log --graph --date-order -C -M --pretty=format:"<%h> %ad [%an] %Cgreen%d%Creset %s" --all --date=short
```

/*Do a commit*/

```
git commit -a -m "Enlace a aplicación desplegada en Heroku"
```

/*Push to remote servers*/

```
git push -u origin master
```


#Heroku help
---------------------------------

```
heroku login 
heroku create --stack cedar
git push heroku master
```


#Activator help
---------------------------------

```
activator new play-todolist
cd play-todolist
rm activator*
activator test
activator run
```
