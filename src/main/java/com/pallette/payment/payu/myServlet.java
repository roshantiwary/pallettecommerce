/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallette.payment.payu;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author root
 */
@WebServlet(urlPatterns = {"/submitorder"})
public class myServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JavaIntegrationKit integrationKit = new JavaIntegrationKit();
        PrintWriter writer = response.getWriter();
        String htmlResponse = "<html> <body> \n"
                + "        <form id=\"payuform\" action=\"" + request.getAttribute("action") + "\"  name=\"payuform\" method=POST >\n"
                + "      <input type=\"hidden\" name=\"key\" value=" + request.getAttribute("key").toString().trim() + ">"
                + "      <input type=\"hidden\" name=\"hash\" value=" + request.getAttribute("hash").toString().trim() + ">"
                + "      <input type=\"hidden\" name=\"txnid\" value=" + request.getAttribute("txnid").toString().trim() + ">"
                + "      <input type=\"hidden\" name=\"amount\" value=" + request.getAttribute("amount").toString().trim() + " />\n"
                + "      <input type=\"hidden\"  name=\"firstname\" id=\"firstname\" value=" + request.getAttribute("firstname").toString().trim() + " />\n"
                + "      <input type=\"hidden\" name=\"email\" id=\"email\" value=" + request.getAttribute("email").toString().trim() + " />\n"
                + "      <input  type=\"hidden\" name=\"phone\" value=" + request.getAttribute("phone") + " >\n"
                + "		<input  type=\"hidden\" name=\"productinfo\" value=" + request.getAttribute("productinfo").toString().trim() + " >\n"
                + "      <input  type=\"hidden\" name=\"surl\"  size=\"64\" value=" + request.getAttribute("surl") + ">\n"
                + "      <input  type=\"hidden\" name=\"furl\" value=" + request.getAttribute("furl") + " size=\"64\" >\n"
                + "      <input type=\"hidden\" name=\"service_provider\" value=\"payu_paisa\" />\n"
                + "      <input  type=\"hidden\" name=\"lastname\" id=\"lastname\" value=" + request.getAttribute("lastname") + " >\n"
                + "       <input  type=\"hidden\" name=\"curl\" value=" + request.getAttribute("curl") + " >\n"
                + "        <input  type=\"hidden\" name=\"address1\" value=" + request.getAttribute("address1") + " >\n"
                + "       <input  type=\"hidden\" name=\"address2\" value=" + request.getAttribute("address2") + " >\n"
                + "        <input  type=\"hidden\" name=\"city\" value=" + request.getAttribute("city") + ">\n"
                + "       <input  type=\"hidden\" name=\"state\" value=" + request.getAttribute("state") + ">\n"
                + "        <input  type=\"hidden\" name=\"country\" value=" + request.getAttribute("country") + " >\n"
                + "        <input name=\"zipcode\"  type=\"hidden\" value=" + request.getAttribute("zipcode") + " >\n"
                + "        <input name=\"udf1\"  type=\"hidden\" value=" + request.getAttribute("udf1") + ">\n"
                + "         <input name=\"udf2\" type=\"hidden\"  value=" + request.getAttribute("udf2") + ">\n"
                + " 	<input name=\"hashString\"  type=\"hidden\" value=" + request.getAttribute("hashString") + ">\n"
                + "          <input name=\"udf3\"  type=\"hidden\" value=" + request.getAttribute("udf3") + " >\n"
                + "          <input name=\"udf4\"  type=\"hidden\" value=" + request.getAttribute("udf4") + " >\n"
               + "          <input name=\"udf5\"  type=\"hidden\" value=" + request.getAttribute("udf5") + " >\n"
               + "          <input name=\"pg\"  type=\"hidden\" value=" + request.getAttribute("pg") + " >\n"
                + "        <input type=\"submit\" value=\"Submit\"  />\n"
                + "    </form>\n"
                + " <script> "
                + " document.getElementById(\"payuform\").submit(); "
                + " </script> "
                + "       </div>   "
                + "  \n"
                + "  </body>\n"
                + "</html>";
