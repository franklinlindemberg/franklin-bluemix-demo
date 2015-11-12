package com.ibm.Beans;

import org.codehaus.jackson.annotate.JsonProperty;

/*
 * Classe Bean do tipo TranslationsBean que serve para fazer o parse para json da resposta da API language translation
 */

 public class TranslationsBean{
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