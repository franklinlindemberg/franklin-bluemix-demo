package com.ibm.Beans;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/*
 * Classe Bean do tipo LTIdentifierBean que serve para fazer o parse para json da resposta da API language translation
 */

public  class LTIdentifierBean{
	@JsonProperty("languages")
	private List<LanguagesBean> languages;

	public List<LanguagesBean> getLanguages() {
		return languages;
	}

	public void setLanguages(List<LanguagesBean> languages) {
		this.languages = languages;
	}
	
	@Override
    public String toString() {
            return "languages: " + languages.toString();
    }
	
}