<%@page import="com.jerryio.borsys.bean.BorrowItem"%>
<%@page import="com.jerryio.borsys.Util"%>
<%@page import="com.jerryio.borsys.bean.BorrowRecord"%>
<%@page import="com.jerryio.borsys.db.EquipmentDB"%>
<%@page import="com.jerryio.borsys.enums.AvailabilityStatus"%>
<%@page import="com.jerryio.borsys.enums.ListingStatus"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.jerryio.borsys.bean.Equipment"%>
<%@page import="com.jerryio.borsys.factory.ObjectDBFactory"%>
<%@include file="WEB-INF/auth-check.jsp"%>
<%@taglib uri="/WEB-INF/tlds/a_tag_lib.tld" prefix="borsys" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="WEB-INF/basic.jsp" %>
        <title>Requests</title>
    </head>

    <body class="d-flex flex-column h-100">
        <%@include file="WEB-INF/header.jsp" %>
        
        <%!
        
        boolean match(String[] keywords, int id) {
            for (String s : keywords)
                if (s.equals(id + "")) return true;
            return false;
        }
        
        boolean match(String[] keywords, ArrayList<BorrowItem> items) {
            for (String s : keywords)
                for (BorrowItem item : items)
                    if (s.equals(item.getEquipmentId() + "")) return true;
            return false;
        }

        %>


        <!-- Begin page content -->
        <main role="main" class="flex-shrink-0">
            <div class="container">
                <h2 class="mt-5 mb-3">Borrow Records</h2>
                
                <div style="max-width: 500px">
                    <%
                        String rawInput = request.getParameter("search");
                        if ("".equals(rawInput)) rawInput = null;
                        
                        String[] keywords = rawInput == null ? null : rawInput.split(" ");
                    %>
                    <form class="form-row my-4" style="padding: 0 5px" action="" method="GET">
                        <input class="form-control form-group col-10" placeholder="record id, student id or equipment id" name="search" value="<%= rawInput == null ? "" : rawInput %>"/>
                        <input class="btn btn-primary form-group col" type="submit" value="Search" style="margin-left: 10px"/>
                    </form>
                    <%                        
                        ArrayList<BorrowRecord> list = ObjectDBFactory.getBorrowRecordDB().getAllBorrowRecords();
                        
                        for (BorrowRecord r : Util.reverseList(list)) { 
                            if (keywords == null || match(keywords, r.getId()) || match(keywords, r.getUserId()) || match(keywords, r.getItemList())) {
                    %>
                                <borsys:borrowrecord-box record="<%= r %>" isStudent="false"/>
                    <%      }
                        }%>
                    
                </div>
            </div>
        </main>
    </body>
</html>
