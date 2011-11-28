package com.orangeandbronze.ams

class MobileServiceCategory {
	String name, description
	
	static constraints = {
		description(blank:true, nullable:true, maxSize:500)
	}
			
}
