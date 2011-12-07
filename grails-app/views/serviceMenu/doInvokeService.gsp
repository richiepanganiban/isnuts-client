<div>
	<div class="mobileServiceTitle">
		${mobileServiceInstance.title}
	</div>
	<div class="mobileServiceDesc">
		<div>
			<b>${mobileServiceInstance.serviceType == 'CALL' ? 'CALLING: ':'SENDING SMS TO:'}: </b>
			${serviceNumber}
		</div>
		<g:if test="${mobileServiceInstance.serviceType == 'SMS'}">
			<div><b>Message: </b>${smsMessage}</div>
		</g:if>
	</div>
	
	<div class="mobileServiceTitle">
		Save To Favorites
	</div>
	<div class="mobileServiceDesc">
		<g:form>
			<b>Shortcut Name: </b>
            <input type="text" name="customMobileServiceName" value="Enter Shortcut Name Here..." size="25"/>		
			<g:each in="${invocationParameters}" var="invocationParameter">
				<input type="hidden" name="${invocationParameter.keywordItemId}" value="${invocationParameter.value}"/>
			</g:each>
			<g:hiddenField name="mobileServiceInstanceId" value="${mobileServiceInstance.id}"/>
			<g:submitToRemote url="[controller:'serviceMenu', action:'saveInvokedService']" update="popupDialogContent" value="GO" class="button" />
		</g:form>	
	</div>
</div>