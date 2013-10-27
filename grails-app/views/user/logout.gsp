<%@ page import="com.gtunes.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title>Logout</title>
	</head>
	<body>
		<h1>Logout</h1>
		<p>See you!</p>
		<g:link controller="store" action="index">Back to the store home</g:link>
	</body>
</html>
