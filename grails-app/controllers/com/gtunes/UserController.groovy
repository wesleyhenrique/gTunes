package com.gtunes

import org.junit.internal.runners.statements.Fail;
import org.springframework.dao.DataIntegrityViolationException

class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	def login(LoginCommand cmd){
    	if(request.method != "POST") return
		
		def user = cmd.GetUser()
		
		if(user && !cmd.hasErrors()){
			session.user = user
			redirect controller:"store"
		}else{
			render view:"/store/index",model:[loginCommand:cmd]
		}
	}
	
	def logout() {
    	session.user = null
		return[session.user]
	}
	
	def register() {
		if(request.method !=  "POST") return
		
		def u = new User()
		u.properties["login","password","firstName","lastName"] = params
		
		//Check if the password and password confirmation matches
		if(params.password != params.confirmPassword){
			u.errors.rejectValue("password","user.password.dontmach")
			return[user:u]
		}
		
		if(u.save()){
			session.user = u
			redirect controller:"store"
		}else{
			u.errors.rejectValue("","could.not.save. try again later.")
			return[user:u]
		}
	}

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def create() {
        [userInstance: new User(params)]
    }

    def save() {
        def userInstance = new User(params)
        if (!userInstance.save(flush: true)) {
            render(view: "create", model: [userInstance: userInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def show(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def edit(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        [userInstance: userInstance]
    }

    def update(Long id, Long version) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (userInstance.version > version) {
                userInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'user.label', default: 'User')] as Object[],
                          "Another user has updated this User while you were editing")
                render(view: "edit", model: [userInstance: userInstance])
                return
            }
        }

        userInstance.properties = params

        if (!userInstance.save(flush: true)) {
            render(view: "edit", model: [userInstance: userInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])
        redirect(action: "show", id: userInstance.id)
    }

    def delete(Long id) {
        def userInstance = User.get(id)
        if (!userInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
            return
        }

        try {
            userInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), id])
            redirect(action: "show", id: id)
        }
    }
}

class LoginCommand{
	String login
	String password
	private u
	
	static constraints = {
		login blank:false
		password blank:false, validator:{val, obj, errors -> 
			if(!(obj.u && obj.u.password == obj.password && obj.u.login == obj.login)){
				errors.rejectValue("password","login.or.password.not.valid")
				return false
			}
		}
	}
	
	def GetUser(){
		u = User.findByLogin(login)
		this.validate()
		return[u]
	}
}