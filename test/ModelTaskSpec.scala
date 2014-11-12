
import org.specs2.mutable._  
import org.specs2.runner._
import org.junit.runner._

import play.api.test._  
import play.api.test.Helpers._

import models.Task
import java.util.Date

@RunWith(classOf[JUnitRunner])
class ModelTaskSpec extends Specification {
	"Task model" should{
		"get task" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val Some(task) = Task.getTask(1)
				task.label must equalTo("User 1 - Tarea 1 - Id 1")
				task.users_id must equalTo(1)
			}
		}

		"get tasks by users_id" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val user_id = 1
				val tasks = Task.allFromUser(user_id)
				tasks.length must equalTo(3)
				var correctas = 0
				tasks.map { task =>
					if(task.users_id == user_id) correctas += 1
				}
				correctas must equalTo(3)
			}
		}

		"get tasks by invalid users_id" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val tasks = Task.allFromUser(99)
				tasks.length must equalTo(0)
			}
		}

		"create task" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				Task.create("Tarea test 1",1,1,Some("2009-09-22 08:08:11"))

				val tasks = Task.all(None)
				var encontrada = false
				tasks.map { task =>
					if(task.label == "Tarea test 1" && task.users_id == 1) encontrada = true
				}
				encontrada must equalTo(true)
				tasks.length must equalTo(11)
			}
		}

		"listing tasks" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val tasks = Task.all(None)
				tasks.length must equalTo(10)
			}
		}

		"delete task" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				Task.delete(1) must equalTo(1)
			}
		}

		"delete fail task" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				Task.delete(99) must equalTo(0)
			}
		}

		"listing user tasks ended" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val tasks = Task.allFromUserEnded(3)
				tasks.length must equalTo(3)
			}
		}

		"listing tasks ended before date" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				//val f = new Date(2014, 11, 12)
				val tasks = Task.all(Some("2014-11-12"))
				tasks.length must equalTo(1)
			}
		}

		"listing tasks by category_id" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				//val f = new Date(2014, 11, 12)
				val tasks = Task.allFromCategory(1)
				tasks.length must equalTo(3)
			}
		}

	}
}