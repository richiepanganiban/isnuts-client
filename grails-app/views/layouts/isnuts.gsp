<!DOCTYPE html>
<html>
    <head>
        <title>SMART DEMO</title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="prototype" />
    </head>
    <body  class="yui-skin-sam">
    	<table cellpadding="0" cellspacing="0">
    		<tr>
    			<td colspan="3" style='height: 50px; background-color: #ffcccc;text-align:center; '>
    				<h1>Header</h1>
    			</td>
    		</tr>
    		<tr>
    			<td width="100px;" style='background-color: #ccffcc;'>
    				Left Side Bar
    			</td>
    			<td>
    				<div style="min-height: 400px;">
    					<g:layoutBody />
    				</div>
    			</td>
    			<td width="100px;" style='background-color: #ffffcc;'>
    				Right Side Bar
    			</td>
    		</tr>
    		<tr>
    			<td colspan="3" style='height: 50px; background-color: #ccccff;text-align:center; '>
    				<b>Footer &copy; 2011</b>
    			</td>
    		</tr>
    	</table>
    </body>
</html>