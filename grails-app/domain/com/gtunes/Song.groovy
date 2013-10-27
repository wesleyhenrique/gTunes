package com.gtunes

class Song {
	String title
	Album album
	Integer duration

	static belongsTo = [album:Album]

	static constraints = {
		title blank: false   	
	}
}
