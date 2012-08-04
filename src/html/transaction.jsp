<%-- 
    Document   : transaction
    Created on : Jul 16, 2012, 5:54:43 PM
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
        <link href="<%=SITE_ROOT%>src/css/common.css" rel="stylesheet">
        <style>
            /* WARNING - static height */
            .gems-transactionCategory {
                height: 350px;
            }
            .gems-transactionCategory button.gems-btn-add {
                float: right;
            }

            /* ERROR  - static width */
            fieldset.gems-transactionUnit select {
                width: 170px
            }
            fieldset.gems-transactionUnit input {
                width: 150px;                
            }

            fieldset.gems-transactionCommon  input,
            fieldset.gems-transactionCommon  button 
            {
                width: 100%;
            }

            /* TODO Hack to fix non-centered button. To fix */
            #submitButton {
                margin-left: 5px;
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
                            <li><a href="<%=SITE_ROOT%>index.jsp">Home</a></li>
                            <li class="active"><a href="#">Transaction</a></li>
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



            <div class="row">
                <div class="span5">

                    <!-- Borrower box -->
                    <div class="gems-transactionCategory well" id="borrower">                        
                        <fieldset>
                            <legend>
                                <span>Borrower</span>
                                <output>&nbsp;</output>                                
                                <button class="btn gems-btn-add" href="#"><i class="icon-plus"></i></button>
                            </legend>
                        </fieldset>
                    </div>

                </div>
                <div class="span2">

                    <div style="height: 20px">
                        &nbsp;
                    </div>

                    <div style="text-align: center">
                        <button id="splitGive" class="btn" href="#"><i class="icon-chevron-left"></i> Give</button>
                        <button id="splitTake" class="btn" href="#">Take <i class="icon-chevron-right"></i></button>
                    </div>

                    <div style="height: 235px">
                        &nbsp;
                    </div>

                    <fieldset class="gems-transactionCommon">

                        <input type="text" id="gems-transaction-datepicker" value="<%=formatter.format(new java.util.Date())%>"/>

                        <!-- WARNING: HTML5 list binding only -->
                        <input type="text" id="gems-transaction-expenseList" list="expenseList" placeholder="expense tag"/>

                        <button id="submitButton" class="btn " type="submit" onclick="sendFormData(); return false;" data-loading-text="Processing transaction...">Transact</button>

                    </fieldset>

                </div>
                <div class="span5">

                    <!-- Lender box -->
                    <div class="gems-transactionCategory well" id="lender">
                        <fieldset>
                            <legend>
                                <span>Lender</span>
                                <output>&nbsp;</output>                                
                                <button class="btn gems-btn-add" href="#"><i class="icon-plus"></i></button>
                            </legend>
                        </fieldset>
                    </div>

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
            $("fieldset button").attr("disabled", "disabled");
            $("fieldset input").attr("disabled", "disabled");

            
            function setPageStatus(status, textStatus, textDescription) {
                var ele = $('#pageAlert');
                ele.find("p strong").text(textStatus);
                ele.find("p span").text(textDescription);
                ele.attr("class", "alert " + status);
            }
            
            // Initialize page with data from server
            // =====================================
            
            var pageLoad = parseInt(0);
            
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
            
            // Load expense list
            var expenseReq = $.ajax({
                url: "<%=SITE_ROOT%>service/info-provider", 
                async: false,
                type: "POST",
                data: {service: 2},
                dataType: "html"
            });           

            expenseReq.done(function(msg) {
                $("#expenseList").html( msg );
                pageLoad++;
            });

            expenseReq.fail(function(jqXHR, textStatus, httpStatus) {
                setPageStatus("alert-error", "Page load error!", "Error loading expense list");
            });
            
            TransactionUnitFactory.initialise($('#userList'));
            
            var borrowerBox = new TransactionCategory ($('#borrower'));
            var lenderBox = new TransactionCategory ($('#lender'));
            
            $("#splitTake").bind("click", {
                self: borrowerBox,
                target: lenderBox
            }, onSplit);
            $("#splitGive").bind("click", {
                self: lenderBox,
                target: borrowerBox
            }, onSplit);
            
            
            $( "#gems-transaction-datepicker" ).datepicker({ dateFormat: "<%=JS_DATE_FORMAT%>"});
            
            // Renable the page
            // ================
            if(pageLoad==2) {
                $("fieldset button").removeAttr("disabled");
                $("fieldset input").removeAttr("disabled");
            }
            
             function onSplit(event) {
                event.data.target.setSplitValue(event.data.self.sumVal);
            };
            
      
            function sendFormData() {
                
                var btn = $("#submitButton");
                btn.button('loading');
                
                // Validate transaction-boxes
                if(!borrowerBox.isValid || !lenderBox.isValid) {
                    setPageStatus("alert-error", "Invalid transaction data!", "One or more data units are invalid");
                    btn.button('reset');
                    return;
                }
                if(borrowerBox.sumVal != lenderBox.sumVal) {
                    setPageStatus("alert-error", "Invalid transaction data!", "Left hand and right hand side sums do not match");
                    btn.button('reset');
                    return;
                }
                
                // Get / validate expense type tag
                var expTypeVal = $("#gems-transaction-expenseList").val();
                if(expTypeVal == "") {
                    setPageStatus("alert-error", "Invalid transaction data!", "Expense type tag cannot be empty");
                    btn.button('reset');
                    return;
                }
                
                // Get datas
                var dateVal =  $( "#gems-transaction-datepicker" ).datepicker("getDate" ).getTime();                
                var borrowerData = borrowerBox.getData();
                var lenderData = lenderBox.getData();
                   
                // Send POST request
                var transactReq = $.ajax({
                    url: "<%=SITE_ROOT%>service/process-transaction", 
                    async: false,
                    type: "POST",
                    data: {
                        "borrowerUser": borrowerData.userList,
                        "borrowerAmount": borrowerData.amountList,
                        "lenderUser": lenderData.userList,
                        "lenderAmount": lenderData.amountList,
                        "date": dateVal,
                        "type": expTypeVal                        
                    },
                    dataType: "html"
                });           

                transactReq.done(function(msg) {
                    var statusCode = parseInt(msg);
                    if(statusCode == 1) {
                        setPageStatus("alert-success", "Transaction successful!", "");
                    } else {
                        setPageStatus("alert-error", "Transaction error!", "Error while processing data on server [code: "+ statusCode + "]");
                    }
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