// return response
        writer.println(htmlResponse);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException {
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {

        JavaIntegrationKit integrationKit = new JavaIntegrationKit();
        PrintWriter writer = response.getWriter();
        String htmlResponse = "<html> <body> \n"
                + "        <form id=\"payuform\" action=\"" + request.getAttribute("action") + "\"  name=\"payuform\" method=POST >\n"
                + "      <input type=\"hidden\" name=\"key\" value=" + request.getAttribute("key").toString().trim() + ">"
                + "      <input type=\"hidden\" name=\"hash\" value=" + request.getAttribute("hash").toString().trim() + ">"
                + "      <input type=\"hidden\" name=\"txnid\" value=" + request.getAttribute("txnid").toString().trim() + ">"
                + "      <input type=\"hidden\" name=\"amount\" value=" + request.getAttribute("amount").toString().trim() + " />\n"
                + "      <input type=\"hidden\"  name=\"firstname\" id=\"firstname\" value=" + request.getAttribute("firstname").toString().trim() + " />\n"
                + "      <input type=\"hidden\" name=\"email\" id=\"email\" value=" + request.getAttribute("email").toString().trim() + " />\n"
                + "      <input  type=\"hidden\" name=\"phone\" value=" + request.getAttribute("phone") + " >\n"
                + "		<input  type=\"hidden\" name=\"productinfo\" value=" + request.getAttribute("productinfo").toString().trim() + " >\n"
                + "      <input  type=\"hidden\" name=\"surl\"  size=\"64\" value=" + request.getAttribute("surl") + ">\n"
                + "      <input  type=\"hidden\" name=\"furl\" value=" + request.getAttribute("furl") + " size=\"64\" >\n"
                + "      <input type=\"hidden\" name=\"service_provider\" value=\"payu_paisa\" />\n"
                + "      <input  type=\"hidden\" name=\"lastname\" id=\"lastname\" value=" + request.getAttribute("lastname") + " >\n"
                + "       <input  type=\"hidden\" name=\"curl\" value=" + request.getAttribute("curl") + " >\n"
                + "        <input  type=\"hidden\" name=\"address1\" value=" + request.getAttribute("address1") + " >\n"
                + "       <input  type=\"hidden\" name=\"address2\" value=" + request.getAttribute("address2") + " >\n"
                + "        <input  type=\"hidden\" name=\"city\" value=" + request.getAttribute("city") + ">\n"
                + "       <input  type=\"hidden\" name=\"state\" value=" + request.getAttribute("state") + ">\n"
                + "        <input  type=\"hidden\" name=\"country\" value=" + request.getAttribute("country") + " >\n"
                + "        <input name=\"zipcode\"  type=\"hidden\" value=" + request.getAttribute("zipcode") + " >\n"
                + "        <input name=\"udf1\"  type=\"hidden\" value=" + request.getAttribute("udf1") + ">\n"
                + "         <input name=\"udf2\" type=\"hidden\"  value=" + request.getAttribute("udf2") + ">\n"
                + " 	<input name=\"hashString\"  type=\"hidden\" value=" + request.getAttribute("hashString") + ">\n"
                + "          <input name=\"udf3\"  type=\"hidden\" value=" + request.getAttribute("udf3") + " >\n"
                + "          <input name=\"udf4\"  type=\"hidden\" value=" + request.getAttribute("udf4") + " >\n"
               + "          <input name=\"udf5\"  type=\"hidden\" value=" + request.getAttribute("udf5") + " >\n"
               + "          <input name=\"pg\"  type=\"hidden\" value=" + request.getAttribute("pg") + " >\n"
                + "        <input type=\"submit\" value=\"Submit\"  />\n"
                + "    </form>\n"
                + " <script> "
                + " document.getElementById(\"payuform\").submit(); "
                + " </script> "
                + "       </div>   "
                + "  \n"
                + "  </body>\n"
                + "</html>";
// return response
        writer.println(htmlResponse);
    
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
