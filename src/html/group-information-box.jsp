<%-- 
    Document   : group-information-box
    Created on : Jul 16, 2012, 5:54:43 PM
    Author     : rishin.goswami
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String SITE_ROOT = "http://localhost:8084/gems/";
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
        <style>
            body {
                padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
            }


            /* WARNING - static height */
            .gems-groupInfoBox {
                height: 350px;
            }
            .gems-groupInfoBox button.gems-btn-add {
                float: right;
            }

            /* ERROR  - static width */
            fieldset.gems-partyInformation select {
                width: 170px
            }
            fieldset.gems-partyInformation input {
                width: 150px;                
            }

            .gems-display-none {
                display: none;
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
                            <li class="active"><a href="#">Home</a></li>
                            <li><a href="#about">About</a></li>
                            <li><a href="#contact">Contact</a></li>
                        </ul>
                    </div><!--/.nav-collapse -->
                </div>
            </div>
        </div>

        <div class="container">



            <div class="row">
                <div class="span5">

                    <div class="gems-groupInfoBox well" id="borrower">
                        <fieldset>

                            <legend>                            
                                Borrower
                                <span class="gems-groupInfoBox-sum"></span>
                                <button class="btn gems-btn-add" href="#"><i class="icon-plus"></i></button>
                            </legend>


                        </fieldset>

                    </div>     

                </div>
                <div class="span2">
                    Image holder
                </div>
                <div class="span5">
                    <div class="gems-groupInfoBox well">
                        <fieldset>

                            <legend>                            
                                Lender                                                   
                                <button class="btn gems-btn-add" href="#"><i class="icon-plus"></i></button>
                            </legend>



                        </fieldset>

                    </div>     
                </div>
            </div>



        </div> <!-- /container -->

        <!-- Model elements
        ================================================== -->
        <datalist id="userList">
            <option value="Homer Simpson">Homer Simpson</option>
            <option value="Bart">Bart</option>
            <option value="Fred Flinstone">Fred Flinstone</option>
        </datalist>




        <!-- Controller elements
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

        <script type="text/javascript">
            
            

            function PartyInformation () {
                this.element = null;                
            };
            
            PartyInformation.prototype.onClose  = function() {
                // NOTE: `this` here will refere to the button element whre the function is binded
                var fieldset = this.parentNode;
                fieldset.parentNode.removeChild(fieldset); /* WARNING - DOM method used */
            };
            
            /**
             * Generates a form element of the following format
             * 
                <fieldset class="gems-partyInformation">
                    <select required="true"></select>
                    <input type="number" required="true"/>
                    <button class="close" >&times;</button>
                </fieldset>
             */
            PartyInformation.prototype.createPartyInfo = function(closeFunction) {
                var partyInfoElement = jQuery('<fieldset/>').addClass('gems-partyInformation');
                var partyInfoSelect =  jQuery('<select/>', {
                    required : true 
                }).append($('#userList').children());
                var partyInfoInput =  jQuery('<input/>', {
                    type: "number",
                    required : true 
                });
                // ERROR - Workaround since &cross; could not go in text
                var partyInfoClose =  jQuery('<button/>', {
                    text: "×"
                }).addClass('close').click(closeFunction);
                partyInfoElement.append(partyInfoSelect).append(" ").append(partyInfoInput).append(partyInfoClose);
            
                return partyInfoElement;
            };
            
            PartyInformation.prototype.getHtmlElement = function() {
                if(this.element==null) {                    
                    this.element = this.createPartyInfo(this.onClose);
                }
                return this.element;
            };

            //var pinf = new PartyInformation();            
            //$('#borrower').append(pinf.getHtmlElement());
            
            var field = jQuery(fieldHtml);
            field.children("selct").append($('#userList').children());
            
          
        </script>

    </body>
</html>
