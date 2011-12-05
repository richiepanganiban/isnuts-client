package com.orangeandbronze.ams

class CustomMobileService {
	String name
	MobileService mobileServiceInstance
	static hasMany = [customKeywordItemValues:CustomKeywordItemValue]
	
	static constraints = {
		name(blank:false, nullable:false, maxSize:30)
	}
}
