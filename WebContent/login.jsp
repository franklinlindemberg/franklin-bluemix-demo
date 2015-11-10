<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Franklin IBM Bluemix Demo</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<link rel="stylesheet" href="/css/style.css" />
    </head>
    <body>
	    <%@include file="/includes/header.jsp" %>
		<%@include file="/includes/left_pane.jsp" %>
		<div class="rightHalf">
			<div style="padding:10px">
				<c:out value="${msg}" />
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