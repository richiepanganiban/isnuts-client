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
</div>