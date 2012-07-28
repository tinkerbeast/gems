/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tinkerbeast.gems.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
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
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // TODO WARNING: Hard-coded value for `world` - Needs better logic
        int MEDIATOR_USER = 3;

        int status = 1;

        // Retrieve and validate values
        // ============================
        Map<String, String[]> req = request.getParameterMap();
        int[] borrowerUser = null;
        float[] borrowerAmount = null;
        int[] lenderUser = null;
        float[] lenderAmount = null;
        Date date = null;
        int type = -1;

        if (status == 1) {
            try {
                borrowerUser = convertToIntArray(req.get("borrowerUser[]"));
                borrowerAmount = convertToFloatArray(req.get("borrowerAmount[]"));
                if (borrowerUser.length != borrowerAmount.length) {
                    throw new IllegalArgumentException("Borrower array corrupted");
                }

                lenderUser = convertToIntArray(req.get("lenderUser[]"));
                lenderAmount = convertToFloatArray(req.get("lenderAmount[]"));
                if (lenderUser.length != lenderAmount.length) {
                    throw new IllegalArgumentException("Lender array corrupted");
                }

                date = new Date(Long.parseLong(req.get("date")[0]));
                type = Integer.parseInt(req.get("type")[0]);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                status = 0;
            }
        }

        // Create creditor / debitor units
        // ===============================
        // ERROR can cause null pointer 
        int transactionLength = borrowerUser.length + lenderUser.length;
        int[] creditor = new int[transactionLength];
        int[] debitor = new int[transactionLength];
        float[] amount = new float[transactionLength];

        if (status == 1) {

            for (int i = 0; i < borrowerUser.length; i++) {
                creditor[i] = borrowerUser[i];
                debitor[i] = MEDIATOR_USER;
                amount[i] = borrowerAmount[i];
            }

            int offset = borrowerUser.length;
            for (int i = 0; i < lenderUser.length; i++) {
                creditor[i + offset] = MEDIATOR_USER;
                debitor[i + offset] = lenderUser[i];
                amount[i + offset] = lenderAmount[i];
            }
        }

        // Store in database
        // =================
        Connection con = null;
        Statement stm = null;

        // establish connection 
        if (status == 1) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gemsdb", "gems", "gems");
                stm = con.createStatement();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                status = 0;
            } catch (SQLException e) {
                e.printStackTrace();
                status = 0;
            }
        }

        // sql operations
        if (status == 1) {
            try {
                String INSERT_TRANSACTION;
                for (int i = 0; i < transactionLength; i++) {
                    INSERT_TRANSACTION = ""
                            + "INSERT INTO `gemsdb`.`transaction` "
                            + "VALUES ( null, " + creditor[i] + "," + debitor[i] + "," + amount[i] + "," + type + ",'" + date.toString() + "')";

                    stm.execute(INSERT_TRANSACTION);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                status = -1;
            }

            try {
                stm.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


        // Output success / failure to page
        // ================================

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println(status);
        } finally {
            out.close();
        }
    }

    int[] convertToIntArray(String[] arr) throws NumberFormatException {

        int[] intArr = new int[arr.length];

        for (int i = 0; i < intArr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }

        return intArr;
    }

    float[] convertToFloatArray(String[] data) throws NumberFormatException {
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
     * Handles the HTTP
     * <code>GET</code> method.
     *
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
     * Handles the HTTP
     * <code>POST</code> method.
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Processes transactions generated by /gems/src/html/transaction.jsp";
    }// </editor-fold>
}
