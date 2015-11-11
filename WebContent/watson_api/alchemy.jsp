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
				<p>A API de language translation permite que palavras e sentenças sejam traduzidas entre diversas linguas. 
				É possível também retroalimentar o sistema a fim de melhorar o grau de acerto nas traduções.</p>
				<p>Para este demo foi implantada a tradução entre português e inglês. Para utilizar basta inserir na caixa 
				abaixo a sentença em português que será disponibilizada a tradução da mesma para o inglês.
				</p>
			</div>
			<form id='alchemy_form' method="post" action="/AlchemyController">
				<fieldset>
					<div style="padding:5px;">
						<h4 style="display:inline">Selecione a opção que deseja analisar:</h4>
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
