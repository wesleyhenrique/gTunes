package com.gtunes



import org.junit.*
import grails.test.mixin.*

@TestFor(UserController)
@Mock(User)
class UserControllerTests {

	void testRegisterPasswordsDontMatch(){
		request.method = "POST"
		
		params.login = "userhome"
		params.password = "abc123"
		params.confirmPassword = "xyz987"
		params.firstName = "User"
		params.lastName = "Home"
		
		def model = controller.register()
		def user = model.user
		
		assert user.hasErrors()
		assert user.errors["password"].code == "user.password.dontmach"	
	}
	
	void testLoginCommandInvalidLogin(){
		request.method = "POST"
		
		def cmd = new LoginCommand()
		
		cmd.login = "invalid1"
		cmd.password = "invalid"
		
		cmd.GetUser()

		assert cmd.hasErrors()
		assert cmd.errors["password"].code == "login.or.password.not.valid"	
	}
	
	void testInvalidLogin(){
		request.method = "POST"
		
		params.login = "invalid2"
		params.password = "invalid"
		
		controller.login()
		def cmd = model.loginCommand
		
		assert cmd.hasErrors()
		assert cmd.errors["password"].code == "login.or.password.not.valid"
		assert session.user == null
		assert view == "/store/index"
	}
	
	void testValidLogin(){
		request.method = "POST"
		
		def u = new User(login:"valid1",password:"valid1",firstName:"Valid",lastName:"Valid").save()
		
		params.login = "valid1"
		params.password = "valid1"
		
		controller.login()
		def cmd = model.loginCommand
		
		assert session.user != null
	}
}
