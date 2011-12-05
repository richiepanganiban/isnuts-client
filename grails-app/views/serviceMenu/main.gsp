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
	    	Fav
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
			alert(mobileServiceInstanceId);
		}
	</script>
</body>
</html>
