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

case class Category(id: Long, name: String,users_id: Long)
	
object Category {

	//Funcion para serializar el objeto de forma implicita
	implicit val categoryWrites: Writes[Category] = (
	  (JsPath \ "id").write[Long] and
	  (JsPath \ "name").write[String] and
	  (JsPath \ "users_id").write[Long]
	)(unlift(Category.unapply))

  	val category = {
		get[Long]("id") ~ 
		get[String]("name") ~
		get[Long]("users_id") map {
			case id~name~users_id=> Category(id,name,users_id)
		}
	}

	/*
	Devuelve todas las categorias
	*/
  	def all(): List[Category] = DB.withConnection { implicit c =>
	  	SQL("select * from category").as(category *)
	}
  
  	//Crea una categoria.
  	def create(name: String,users_id: Long) {
	  	DB.withConnection { implicit c =>
	    	SQL("insert into category (name,users_id) values ({name},{users_id})").on(
	      		'name -> name,'users_id -> users_id
	    	).executeUpdate()
	  	}
  	}
  	
  	//Eliminar una categoria.
  	def delete(id: Long) : Int =  {
  		DB.withConnection { implicit c =>
	    	SQL("delete from category where id = {id}").on(
	      	'id -> id
	    	).executeUpdate()
	  	}
  	}

  	//Obtener una categoria.
  	def getCategory(id: Long): Option[Category] = DB.withConnection { implicit c =>
	  	val rows = SQL("select * from category where id = {id}").on("id" -> id).apply()
	  	if(!rows.isEmpty){
	  		val firstRow = rows.head
	  		val category: Option[Category] = Some(new Category(firstRow[Long]("id"),firstRow[String]("name"),firstRow[Long]("users_id")))
	  		category
	  	} 		
	  	else{ None }
	}

	//Listar todas las categorias de un usuario.
	def allFromUser(users_id:Long): List[Category] = DB.withConnection { implicit c =>
	  	SQL("select * from category where users_id = {users_id}").on('users_id -> users_id).as(category *)
	}
}