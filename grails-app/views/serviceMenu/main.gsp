<%@ page import="com.orangeandbronze.ams.*"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="isnuts" />
	<gui:resources components="tabView, dialog"/>
	<style>
		.mobileServiceMenuItem {
			border: 1px solid #000;
			background-color: #eee;
			width: 400px;
			margin-top: 5px;
			margin-bottom: 5px;
			padding-top: 5px;
			padding-bottom: 5px;
			padding-left: 10px;
			padding-right: 10px;
			text-align: center;
			clear: both;
		}
		
		.mobileServiceMenuItem a {
			color: #000;
			font-weight: bold;
		}
		
		.mobileServiceTitle {
			width: 95%;
			text-align: center;
			font-size: 14px;
			background-color:#EEE8AA;
			color:#000;
			font-weight: bold;
			padding-top: 3px;
			padding-bottom: 3px;
			border: 1px solid #000;
			clear: both;
		}
		
		.mobileServiceDesc {
			width: 95%;
			border-left: 1px solid #000;
			border-right: 1px solid #000;
			border-bottom: 1px solid #000;
			clear: both;
		}
		
		.button {
			float: right;
			margin-left: 5px;
			margin-right: 5px;
			font-size: 11px;
			padding-left: 5px;
			padding-right: 5px;
			padding-top: 2px;
			border: 1px solid #880000;
			background-color: #ff0000;
		}
		
		.button a {
			color: #ccc;
		}
	</style>
</head>
<body>

	<gui:tabView>
	    <gui:tab label="Featured" active="true">
			<g:each in="${featuredMobileServices}" var="featuredMobileService">
				<g:render template="/serviceMenu/mobileServiceMenuItem" model="${[mobileServiceInstance:featuredMobileService.mobileServiceInstance]}"/>
			</g:each>
	    </gui:tab>
	    <gui:tab label="Favorites">
	    	<g:each in="${customMobileServices}" var="customMobileService">
	    		<g:render template="/serviceMenu/customMobileServiceMenuItem" model="${[customMobileService:customMobileService]}"/>
	    	</g:each>
	    	
	    </gui:tab>
		<g:each in="${categorizedServicesList}" var="categorizedServices">
		    <gui:tab label="${categorizedServices.categoryName }">
				<g:each in="${categorizedServices.services}" var="mobileServiceInstance">
					<g:render template="/serviceMenu/mobileServiceMenuItem" model="${[mobileServiceInstance:mobileServiceInstance]}"/>
				</g:each>
		    </gui:tab>
		</g:each>
	</gui:tabView>

	
	<script>
		function showService(mobileServiceInstanceId) {
			document.getElementById('popupDialogContent').innerHTML = 'Loading...';
			new Ajax.Updater("popupDialogContent",
				"${resource(dir:'serviceMenu', file:'showService')}",
				{method:'get', parameters: {mobileServiceInstanceId:mobileServiceInstanceId}, evalScripts: true}
			);		
			GRAILSUI.popupDialog.show();	
		}
		
		function showCustomService(customMobileServiceInstanceId) {
			document.getElementById('popupDialogContent').innerHTML = 'Loading...';
			new Ajax.Updater("popupDialogContent",
				"${resource(dir:'serviceMenu', file:'showCustomService')}",
				{method:'get', parameters: {customMobileServiceInstanceId:customMobileServiceInstanceId}, evalScripts: true}
			);		
			GRAILSUI.popupDialog.show();	
		}
	</script>
</body>
</html>
