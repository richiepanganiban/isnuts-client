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
	
	def invokeService = {
		def mobileServiceInstance = MobileService.get(params.mobileServiceInstanceId)
		def invocationParameters = getInvocationParameters(mobileServiceInstance)
		invocationParameters.each {
			def invocationParameter = it
			if (invocationParameter.value == '') {
				flash.message = "Please fill up all fields"
				render (view:'showService', model:[mobileServiceInstance:mobileServiceInstance, invocationParameters:invocationParameters])
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
		def smsMessage = sb.toString()
		
		[mobileServiceInstance:mobileServiceInstance, invocationParameters:invocationParameters, serviceNumber:serviceNumber, smsMessage:smsMessage]
	}
	
	def saveInvokedService = {
		def mobileServiceInstance = MobileService.get(params.mobileServiceInstanceId)
		def invocationParameters = getInvocationParameters(mobileServiceInstance)
		
		def customMobileServiceName = params.customMobileServiceName in ['', null, 'null'] ? mobileServiceInstance.name : params.customMobileServiceName
		String appendedMobileNumber = mobileServiceInstance.appendMobileToServiceNumber ? invocationParameters.find {it.keywordItemId == 0}.value : ''
		
		def customMobileServiceInstance = new CustomMobileService(name:customMobileServiceName, mobileServiceInstance:mobileServiceInstance, appendedMobileNumber:appendedMobileNumber).save(flush:true)
		mobileServiceInstance.keywordItems.sort{it.id}.each {
			def keywordItem = it
			if (keywordItem.itemType in ['USER_INPUT', 'MOBILE_NUMBER']) {
				def value = invocationParameters.find {it.keywordItemId == keywordItem.id}.value
				new CustomKeywordItemValue(customMobileServiceInstance:customMobileServiceInstance, valueForKeywordItem:keywordItem, value:value).save()
			}
		}
		render "<script>window.location.reload()</script>"
	}
	
	def showCustomService = {
		def customMobileServiceInstance = CustomMobileService.get(params.customMobileServiceInstanceId)
		def invocationParameters = getCustomInvocationParameters(customMobileServiceInstance)
		
		[customMobileServiceInstance:customMobileServiceInstance, invocationParameters:invocationParameters]
	}
	
	def getCustomInvocationParameters(customMobileServiceInstance) {
		def result = []
		def mobileServiceInstance = customMobileServiceInstance.mobileServiceInstance
		if (mobileServiceInstance.appendMobileToServiceNumber) {
			def value = params['0'] in ['', null, 'null'] ? customMobileServiceInstance.appendedMobileNumber : params['0']
			result << [keywordItemId:0, label:'Enter 10 Digit Mobile Number', itemType:'MOBILE_NUMBER', value:value]
		}
		mobileServiceInstance.keywordItems.sort{it.id}.each {
			def keywordItem = it
			if (keywordItem.itemType in ['USER_INPUT', 'MOBILE_NUMBER']) {
				def value = params["${keywordItem.id}"] in ['', null, 'null'] ?
				  customMobileServiceInstance.customKeywordItemValues.find{it.valueForKeywordItem.id == keywordItem.id}?.value :
				  params["${keywordItem.id}"]
				result << [keywordItemId:keywordItem.id, label:keywordItem.label, itemType:keywordItem.itemType, value:value]
			}
		}
		return result
	}

	def invokeCustomService = {
		def customMobileServiceInstance = CustomMobileService.get(params.customMobileServiceInstanceId)
		def mobileServiceInstance = customMobileServiceInstance.mobileServiceInstance
		def invocationParameters = getCustomInvocationParameters(customMobileServiceInstance)

		invocationParameters.each {
			def invocationParameter = it
			if (invocationParameter.value == '') {
				flash.message = "Please fill up all fields"
				render (view:'showCustomService', model:[customMobileServiceInstance:customMobileServiceInstance, invocationParameters:invocationParameters])
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
		def smsMessage = sb.toString()
		
		[mobileServiceInstance:mobileServiceInstance, invocationParameters:invocationParameters, serviceNumber:serviceNumber, smsMessage:smsMessage]

	}
	
	def updateCustomService = {
		def customMobileServiceInstance = CustomMobileService.get(params.customMobileServiceInstanceId)
		def mobileServiceInstance = customMobileServiceInstance.mobileServiceInstance
		def invocationParameters = getCustomInvocationParameters(customMobileServiceInstance)
		
		if (mobileServiceInstance.appendMobileToServiceNumber) {
			customMobileServiceInstance.appendedMobileNumber = invocationParameters.find {it.keywordItemId == 0}?.value
			customMobileServiceInstance.save()
		}
		customMobileServiceInstance.customKeywordItemValues.each {
			def customKeywordItemValue = it
			customKeywordItemValue.value = invocationParameters.find{it.keywordItemId == customKeywordItemValue.valueForKeywordItem.id}?.value
			customKeywordItemValue.save()
		}
		flash.message = "Changes Saved."
		render (view:'showCustomService', model:[customMobileServiceInstance:customMobileServiceInstance, invocationParameters:invocationParameters])
	}
}



