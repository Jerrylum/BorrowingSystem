<%@page import="com.jerryio.borsys.bean.User"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.jerryio.borsys.Util"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">

    <head>
        <%@include file="WEB-INF/basic.jsp" %>
        <link href="static/style/login.css" rel="stylesheet" type="text/css"/>
        <title>Login</title>
    </head>

    <%
        if (session.getAttribute("me") != null) { // logged in
            Util.forward(getServletContext(), request, response, "/index.jsp"); // go to index page and redirect to other pages
        }
        
        String oldId = (String)session.getAttribute("login-id");
        oldId = oldId == null ? "" : oldId;

        Object err = session.getAttribute("login-error");
        session.setAttribute("login-error", null);
        boolean isError = err != null;
    %>

    <body class="text-center">
        <form class="form-signin" action="UserController" method="POST" onsubmit="redirect.value=window.location.pathname">
            <h1 class="h2 mb-5 font-weight-normal">IVPET<br/>Borrowing System</h1>
            <h1 class="h4 mb-3 font-weight-normal">Please log in</h1>
            <label for="inputEmail" class="sr-only">Email address</label>
            <input type="text" name="id" class="form-control" placeholder="User ID" value="<%= oldId%>" required <%= oldId.equals("") ? "autofocus" : "" %>/>
            <label for="inputPassword" class="sr-only">Password</label>
            <input type="password" name="pwd" class="form-control" placeholder="Password" required <%= oldId.equals("") ? "" : "autofocus" %>/>
            <input type="hidden" name="action" value="login"/>
            <input type="hidden" name="redirect" value=""/>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
            <p class="mt-2 text-warning"><%= isError ? "Incorrect user id or password" : "&nbsp;" %></p>
            <p class="mt-4 mb-3 text-muted">Created by Jerry &AMP; Theo</p>
        </form>
    </body>

</html>