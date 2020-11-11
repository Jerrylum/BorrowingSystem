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
        <title>Discover</title>
    </head>

    <body class="d-flex flex-column h-100">
        <%@include file="WEB-INF/header.jsp" %>


        <!-- Begin page content -->
        <main role="main" class="flex-shrink-0">
            <div class="container">
                <h2 class="mt-5">Borrow Records</h2>
            </div>
        </main>
    </body>
</html>
