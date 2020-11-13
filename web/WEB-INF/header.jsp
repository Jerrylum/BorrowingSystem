<%@page import="com.jerryio.borsys.enums.RoleType"%>
<%@page import="com.jerryio.borsys.Util"%>
<%!
    class CreateLinkHelper {

        String requestJsp;
        HttpServletRequest request;

        CreateLinkHelper(HttpServletRequest req) {
            String[] buf = req.getRequestURI().split("/");
            requestJsp = buf[buf.length - 1];
            request = req;
        }

        String link(String name, String url) {
            String finalUrl = request.getContextPath() + "/" + url;
            String activeFlag = url.equals(requestJsp) ? "active" : "";
            return "<li class=\"nav-item\">"
                    + "<a class=\"nav-link " + activeFlag + "\" href=\"" + finalUrl + "\">"
                    + name
                    + "</a></li>";
        }
    }

%>

<%

    CreateLinkHelper helper = new CreateLinkHelper(request);

%>

<header>
    <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="index.jsp">IVPET</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav mr-auto">
                <%  if (me.getRole() == RoleType.STUDENT) {
                        out.print(
                            helper.link("Discover", "discover.jsp") +
                            helper.link("Records", "records.jsp")
                        );
                    } else if (me.getRole() == RoleType.TECHNICIAN) {
                        out.print(
                            helper.link("Inventory", "inventory.jsp") +
                            helper.link("Requests", "requests.jsp") +
                            helper.link("Overdue", "overdue.jsp")
                        );
                    } else if (me.getRole() == RoleType.ADMIN) {
                        out.print(
                            helper.link("Inventory", "inventory.jsp") +
                            helper.link("Requests", "requests.jsp") +
                            helper.link("Overdue", "overdue.jsp") +
                            helper.link("Statistic", "statistic.jsp") +
                            helper.link("Recent", "recent.jsp") +
                            helper.link("Account", "account.jsp")
                        );
                    }

                %>
            </ul>
            <form class="form-inline mt-2 mt-md-0">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="navbar-brand"><%= me.getName()%></a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-outline-success my-2 my-sm-0" href="UserController?action=logout">Logout</a>
                    </li>
                </ul>

            </form>
        </div>
    </nav>
</header>