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
	     	<div>
				<p>Cadastre-se aqui para utilizar o demo!
				</p>
			</div>
			<div>
				<form id='cadastroForm' method="post" action="/UserController">
					<fieldset>
						<input type="hidden" name="action" value="cadastrar" />
						<table class="addform">
	                		<tr>
	                			<td>
	                        		Nome : 
	                        	</td>
	                        	<td> 
	                        		<input type="text" name="firstName" />
	                                <br /> 
	                            </td>
	                     	</tr>
	                		<tr>
	                			<td>
	                				Sobrenome : 
	                			</td>
	                			<td> 
	                				<input type="text" name="lastName" /> <br />
	                			</td>
	                		</tr> 
	                		<tr>
	                			<td>
	                				Endereço : 
	                			</td>
	                			<td> 
	                				<input type="text" name="address"/> 
	                				<br />
	                			</td>
	                		</tr> 
	                		<tr>
	                			<td>
	                				Sexo : 
	                			</td>
	                			<td> 
	                				<select name="sex" id="sex">
	                            		<option value="F" onClick="setSex(this.value)" selected>Feminino</option>
	                            		<option value="M" onClick="setSex(this.value)">Masculino</option>
	                        		</select>
	                				<br /> 
	                			</td>
	                		</tr>
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
			</div>
		</div>
    </body>
</html>
