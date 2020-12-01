<%@include file="WEB-INF/auth-check.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>    
    <head>
        <%@include file="WEB-INF/basic.jsp" %>
        <title>Statistic</title>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">
            google.charts.load('current', {packages: ['corechart', 'bar']});
            google.charts.setOnLoadCallback(init);
            
            $(function() {$("#submit").click(draw);})

            function init() {                
                draw();
            }

            async function draw() {
                var fieldFrom = $("#from");
                var fieldTo = $("#to");
                
                var rawData = await $.getJSON("api/UtilizationRate?from=" + fieldFrom.val() + "&to=" + fieldTo.val());

                var data = google.visualization.arrayToDataTable(rawData);

                var options = {
                  title: 'Utilization Rate of Equipment',
                  legend: {
                    position: 'top'
                  },
                  hAxis: {
                    title: 'Utilization Rate (%)',
                    minValue: 0
                  },
                  vAxis: {
                    title: 'Equipment'
                  },
                  width: '100%',
                  height: rawData.length * 40
                };

                var chart = new google.visualization.BarChart(document.getElementById('curve_chart'));

                chart.draw(data, options);
            }
        </script>
    </head>

    <body class="d-flex flex-column h-100">
        <%@include file="WEB-INF/header.jsp" %>


        <!-- Begin page content -->
        <main role="main" class="flex-shrink-0">
            <div class="container">
                <h2 class="mt-5 mb-3">Statistic</h2>
                <div class="mb-3">
                    Calculate from <input id="from" type="date" value="2020-11-01"/> to <input id="to" type="date" value="2020-12-01"/><br/>
                    <button class="btn btn-sm btn-primary btn-inline" id="submit">Submit</button>
                </div>
                <div style="max-width: 900px">
                    <div id="curve_chart" style=""></div>
                    
                </div>
            </div>
        </main>
    </body>
</html>
