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
			result << [keywordItemId:0, label:'Enter 10 Digit Mobile Number', itemType:'MOBILE_NUMBER', value:'']
		}
		mobileServiceInstance.keywordItems.sort{it.id}.each {
			def keywordItem = it
			if (keywordItem.itemType in ['USER_INPUT', 'MOBILE_NUMBER']) {
				result << [keywordItemId:keywordItem.id, label:keywordItem.label, itemType:keywordItem.itemType, value:'']
			}
		}
		return result
	}
}



