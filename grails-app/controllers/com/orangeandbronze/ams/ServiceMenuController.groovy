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
}
