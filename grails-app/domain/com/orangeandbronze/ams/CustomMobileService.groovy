package com.orangeandbronze.ams

class CustomMobileService {
	MobileService mobileServiceInstance
	static hasMany = [customKeywordItemValues:CustomKeywordItemValue]
}
