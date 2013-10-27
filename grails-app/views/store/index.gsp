
<%@ page import="com.gtunes.Store" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>gTunes</title>
	</head>
	<body>
		<div id="welcome" class="welcome">
			<h1>Welcome to gTunes</h1>
			<h2>Your online music store and storage service!</h2>	
			<p>Manage your own library, browse music and purchase new tracks as they become available.</p>
		</div>
		
		<div id="navPane" class="signupBox">
			<g:if test="${session.user}">
				<ul>
					<li><g:link controller="user" action="music">My music</g:link></li>
					<li><g:link controller="store" action="shop">Store</g:link></li>
				</ul>
			</g:if>
			<g:else>
				Need an account?<br/>
				<g:link controller="user" action="register">Signup now</g:link> to start you own personal music collection.
			</g:else>
		</div>
		
		<div id="loginBox" class="loginBox">
			<g:if test="${session?.user}">
				<div style="margin-top:20px">
					<div style="float:right;">
						<a href="#">Profile</a> | 
						<g:link controller="user" action="logout">Logout</g:link><br/>
						Welcome back <span id="userFirstName">${session?.user?.firstName}</span><br/><br/>
						You have purchased {${session.user.purchasedSongs?.size() ?: 0}} songs.<br/>
					</div>
				</div>
			</g:if>
			<g:else>
				<g:form name="loginForm" url="[controller:'user',action:'login']">
				<div>Username:</div> <g:textField name="login" value="${fieldValue(bean:LoginCmd,field:'login')}"></g:textField>
				<div>Password:</div> <g:passwordField name="password"></g:passwordField><br/>
				<input type="image" src="${createLinkTo(dir:'image',file:'login-button.gif')}'}" name="loginButton" id="loginButton" border="0"></input>
				</g:form>
				<g:renderErrors bean="${loginCommand}"></g:renderErrors>
			</g:else>
		</div>
		
	</body>
</html>
