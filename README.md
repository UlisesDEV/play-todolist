This is your new Play application
=================================

This file will be packaged with your application, when using `activator dist`.

Heroku APP URL: http://desolate-savannah-9377.herokuapp.com/
Practica 0: http://www.dccia.ua.es/dccia/inf/asignaturas/MADS/practicas/Practica0.html

#TASK 1
---------------------------------

>**[WIP]** TODO Create API REST

> - **[RESOLVED] 3.1** GET /tasks/{id}
> - **3.2** POST /tasks
> - **3.3** GET /tasks
> - **3.4** DELETE /tasks/{id}


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
