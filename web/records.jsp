<%@page import="java.util.Collections"%>
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
        <title>Records</title>
    </head>

    <body class="d-flex flex-column h-100">
        <%@include file="WEB-INF/header.jsp" %>


        <!-- Begin page content -->
        <main role="main" class="flex-shrink-0">
            <div class="container">
                <h2 class="mt-5 mb-3">Borrow Records</h2>
                
                <div style="max-width: 500px">
                    <%
                        ArrayList<BorrowRecord> list = (ArrayList<BorrowRecord>)ObjectDBFactory.getBorrowRecordDB().getAllBorrowRecords();
                        
                        Collections.reverse(list);
                        
                        for (BorrowRecord r : list) { 
                            if (r.getUserId() == me.getId()) {
                    %>
                               <borsys:borrowrecord-box record="<%= r %>" isStudent="false"/>
                    <%      }
                        } %>
                    
                </div>
            </div>
        </main>
    </body>
</html>
