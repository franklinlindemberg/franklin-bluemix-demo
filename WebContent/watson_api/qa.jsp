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
				<form id='qaForm' method="post" action="/QAController">
					<fieldset>
						<div style="padding:5px;">
							<h4 style="display:inline">Selecione o assunto que deseja testar:</h4>
							<div style="display:inline;">
								<select style="width: 200px;" id="select" name="dataset">
									<option value="healthcare"
										<%=(request.getParameter("dataset") != null && request.getParameter("dataset").equals("travel")) ? "" : "selected" %>
										>Assistência Médica
									</option>
									<option value="travel"
										<%=(request.getParameter("dataset") != null && request.getParameter("dataset").equals("travel")) ? "selected" : "" %>
										>Viagem
									</option>
								</select>
							</div>
						</div>
						<div>
							<div style="padding:5px;">
								<input style="width:600px; height: 20px; padding-left:5px;" id="questionText" name="questionText" placeholder="Type a question..."
									 autofocus value="${questionText}">
								<a 
									href="javascript:showExample(true)"
									style="padding-left:5px;" >Try an example question
								</a>
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
						<h2 style="padding:5px;">Respostas e Convicção</h2>
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
		<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="/js/example_input.js"></script>
	</body>
</html>