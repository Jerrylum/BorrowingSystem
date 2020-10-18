<%@page import="com.jerryio.borsys.Util"%>
<%@page import="com.jerryio.borsys.bean.User"%>
<%
User me = (User)session.getAttribute("me");
if (me == null) {
    Util.redirect(request, response, "/login.jsp");
    return;
}%>


