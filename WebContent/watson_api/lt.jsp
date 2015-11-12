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
			<div>
				<img style="width:70px; height:70px;" src="/images/lt.png">
				<h2 style="display:inline">Language Translation Watson API</h2>
				<p>A API de language translation permite que palavras e sentenças sejam traduzidas entre diversas linguas. 
				É possível também retroalimentar o sistema a fim de melhorar o grau de acerto nas traduções.</p>
				<p>Para este demo foi implantada a tradução entre português e inglês. Para utilizar basta inserir na caixa 
				abaixo a sentença em português que será disponibilizada a tradução da mesma para o inglês.
				</p>
			</div>
			<div>
				<form id='ltForm' method="post" action="/LTController">
					<fieldset>
						<input type="hidden" name="action" value="traduzir" />
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