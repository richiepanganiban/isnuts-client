<div>
	<div class="mobileServiceTitle">
		<b>${customMobileServiceInstance.mobileServiceInstance.title}: </b>${customMobileServiceInstance.name }
	</div>
	<div class="mobileServiceDesc">
		<g:form>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>		
			<g:each in="${invocationParameters}" var="invocationParameter">
				<div style="margin-left: 10px;">
					<b>${invocationParameter.label}: </b>
					<input type="text" name="${invocationParameter.keywordItemId}" value="${invocationParameter.value}" size="25"/>
				</div>
			</g:each>
			<g:hiddenField name="customMobileServiceInstanceId" value="${customMobileServiceInstance.id}"/>
			<g:submitToRemote url="[controller:'serviceMenu', action:'invokeService']" update="popupDialogContent" value="GO" class="button" />
		</g:form>
	</div>
	
</div>