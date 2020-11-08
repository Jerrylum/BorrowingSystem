<%@page import="com.jerryio.borsys.factory.ObjectDBFactory"%>
<%@include file="WEB-INF/auth-check.jsp"%>
<%@taglib uri="/WEB-INF/tlds/a_tag_lib.tld" prefix="borsys" %>
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
                <h2 class="mt-5">Account</h2>
                <h3 class="lead mt-4 mb-3">Create</h3>
                <form class="mb-1" style="max-width: 500px" action="UserController" method="POST">
                    <input type="hidden" name="action" value="add" />
                    <input type="text" name="name" class="form-control mb-2" placeholder="Name" required autofocus/>
                    <input type="password" name="pwd" class="form-control mb-2" placeholder="Password" required/>
                    <select name="role" class="form-control col-4" required/>
                    <option value="STUDENT">Student</option>
                    <option value="TECHNICIAN">Technician</option>
                    <option value="ADMIN">Admin</option>
                    </select>
                    <br/>
                    <button class="btn btn-primary btn-inline" type="submit">Confirm</button>
                </form>
                <br/>

                <h3 class="lead mt-5 mb-3">View / Edit</h3>
                <!--<div class="mb-3" style="max-width: 500px">
                    <form class="mb-1 row">
                        <div class="col-10">
                            <input type="text" name="search" class="form-control mb-2 inline" placeholder="name or id" required/>
                        </div>
                        <div>
                            <button class="btn btn-primary btn-inline" type="submit">Search</button>
                        </div>
                    </form>
                </div>
                <hr class="mt-2 mb-1"/><br/>-->
                <div style="max-width: 500px">
                    <% for (User u : ObjectDBFactory.getUserDB().getAllUsers()) { %>
                        <borsys:account-box user="<%= u %>"/>
                    <% } %>
                </div>
            </div>
        </main>
    </body>
</html>
