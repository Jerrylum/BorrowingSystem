<%@page import="com.jerryio.borsys.bean.Equipment"%>
<%@page import="com.jerryio.borsys.bean.BorrowItem"%>
<%@page import="com.jerryio.borsys.factory.ObjectDBFactory"%>
<%@include file="WEB-INF/auth-check.jsp"%>
<%@taglib uri="/WEB-INF/tlds/a_tag_lib.tld" prefix="borsys" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="WEB-INF/basic.jsp" %>
        <title>Inventory</title>
    </head>

    <body class="d-flex flex-column h-100">
        <%@include file="WEB-INF/header.jsp" %>
        

        <!-- Begin page content -->
        <main role="main" class="flex-shrink-0">
            <div class="container">
                <h2 class="mt-5">Inventory</h2>
                <%
                for (BorrowItem item : ObjectDBFactory.getBorrowItemDB().getAllBorrowItems()) {
                    if (item.isOverdue()) {
                        out.print("<div class=\"alert alert-danger my-4\"  style=\"max-width: 500px\" role=\"alert\">Overdue loaned equipment</div>");
                        break;                                    
                    }
                }

                %>
                <h3 class="lead mt-4 mb-3">Create</h3>
                
                <form class="mb-1" style="max-width: 500px" action="EquipmentController" method="POST">
                    <input type="hidden" name="action" value="add" />
                    <input type="text" name="name" class="form-control mb-2" placeholder="Name" required autofocus/>
                    <textarea name="description" class="form-control mb-2" placeholder="Description" required></textarea>
                    <div class="form-check">
                        <input type="checkbox" name="listing" class="form-check-input mb-2" 
                               value="true" checked/>
                        <label class="form-check-label" for="listing"> Listing</label>
                    </div>
                    <br/>
                    <button class="btn btn-primary btn-inline" type="submit">Confirm</button>
                </form>
                <br/>

                <h3 class="lead mt-5 mb-3">View / Edit</h3>
                <div style="max-width: 500px">
                    <% for (Equipment e : ObjectDBFactory.getEquipmentDB().getAllEquipments()) { %>
                        <borsys:equipment-box equipment="<%= e %>" isStudent="false"/>
                    <% } %>
                </div>
            </div>
        </main>
    </body>
</html>
