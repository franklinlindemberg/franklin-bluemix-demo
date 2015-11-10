<!DOCTYPE html>
<html>
	<head>
		<title>Franklin IBM Bluemix Demo</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="/css/style.css" />
		<%
        	if(session.getAttribute("user") == null)
            {
                response.sendRedirect("/login.jsp");
          	}  
   		%>
	</head>
	<body>
		<%@include file="/includes/header.jsp" %>
		<%@include file="/includes/left_pane.jsp" %>
		<div class="rightHalf">
			<form action="/AlchemyController" id="alchemy_form" method="post">
			  	URL a ser analisada:<input type="text" name="url_source">
			  	<input type="submit">
			</form>
		    <select name="alchemy_list" form="alchemy_form">
				<option value="entity_extraction">Entity Extraction</option>
			  	<option value="language_detection">Language Detection</option>
			  	<option value="sentiment_analisys">Sentiment Analysis</option>
			</select>
		</div>
		<script type="text/javascript" src="/js/index.js"></script>
	</body>
</html>
