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
//import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Tulio
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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String artista;
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document doc = null;
        System.out.println(request.getRequestURI());
        artista = request.getParameter("nomeArtista");
        if(artista != null && !artista.equals(""))
        {
            artista = "http://ws.audioscrobbler.com/2.0/?method=artist.getinfo&artist=" + artista + "&api_key=917a49b4c9325caecc0bec862aae4397";
            try
            {
                factory = DocumentBuilderFactory.newInstance();
                builder = factory.newDocumentBuilder();
                doc = builder.parse(artista);
                
                artista = "<ul style=\"color: white; padding-left:10vw; padding-right:10vw; padding-top:5vh; font-family: Calibri, serif; font-size: 24px;\">";
                
                NodeList nodes = doc.getElementsByTagName("name");
                Element element = (Element) nodes.item(0);
                artista += "<ol style=\"font-size: 32px\">" + element.getTextContent() + "</ol>";
                
                nodes = doc.getElementsByTagName("listeners");
                element = (Element) nodes.item(0);
                artista += "<ol style=\"padding-top: 15px\"> Ouvintes: " + element.getTextContent() + "</ol>";
                
                nodes = doc.getElementsByTagName("playcount");
                element = (Element) nodes.item(0);
                artista += "<ol> Vezes tocado: " + element.getTextContent() + "</ol>";
                
                nodes = doc.getElementsByTagName("summary");
                for (int i = 0; i < nodes.getLength(); i++) 
                {
                  element = (Element) nodes.item(i);
                  artista += "<ol style=\"padding-top: 15px\">" + element.getTextContent() + "</ol>";
                }
                 artista += "</ul>";
                 
                String resp = "<html>\n" +
                        "    <head>\n"
                        + "        <title>Artistas Web</title>\n"
                        + "        <meta charset=\"UTF-8\">\n"
                        + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "        <link href=\"estilo.css\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <header>\n"
                        + "            Info Artistas\n"
                        + "        </header>\n"
                        + "        <main >\n"
                        + artista            
                        + "        </main>\n"
                        + "    </body>\n"
                        + "</html>";
                        
                response.getWriter().print(resp);
            }
            catch(Exception e)
            {
                System.out.println("Artista" + e.toString());
            }
        }
        else
        {
            artista = "http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&limit=10&api_key=917a49b4c9325caecc0bec862aae4397";
            System.out.println("TOP 100");
            try
            {
                factory = DocumentBuilderFactory.newInstance();
                builder = factory.newDocumentBuilder();
                doc = builder.parse(artista);
                artista = "<ul style=\"color: white; padding-top:30px; font-family: Calibri, serif; font-size: 24px;\">";
                NodeList nodes = doc.getElementsByTagName("name");
                for (int i = 0; i < nodes.getLength(); i++) 
                {
                  Element element = (Element) nodes.item(i);
                  artista += "<ol>" +  (i + 1) + "º - " + element.getTextContent() + "</ol>";
                }
                 artista += "</ul>";
                 
                 String resp = "<html>\n" +
                        "    <head>\n"
                        + "        <title>Artistas Web</title>\n"
                        + "        <meta charset=\"UTF-8\">\n"
                        + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "        <link href=\"estilo.css\" rel=\"stylesheet\" type=\"text/css\"/>\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <header>\n"
                        + "            Info Artistas\n"
                        + "        </header>\n"
                        + "        <main>\n"
                        + "<h4 style=\"font-family: Calibri, serif; font-weight: normal; font-size: 36px; color: white; margin-top: 30px;\">Top 10 Artistas</h4>"
                        + artista            
                        + "        </main>\n"
                        + "    </body>\n"
                        + "</html>";
                 
                response.getWriter().print(resp);
            }
            catch(Exception e)
            {
                System.out.println("Top 100" + e.toString());
            }
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
