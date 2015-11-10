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
	 * Translation API
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
		String ret = null;
		ServletContext context = req.getServletContext();

	   	try {
	   		String path = context.getRealPath("/WEB-INF/classes/keys/api_key.txt");
			switch(alchemySelection){
			case "entity_extraction":
				AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile(path);
				Document doc = alchemyObj.URLGetRankedNamedEntities(urlSource);
				ret = getStringFromDocument(doc);
			case "language_detection":
				break;
			case "sentiment_analisys":
				break;
			default:
				break;
			}
   		
			//Send question and answers to index.jsp
			req.setAttribute("answers", ret);
			
			System.out.println(ret);
			

		} catch (Exception e) {
			// Log something and return an error message
			logger.log(Level.SEVERE, "got error: "+e.getMessage(), e);
			req.setAttribute("error", e.getMessage());
		}

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

