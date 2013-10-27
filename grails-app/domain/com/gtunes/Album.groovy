package com.gtunes

class Album {
	String title
	static belongsTo = [artist:Artist]

	static constraints = {
    		title(unique:true,
			validator:{val,obj -> if(val?.equals("Mutter2")){return false}}
		)
	}

	String toString(){
		return title
	}
}
