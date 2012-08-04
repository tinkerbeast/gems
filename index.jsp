<%-- 
    Document   : index
    Created on : Jul 16, 2012, 5:41:58 PM
    Author     : rishin.goswami
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String SITE_ROOT = "http://localhost:8080/gems/";
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Gems</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Le styles -->
        <link href="<%=SITE_ROOT%>lib/bootstrap/css/bootstrap.css" rel="stylesheet">
        <link href="<%=SITE_ROOT%>src/css/common.css" rel="stylesheet">
        <style>

            .jumbotron h1 {
                font-size: 63px;
                font-weight: bold;
                letter-spacing: -1px;
                line-height: 1;
                margin-bottom: 7px;
            }
            .jumbotron p {
                font-weight: 300;
                margin-bottom: 14px;
            }

            .masthead h1, .masthead p {
                text-align: center;
            }
            .masthead h1 {
                margin-bottom: 14px;
            }
            .masthead p {
                font-size: 23px;
                line-height: 28px;
                margin-left: 4%;
                margin-right: 4%;
            }




        </style>
        <link href="<%=SITE_ROOT%>lib/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">

        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
          <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->

        <!-- Le fav and touch icons -->
        <link rel="shortcut icon" href="">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="">
        <link rel="apple-touch-icon-precomposed" href="">
    </head>

    <body>

        <!-- Visual elements
        ================================================== -->

        <!-- Navigation bar -->
        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">

                    <!-- Navbar collapse button -->
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>

                    <!-- Logo / brand name -->
                    <a class="brand" href="#">Gems</a>

                    <!-- Site common navigation -->
                    <div class="nav-collapse">
                        <ul class="nav">
                            <li class="active"><a href="#">Home</a></li>
                            <li><a href="<%=SITE_ROOT%>src/html/transaction.jsp">Transaction</a></li>
                            <li><a href="<%=SITE_ROOT%>src/html/summary.jsp">Summary</a></li>
                            <li><a href="#about">About</a></li>
                            <li><a href="#contact">Contact</a></li>
                        </ul>                            
                    </div>


                    <!-- Site common toolbar -->
                    <form class="navbar-form btn-group pull-right">
                        <a class="btn btn-inverse" href="#"><i class="gemsicon-help"></i></a>
                    </form>

                </div>
            </div>
        </div>
        <!-- /Navigation bar -->

        <div class="container">

            <header class="jumbotron masthead">
                <h1>Gems expense management system</h1>
                <p>Gems has reached yet another version. It's slicker, it's shinier 
                    and it's HTML5. Still a lot of work to be done to clean up 
                    things, but <strong>welcome to Gems 3 beta</strong>.</p>
            </header>



        </div> <!-- /container -->

        <!-- Le javascript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="<%=SITE_ROOT%>lib/jquery/js/jquery.js"></script>
        <script src="<%=SITE_ROOT%>lib/bootstrap/js/bootstrap-transition.js"></script>
        <script src="<%=SITE_ROOT%>lib/bootstrap/js/bootstrap-alert.js"></script>
        <script src="<%=SITE_ROOT%>lib/bootstrap/js/bootstrap-modal.js"></script>
        <script src="<%=SITE_ROOT%>lib/bootstrap/js/bootstrap-dropdown.js"></script>
        <script src="<%=SITE_ROOT%>lib/bootstrap/js/bootstrap-scrollspy.js"></script>
        <script src="<%=SITE_ROOT%>lib/bootstrap/js/bootstrap-tab.js"></script>
        <script src="<%=SITE_ROOT%>lib/bootstrap/js/bootstrap-tooltip.js"></script>
        <script src="<%=SITE_ROOT%>lib/bootstrap/js/bootstrap-popover.js"></script>
        <script src="<%=SITE_ROOT%>lib/bootstrap/js/bootstrap-button.js"></script>
        <script src="<%=SITE_ROOT%>lib/bootstrap/js/bootstrap-collapse.js"></script>
        <script src="<%=SITE_ROOT%>lib/bootstrap/js/bootstrap-carousel.js"></script>
        <script src="<%=SITE_ROOT%>lib/bootstrap/js/bootstrap-typeahead.js"></script>

    </body>
</html>
