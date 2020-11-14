<%@page import="com.jerryio.borsys.db.EquipmentDB"%>
<%@page import="com.jerryio.borsys.enums.AvailabilityStatus"%>
<%@page import="com.jerryio.borsys.enums.ListingStatus"%>
<%@page import="java.util.ArrayList"%>
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
        <title>Discover</title>
    </head>

    <body class="d-flex flex-column h-100">
        <%@include file="WEB-INF/header.jsp" %>

        <%  EquipmentDB db = ObjectDBFactory.getEquipmentDB();

            if (session.getAttribute("cart") == null) {
                session.setAttribute("cart", new ArrayList<Equipment>());
            }

            ArrayList<Equipment> cart = (ArrayList<Equipment>) session.getAttribute("cart");

            String action = request.getParameter("action");

            
            if ("clear".equals(action))
                cart.clear();
            else if (action != null) {
                String id = request.getParameter("id");
                Equipment e = db.getEquipment(Integer.parseInt(id));
                
                if (e != null)         
                    if ("add".equals(action)) {
                        if (cart.contains(e) == false) cart.add(e);
                    } else {
                        cart.remove(e);                        
                    }
                        
            }
                
        %>

        <!-- Begin page content -->
        <main role="main" class="flex-shrink-0">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <h3 class="mt-5">Discover</h3>
                        <div style="max-width: 500px">
                            <%
                            for (BorrowItem item : ObjectDBFactory.getBorrowItemDB().getAllBorrowItems()) {
                                if (item.getRecord().getUserId() == me.getId() && item.isOverdue()) {
                                    out.print("<div class=\"alert alert-danger my-4\" role=\"alert\">Overdue loaned equipment</div>");
                                    break;                                    
                                }
                            }
                            
                            %>
                            
                            
                            <%  for (Equipment e : db.getAllEquipments()) {
                                    if (e.getListing() == ListingStatus.ENABLE){
                            %>
                            <borsys:equipment-box equipment="<%= e %>" isStudent="true" canAddToCart="<%= !cart.contains(e)%>"/>
                            <%      }
                                } %>
                        </div>
                    </div>
                    <div class="col">
                        <h3 class="mt-5">Order List</h3>
                        <%
                            if (cart.size() == 0) {
                                out.print("<p class=\"head\">Empty</p>");
                            } else {
                                for (Equipment e : cart) {
                                    if (e.getStatus() == AvailabilityStatus.FREE && e.getListing() == ListingStatus.ENABLE) {
                                        out.print("<div class=\"card mb-3 shadow-sm\">"
                                                + "    <div class=\"card-body\">"
                                                + "        <p class=\"lead\">" + e.getName() + " (ID: " + e.getId() + ")</p>"
                                                + "        <form style=\"display: inline\" action=\"discover.jsp\" method=\"POST\">"
                                                + "            <input type=\"hidden\" name=\"action\" value=\"remove\" />"
                                                + "            <input type=\"hidden\" name=\"id\" value=\"" + e.getId() + "\" />"
                                                + "            <button class=\"btn btn-danger btn-inline btn-sm\" type=\"submit\">Remove</button>"
                                                + "        </form>"
                                                + "    </div>"
                                                + "</div>");
                                    }
                                }
                                out.print("<a class=\"btn btn-primary btn-inline btn-sm\" href=\"BorrowRecordController?action=checkout\">Checkout Order</a> ");
                                out.print("<a class=\"btn btn-danger btn-inline btn-sm\" href=\"discover.jsp?action=clear\">Remove All</a>");
                            }
                            %>
                    </div>
                </div>

            </div>
        </main>
    </body>
</html>
