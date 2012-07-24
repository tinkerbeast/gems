/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tinkerbeast.gems.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tinkerbeast.trans.ConvertData;
import org.tinkerbeast.trans.DataProcessor;
import org.tinkerbeast.trans.ValidateData;
import org.tinkerbeast.util.Array;
import org.tinkerbeast.util.MySQLConnection;
import org.tinkerbeast.util.StatusCode;

/**
 *
 * @author rishin.goswami
 */
public class NewProcessTransaction extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        // *** process data ***

        // [DATA] retreive values
        Map<String, String[]> req = request.getParameterMap();
        // [CONV]
        ConvertData<Integer, String> intConv = new ConvertData<Integer, String>() {

            @Override
            public Integer convert(String data) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Integer[] convert(String[] data) {
                Integer[] intArr = new Integer[data.length];
                for (int i = 0; i < intArr.length; i++) {
                    intArr[i] = Integer.getInteger(data[i]);
                }
                return intArr;
            }
        };
        ConvertData<java.sql.Date, String> sqlDateConv = new ConvertData<java.sql.Date, String>() {

            @Override
            public Date convert(String data) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public Date[] convert(String[] data) {
                java.sql.Date[] dateArr = new java.sql.Date[data.length];

                for (int i = 0; i < dateArr.length; i++) {
                    dateArr[i] = new java.sql.Date(Long.parseLong(data[i]));
                }

                return dateArr;
            }
        };
        // [VALD]
        ValidateData lenVald = new ValidateData() {

            @Override
            public boolean isValid(Map<String, Array<? extends Object>> data) {
                String[] keys = (String[]) data.keySet().toArray();

                int len = data.get(keys[0]).length();
                for (int i = 1; i < keys.length; i++) {
                    int tempLen = data.get(keys[i]).length();
                    if (len != tempLen) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public String getLog() {
                return "INFO: no log kept";
            }
        };
        // [PARAMS]
        String[] groups = {"creditor", "debitor", "amount", "type", "date"};
        ConvertData[] convs = {intConv, intConv, intConv, intConv, sqlDateConv};
        ValidateData[] valds = {lenVald};

        // [DP]
        DataProcessor dp = new DataProcessor(req, groups, convs, valds);
        // [RESDATA]
        Map<String, Array<?>> resData = dp.getProcessedData();
        if (resData == null) {
            sendResponse(response, StatusCode.GENERIC_FAIL, dp.getLog());
            return;
        }

        // *** database connection ***
        Connection con = MySQLConnection.getConnection("jdbc:mysql://localhost:3306/gemsdb", "gems", "gems");

        //Statement stm = con.createStatement();


        /*
        try {
        // establish connection
        Connection con = null;
        Statement stm = null;
        try {
        Class.forName("com.mysql.jdbc.Driver");

        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gemsdb", "gems", "gems");
        stm = con.createStatement();
        } catch (ClassNotFoundException e) {
        status = 0;
        e.printStackTrace();
        }


        // sql operations

        if (status != 0) {
        String INSERT_TRANSACTION = "";

        for (int i = 0; i < len; i++) {
        INSERT_TRANSACTION = "INSERT INTO  gemsdb.`transaction` VALUES ( null, " + creditor[i] + "," + debitor[i] + "," + amount[i] + "," + type[i] + ",'" + date[i].toString() + "')";

        stm.execute(INSERT_TRANSACTION);

        //status = stm.execute(INSERT_TRANSACTION) ? 1 : 0;
        //System.out.println(INSERT_TRANSACTION);
        }
        }
        } catch (SQLException e) {
        }




        try {
        out.println("<root>");
        out.println("<status>" + status + "</status>");
        out.println("<message></message>");
        out.println("</root>");
        } finally {
        out.close();
        }

        // close connection
        con.close();

        }
        finally

        {
        out.close();
        }
         */
    }

    public void sendResponse(HttpServletResponse response, int statusCode, String message)
            throws ServletException, IOException {

        response.setContentType("text/xml;charset=UTF-8");

        PrintWriter out = response.getWriter();

        StringBuffer sb = new StringBuffer();
        sb.append("<root>");
        sb.append("<status>").append(statusCode).append("</status>");
        sb.append("<message>)").append(message).append("</message>");
        sb.append("</root>");

        out.print(sb);

        out.close();
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);






    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);






    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";



    }// </editor-fold>
}
