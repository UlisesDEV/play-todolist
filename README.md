This is your new Play application
=================================

This file will be packaged with your application, when using `activator dist`.

Heroku APP URL: http://desolate-savannah-9377.herokuapp.com/
Practica 0: http://www.dccia.ua.es/dccia/inf/asignaturas/MADS/practicas/Practica0.html

=================================
#TASK 1 

$ heroku login 
$ heroku create --stack cedar 
$ git push heroku master

$ git commit -a -m "Enlace a aplicación desplegada en Heroku"
$ git push -u origin master

=================================
#TASK 2

TODO http://www.playframework.com/documentation/2.1.x/ScalaTodoList

# etiquetamos el último commit con v0.0
git tag -a v0.0 -m "Versión inicial"
git push origin --tags
