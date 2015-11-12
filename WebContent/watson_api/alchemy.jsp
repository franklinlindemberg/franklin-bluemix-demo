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
				<img style="width:70px; height:70px;" src="/images/alchemy.png">
				<h2 style="display:inline">Alchemy API</h2>
				<p>O Alchemy API é uma API que possui diversas chamadas para tratamento de sites,textos e fotos, permitindo a análise
				e extração automática de informações das fontes passadas como parâmetro
				</p>
				<p>Para este demo foram implantadas 3 chamadas do Alchemy API: Entity extraction, image tagging e sentiment analysis. Todos 
				aceitando como parâmetro uma URL.
				</p>
				<p>Para realizar o teste basta escolher a chamada que deseja executar, colocara URL desejada e apertar em analisar.
				</p>
			</div>
			<div>
				<form id='alchemy_form' method="post" action="/AlchemyController">
					<fieldset>
						<div style="padding:5px;">
							<h4 style="display:inline">Selecione a opção que deseja analisar:</h4>
							<div style="display:inline;">
								<select style="width: 200px;" id="select" name="alchemy_list">
									<option value="entity_extraction">
										Entity Extraction
									</option>
									<option value="image_tagging">
										Image Tagging
									</option>
									<option value="sentiment_analisys">
										Sentiment Analysis
									</option>
								</select>
							</div>
						</div>
						<div>
							<div style="padding:5px;">
								URL a ser analisada:<input style="width:600px; height: 20px; padding-left:5px;" id="url_source" name="url_source" placeholder="Digite uma url a ser analisada"
									 autofocus value="${site}">
							</div>
							<div style="padding:5px;">
								<button style="width:100px;" type="submit">Analisar</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div>
				<div>
					<c:if test="${not empty site }">
						<h2 style="padding:5px;">Resultado</h2>
							<pre>
								<c:out value="${answers } " />
							</pre>
					</c:if>
				</div>
			</div>
		</div>
	</body>
</html>
