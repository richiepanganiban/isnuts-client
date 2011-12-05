<%@ page import="com.orangeandbronze.ams.*"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="layout" content="isnuts" />
	<resource:tabView />
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
				Featured 
			</richui:tabContent>

			<richui:tabContent>
				Favs
			</richui:tabContent>

			<g:each in="${categorizedServicesList}" var="categorizedServices">
				<richui:tabContent>
					${categorizedServices.categoryName }
				</richui:tabContent>
			</g:each>
		</richui:tabContents>
	</richui:tabView>
</body>
</html>
