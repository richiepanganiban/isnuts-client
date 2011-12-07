<div>
	<div class="mobileServiceTitle">
		${mobileServiceInstance.title}
	</div>
	<div class="mobileServiceDesc">
		<div><b>Description:</b></div><br/>
		<div>&nbsp;&nbsp;&nbsp;&nbsp;${mobileServiceInstance.description}</div>
	</div>
	<br/>
	
	<div class="mobileServiceTitle">
		Use Service
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
			<g:hiddenField name="mobileServiceInstanceId" value="${mobileServiceInstance.id}"/>
			<g:submitToRemote url="[controller:'serviceMenu', action:'invokeService']" update="popupDialogContent" value="GO" class="button" />
			<g:submitToRemote url="[controller:'serviceMenu', action:'createCustomService']" update="popupDialogContent" value="Add To Favorites" class="button" />
		</g:form>
	</div>
	
</div>