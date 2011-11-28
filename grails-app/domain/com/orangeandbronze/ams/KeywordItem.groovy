package com.orangeandbronze.ams

class KeywordItem {
	
	static belongsTo = [mobileServiceInstance:MobileService]
	String label
	String itemType
	String literalValue
	
    static constraints = {
		label(blank:true, nullable:true)
		literalValue(blank:true, nullable:true)
    }
}
