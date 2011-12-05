package com.orangeandbronze.ams

class CustomKeywordItemValue {
	
	static belongsTo = [customMobileServiceInstance:CustomMobileService]
	KeywordItem valueForKeywordItem
	String value
	
    static constraints = {
		value(blank:false, nullable:false)
    }
}
