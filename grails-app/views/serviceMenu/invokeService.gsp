<div>
	<div class="mobileServiceTitle">
		${mobileServiceInstance.title}
	</div>
	<div class="mobileServiceDesc">
		<g:each in="${invocationParameters}" var="invocationParameter">
			<div>
				<b>${invocationParameter.label}: </b>
				<input type="text" name="${invocationParameter.keywordItemId}" value="${invocationParameter.value}" size="25"/>
			</div>
		</g:each>
	</div>
</div>