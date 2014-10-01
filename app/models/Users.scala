package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Users(id: Long, email: String,login: String,password: String)
	
object Users {
	implicit val taskWrites: Writes[Users] = (
	  (JsPath \ "id").write[Long] and
	  (JsPath \ "email").write[String] and
	  (JsPath \ "login").write[String] and
	  (JsPath \ "password").write[String]
	)(unlift(Users.unapply))

  	val users = {
		get[Long]("id") ~ 
		get[String]("email") ~ 
		get[String]("login") ~ 
		get[String]("password") map {
			case id~email~login~password => Users(id, email,login,password)
		}
	}

  	def all(): List[Users] = DB.withConnection { implicit c =>
	  	SQL("select * from users").as(task *)
	}
  
  	def create(label: String) {
	  	DB.withConnection { implicit c =>
	    	SQL("insert into users (email,login,password) values ({email},{login},{password})").on(
	      		"email" -> email, "login" -> login, "password" -> password
	    	).executeUpdate()
	  	}
  	}
  
  	def delete(id: Long) : Int =  {
  		DB.withConnection { implicit c =>
	    	SQL("delete from users where id = {id}").on(
	      	'id -> id
	    	).executeUpdate()
	  	}
  	}

  	def getTask(id: Long): Task = DB.withConnection { implicit c =>
	  	val rows = SQL("select * from users where id = {id}").on("id" -> id).apply()
	  	if(!rows.isEmpty){
	  		val firstRow = rows.head
	  		new Task(firstRow[Long]("id"),firstRow[String]("email"),firstRow[String]("login"),firstRow[String]("password"))
	  	} 		
	  	else{ new Task(0,"") }
	}
}