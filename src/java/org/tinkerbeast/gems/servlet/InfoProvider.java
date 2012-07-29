/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tinkerbeast.gems.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
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

        PrintWriter out = response.getWriter();
        try {

            // probably bad coding using 'if's ... but practical
            if (serviceCode == 1) {
                response.setContentType("text/html;charset=UTF-8");

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
                response.setContentType("text/html;charset=UTF-8");

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

            // Debitor table, Creditor table, User table
            if (serviceCode == 3) {
                response.setContentType("text/xml;charset=UTF-8");

                // TODO WARNING: Hard-coded value for `world` - Needs better logic
                int MEDIATOR_USER = 3;

                int p_id = MEDIATOR_USER; //Integer.parseInt(request.getParameter("user"));
                long fromDate = Long.parseLong(request.getParameter("fromDate"));
                long toDate = Long.parseLong(request.getParameter("toDate"));

                Map<String, Float> accountMap = new HashMap<String, Float>();
                final String TABLE_HEADER = ""
                        + "<table class=\"table table-bordered table-condensed \">"
                        + "<thead><tr>"
                        + "<th>User</th>"
                        + "<th>Amount</th>"
                        + "</tr></thead>"
                        + "<tbody>";

                final String TABLE_FOOTER = "</tbody></table>";

                out.println("<root>");

                // Debitor table
                out.print("<debitor><![CDATA[");
                rs = stm_scrl_r.executeQuery(getQueryString(p_id, fromDate, toDate, 3));
                out.print("<h3>Debit table</h3>");
                out.print(TABLE_HEADER);
                while (rs.next()) {
                    String party = rs.getString("p_name");
                    float amount = rs.getFloat("total");
                    out.print("<tr><td>" + party + "</td><td>" + amount + "</td></tr>");
                    accountMap.put(party, amount);
                }
                out.print(TABLE_FOOTER);
                out.println("]]></debitor>");

                // Creditor table
                out.print("<creditor><![CDATA[");
                rs = stm_scrl_r.executeQuery(getQueryString(p_id, fromDate, toDate, 4));
                out.print("<h3>Credit table</h3>");
                out.print(TABLE_HEADER);
                while (rs.next()) {
                    String party = rs.getString("p_name");
                    float amount = rs.getFloat("total");
                    out.print("<tr><td>" + party + "</td><td>" + amount + "</td></tr>");
                    Float debitObj = accountMap.get(party);
                    float debit = debitObj == null ? 0.0f : debitObj.floatValue();
                    accountMap.put(party, (debit - amount));
                }
                out.print(TABLE_FOOTER);
                out.println("]]></creditor>");


                out.println("<user><![CDATA[");
                out.print("<h3>User summary</h3>");
                out.print("<table class=\"table table-bordered leade\">");
                Set<String> keys = accountMap.keySet();
                for (String key : keys) {
                    Float amount = accountMap.get(key);
                    String color = amount < 0 ? "#B94A48" : "#468847";
                    out.print("<tr><th>" + key + "</th><th style=\"color:" + color + "\">" + accountMap.get(key) + "</th></tr>");
                }
                out.print("</table>");
                out.println("]]></user>");

                out.println("</root>");
                
            }

            // Testing purposes
            if (serviceCode == 5) {
                response.setContentType("text/xml;charset=UTF-8");

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

            // Get the minimum date in transactions or current date
            // ====================================================
            if (serviceCode == 6) {
                response.setContentType("text/html;charset=UTF-8");

                rs = stm_scrl_r.executeQuery("select  MIN(`t_date`) from `gemsdb`.`transaction`");

                Date minDate = new Date(new java.util.Date().getTime());
                if (rs.next()) {
                    minDate = rs.getDate(1);
                }
                out.println(minDate.getTime());
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
