package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.api.libs.json
import play.api.libs.json._
import play.api.libs.functional.syntax._
import java.util.{Date, Locale}
import java.text.DateFormat
import java.text.DateFormat._
import java.text.SimpleDateFormat

case class Task(id: Long, label: String,users_id: Long,category_id: Long,end: Option[Date] = None)
	
object Task {

	//Funcion para serializar el objeto de forma implicita
	implicit val taskWrites: Writes[Task] = (
	  (JsPath \ "id").write[Long] and
	  (JsPath \ "label").write[String] and
	  (JsPath \ "users_id").write[Long] and
	  (JsPath \ "category_id").write[Long] and
	  (JsPath \ "end").write[Option[Date]]
	)(unlift(Task.unapply))

  	val task = {
		get[Long]("id") ~ 
		get[String]("label") ~
		get[Long]("users_id") ~
		get[Long]("category_id") ~
		get[Option[Date]]("end") map {
			case id~label~users_id~category_id~end=> Task(id,label,users_id,category_id,end)
		}
	}

	/*
	Devuelve todas las tareas sino obtiene fecha. En caso contrario devuelve todas
	las tareas terminadas antes de esa fecha.
	*/
  	def all(end: Option[String]): List[Task] = DB.withConnection { implicit c =>
	  	end match {
		  case Some(end) =>
			SQL("select * from task where end < {end}").on("end" -> end).as(task *)
		  case None =>
		    SQL("select * from task").as(task *)
		}
	}
  
  	//Crea una tarea.
  	def create(label: String,users_id: Long,category_id:Long,end:Option[String]) {
	  	DB.withConnection { implicit c =>
	    	SQL("insert into task (label,users_id,category_id,end) values ({label},{users_id},{category_id},{end})").on(
	      		'label -> label,'users_id -> users_id,'category_id -> category_id,'end -> end
	    	).executeUpdate()
	  	}
  	}
  	
  	//Eliminar una tarea.
  	def delete(id: Long) : Int =  {
  		DB.withConnection { implicit c =>
	    	SQL("delete from task where id = {id}").on(
	      	'id -> id
	    	).executeUpdate()
	  	}
  	}

  	//Obtener una tarea.
  	def getTask(id: Long): Option[Task] = DB.withConnection { implicit c =>
	  	val rows = SQL("select * from task where id = {id}").on("id" -> id).apply()
	  	if(!rows.isEmpty){
	  		val firstRow = rows.head
	  		val task: Option[Task] = Some(new Task(firstRow[Long]("id"),firstRow[String]("label"),firstRow[Long]("users_id"),firstRow[Long]("category_id"),firstRow[Option[Date]]("end")))
	  		task
	  	} 		
	  	else{ None }
	}

	//Listar todas las tareas de un usuario.
	def allFromUser(users_id:Long): List[Task] = DB.withConnection { implicit c =>
	  	SQL("select * from task where users_id = {users_id}").on('users_id -> users_id).as(task *)
	}

	//Listar todas las tareas de un usuario que han sido realizadas.
	def allFromUserEnded(users_id:Long): List[Task] = DB.withConnection { implicit c =>
	  	SQL("select * from task where users_id = {users_id} and end IS NOT NULL").on('users_id -> users_id).as(task *)
	}

	//Listar todas las tareas de un usuario.
	def allFromCategory(category_id:Long): List[Task] = DB.withConnection { implicit c =>
	  	SQL("select * from task where category_id = {category_id}").on('category_id -> category_id).as(task *)
	}

	/*
	Ejemplo para el futuro:

	select * 
	from TableA 
	where startdate >= to_timestamp('12-01-2012 21:24:00', 'dd-mm-yyyy hh24:mi:ss')
	  and startdate <= to_timestamp('12-01-2012 21:25:33', 'dd-mm-yyyy hh24:mi:ss')

	*/
}