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
				<div>
					<c:out value="${msg}" />
				</div>
				<div style = "padding:10px">
					<div>Este demo tem como objetivo testar a plataforma de cloud Bluemix da IBM e b
					em como alguns dos seus componentes.
					</div>
					<div>Os componentes utilizados para o demo foram:
					</div>
					<div style = "padding:15px">
						SQL
						<br>
						Language Translation Watson API
						<br>
						Questions and Answers Watson API
						<br>
						Alchemy Watson API
						</div>
					<br>
				</div>
				<div style = "padding:15px">
					Para utilizar este demo deve-se inicialmente realizar o cadastro ou utilizar o usuário admin (senha admin). Em seguida
					é possível escolher a API desejada e seguir as instruções da página de cada API.
				</div>
			</div>
		</div>
    </body>
</html>
