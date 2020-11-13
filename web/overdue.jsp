<%@page import="com.jerryio.borsys.enums.BorrowType"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jerryio.borsys.db.BorrowItemDB"%>
<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
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
        <title>Recent borrow item</title>
    </head>

    <body class="d-flex flex-column h-100">
        <%@include file="WEB-INF/header.jsp" %>


        <!-- Begin page content -->
        <main role="main" class="flex-shrink-0">
            <div class="container">
                <h2 class="mt-5 mb-3">Overdue Item</h2>
                
                <div style="max-width: 500px">
                    <%
                        
                        BorrowItemDB db = ObjectDBFactory.getBorrowItemDB();
                        ArrayList<BorrowItem> list = new ArrayList<BorrowItem>(db.getAllBorrowItems());
                        
                        Collections.sort(list, new Comparator<BorrowItem>() {
                            @Override
                            public int compare(BorrowItem i1, BorrowItem i2) {
                              return i2.getFrom().compareTo(i1.getFrom());
                            }
                        });
                        
                        Date now = new Date();
                        boolean isEmpty = true;
                        
                        for (BorrowItem item : list) {
                            if (item.isOverdue()) {
                            %>
                                <borsys:borrowitem-box item="<%= item %>"/>
                        <%
                                isEmpty = false;
                            }
                        } 
                        
                        
                        if (isEmpty)
                            out.print("Empty");
                        %>
                    
                </div>
            </div>
        </main>
    </body>
</html>
