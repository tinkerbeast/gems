/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tinkerbeast.gems.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author rishin.goswami
 */
public class InfoProvider extends HttpServlet {

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
            throws ServletException, IOException, SQLException {

        // get service code
        String temp = request.getParameter("service");
        int serviceCode = temp == null ? 0 : Integer.parseInt(temp);

        // establish connection
        Connection con = null;
        Statement stm_scrl_r = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gemsdb", "gems", "gems");
            stm_scrl_r = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

        } catch (ClassNotFoundException e) {
            serviceCode = 0;
            e.printStackTrace();
        }


        // sql vars
        final String RETREIVE_PARTY = "SELECT * FROM gemsdb.party";
        final String RETREIVE_ETYPE = "SELECT * FROM gemsdb.expense";



        ResultSet rs;

        // write output
        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            // probably bad coding using 'if's ... but practical
            if (serviceCode == 1) {

                rs = stm_scrl_r.executeQuery(RETREIVE_PARTY);

                //out.println("<root>");
                while (rs.next()) {
                    int value = rs.getInt("p_id");
                    String name = rs.getString("p_name");

                    //out.println("<user name=\"" + name + "\" value=\"" + value + "\"></user>");
                    out.println("<option value=\"" + value + "\">" + name + "</option>");
                }
                //out.println("</root>");
            }
            if (serviceCode == 2) {
                rs = stm_scrl_r.executeQuery(RETREIVE_ETYPE);

                //out.println("<root>");
                while (rs.next()) {
                    int value = rs.getInt("e_id");
                    String name = rs.getString("e_name");

                    //out.println("<etype name=\"" + name + "\" value=\"" + value + "\"></etype>");
                    out.println("<option value=\"" + value + "\">" + name + "</option>");
                }
                //out.println("</root>");
            }

            if (serviceCode == 3 || serviceCode == 4) {

                int p_id = Integer.parseInt(request.getParameter("user"));
                long fromDate = Long.parseLong(request.getParameter("fromDate"));
                long toDate = Long.parseLong(request.getParameter("toDate"));


                rs = stm_scrl_r.executeQuery(getQueryString(p_id, fromDate, toDate, serviceCode));

                out.println("<root>");
                while (rs.next()) {
                    String party = rs.getString("p_name");
                    float amount = rs.getFloat("total");

                    out.println("<info party=\"" + party + "\" amount=\"" + amount + "\"></info>");
                }
                out.println("</root>");
            }

            // Testing purposes
            if (serviceCode == 5) {
                rs = stm_scrl_r.executeQuery("SELECT * FROM `gemsdb`.`partytransaction`");
                ResultSetMetaData rsmd = rs.getMetaData();
                int colCount = rsmd.getColumnCount();
                out.println("<root><![CDATA[");
                while (rs.next()) {
                    out.print(rs.getRow() + " : ");
                    for (int i = 1; i <= colCount; i++) {
                        out.print(rs.getString(i));
                        out.print(",");
                    }
                    out.println();
                }
                out.println("]]></root>");
            }

        } finally {
            out.close();
        }


        // close connection
        con.close();
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
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String getQueryString(int pid, long fromDt, long toDt, int status) {

        String fromDate = new Date(fromDt).toString();
        String toDate = new Date(toDt).toString();

        final String GET_CREDITORS = ""
                + "SELECT `p`.`p_name`, `t`.`total`"
                + "FROM"
                + "      (SELECT `t_debitor`, sum(`t_amount`) as `total`"
                + "       FROM `gemsdb`.`transaction`"
                + "       WHERE"
                + "        `t_creditor` = " + pid + "   AND"
                + "        `t_date` <= '" + toDate + "'  AND"
                + "        `t_date` >= '" + fromDate + "'"
                + "       GROUP BY `t_debitor`)   as `t`,"
                + "      `gemsdb`.`party`         as `p`"
                + "WHERE `t`.`t_debitor` = `p`.`p_id`";

        final String GET_DEBITORS = ""
                + "SELECT `p`.`p_name`, `t`.`total`"
                + "FROM"
                + "      (SELECT `t_creditor`, sum(`t_amount`) as `total`"
                + "       FROM `gemsdb`.`transaction`"
                + "       WHERE"
                + "        `t_debitor` = " + pid + "    AND"
                + "        `t_date` <= '" + toDate + "'  AND"
                + "        `t_date` >= '" + fromDate + "'"
                + "       GROUP BY `t_creditor`)   as `t`,"
                + "      `gemsdb`.`party`         as `p`"
                + "WHERE `t`.`t_creditor` = `p`.`p_id`";

        return status == 3 ? GET_CREDITORS : status == 4 ? GET_DEBITORS : null;
    }
}
