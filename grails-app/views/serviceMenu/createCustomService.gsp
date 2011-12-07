<div>
	<div class="mobileServiceTitle">
		${mobileServiceInstance.title}
	</div>
	<div class="mobileServiceDesc">
		<b>Save To Favorites</b><br/><br/>
		<g:form>
			<b>Shortcut Name: </b>
            <input type="text" name="customMobileServiceName" value="Enter Shortcut Name Here..." size="25"/>		
			<g:each in="${invocationParameters}" var="invocationParameter">
				<input type="hidden" name="${invocationParameter.keywordItemId}" value="${invocationParameter.value}"/>
			</g:each>
			<g:hiddenField name="mobileServiceInstanceId" value="${mobileServiceInstance.id}"/>
			<g:submitToRemote url="[controller:'serviceMenu', action:'saveInvokedService']" update="popupDialogContent" value="Save" class="button" />
		</g:form>	
	</div>
</div>