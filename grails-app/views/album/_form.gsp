<%@ page import="com.gtunes.Album" %>



<div class="fieldcontain ${hasErrors(bean: albumInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="album.title.label" default="Title" />
		
	</label>
	<g:textField name="title" value="${albumInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: albumInstance, field: 'artist', 'error')} required">
	<label for="artist">
		<g:message code="album.artist.label" default="Artist" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="artist" name="artist.id" from="${com.gtunes.Artist.list()}" optionKey="id" required="" value="${albumInstance?.artist?.id}" class="many-to-one"/>
</div>

