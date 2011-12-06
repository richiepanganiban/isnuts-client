package com.orangeandbronze.ams

class ServiceMenuController {

    def index = {
		redirect(action:'main') 
	}
	
	def main = {
		def featuredMobileServices = FeaturedMobileService.listOrderByPriority()
		def customMobileServices = CustomMobileService.listOrderByName()
		def categorizedServicesList = []
		
		categorizedServicesList << [categoryName:'All', services:MobileService.listOrderByTitle()]
		MobileServiceCategory.listOrderByName().each {
			def category = it
			def categoryName = category.name
			def services = MobileService.executeQuery("select m from MobileService m where exists (select 1 from m.categories c where c = :category)",[category:category])
			categorizedServicesList << [categoryName:categoryName, services:services]
		}
		[featuredMobileServices:featuredMobileServices, customMobileServices:customMobileServices, categorizedServicesList:categorizedServicesList]
	}
	
	def showService = {
		def mobileServiceInstance = MobileService.get(params.mobileServiceInstanceId)
		[mobileServiceInstance:mobileServiceInstance]
	}
	
	def invokeService = {
		def mobileServiceInstance = MobileService.get(params.mobileServiceInstanceId)
		def invocationParameters = getInvocationParameters(mobileServiceInstance)
		[mobileServiceInstance:mobileServiceInstance, invocationParameters:invocationParameters]
	}
	
	def getInvocationParameters(mobileServiceInstance) {
		def result = []
		if (mobileServiceInstance.appendMobileToServiceNumber) {
			def value = params['0'] in ['', null, 'null'] ? '' : params['0']
			result << [keywordItemId:0, label:'Enter 10 Digit Mobile Number', itemType:'MOBILE_NUMBER', value:value]
		}
		mobileServiceInstance.keywordItems.sort{it.id}.each {
			def keywordItem = it
			def value = params["${keywordItem.id}"] in ['', null, 'null'] ? '' : params["${keywordItem.id}"]
			if (keywordItem.itemType in ['USER_INPUT', 'MOBILE_NUMBER']) {
				result << [keywordItemId:keywordItem.id, label:keywordItem.label, itemType:keywordItem.itemType, value:value]
			}
		}
		return result
	}
	
	def doInvokeService = {
		def mobileServiceInstance = MobileService.get(params.mobileServiceInstanceId)
		def invocationParameters = getInvocationParameters(mobileServiceInstance)
		invocationParameters.each {
			def invocationParameter = it
			if (invocationParameter.value == '') {
				flash.message = "Please fill up all fields"
				render (view:'invokeService', model:[mobileServiceInstance:mobileServiceInstance, invocationParameters:invocationParameters])
				return
			}
		}
		
		String serviceNumber = mobileServiceInstance.serviceNumber
		if (mobileServiceInstance.appendMobileToServiceNumber) {
			serviceNumber = serviceNumber + invocationParameters.find {it.keywordItemId == 0}.value
		}
		
		StringBuilder sb = new StringBuilder()
		if (mobileServiceInstance.serviceType == 'SMS') {
			mobileServiceInstance.keywordItems.sort{it.id}.each {
				def keywordItem = it
				if (keywordItem.itemType == 'SPACE') {
					sb.append(" ");
				} else if (keywordItem.itemType == 'LITERAL') {
					sb.append(keywordItem.literalValue);
				} else {
					sb.append(invocationParameters.find {it.keywordItemId == keywordItem.id}.value);
				}
			}
		}
		
		render "<div><b>${mobileServiceInstance.serviceType == 'CALL' ? 'RINGING':'SENDING SMS'}</b><div>"
		render "<div><b>Number: </b>${serviceNumber}<div>"
		if (mobileServiceInstance.serviceType == 'SMS') {
			render "<div><b>Message: </b>${sb}<div>"
		}
	}
}



