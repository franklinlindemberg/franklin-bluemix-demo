package com.ibm.Beans;

import org.codehaus.jackson.annotate.JsonProperty;

/*
 * Classe Bean do tipo LanguagesBean que serve para fazer o parse para json da resposta da API language translation
 */

public class LanguagesBean{
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