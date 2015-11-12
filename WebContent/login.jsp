<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Franklin IBM Bluemix Demo</title>
		<link rel="stylesheet" href="/css/style.css" />
    </head>
    <body>
	    <%@include file="/includes/header.jsp" %>
		<%@include file="/includes/left_pane.jsp" %>
		<div class="rightHalf">
			<div style="padding:10px">
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
	    	<form id='cadastroForm' method="post" action="/UserController">
				<fieldset>
					<input type="hidden" name="action" value="entrar" />
					<table class="addform">
                		<tr>
                			<td>
                				Email : 
                			</td>
                			<td> 
                				<input type="email" name="email"/> 
                				<br />
                			</td>
                		</tr>
                		<tr>
                			<td>
                				Senha : 
                			</td>
                			<td> 
                				<input type="password" name="password"/> 
                				<br />
                			</td>
                		</tr>
	 				</table>
       			 	<p style="float:left; padding-left: 80px">
          				<input type="submit" value="Login" />
					</p>
				</fieldset>
			</form>
			<div style="padding:10px">
				<a href="/cadastrar.jsp">Clique aqui para realizar o cadastro</a>
			</div>
		</div>
    </body>
</html>