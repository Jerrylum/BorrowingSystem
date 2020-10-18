<%@page import="com.jerryio.borsys.enums.RoleType"%>
<%@page import="com.jerryio.borsys.Util"%>
<%@page import="com.jerryio.borsys.bean.User"%>
<%
User me = (User)session.getAttribute("me");

if (me == null) {
    Util.redirect(request, response, "/login.jsp");
} else if (me.getRole() == RoleType.STUDENT) {
    Util.redirect(request, response, "/discover.jsp");
} else {
    Util.redirect(request, response, "/inventory.jsp");
}%>


