import org.specs2.mutable._  
import org.specs2.runner._
import org.junit.runner._

import play.api.test._  
import play.api.test.Helpers._

import models.Category
import java.util.Date

@RunWith(classOf[JUnitRunner])
class ModelCategorySpec extends Specification {
	"Task model" should{
		"get category" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val Some(category) = Category.getCategory(1)
				category.name must equalTo("Categoria 1 - Usuario 1")
				category.users_id must equalTo(1)
			}
		}

		"get categories by users_id" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val user_id = 1
				val categories = Category.allFromUser(user_id)
				categories.length must equalTo(3)
				var correctas = 0
				categories.map { category =>
					if(category.users_id == user_id) correctas += 1
				}
				correctas must equalTo(3)
			}
		}

		"get categories by invalid users_id" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val categories = Category.allFromUser(99)
				categories.length must equalTo(0)
			}
		}

		"create category" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				Category.create("Tarea test 1",1)

				val categories = Category.all()
				var encontrada = false
				categories.map { category =>
					if(category.name == "Tarea test 1" && category.users_id == 1) encontrada = true
				}
				encontrada must equalTo(true)
				categories.length must equalTo(10)
			}
		}

		"listing categories" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val categories = Category.all()
				categories.length must equalTo(9)
			}
		}

		"delete category" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				Category.delete(1) must equalTo(1)
			}
		}

		"delete fail category" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				Category.delete(99) must equalTo(0)
			}
		}

	}
}