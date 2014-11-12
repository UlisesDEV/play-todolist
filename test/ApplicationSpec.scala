package test

import org.specs2.mutable._  
import org.specs2.runner._
import org.junit.runner._

import play.api.test._  
import play.api.test.Helpers._

import play.api.libs.json._
import play.api.libs.functional.syntax._

@RunWith(classOf[JUnitRunner])
class ApplicationSpec extends Specification {

  "Application" should {

    "send 404 on a bad request" in {  
      running(FakeApplication()) {  
        route(FakeRequest(GET, "/boum")) must beNone  
      }
    }

    "redirect to /tasksForms" in new WithApplication{
        val home = route(FakeRequest(GET, "/")).get
        status(home) must equalTo(303)
        redirectLocation(home) must equalTo(Some("/tasksForms"))
      }

    "get all tasks in json" in {  
      running(FakeApplication()) {

        val Some(home) = route(FakeRequest(GET, "/tasks"))

        status(home) must equalTo(OK)  
        contentType(home) must beSome.which(_ == "application/json")

        

        val json = Json.parse(contentAsString(home)).as[List[Map[String,JsValue]]]
        json.length must equalTo(10)

        contentAsString(home) must contain ("\"id\":1,\"label\":\"User 1 - Tarea 1 - Id 1\"")
        contentAsString(home) must contain ("\"id\":2,\"label\":\"User 1 - Tarea 2 - Id 2\"")
        contentAsString(home) must contain ("\"id\":3,\"label\":\"User 1 - Tarea 3 - Id 3\"")
        contentAsString(home) must contain ("\"id\":4,\"label\":\"User 2 - Tarea 1 - Id 4\"")
        contentAsString(home) must contain ("\"id\":5,\"label\":\"User 2 - Tarea 2 - Id 5\"")
        contentAsString(home) must contain ("\"id\":6,\"label\":\"User 2 - Tarea 3 - Id 6\"")
        contentAsString(home) must contain ("\"id\":7,\"label\":\"User 3 - Tarea 1 - Id 7\"")
        contentAsString(home) must contain ("\"id\":8,\"label\":\"User 3 - Tarea 2 - Id 8\"")
        contentAsString(home) must contain ("\"id\":9,\"label\":\"User 3 - Tarea 3 - Id 9\"")
        contentAsString(home) must contain ("\"id\":10,\"label\":\"User 3 - Tarea 4 - Id 10\"")

        //step(println(contentAsString(home)))
      }
    }

    "get task in json" in { 
      running(FakeApplication()) {
        val id_task = 1
        val Some(home) = route(FakeRequest(GET, "/tasks/"+id_task))
        status(home) must equalTo(OK)  
        contentType(home) must beSome.which(_ == "application/json")
        contentAsString(home) must contain ("\"id\":1,\"label\":\"User 1 - Tarea 1 - Id 1\"")
        //step(println(contentAsString(home)))
      }
    }

    "get fail task in json" in { 
      running(FakeApplication()) {
        val id_task = 99
        val Some(home) = route(FakeRequest(GET, "/tasks/"+id_task))
        status(home) must equalTo(404)
        contentAsString(home) must contain ("La tarea no existe")
        //step(println(contentAsString(home)))
      }
    }

    "delete task" in { 
      running(FakeApplication()) {
        val id_task = 1
        val Some(home) = route(FakeRequest(DELETE, "/tasks/"+id_task))
        status(home) must equalTo(200)
        //step(println(contentAsString(home)))
      }
    }

    "delete fail task" in { 
      running(FakeApplication()) {
        val id_task = 99
        val Some(home) = route(FakeRequest(DELETE, "/tasks/"+id_task))
        status(home) must equalTo(404)
        //step(println(contentAsString(home)))
      }
    }

    "all tasks from user" in { 
      running(FakeApplication()) {
        val user_login = "user1"
        val Some(home) = route(FakeRequest(GET, "/"+user_login+"/tasks"))
        status(home) must equalTo(200)

        val json = Json.parse(contentAsString(home)).as[List[Map[String,JsValue]]]
        json.length must equalTo(3)

        contentAsString(home) must contain ("\"id\":1,\"label\":\"User 1 - Tarea 1 - Id 1\"")
        contentAsString(home) must contain ("\"id\":2,\"label\":\"User 1 - Tarea 2 - Id 2\"")
        contentAsString(home) must contain ("\"id\":3,\"label\":\"User 1 - Tarea 3 - Id 3\"")
        //step(println(contentAsString(home)))
      }
    }

    "all tasks from fail user" in { 
      running(FakeApplication()) {
        val user_login = "usertest"
        val Some(home) = route(FakeRequest(GET, "/"+user_login+"/tasks"))
        status(home) must equalTo(404)
        contentAsString(home) must contain ("El usuario no existe")
        //step(println(contentAsString(home)))
      }
    }

    "create task by user" in { 
      running(FakeApplication()) {
        val user_login = "user1"
        val Some(home) = route(FakeRequest(POST, "/"+user_login+"/tasks").withFormUrlEncodedBody(("label", "User 1 - Tarea 4 - Id 11"),("users_id","0"),("category_id","1"),("end", "2009-09-22 08:08:11")))
        status(home) must equalTo(200)

        contentAsString(home) must contain ("\"label\":\"User 1 - Tarea 4 - Id 11\",\"users_id\":1,\"category_id\":1,\"end\":\"2009-09-22 08:08:11\"")
        //step(println(contentAsString(home)))
      }
    }

    "create task by fail user" in { 
      running(FakeApplication()) {
        val user_login = "user99"
        val Some(home) = route(FakeRequest(POST, "/"+user_login+"/tasks").withFormUrlEncodedBody(("label", "User 1 - Tarea 4 - Id 11"),("users_id","0"),("category_id","1"),("end", "2009-09-22 08:08:11")))
        status(home) must equalTo(404)
        //step(println(contentAsString(home)))
      }
    }

    "create task bad request" in { 
      running(FakeApplication()) {
        val user_login = "user1"
        val Some(home) = route(FakeRequest(POST, "/"+user_login+"/tasks").withFormUrlEncodedBody(("fail", "fail post")))
        status(home) must equalTo(400)
        //step(println(contentAsString(home)))
      }
    }

    "all ended tasks from user" in { 
      running(FakeApplication()) {
        val user_login = "user1"
        val Some(home) = route(FakeRequest(GET, "/"+user_login+"/tasks/completed"))
        status(home) must equalTo(200)

        val json = Json.parse(contentAsString(home)).as[List[Map[String,JsValue]]]
        json.length must equalTo(3)
        //step(println(contentAsString(home)))
      }
    }

    "all ended tasks from fail user" in { 
      running(FakeApplication()) {
        val user_login = "usertest"
        val Some(home) = route(FakeRequest(GET, "/"+user_login+"/tasks/completed"))
        status(home) must equalTo(404)
        contentAsString(home) must contain ("El usuario no existe")
        //step(println(contentAsString(home)))
      }
    }

    "all categories from user" in { 
      running(FakeApplication()) {
        val user_login = "user1"
        val Some(home) = route(FakeRequest(GET, "/"+user_login+"/categories"))
        val json = Json.parse(contentAsString(home)).as[List[Map[String,JsValue]]]
        json.length must equalTo(3)
        //step(println(contentAsString(home)))
      }
    }

    "all tasks from category" in { 
      running(FakeApplication()) {
        val category_id = 1
        val Some(home) = route(FakeRequest(GET, "/tasks/category/"+category_id))
        status(home) must equalTo(200)
        val json = Json.parse(contentAsString(home)).as[List[Map[String,JsValue]]]
        json.length must equalTo(3)
        //step(println(contentAsString(home)))
      }
    }
  }   
}