<%@include file="WEB-INF/auth-check.jsp"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="WEB-INF/basic.jsp" %>
        <title>Discover</title>
    </head>

    <body class="d-flex flex-column h-100">
        <%@include file="WEB-INF/header.jsp" %>
        

        <!-- Begin page content -->
        <main role="main" class="flex-shrink-0">
            <div class="container">
                <h1 class="mt-5">Sticky footer with fixed navbar</h1>
                <p class="lead">Pin a footer to the bottom of the viewport in desktop browsers with this custom HTML and CSS. A fixed navbar has been added with <code>padding-top: 60px;</code> on the <code>main &gt; .container</code>.</p>
                <p>Back to <a href="../examples/sticky-footer/">the default sticky footer</a> minus the navbar.</p>
                <p><%= request.getRequestURI() %></p>
            </div>
        </main>
    </body>
</html>
