package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Task
import models.Users
import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.util.{Date, Locale}
import java.text.DateFormat
import java.text.DateFormat._




object Application extends Controller {

	/* FORM TASKS */
	val taskForm = Form[(String,Long,Long,Option[String])](
		tuple(
		    "label" -> nonEmptyText,
		    "users_id" -> longNumber(min = 0),
		    "category_id" -> longNumber(min = 0),
		    "end" -> optional(text)
		  )
		)

  	def index = Action {
    	Redirect(routes.Application.tasksForms())
  	}

  	// Devuelve todas las tareas posteriores a la fecha si lo indican
  	// Ej: http://localhost:9000/tasksForms?end=2009-09-23
 	def tasksForms(end: Option[String]) = Action {
 		Ok(views.html.index(Task.all(end), taskForm,Users.all()))
	}	
  
  	def newTaskForms = Action { implicit request =>
	  	taskForm.bindFromRequest.fold(
	    	errors => BadRequest(views.html.index(Task.all(None), errors,Users.all())),
	    	{ case(label, users_id,category_id,end) => {
		    		Task.create(label,users_id,category_id,end)
		      		Redirect(routes.Application.tasksForms())
	    		}
	    	}
	  	)
	}

	def deleteTaskForms(id: Long) = Action { 
  		Task.delete(id)
	 	Redirect(routes.Application.tasksForms())
	}

	/* FORMS USERS */
	val usersForm = Form( "label" -> nonEmptyText )

	def usersForms = Action {
		Ok(views.html.users(Users.all(), usersForm))
	}

	def userTasksForms(login: String) = Action {
		val user = Users.getUserByLogin(login)
		user match {
		  case Some(user) =>
		    Ok(views.html.index(Task.allFromUser(user.id), taskForm,Users.all()))
		  case None =>
		    Ok("No existe el usuario")
		}
		
	}

	/* API REST */
	val apitaskForm = Form[(String,Long,Long,Option[String])](
		tuple(
		    "label" -> nonEmptyText,
		    "users_id" -> longNumber,
		    "category_id" -> longNumber,
		    "end" -> optional(text)
		  )
		)

	// Devuelve todas las tareas. Si se indica fecha final devuelve las terminadas antes de esa fecha.
	def tasks(end: Option[String]) = Action {
		val tasks = Json.toJson(Task.all(end))
		Ok(tasks)
	}

	// Devuelve una tarea serializada
	def getTask(id: Long) = Action {
		val task = Task.getTask(id)

		task match {
		  case Some(task) =>
			Ok(Json.toJson(task))
		  case None =>
		    NotFound("La tarea no existe")
		}
	}

	// Crea una nueva tarea y la retorna serializada.
	def newTask = Action { implicit request =>
	  	taskForm.bindFromRequest.fold(
	    	errors => BadRequest(""),
	    	{ case(label,users_id,category_id,end) => {
		    		Task.create(label,users_id,category_id,end)
		      		Ok(Json.obj("label" ->label,"users_id" -> users_id,"category_id" -> category_id,"end" -> end))
	    		}
	    	}
	  	)
	}
  	
  	// Elimina una tarea
  	def deleteTask(id: Long) = Action {
  		if(Task.delete(id) > 0){
  			Ok("")
  		} else {
  			NotFound("")
  		}
	}

	// Devuelve las tareas de un usuario si existe.
	def userTasks(login: String) = Action {
		val user = Users.getUserByLogin(login)
		user match {
		  case Some(user) =>
		  val task = Json.toJson(Task.allFromUser(user.id))
			Ok(task)
		  case None =>
		    NotFound("El usuario no existe")
		}
		
	}

	// Genera una nueva tarea asignandosela al usuario indicado
	def newUserTask(login: String) = Action { implicit request =>
	  	apitaskForm.bindFromRequest.fold(
	    	errors => BadRequest(""),
		    { case(label, users_id,category_id,end) => {
		    	val user = Users.getUserByLogin(login)
				user match {
				  case Some(user) =>
				  	Task.create(label,user.id,category_id,end)
		      		Ok(Json.obj("label" -> label, "users_id" -> user.id,"category_id"->category_id,"end"->end))
				  case None =>
				    NotFound("")
				}
		    }
		}
	  	)
	}

	// Devuelve las tareas terminadas (las que tienen fecha de finalizacion) de un usuario
	// Ej: http://localhost:9000/user1/tasks/completed
	def userTasksEnded(login: String) = Action {
		val user = Users.getUserByLogin(login)
		user match {
		  case Some(user) =>
		  val task = Json.toJson(Task.allFromUserEnded(user.id))
			Ok(task)
		  case None =>
		    NotFound("El usuario no existe")
		}
		
	}

}