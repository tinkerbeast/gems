/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tinkerbeast.gems.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rishin.goswami
 */
public class ProcessTransaction extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        // valid status code
        int status = 1;


        //retreived values
        Map<String, String[]> req = request.getParameterMap();

        int[] creditor = convertToIntArray(req.get("creditor"));
        int[] debitor = convertToIntArray(req.get("debitor"));        
        int[] type = convertToIntArray(req.get("type"));
        float[] amount = convertToFloatArray(req.get("amount"));
        java.sql.Date[] date = convertToDateArray(req.get("date"));


        // wrong logic, but fast logic
        int len = creditor.length ^ debitor.length ^ amount.length ^ date.length ^ type.length;
        if (len != creditor.length) {
            status = 0;
        }

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
            String INSERT_TRANSACTION;

            for (int i = 0; i < len; i++) {
                INSERT_TRANSACTION = "INSERT INTO  gemsdb.`transaction` VALUES ( null, " + creditor[i] + "," + debitor[i] + "," + amount[i] + "," + type[i] + ",'" + date[i].toString() + "')";

                stm.execute(INSERT_TRANSACTION);

                //status = stm.execute(INSERT_TRANSACTION) ? 1 : 0;
                //System.out.println(INSERT_TRANSACTION);
            }
        }



        // write output
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
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

    int[] convertToIntArray(String[] arr) {

        int[] intArr = new int[arr.length];

        for (int i = 0; i < intArr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }

        return intArr;
    }

    float[] convertToFloatArray(String[] data) {
        float[] floatArr = new float[data.length];
        for (int i = 0; i < floatArr.length; i++) {
            floatArr[i] = Float.parseFloat(data[i]);
        }
        return floatArr;
    }

    java.sql.Date[] convertToDateArray(String[] arr) {

        java.sql.Date[] dateArr = new java.sql.Date[arr.length];

        for (int i = 0; i < dateArr.length; i++) {
            dateArr[i] = new java.sql.Date(Long.parseLong(arr[i]));
        }

        return dateArr;
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
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
