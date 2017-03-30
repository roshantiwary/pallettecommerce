/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallette.payment.payu;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
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
//        request.setAttribute("key", "rjQUPktU");
//        request.setAttribute("amount", 1);
//        request.setAttribute("firstname", "Roshan");
//        request.setAttribute("email", "roshantiwary@gmail.com");
//        request.setAttribute("phone", "1234567890");
//        request.setAttribute("productinfo", "test");
//        request.setAttribute("surl", "http://www.palletteapart.com");
//        request.setAttribute("furl", "http://www.pareeksamaj.net");
//        request.setAttribute("service_provider", "payu_paisa");
        Map<String, String> values = integrationKit.hashCalMethod(request, response);
        PrintWriter writer = response.getWriter();
// build HTML code
        String htmlResponse = "<html> <body> \n"
                + "        <form id=\"payuform\" action=\"" + values.get("action") + "\"  name=\"payuform\" method=POST >\n"
                + "      <input type=\"hidden\" name=\"key\" value=" + values.get("key").trim() + ">"
                + "      <input type=\"hidden\" name=\"hash\" value=" + values.get("hash").trim() + ">"
                + "      <input type=\"hidden\" name=\"txnid\" value=" + values.get("txnid").trim() + ">"
                + "      <input type=\"hidden\" name=\"amount\" value=" + values.get("amount").trim() + " />\n"
                + "      <input type=\"hidden\"  name=\"firstname\" id=\"firstname\" value=" + values.get("firstname").trim() + " />\n"
                + "      <input type=\"hidden\" name=\"email\" id=\"email\" value=" + values.get("email").trim() + " />\n"
                + "      <input  type=\"hidden\" name=\"phone\" value=" + values.get("phone") + " >\n"
                + "		<input  type=\"hidden\" name=\"productinfo\" value=" + values.get("productinfo").trim() + " >\n"
                + "      <input  type=\"hidden\" name=\"surl\"  size=\"64\" value=" + values.get("surl") + ">\n"
                + "      <input  type=\"hidden\" name=\"furl\" value=" + values.get("furl") + " size=\"64\" >\n"
                + "      <input type=\"hidden\" name=\"service_provider\" value=\"payu_paisa\" />\n"
                + "      <input  type=\"hidden\" name=\"lastname\" id=\"lastname\" value=" + values.get("lastname") + " >\n"
                + "       <input  type=\"hidden\" name=\"curl\" value=" + values.get("curl") + " >\n"
                + "        <input  type=\"hidden\" name=\"address1\" value=" + values.get("address1") + " >\n"
                + "       <input  type=\"hidden\" name=\"address2\" value=" + values.get("address2") + " >\n"
                + "        <input  type=\"hidden\" name=\"city\" value=" + values.get("city") + ">\n"
                + "       <input  type=\"hidden\" name=\"state\" value=" + values.get("state") + ">\n"
                + "        <input  type=\"hidden\" name=\"country\" value=" + values.get("country") + " >\n"
                + "        <input name=\"zipcode\"  type=\"hidden\" value=" + values.get("zipcode") + " >\n"
                + "        <input name=\"udf1\"  type=\"hidden\" value=" + values.get("udf1") + ">\n"
                + "         <input name=\"udf2\" type=\"hidden\"  value=" + values.get("udf2") + ">\n"
                + " 	<input name=\"hashString\"  type=\"hidden\" value=" + values.get("hashString") + ">\n"
                + "          <input name=\"udf3\"  type=\"hidden\" value=" + values.get("udf3") + " >\n"
                + "          <input name=\"udf4\"  type=\"hidden\" value=" + values.get("udf4") + " >\n"
               + "          <input name=\"udf5\"  type=\"hidden\" value=" + values.get("udf5") + " >\n"
               + "          <input name=\"pg\"  type=\"hidden\" value=" + values.get("pg") + " >\n"
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        processRequest(request, response);

        JavaIntegrationKit integrationKit = new JavaIntegrationKit();
        request.setAttribute("key", "rjQUPktU");
        request.setAttribute("amount", 1);
        request.setAttribute("firstname", "Roshan");
        request.setAttribute("email", "roshantiwary@gmail.com");
        request.setAttribute("phone", "1234567890");
        request.setAttribute("productinfo", "test");
        request.setAttribute("surl", "http://localhost:8080/pallette-commerce-1.0.0/success");
        request.setAttribute("furl", "http://localhost:8080/pallette-commerce-1.0.0/failure");
        request.setAttribute("service_provider", "payu_paisa");
        Map<String, String> values = integrationKit.hashCalMethod(request, response);
        PrintWriter writer = response.getWriter();
