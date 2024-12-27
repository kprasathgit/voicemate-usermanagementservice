package com.voicemate.translationservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.voicemate.translationservicecommon.Result;

@Service
public class TranslationalService {

	@Value("${google.cloud.translation.api.key}")
	private String apiKey;

	@Value("${google.cloud.translation.default.language}")
	private String defaultLanguage;

	private Translate translate;

	// Constructor initializes the Google Cloud Translation API service
	public TranslationalService() {

		this.translate = TranslateOptions.getDefaultInstance().getService();
	}

	// Use @PostConstruct to initialize the Translate service after dependency
	// injection
//	@PostConstruct
//	public void init() {
//		this.translate = TranslateOptions.getDefaultInstance().getService();
//	}

	/**
	 * Automatically detects the language of the text.
	 * 
	 * @param text The text whose language is to be detected.
	 * @return The detected language code.
	 */
	public String detectLanguage(String text) throws Exception {

		try {
			Detection detection = translate.detect(text);
			return detection.getLanguage();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * Translate a text from source language to target language.
	 * 
	 * @param text       The text to translate.
	 * @param sourceLang The source language code.
	 * @param targetLang The target language code.
	 * @return The translated text.
	 */
	@Cacheable(value = "translations", key = "#text + '-' + #targetLang")
	public Result translateText(String text, String sourceLang, String targetLang) throws Exception {

		try {
			Translation translation = translate.translate(text, Translate.TranslateOption.sourceLanguage(sourceLang),
					Translate.TranslateOption.targetLanguage(targetLang));
			return new Result(true, translation.getTranslatedText());

		} catch (Exception e) {
			return new Result(false, e.getMessage());
		}

	}
}
