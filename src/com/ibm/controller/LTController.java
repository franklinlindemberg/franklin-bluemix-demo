package com.ibm.controller;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.map.ObjectMapper;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import com.ibm.Beans.*;

/*
 * Classe Controller que receberá as requisições da pagina lt.jsp, chamará a API language translation e redirecionará a resposta.
 */

@MultipartConfig
public class LTController extends HttpServlet {
	private static Logger logger = Logger.getLogger(LTController.class.getName());
	private static final long serialVersionUID = 1L;

	private String serviceName = "language_translation";

	// If running locally complete the variables below with the information in VCAP_SERVICES
	private String baseURL = "<service url>";
	private String username = "<service username>";
	private String password = "<service password>";

	/**
	 * Forward the request to the /watson_api/lt.jsp file
	 *
	 * @param req the req
	 * @param resp the resp
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/watson_api/lt.jsp").forward(req, resp);
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
		
		String action = req.getParameter("action");
		
		/*
		 * verifica qual ação foi solicitada:
		 * - chamada da pagina qa.jsp (traduzira o conteudo para chamar a api questions and answers)
		 * - chamada da pagina lt.jsp (apenas traduzira o texto)
		 */
		switch(action){
		case "traduzir_QA":
			
			// verifica em qual lingua se encontra o texto
			String language = getLanguage(req);
			
			System.out.println("Lingua original do texto: " + language);
			
    		switch(language){
    		// caso seja em português, o texto será traduzido para ingles e sera feito o redirecionamento para o QAController
    		case "pt":
    			LTBean textTranslated;
    			req = getTranslation(req, req.getParameter("questionText"), "pt-en");
    			textTranslated = (LTBean) req.getAttribute("answers");
    			System.out.println("Texto traduzido: " + textTranslated);
    			req.setAttribute("question", req.getParameter("questionText"));
    			req.setAttribute("questionText", textTranslated.getTranslations().get(0).getTranslation());
    			req.getRequestDispatcher("/QAController").forward(req, resp);
    			break;
    		// caso seja em ingles, apenas redireciona para o QAController
    		case "en":
    			req.setAttribute("question", req.getParameter("questionText"));
    			req.setAttribute("questionText", req.getParameter("questionText"));
    	    	req.getRequestDispatcher("/QAController").forward(req, resp);
    			break;
    		default:
    			break;
    		}
			break;
		case "traduzir":
			req = getTranslation(req, req.getParameter("text"), "pt-en");
			req.setAttribute("text", req.getParameter("text"));
	    	req.getRequestDispatcher("/watson_api/lt.jsp").forward(req, resp);
			break;
		default:
			break;
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
		processVCAP_Services();
	}
	
    /**
     * If exists, process the VCAP_SERVICES environment variable in order to get the 
     * username, password and baseURL
     */
    private void processVCAP_Services() {
    	logger.info("Processing VCAP_SERVICES");
        JSONObject sysEnv = getVcapServices();
        if (sysEnv == null) return;
        logger.info("Looking for: "+ serviceName );

        if (sysEnv.containsKey(serviceName)) {
			JSONArray services = (JSONArray)sysEnv.get(serviceName);
			JSONObject service = (JSONObject)services.get(0);
			JSONObject credentials = (JSONObject)service.get("credentials");
			baseURL = (String)credentials.get("url");
			username = (String)credentials.get("username");
			password = (String)credentials.get("password");
			logger.info("baseURL  = "+baseURL);
			logger.info("username   = "+username);
			logger.info("password = "+password);
    	} else {
        	logger.warning(serviceName + " is not available in VCAP_SERVICES, "
        			+ "please bind the service to your application");
        }
    }

    /**
     * Gets the <b>VCAP_SERVICES</b> environment variable and return it
     *  as a JSONObject.
     *
     * @return the VCAP_SERVICES as Json
     */
    private JSONObject getVcapServices() {
        String envServices = System.getenv("VCAP_SERVICES");
        if (envServices == null) return null;
        JSONObject sysEnv = null;
        try {
        	 sysEnv = JSONObject.parse(envServices);
        } catch (IOException e) {
        	// Do nothing, fall through to defaults
        	logger.log(Level.SEVERE, "Error parsing VCAP_SERVICES: "+e.getMessage(), e);
        }
        return sysEnv;
    }
    
    //metodo que realiza a tradução do texto passado como parametro
    // recebe como parametros o texto, e o depara da traducao
    private HttpServletRequest getTranslation(HttpServletRequest req, String text, String modelId){
    	ObjectMapper mapper = new ObjectMapper();
		LTBean answerLT ;
		String answersJson;
		URI serviceURI;
		Executor executor;
		
		//create the {
		//	'model_id:'...',
		//  'text': } json as requested by the service
		JSONObject postData = new JSONObject();
		postData.put("model_id",modelId);
		postData.put("text",text);

    	try {
    		executor = Executor.newInstance().auth(username, password);
    		serviceURI = new URI(baseURL+ "/v2/translate").normalize();
    		answersJson = executor.execute(Request.Post(serviceURI)
			    .addHeader("Accept", "application/json")
			    .addHeader("X-SyncTimeout", "30")
			    .bodyString(postData.toString(), ContentType.APPLICATION_JSON)
			    ).returnContent().asString();

    		// faz o parse da resposta para json
    		answerLT = mapper.readValue(answersJson, LTBean.class);
    		
			req.setAttribute("answers", answerLT);
			
		} catch (Exception e) {
			// Log something and return an error message
			logger.log(Level.SEVERE, "got error: "+e.getMessage(), e);
			req.setAttribute("error", e.getMessage());
		}

    	return req;
    }
    
    // metodo que verifica em qual lingua se encontra o texto
    private String getLanguage(HttpServletRequest req){
    	ObjectMapper mapper = new ObjectMapper();
    	LTIdentifierBean answerLAIdentifier;
		String answersJson;
		URI serviceURI;
		Executor executor;
		String language = null;
		
		String question = req.getParameter("questionText");
		
		try {
			executor = Executor.newInstance().auth(username, password);
			serviceURI = new URI(baseURL+ "/v2/identify").normalize();
    		answersJson = executor.execute(Request.Post(serviceURI)
			    .addHeader("Accept", "application/json")
			    .addHeader("X-SyncTimeout", "30")
			    .bodyString(question, ContentType.TEXT_PLAIN)
			    ).returnContent().asString();
    		
    		// faz o parse da resposta para json
    		answerLAIdentifier = mapper.readValue(answersJson, LTIdentifierBean.class);
    		
    		language = answerLAIdentifier.getLanguages().get(0).getLanguage();
    		
    		return language;
    		
		}
		catch (Exception e) {
			// Log something and return an error message
			logger.log(Level.SEVERE, "got error: "+e.getMessage(), e);
			req.setAttribute("error", e.getMessage());
		}
		return language;
    }
}