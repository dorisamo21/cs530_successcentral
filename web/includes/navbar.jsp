<nav class="navbar fixed-top navbar-expand-lg navbar-dark" style="background-color: #195697;">
    <a class="navbar-brand" href="/scapp/home"><img src="/scapp/assests/sc_fulllogo-15x5-white.png" alt="sclogo" width="100" height="33"></a>
    <button class="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <% if (request.getSession().getAttribute("email") != null && request.getSession().getAttribute("isAdmin") == "true") { %>
                <li class="nav-item">
                    <a class="nav-link" href="mentee_list">
                        Mentee List
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="mentor_list">
                        Mentor List
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="dynamic_matches">
                        Matching Page
                    </a>
                </li>
                <li class="nav-item">
                   <a class="nav-link" href="matched_team">
                    Introduction Email
                  </a>
                </li>
            <% } else if (request.getSession().getAttribute("email") != null) { %>
                <li class="nav-item">
                    <a class="nav-link" href="mentee_list">
                        Mentee List
                    </a>
                </li>
            <% } else { %>
                <li class="nav-item">
                    <a class="nav-link" href="mentee_form">
                        Mentee Intake
                    </a>
                </li>
               <li class="nav-item">
                   <a class="nav-link" href="mentor_form">
                        Mentor Intake
                  </a>
               </li>

            <% } %>
            <li class="nav-item dropdown ">
                <% if (request.getSession().getAttribute("email") != null) { %>
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" id="foo" role="button" aria-haspopup="true" aria-expanded="false">
                        <%= request.getSession().getAttribute("email") %>
                        <% if (request.getSession().getAttribute("isAdmin") == "true") { %>
                        (Admin)
                        <% } %>
                    </a>
                    <div class="dropdown-menu" aria-labelledby="foo">
                        <form action="logout" method="post">
                            <button type="submit" class="dropdown-item">Sign out</button>
                        </form>
                    </div>
                <% } else { %>
                    <a class="nav-link" href="login">
                        Login
                    </a>
                <% } %>
            </li>

        </ul>
    </div>
</nav>