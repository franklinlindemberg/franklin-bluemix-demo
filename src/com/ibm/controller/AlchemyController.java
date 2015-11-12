package com.ibm.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

import com.ibm.watson.alchemyapi.AlchemyAPI;

import org.w3c.dom.Document;

import java.io.*;
import java.util.logging.Level;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/*
 * Classe Controller que receberá as requisições da pagina alchemy.jsp, chamará a API alchemy e redirecionará a resposta.
 */

@MultipartConfig
public class AlchemyController extends HttpServlet {
	
	/**
	 * 
	 */
	private static Logger logger = Logger.getLogger(AlchemyController.class.getName());
	private static final long serialVersionUID = 1L;

	/**
	 * Forward the request to the /watson_api/alchemy.jsp file
	 *
	 * @param req the req
	 * @param resp the resp
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/watson_api/alchemy.jsp").forward(req, resp);
	}
	
	/**
	 * Alchemy API
	 *
	 *
	 * @param req the Http Servlet request
	 * @param resp the Http Servlet response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doPost");

		String alchemySelection = req.getParameter("alchemy_list");
		String urlSource = req.getParameter("url_source");
		String answer = null;
		ServletContext context = req.getServletContext();
		AlchemyAPI alchemyObj;
		Document doc;

	   	try {
	   		// lê a api_key da api alchemy. Necessária para realizar as chamadas a API
	   		String path = context.getRealPath("/WEB-INF/classes/keys/api_key.txt");
	   		
	   		// verifica qual tipo de chamada será feita na API
			switch(alchemySelection){
			case "entity_extraction":
				alchemyObj = AlchemyAPI.GetInstanceFromFile(path);
				doc = alchemyObj.URLGetRankedNamedEntities(urlSource);
				answer = getStringFromDocument(doc);
				break;
			case "image_tagging":
				alchemyObj = AlchemyAPI.GetInstanceFromFile(path);
				doc = alchemyObj.URLGetRankedImageKeywords(urlSource);
				answer = getStringFromDocument(doc);
				break;
			case "sentiment_analisys":
				alchemyObj = AlchemyAPI.GetInstanceFromFile(path);
				doc = alchemyObj.URLGetTextSentiment(urlSource);
				answer = getStringFromDocument(doc);
				break;
			default:
				break;
			}
   		
			// redireciona as variaveis de volta para a pagina como atributo
			req.setAttribute("answers", answer);
			req.setAttribute("site", urlSource);
			
			System.out.println("Resposta: " + answer);
			

		} catch (Exception e) {
			// Log something and return an error message
			logger.log(Level.SEVERE, "got error: "+e.getMessage(), e);
			req.setAttribute("error", e.getMessage());
		}

	   	//redirecionara de volta para a pagina alchemy.jsp
	   	req.getRequestDispatcher("/watson_api/alchemy.jsp").forward(req, resp);
	}

    // utility method
    private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}

