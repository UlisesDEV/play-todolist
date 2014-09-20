package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Task
import play.api.libs.json._
import play.api.libs.functional.syntax._



object Application extends Controller {

	/*FORM TASKS*/
	
	val taskForm = Form( "label" -> nonEmptyText )

  	def index = Action {
    	Redirect(routes.Application.tasksForms)
  	}

 	def tasksForms = Action {
		Ok(views.html.index(Task.all(), taskForm))
	}	
  
  	def newTaskForms = Action { implicit request =>
	  	taskForm.bindFromRequest.fold(
	    	errors => BadRequest(views.html.index(Task.all(), errors)),
		    label => {
		      Task.create(label)
		      Redirect(routes.Application.tasksForms)
		    }
	  	)
	}

	def deleteTaskForms(id: Long) = Action { 
  		Task.delete(id)
	 	Redirect(routes.Application.tasksForms)
	}

	/*API REST*/

	def tasks = Action {
		val tasks = Json.toJson(Task.all())
		Ok(tasks)
	}

	def getTask(id: Long) = Action {
		val task = Json.toJson(Task.getTask(id))
		Ok(task)
	}

	def newTask = Action { implicit request =>
	  	taskForm.bindFromRequest.fold(
	    	errors => BadRequest(""),
		    label => {
		      Task.create(label)
		      Ok(Json.obj("label" ->label))
		    }
	  	)
	}
  
  	def deleteTask(id: Long) = Action {
  		if(Task.delete(id) > 0){
  			Ok("")
  		} else {
  			NotFound("")
  		}
	}

}