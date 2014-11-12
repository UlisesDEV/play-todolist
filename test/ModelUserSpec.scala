import org.specs2.mutable._  
import org.specs2.runner._
import org.junit.runner._

import play.api.test._  
import play.api.test.Helpers._

import models.Users
import models.Task

@RunWith(classOf[JUnitRunner])
class ModelUserSpec extends Specification {
	"User model" should{
		"get user" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val Some(user) = Users.getUser(1)
				user.email must equalTo("user1@user1.com")
				user.login must equalTo("user1")
				user.password must equalTo("pass1")
			}
		}

		"get invalid user" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val user = Users.getUser(99)
				user should be (None)
				
			}
		}

		"get user by login" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val Some(user) = Users.getUserByLogin("user1")
				user.email must equalTo("user1@user1.com")
				user.login must equalTo("user1")
				user.password must equalTo("pass1")
			}
		}

		"get invalid user by login" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val user = Users.getUserByLogin("user111")
				user should be (None)
			}
		}

		"create user" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				Users.create("usertest@usertest.com","usertest","testpassword")

				val users = Users.all()
				var encontrada = false
				users.map { user =>
					if(user.email == "usertest@usertest.com" && user.login == "usertest" && user.password == "testpassword") encontrada = true
				}
				encontrada must equalTo(true)
				users.length must equalTo(4)
			}
		}

		"listing users" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				val users = Users.all()
				users.length must equalTo(3)
			}
		}

		"delete user" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				Users.delete(1) must equalTo(1)
				val tasks = Task.all(None)
				tasks.length must equalTo(6)
			}
		}

		"delete fail user" in {
			running(FakeApplication(additionalConfiguration = inMemoryDatabase())){
				Users.delete(99) must equalTo(0)
			}
		}
	}
}