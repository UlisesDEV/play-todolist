package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.api.libs.json._
import play.api.libs.functional.syntax._

//TODO Add endTast dateTime
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
	  	SQL("select * from users").as(users *)
	}
  
  	def create(email: String,login: String,password: String) {
	  	DB.withConnection { implicit c =>
	    	SQL("insert into users (email,login,password) values ({email},{login},{password})").on(
	      		'email -> email, 'login -> login, 'password -> password
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

  	def getUser(id: Long): Option[Users] = DB.withConnection { implicit c =>
	  	val rows = SQL("select * from users where id = {id}").on("id" -> id).apply()
	  	if(!rows.isEmpty){
	  		val firstRow = rows.head
	  		val user: Option[Users] = Some(new Users(firstRow[Long]("id"),firstRow[String]("email"),firstRow[String]("login"),firstRow[String]("password")))
	  		user
	  	} 		
	  	else{ None }
	}

	def getUserByLogin(login: String): Option[Users] = DB.withConnection { implicit c =>
	  	val rows = SQL("select * from users where login = {login}").on("login" -> login).apply()
	  	if(!rows.isEmpty){
	  		val firstRow = rows.head
	  		val user: Option[Users] = Some(new Users(firstRow[Long]("id"),firstRow[String]("email"),firstRow[String]("login"),firstRow[String]("password")))
	  		user
	  	} 		
	  	else{ None }
	}
}