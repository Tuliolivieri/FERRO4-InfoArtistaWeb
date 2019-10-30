/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Aluno
 * 
 * //// CONSULTA CANTOR
 * http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=Name&api_key=917a49b4c9325caecc0bec862aae4397
 * 
 * /// CONSULTA TOP 10
 * http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&limit=10&api_key=917a49b4c9325caecc0bec862aae4397
 */
@WebServlet(name = "BuscaArtista", urlPatterns = {"/BuscaArtista"})
public class BuscaArtista extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws javax.xml.parsers.ParserConfigurationException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document doc = null;

        String artista = request.getParameter("nomeArtista");
        String consulta = "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + artista + "&api_key=917a49b4c9325caecc0bec862aae4397";
        try 
        {
            factory=DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = (Document) builder.parse(new InputSource(new StringReader(consulta)));
            System.out.println("MFOEE");
            System.out.println(doc.getText(0, 100));
        } 
        catch (ParserConfigurationException | BadLocationException| SAXException e) 
        {
            System.out.println("Erro: " + e.getMessage());
        }
        
        
        //System.out.println("");
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet BuscaArtista</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BuscaArtista at " + request.getContextPath() + "</h1>");
            out.println("<h4>" + artista + "</h4>");
            out.println("<h>" + consulta + "</h>");
            out.println("</body>");
            out.println("</html>");
        }
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
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
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
        return "Short description";
    }// </editor-fold>

}
