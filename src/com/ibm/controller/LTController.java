package com.ibm.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
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
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

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
		
		switch(action){
		case "traduzir_QA":
			
			String language = getLanguage(req);
			
    		switch(language){
    		case "pt":
    			LT textTranslated;
    			req = getTranslation(req, req.getParameter("questionText"), "pt-en");
    			textTranslated = (LT) req.getAttribute("answers");
    			System.out.println(textTranslated);
    			req.setAttribute("question", req.getParameter("questionText"));
    			req.setAttribute("questionText", textTranslated.getTranslations().get(0).getTranslation());
    			req.getRequestDispatcher("/QAController").forward(req, resp);
    			break;
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
    
    public static class LT{
    	@JsonProperty("word_count")
    	private Integer word_count;
    	@JsonProperty("character_count")
		private Integer character_count;
    	@JsonProperty("translations")
		private List<Translations> translations;
        
        public Integer getWord_count() {
			return word_count;
		}
		public void setWord_count(Integer word_count) {
			this.word_count = word_count;
		}
		
		public Integer getCharacter_count() {
			return character_count;
		}
		public void setCharacter_count(Integer character_count) {
			this.character_count = character_count;
		}

		public List<Translations> getTranslations() {
			return translations;
		}
		public void setTranslations(List<Translations> translations) {
			this.translations = translations;
		}
		
		@Override
        public String toString() {
                return "word_count: " + word_count + "\n character_count: " + character_count + 
                		"\n Translations: " + translations.toString();
        }
        
    }
    
    public static class Translations{
    	@JsonProperty("translation")
    	private String translation;

		public String getTranslation() {
			return translation;
		}

		public void setTranslation(String translation) {
			this.translation = translation;
		}
		
		@Override
        public String toString() {
                return "translation: " + translation;
        }
    }
    
    public static class LAIdentifier{
    	@JsonProperty("languages")
    	private List<Languages> languages;

		public List<Languages> getLanguages() {
			return languages;
		}

		public void setLanguages(List<Languages> languages) {
			this.languages = languages;
		}
    	
		@Override
        public String toString() {
                return "languages: " + languages.toString();
        }
    	
    }
    
    public static class Languages{
    	@JsonProperty("language")
    	private String language;
    	@JsonProperty("confidence")
    	private String confidence;

		
		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public String getConfidence() {
			return confidence;
		}

		public void setConfidence(String confidence) {
			this.confidence = confidence;
		}

		@Override
        public String toString() {
                return "language: " + language + " \nconfidence: " + confidence;
        }
    }
    
    private HttpServletRequest getTranslation(HttpServletRequest req, String text, String modelId){
    	ObjectMapper mapper = new ObjectMapper();
		LT answerLT;
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

    		answerLT = mapper.readValue(answersJson, LT.class);
    		
    		System.out.println(answerLT.toString());
    		
			req.setAttribute("answers", answerLT);
			
		} catch (Exception e) {
			// Log something and return an error message
			logger.log(Level.SEVERE, "got error: "+e.getMessage(), e);
			req.setAttribute("error", e.getMessage());
		}

    	return req;
    }
    
    private String getLanguage(HttpServletRequest req){
    	ObjectMapper mapper = new ObjectMapper();
    	LAIdentifier answerLAIdentifier;
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
    		
    		answerLAIdentifier = mapper.readValue(answersJson, LAIdentifier.class);
    		
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