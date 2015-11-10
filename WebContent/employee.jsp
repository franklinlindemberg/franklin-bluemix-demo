<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
        <link href="./css/style.css" 
              rel="stylesheet" type="text/css"/> 
        <script>
            function setSex(val) {
                document.getElementById("sex").value = val;
            }
            function setConfiguredOpt(val) {
                if (val === "M") {
                    document.getElementById("opts2").setAttribute("selected", "true");
                } else {
                    document.getElementById("opts1").setAttribute("selected", "true");
                }
            }
        </script> 
        <title>Add new employee</title>
    </head>
    <body onLoad='setConfiguredOpt("<c:out value="${employee.sex}" />");'>

        <h1 id = "message" style="margin-left: auto; margin-right: auto; width:320px;padding-left: 70px;">Add new employee</h1>
        <form method="POST" action='EmployeeController' name="frmAddEmployee">
            <input type="hidden" name="action" value="<c:out value="${param.action}" />" />
            <table class="addform">
                <tr><td>
                        User ID : </td><td> <input type="text" readonly="readonly" name="empId"
                                               value="<c:out value="${param.empId}" />" /> <br /> </td></tr>
                <tr><td>First Name : </td><td> <input
                            type="text" name="firstName"
                            value="<c:out value="${employee.firstName}" />" /> <br /></td></tr> 
                <tr><td>Last Name : </td><td> <input
                            type="text" name="lastName"
                            value="<c:out value="${employee.lastName}" />" /> <br /></td></tr> 
                <tr><td>Hiring Date : </td><td> <input
                            type="text" name="hiringDate"
                            value="<fmt:formatDate pattern="MM/dd/yyyy" value="${employee.hiringDate}" />" /> <br /> </td></tr>
                <tr><td>Phone : </td><td> <input type="text" name="phone"
                                                 value="<c:out value="${employee.phone}" />" /> <br /></td></tr>
                <tr><td>Sex : </td><td>               
                        <select name="sex" id="sex" value="<c:out value="${employee.sex}" />">
                            <option value="F" onClick="setSex(this.value)" id="opts1">Feminin</option>
                            <option value="M" onClick="setSex(this.value)" id="opts2">Masculin</option>
                        </select>
                        <br />
                <tr><td>Position Id : </td><td> <input type="text" name="positionId"
                                                       value="<c:out value="${employee.positionId}" />" /> <br /></td></tr>
            </table>
            <p style="margin-left: auto; margin-right: auto; width:200px;">
                <input
                    type="submit" value="Submit" />
            </p>
        </form>
        <p style="margin-left: auto; margin-right: auto; width:200px;">
            <br />
            <a href="EmployeeController?action=listEmployee" >Show All Employee Items</a>
            <br />
            <a href="index.jsp" >Index</a>
        </p>    
    </body>
</html>