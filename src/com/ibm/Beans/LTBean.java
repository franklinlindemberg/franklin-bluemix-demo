package com.ibm.Beans;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/*
 * Classe Bean do tipo LTBean que serve para fazer o parse para json da resposta da API language translation
 */

public class LTBean{
	@JsonProperty("word_count")
	private Integer word_count;
	@JsonProperty("character_count")
	private Integer character_count;
	@JsonProperty("translations")
	private List<TranslationsBean> translations;
    
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

	public List<TranslationsBean> getTranslations() {
		return translations;
	}
	public void setTranslations(List<TranslationsBean> translations) {
		this.translations = translations;
	}
	
	@Override
    public String toString() {
            return "word_count: " + word_count + "\n character_count: " + character_count + 
            		"\n Translations: " + translations.toString();
    }
}