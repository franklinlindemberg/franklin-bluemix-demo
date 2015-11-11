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
				<h2 style="display:inline">Perguntas e Respostas Watson API</h2>
				<p>A API de language translation permite que palavras e senten�as sejam traduzidas entre diversas linguas. 
				� poss�vel tamb�m retroalimentar o sistema a fim de melhorar o grau de acerto nas tradu��es.</p>
				<p>Para este demo foi implantada a tradu��o entre portugu�s e ingl�s. Para utilizar basta inserir na caixa 
				abaixo a senten�a em portugu�s que ser� disponibilizada a tradu��o da mesma para o ingl�s.
				</p>
			</div>
			<form id='alchemy_form' method="post" action="/AlchemyController">
				<fieldset>
					<div style="padding:5px;">
						<h4 style="display:inline">Selecione a op��o que deseja analisar:</h4>
						<div style="display:inline;">
							<select style="width: 200px;" id="select" name="alchemy_list">
								<option value="entity_extraction">
									Entity Extraction
								</option>
								<option value="language_detection">
									Language Detection
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
								 autofocus value="${url_source}">
						</div>
						<div style="padding:5px;">
							<button style="width:100px;" type="submit">Analisar</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</body>
</html>
