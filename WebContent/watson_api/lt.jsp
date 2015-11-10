<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
			<div>
				<img style="width:70px; height:70px;" src="/images/app.png">
				<h2 style="display:inline">Perguntas e Respostas Watson API</h2>
				<p>The IBM Watson Question Answer (QA) service provides an API,
					referred to as the QAAPI, that enables you to add the power of the
					IBM Watson cognitive computing system to your application. With
					this service, you can connect to Watson, post questions, and
					receive responses that you can use in your application.
				</p>
			</div>
			<div>
				<form id='qaForm' method="post" action="/LTController">
					<fieldset>
						<div>
							<div style="padding:5px;">
								<input style="width:600px; height: 20px; padding-left:5px;" id="text" name="text" placeholder="Escreva uma frase em português..."
									 autofocus value="${text}">
							</div>
							<div style="padding:5px;">
								<button style="width:100px;" type="submit">Traduzir</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div>
				<div>
					<c:if test="${not empty text }">
						<h2 style="padding:5px;">Tradução</h2>
						<div >
							<c:if test="${not empty answers }">
								<c:forEach var="answer" items="${answers.translations}" varStatus="count">
									<div>
										<div style="padding:5px;">
											<div style="padding:5px;">
												${count.count}. -  ${answer.translation}
											</div>
										</div>
									</div>
						        </c:forEach>
							</c:if>
							<c:if test="${empty answers }">
								<div>
									Sem tradução para o texto inserido. Tente outro!
								</div> 
							</c:if>
						</div>
					</c:if>
					<c:if test="${not empty error}">
						<h2>Output</h2>
						<div>
							<p style="font-weight:bold;color:red;">Error: ${error}</p>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</body>
</html>