// build HTML code
        String htmlResponse = "<html> <body> \n"
                + "        <form id=\"payuform\" action=\"" + values.get("action") + "\"  name=\"payuform\" method=POST >\n"
                + "      <input type=\"hidden\" name=\"key\" value=" + values.get("key").trim() + ">"
                + "      <input type=\"hidden\" name=\"hash\" value=" + values.get("hash").trim() + ">"
                + "      <input type=\"hidden\" name=\"txnid\" value=" + values.get("txnid").trim() + ">"
                + "      <input type=\"hidden\" name=\"amount\" value=" + values.get("amount").trim() + " />\n"
                + "      <input type=\"hidden\"  name=\"firstname\" id=\"firstname\" value=" + values.get("firstname").trim() + " />\n"
                + "      <input type=\"hidden\" name=\"email\" id=\"email\" value=" + values.get("email").trim() + " />\n"
                + "      <input  type=\"hidden\" name=\"phone\" value=" + values.get("phone") + " >\n"
                + "		<input  type=\"hidden\" name=\"productinfo\" value=" + values.get("productinfo").trim() + " >\n"
                + "      <input  type=\"hidden\" name=\"surl\"  size=\"64\" value=" + values.get("surl") + ">\n"
                + "      <input  type=\"hidden\" name=\"furl\" value=" + values.get("furl") + " size=\"64\" >\n"
                + "      <input type=\"hidden\" name=\"service_provider\" value=\"payu_paisa\" />\n"
                + "      <input  type=\"hidden\" name=\"lastname\" id=\"lastname\" value=" + values.get("lastname") + " >\n"
                + "       <input  type=\"hidden\" name=\"curl\" value=" + values.get("curl") + " >\n"
                + "        <input  type=\"hidden\" name=\"address1\" value=" + values.get("address1") + " >\n"
                + "       <input  type=\"hidden\" name=\"address2\" value=" + values.get("address2") + " >\n"
                + "        <input  type=\"hidden\" name=\"city\" value=" + values.get("city") + ">\n"
                + "       <input  type=\"hidden\" name=\"state\" value=" + values.get("state") + ">\n"
                + "        <input  type=\"hidden\" name=\"country\" value=" + values.get("country") + " >\n"
                + "        <input name=\"zipcode\"  type=\"hidden\" value=" + values.get("zipcode") + " >\n"
                + "        <input name=\"udf1\"  type=\"hidden\" value=" + values.get("udf1") + ">\n"
                + "         <input name=\"udf2\" type=\"hidden\"  value=" + values.get("udf2") + ">\n"
                + " 	<input name=\"hashString\"  type=\"hidden\" value=" + values.get("hashString") + ">\n"
                + "          <input name=\"udf3\"  type=\"hidden\" value=" + values.get("udf3") + " >\n"
                + "          <input name=\"udf4\"  type=\"hidden\" value=" + values.get("udf4") + " >\n"
               + "          <input name=\"udf5\"  type=\"hidden\" value=" + values.get("udf5") + " >\n"
               + "          <input name=\"pg\"  type=\"hidden\" value=" + values.get("pg") + " >\n"
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
    }// </editor-fold>
}
