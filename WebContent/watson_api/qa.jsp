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
				<img style="width:70px; height:70px;" src="/images/qa.png">
				<h2 style="display:inline">Questions & Answers Watson API</h2>
				<p>A API de questions and answers possibilita a colocar perguntas sobre o dominio "Viagens" ou "Medicina"
				e obter respostas a partir do Watson, cada uma com o seu grau de confian�a.</p>
				<p>Para este demo foi implantado a API de questions and answers onde � poss�vel realizar as perguntas tanto em
				portugu�s quanto em ingl�s (sem necessidade de especificar a lingua). A aplica��o ir� reconhecer automaticamente
				a lingua na qual a pergunta foi feita (a partir da API de language translation) e ser� traduzido para ao ingl�s 
				caso seja necess�rio (visto que n�o � poss�vel realizar perguntas diretamente em portugu�s). As respostas ser�o
				sempre em ingl�s para n�o serem realizadas muitas tradu��es (uso da API � limitado).
				</p>
				<p>
				Para utilizar o demo basta escolher o dominio que deseja utilizar, escrever a pergunta desejada em ingles ou 
				portugues e apertar o bot�o de pergunta.
			</div>
			<div>
				<form id='qaForm' method="post" action="/LTController">
					<fieldset>
						<input type="hidden" name="action" value="traduzir_QA" />
						<div style="padding:5px;">
							<h4 style="display:inline">Selecione o assunto que deseja testar:</h4>
							<div style="display:inline;">
								<select style="width: 200px;" id="select" name="dataset">
									<option value="healthcare">
										Medicina
									</option>
									<option value="travel">
										Viagem
									</option>
								</select>
							</div>
						</div>
						<div>
							<div style="padding:5px;">
								<input style="width:600px; height: 20px; padding-left:5px;" id="questionText" name="questionText" 
								placeholder="Escreva uma pergunta (em portugu�s ou ingl�s)"
									 autofocus value="${question}">
							</div>
							<div style="padding:5px;">
								<button style="width:100px;" type="submit">Perguntar</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
			<div>
				<div>
					<c:if test="${not empty questionText }">
						<h2 style="padding:5px;">Respostas e Convic��o</h2>
						<div >
							<c:if test="${not empty answers }">
								<c:forEach var="answer" items="${answers}" varStatus="count">
									<div>
										<div style="padding:5px;">
											<div style="padding:5px;">
												${count.count}. -  ${answer.text}
											</div>
											<div style="font-size:13px; padding-left: 20px">
												<strong>Confidence: </strong>${answer.confidence}
											</div>
										</div>
									</div>
						        </c:forEach>
							</c:if>
							<c:if test="${empty answers }">
								<div>
									Sem respostas para esta pergunta, tente outra!
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