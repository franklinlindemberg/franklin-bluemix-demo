<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	if(session.getAttribute("user") == null)
    {
        response.sendRedirect("/login.jsp");
  	}  
	else
	{
		session.invalidate();
		response.sendRedirect("login.jsp");
	}
%>