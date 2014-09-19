package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Task(id: Long, label: String)
	
object Task {
	implicit val taskWrites: Writes[Task] = (
	  (JsPath \ "id").write[Long] and
	  (JsPath \ "label").write[String]
	)(unlift(Task.unapply))

  	val task = {
		get[Long]("id") ~ 
		get[String]("label") map {
			case id~label => Task(id, label)
		}
	}

  	def all(): List[Task] = DB.withConnection { implicit c =>
	  	SQL("select * from task").as(task *)
	}
  
  	def create(label: String) {
	  	DB.withConnection { implicit c =>
	    	SQL("insert into task (label) values ({label})").on(
	      		'label -> label
	    	).executeUpdate()
	  	}
  	}
  
  	def delete(id: Long) {
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
	  		new Task(firstRow[Long]("id"),firstRow[String]("label"))
	  	} 		
	  	else{ new Task(0,"") }
	}
}