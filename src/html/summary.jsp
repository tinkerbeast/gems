<%-- 
    Document   : summary
    Created on : Jul 29, 2012, 02:35:54 PM
    Author     : rishin.goswami
--%>

<%@page import="java.text.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String SITE_ROOT = "http://localhost:8080/gems/";
    String DATE_FORMAT = "E, dd MMM yyyy";
    String JS_DATE_FORMAT = "D, dd M yy";

    Format formatter = new SimpleDateFormat(DATE_FORMAT);
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
        <link type="text/css" href="<%=SITE_ROOT%>lib/jquery-ui/css/jquery-ui.css" rel="stylesheet" />
        <link type="text/css" href="<%=SITE_ROOT%>lib/bootstrap/css/bootstrap.css" rel="stylesheet">
        <style>
            body {
                padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
                overflow-y: scroll;
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

        <div class="navbar navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="#">Gems</a>
                    <div class="nav-collapse">
                        <ul class="nav">
                            <li><a href="<%=SITE_ROOT%>index.jsp">Home</a></li>
                            <li><a href="<%=SITE_ROOT%>src/html/group-information-box.jsp">Transaction</a></li>
                            <li class="active"><a href="#">Summary</a></li>
                            <li><a href="#about">About</a></li>
                            <li><a href="#contact">Contact</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>


        <div class="container">






            <div class="row">

                <div class="span3">
                    <!-- TODO valid user selction-->
                    <label>Group name</label>
                    <input class="" type="text" value="world" disabled="disabled"/>
                </div>

                <div class="span3">
                    <label>From date</label>
                    <input id="gems-summary-dateFrom" type="text" name="fromDate" value="<%=formatter.format(new java.util.Date())%>"/>
                </div>

                <div class="span3">
                    <label>To date</label>
                    <input id="gems-summary-dateTo" type="text" name="toDate" value="<%=formatter.format(new java.util.Date())%>"/>
                </div>

                <div class="span3">
                    <!-- bad semantics TODO fix -->
                    <label>&nbsp;</label>
                    <button id="gems-summary-fetch" class="btn " 
                            type="submit" data-loading-text="Fetching data..."
                            onclick="sendFormData();return false;">Fetch data</button>
                </div>

            </div>

            <hr>    

            <div class="row">
                <div id="tempId" class="span4">

                   

                </div>    
            </div>


            <div id="pageAlert" class="alert hidden">
                <!--<button class="close" data-dismiss="alert">×</button>-->
                <p>
                    <strong>Alert heading placeholder</strong> 
                    <span>Alert text placeholder</span>
                </p>
            </div>



        </div> <!-- /container -->



        <!-- Model elements
        ================================================== -->

        <!-- WARNING: HTML5 only elements -->
        <datalist id="userList"></datalist>

        <!-- WARNING: HTML5 only elements -->
        <datalist id="expenseList"></datalist>



        <!-- Controller elements
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="<%=SITE_ROOT%>lib/jquery/js/jquery.js"></script>
        <script src="<%=SITE_ROOT%>lib/jquery-ui/js/jquery-ui.js"></script>

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

        <script src="<%=SITE_ROOT%>src/js/linked-list.js"></script>

        <script src="<%=SITE_ROOT%>src/js/transaction-unit.js"></script>
        <script src="<%=SITE_ROOT%>src/js/transaction-category.js"></script>

        <script type="text/javascript">
            
            // Disable everything at begining
            // ==============================

            // TODO
            //$("form button").attr("disabled", "disabled");
            //$("form input").attr("disabled", "disabled");

            
            function setPageStatus(status, textStatus, textDescription) {
                var ele = $('#pageAlert');
                ele.find("p strong").text(textStatus);
                ele.find("p span").text(textDescription);
                ele.attr("class", "alert " + status);
            }
            
            // Initialize page with data from server
            // =====================================
            
            var pageLoad = parseInt(0);
            
            /*
            // Load user list
            var userReq = $.ajax({
                url: "<%=SITE_ROOT%>service/info-provider", 
                async: false,
                type: "POST",
                data: {service: 1},
                dataType: "html"
            });           

            userReq.done(function(msg) {
                $("#userList").html( msg );
                pageLoad++;
            });

            userReq.fail(function(jqXHR, textStatus, httpStatus) {
                setPageStatus("alert-error", "Page load error!", "Error loading user list");
            });
             */
            
            
            
            $( "#gems-summary-dateFrom").datepicker({ dateFormat: "<%=JS_DATE_FORMAT%>"});
            $( "#gems-summary-dateTo").datepicker({ dateFormat: "<%=JS_DATE_FORMAT%>"});
            
            var dateReq = $.ajax({
                url: "<%=SITE_ROOT%>service/info-provider", 
                async: false,
                type: "POST",
                data: {service: 6},
                dataType: "html"
            });
            dateReq.done(function(msg) {
                $( "#gems-summary-dateFrom").datepicker("setDate", new Date(parseInt(msg)));
                pageLoad++;
            });
            dateReq.fail(function(jqXHR, textStatus, httpStatus) {
                setPageStatus("alert-error", "Page load error!", "Error getting initial date");
            });
            
            
            // Renable the page
            // ================
            
            //TODO
            //if(pageLoad==1) {                
            //    $("form input").removeAttr("disabled");
            //    $("form button").removeAttr("disabled");
            //}
            
            
      
            function sendFormData() {
                
                var btn = $("#gems-summary-fetch");
                btn.button('loading');
                
                // Get dates
                var dateFromVal =  $( "#gems-summary-dateFrom" ).datepicker("getDate" ).getTime();
                var dateToVal =  $( "#gems-summary-dateTo" ).datepicker("getDate" ).getTime();
                   
                // Send POST request
                var transactReq = $.ajax({
                    url: "<%=SITE_ROOT%>service/info-provider", 
                    async: false,
                    type: "POST",
                    data: {
                        "service": 3,
                        "fromDate": dateFromVal,
                        "toDate": dateToVal
                    },
                    dataType: "html"
                });
                transactReq.done(function(msg) {
                    $('#tempId').html(msg);
                });
                transactReq.fail(function(jqXHR, textStatus, httpStatus) {
                    setPageStatus("alert-error", "Transaction error!", "Connection / HTTP error [" + textStatus +": "+ httpStatus + "]");
                });
            
                btn.button('reset');
            }
            
            function show_all_fields(obj) {
                var fields = [];
                for (var m in obj) {
                    fields.push("\n" + typeof obj[m] + " : " + m);
                }
                return fields.join(",");
            }
            
          
        </script>

    </body>
</html>
