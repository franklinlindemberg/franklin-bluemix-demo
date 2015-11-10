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

		String modelId = "pt-en";
		String text = req.getParameter("text");

		//create the {
		//	'model_id:'...',
		//  'text': } json as requested by the service
		JSONObject postData = new JSONObject();
		postData.put("model_id",modelId);
		postData.put("text",text);

    	try {
    		Executor executor = Executor.newInstance().auth(username, password);
    		URI serviceURI = new URI(baseURL+ "/v2/translate").normalize();
    		String answersJson = executor.execute(Request.Post(serviceURI)
			    .addHeader("Accept", "application/json")
			    .addHeader("X-SyncTimeout", "30")
			    .bodyString(postData.toString(), ContentType.APPLICATION_JSON)
			    ).returnContent().asString();

    		ObjectMapper mapper = new ObjectMapper();
    		LT answer =mapper.readValue(answersJson, LT.class);
    		
    		System.out.println(text);
    		
			//Send question and answers to index.jsp
			req.setAttribute("answers", answer);
			req.setAttribute("text", text);
			
			

		} catch (Exception e) {
			// Log something and return an error message
			logger.log(Level.SEVERE, "got error: "+e.getMessage(), e);
			req.setAttribute("error", e.getMessage());
		}

    	req.getRequestDispatcher("/watson_api/lt.jsp").forward(req, resp);
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
}