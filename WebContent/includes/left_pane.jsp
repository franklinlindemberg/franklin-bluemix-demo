<!-- possui apenas o left pane da pagina -->

<div class="leftHalf">
	<div>
		<h1>IBM Watson APIs</h1>
	</div>
	<div style="padding:5px;">
		<a href="/watson_api/alchemy.jsp" style="font-size: 1em !important;">Alchemy API</a>
	</div>
	<div style="padding:5px;">
		<a href="/watson_api/qa.jsp" style="font-size: 1em !important;">Questions & Answers API</a>
	</div>
	<div style="padding:5px;">
		<a href="/watson_api/lt.jsp" style="font-size: 1em !important;">Language Translation API</a>
	</div>
	<%
	if(session.getAttribute("user") != null)
    {
		out.print("<div style='padding:20px;'>");
		out.print("<form id='sairForm' method='post' action='/sair.jsp'>");
		out.print("<button style='width:100px;' type='submit'>Sair</button>");
		out.print("</form>");
		out.print("</div>");
  	}  
	%>
	
</div>