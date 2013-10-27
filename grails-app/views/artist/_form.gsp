<%@ page import="com.gtunes.Artist" %>



<div class="fieldcontain ${hasErrors(bean: artistInstance, field: 'name', 'error')} ">
	<label for="name">
		<g:message code="artist.name.label" default="Name" />
		
	</label>
	<g:textField name="name" value="${artistInstance?.name}"/>
</div>

