<%@ page import="com.gtunes.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title>Register</title>
	</head>
	<body>
		<h1>Registration</h1>

		<div class="erros">
			<g:renderErrors bean="${user}"></g:renderErrors>
		</div>
		
		<g:form action="register" name="registerForm">
			<div class="formField">
				<label for="login">Login:</label>
				<g:textField name="login" value="${user?.login}"></g:textField>
			</div>
			
			<div class="formField">
				<label for="password">Password:</label>
				<g:passwordField name="password" value="${user?.password}"></g:passwordField>
			</div>
			
			<div class="formField">
				<label for="confirmPassword">Confirm password:</label>
				<g:passwordField name="confirmPassword" value="${params?.confirmPassword}"></g:passwordField>
			</div>
			
			<div class="formField">
				<label for="firstName">First name:</label>
				<g:textField name="firstName" value="${user?.firstName}"></g:textField>
			</div>
			
			<div class="formField">
				<label for="lastName">Last name:</label>
				<g:textField name="lastName" value="${user?.lastName}"></g:textField>
			</div>
			
			<g:submitButton name="register" class="formButton" value="Register"/>
			
		</g:form>
	</body>
</html>
