
import org.specs2.mutable._  
import org.specs2.runner._
import org.junit.runner._

import play.api.test._  
import play.api.test.Helpers._

import models.Task

@RunWith(classOf[JUnitRunner])
class ModelTaskSpec extends Specification {
	"Task model" should{
		"get task" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val task = Task.getTask(1)
				task.label must equalTo("User 1 - Tarea 1 - Id 1")
				task.users_id must equalTo(1)
			}
		}

		"create task" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				Task.create("Tarea test 1",1,Some("2009-09-22 08:08:11"))

				val tasks = Task.all(None)
				var encontrada = false
				tasks.map { task =>
					if(task.label == "Tarea test 1" && task.users_id == 1) encontrada = true
				}
				encontrada must equalTo(true)
			}
		}

		"listing tasks" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val tasks = Task.all(None)
				tasks.length must equalTo(9)
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
	}
}