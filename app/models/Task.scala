package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Task(id: Long, label: String,users_id: Long)
	
object Task {
	implicit val taskWrites: Writes[Task] = (
	  (JsPath \ "id").write[Long] and
	  (JsPath \ "label").write[String] and
	  (JsPath \ "users_id").write[Long]
	)(unlift(Task.unapply))

  	val task = {
		get[Long]("id") ~ 
		get[String]("label") ~ 
		get[Long]("users_id") map {
			case id~label~users_id => Task(id, label,users_id)
		}
	}

  	def all(): List[Task] = DB.withConnection { implicit c =>
	  	SQL("select * from task").as(task *)
	}
  
  	def create(label: String,users_id: Long) {
	  	DB.withConnection { implicit c =>
	    	SQL("insert into task (label,users_id) values ({label},{users_id})").on(
	      		'label -> label,'users_id -> users_id
	    	).executeUpdate()
	  	}
  	}
  
  	def delete(id: Long) : Int =  {
  		DB.withConnection { implicit c =>
	    	SQL("delete from task where id = {id}").on(
	      	'id -> id
	    	).executeUpdate()
	  	}
  	}

  	def getTask(id: Long): Task = DB.withConnection { implicit c =>
	  	val rows = SQL("select * from task where id = {id}").on("id" -> id).apply()
	  	if(!rows.isEmpty){
	  		val firstRow = rows.head
	  		new Task(firstRow[Long]("id"),firstRow[String]("label"),firstRow[Long]("users_id"))
	  	} 		
	  	else{ new Task(0,"",0) }
	}
}