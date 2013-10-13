package com.gtunes

class UserController {
	def login(LoginCommand cmd) {
		if(request.method == 'POST') {
			if(!cmd.hasErrors()) {
				session.user = cmd.getUser()
				redirect controller:'store'
			}
			else {
				render view:'/store/index', model:[loginCmd:cmd]
			}
		}
		else {
			render view:'/store/index'
		}
	}
	
	def register() {
		if(request.method == 'POST') {
			def user = new User()
            user.properties['login', 'password', 'firstName', 'lastName'] = params
			if(user.password != params.confirm) {
				user.errors.rejectValue("password", "user.password.dontmatch")
				return [user:user]
			}
			else if(user.save()) {
				session.user = user
				redirect controller:"store"
			}
			else {
				return [user:user]
			}
		}
	}
	
	def logout() {
		session.invalidate()
		redirect controller:"store"
	}
}

class LoginCommand {
	String login
	String password
	private user
	User getUser() { 
		if(!user && login) 
			user = User.findByLogin(login, [fetch:[purchasedSongs:'join']])
		return user
	}
	static constraints = {
		login blank:false, validator:{ val, obj ->
			if(!obj.user)
				return "user.not.found"
		}
		password blank:false, validator:{ val, obj ->			
			if(obj.user && obj.user.password != val)
				return "user.password.invalid"
		}
	}
}
