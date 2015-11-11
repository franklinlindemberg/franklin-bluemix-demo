<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Franklin IBM Bluemix Demo</title>
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
			<div style="padding:10px">
				<c:out value="${msg}" />
			</div>
		</div>
    </body>
</html>
