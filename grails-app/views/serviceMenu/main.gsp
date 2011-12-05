<%@ page import="com.orangeandbronze.ams.*"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="isnuts" />
	<resource:tabView />
	<style>
		.mobileServiceMenuItem {
			border: 1px solid #000;
			background-color: #eee;
			width: 300px;
			margin-top: 5px;
			margin-bottom: 5px;
			height: 20px;
			padding-top: 5px;
			padding-left: 10px;
			padding-right: 10px;
		}
	</style>
</head>
<body>
	<richui:tabView id="tabView">
		<richui:tabLabels>
			<richui:tabLabel selected="true" title="Featured" />
			<richui:tabLabel title="Favorites" />
			<g:each in="${categorizedServicesList}" var="categorizedServices">
				<richui:tabLabel title="${categorizedServices.categoryName }" />
			</g:each>
		</richui:tabLabels>

		<richui:tabContents>
			<richui:tabContent>
				<g:each in="${featuredMobileServices}" var="featuredMobileService">
					<g:render template="/serviceMenu/mobileServiceMenuItem" model="${[mobileServiceInstance:featuredMobileService.mobileServiceInstance]}"/>
				</g:each>
			</richui:tabContent>

			<richui:tabContent>
				Favs
			</richui:tabContent>

			<g:each in="${categorizedServicesList}" var="categorizedServices">
				<richui:tabContent>
					<g:each in="${categorizedServices.services}" var="mobileServiceInstance">
						<g:render template="/serviceMenu/mobileServiceMenuItem" model="${[mobileServiceInstance:mobileServiceInstance]}"/>
					</g:each>
				</richui:tabContent>
			</g:each>
		</richui:tabContents>
	</richui:tabView>
</body>
</html>
