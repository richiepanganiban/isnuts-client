import com.google.gson.Gson
import com.orangeandbronze.ams.FeaturedMobileService
import com.orangeandbronze.ams.KeywordItem
import com.orangeandbronze.ams.MobileService
import com.orangeandbronze.ams.MobileServiceCategory

class BootStrap {
	def JSON_CATEGORIES = '[{"id":2,"version":4,"name":"Call Offers"},{"id":1,"version":2,"name":"General"},{"id":3,"version":2,"name":"Text Offers"}]'
	def JSON_SERVICES = '[{"id":1,"version":1,"activePromo":true,"serviceType":"SMS","title":"Balance Inquiry","description":"Inquire your balance. Cost: Free","serviceNumber":"222","appendMobileToServiceNumber":false,"keywordItems":[{"id":1,"version":0,"itemType":"LITERAL","literalValue":"BAL"}],"categories":[{"id":1,"version":2,"name":"General"}]},{"id":5,"version":2,"activePromo":true,"serviceType":"SMS","title":"DUO","description":"Unlimited calls to Globe Landlines and selected landlines for only P450/30days","serviceNumber":"8888","appendMobileToServiceNumber":false,"keywordItems":[{"id":5,"version":0,"itemType":"USER_INPUT","label":"Area"},{"id":3,"version":0,"itemType":"LITERAL","literalValue":"DUO"},{"id":6,"version":0,"itemType":"SPACE"},{"id":4,"version":0,"itemType":"SPACE"},{"id":7,"version":0,"itemType":"LITERAL","literalValue":"450"}],"categories":[{"id":2,"version":4,"name":"Call Offers"},{"id":3,"version":2,"name":"Text Offers"}]},{"id":2,"version":1,"activePromo":true,"serviceType":"SMS","title":"Pasa Load","description":"Share load to family and friends. Cost: P1 per transaction","serviceNumber":"2","appendMobileToServiceNumber":true,"keywordItems":[{"id":2,"version":0,"itemType":"USER_INPUT","label":"Amount"}],"categories":[{"id":1,"version":2,"name":"General"}]},{"id":6,"version":2,"activePromo":true,"serviceType":"SMS","title":"Super DUO","description":"Unlimited calls to Globe/TM, Globe Landlines and selected landlines for only P599/30days.","serviceNumber":"8888","appendMobileToServiceNumber":false,"keywordItems":[{"id":12,"version":0,"itemType":"LITERAL","literalValue":"599"},{"id":11,"version":0,"itemType":"SPACE"},{"id":9,"version":0,"itemType":"SPACE"},{"id":10,"version":0,"itemType":"USER_INPUT","label":"Area"},{"id":8,"version":0,"itemType":"LITERAL","literalValue":"SUPERDUO"}],"categories":[{"id":2,"version":4,"name":"Call Offers"},{"id":3,"version":2,"name":"Text Offers"}]},{"id":3,"version":1,"activePromo":true,"serviceType":"CALL","title":"Super Sakto Calls","description":"Call your friends on Globe and TM for only P0.15 per second!","serviceNumber":"232","appendMobileToServiceNumber":true,"keywordItems":[],"categories":[{"id":2,"version":4,"name":"Call Offers"}]},{"id":4,"version":1,"activePromo":true,"serviceType":"CALL","title":"TAWAG 236 ","description":"20 minutes of chika for only 20 pesos!","serviceNumber":"236","appendMobileToServiceNumber":true,"keywordItems":[],"categories":[{"id":2,"version":4,"name":"Call Offers"}]}]'
	def JSON_FEATURED_SERVICES = '[{"id":1,"version":0,"mobileServiceInstance":{"id":5},"priority":1},{"id":2,"version":0,"mobileServiceInstance":{"id":6},"priority":2}]'
	
    def init = { servletContext ->
		Gson gson = new Gson();
		def categories = gson.fromJson(JSON_CATEGORIES, MobileServiceCategory[].class);
		categories.sort{it.id}.each {
			def cat = it
			def category = new MobileServiceCategory(cat.properties)
			category.save(flush:true)
		}
		
		def services = gson.fromJson(JSON_SERVICES, MobileService[].class);
		services.sort{it.id}.each {
			def svc = it
			def service = new MobileService(svc.properties)
			def keywordItems = svc.keywordItems.collect{new KeywordItem(label:it.label, itemType:it.itemType, literalValue:it.literalValue)}
			def cats = svc.categories.collect{MobileServiceCategory.get(it.id)}
			service.categories.clear()
			service.keywordItems.clear()
			service.save(flush:true)
			keywordItems.each {
				def keywordItem = it
				keywordItem.mobileServiceInstance = service
				keywordItem.save(flush:true)
			}
			cats.each {
				def category = it
				service.addToCategories(category)
			}
		}

		def featuredServices = gson.fromJson(JSON_FEATURED_SERVICES, FeaturedMobileService[].class);
		featuredServices.sort{it.id}.each {
			def featuredService = it
			new FeaturedMobileService(mobileServiceInstance:MobileService.get(featuredService.mobileServiceInstance.id), priority:featuredService.priority).save(flush:true)
		}
    }
	
    def destroy = {
    }
}
