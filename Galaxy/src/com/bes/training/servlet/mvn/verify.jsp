<%@ page import="java.util.*"%>
<html>
<body>
	<h1 align="center">Welcome Bes</h1>
	<p>

		<%
			boolean isMember = (Boolean) request.getAttribute("verify");
			if (isMember) {
				out.print("you are member , welcome");
			} else {
				out.print("sorry ,you are not member,please away");
			}
		%>
	
</body>
</html>