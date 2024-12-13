package com.voicemate.translationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.voicemate.translationservice.service.RateLimiter;
import com.voicemate.translationservice.service.TranslationalService;
import com.voicemate.translationservicecommon.Result;

@RestController
@RequestMapping(value = "/translate")
public class TranslationController {

	@Autowired
	private TranslationalService translationalService;

	@Autowired
	private RateLimiter rateLimiter;

	@GetMapping("/withoutsourcelang")
	public ResponseEntity<?> translate(@RequestParam String userId, @RequestParam String text,
			@RequestParam String targetLang) throws Exception {
		try {

			/*
			 * AFTER IMPLEMENT DELETE THIS. you could implement a retry mechanism where
			 * users automatically try again after a brief delay or use a queuing mechanism
			 * to process requests later. For example, instead of a direct rejection, a
			 * "please try again in a minute" message can be provided.
			 */

			// Check if the user is allowed to make the request (rate limiting)
			if (!rateLimiter.isAllowed(userId)) {
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
						"Rate limit exceeded. Please try again later.");
			}

			String sourceLang = translationalService.detectLanguage(text);
			Result result = translationalService.translateText(text, sourceLang, targetLang);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
	}

	@GetMapping("/withsourcelang")
	public ResponseEntity<?> translate(@RequestParam String userId, @RequestParam String text,
			@RequestParam String sourceLang, @RequestParam String targetLang) throws Exception {
		try {

			/*
			 * AFTER IMPLEMENT DELETE THIS. you could implement a retry mechanism where
			 * users automatically try again after a brief delay or use a queuing mechanism
			 * to process requests later. For example, instead of a direct rejection, a
			 * "please try again in a minute" message can be provided.
			 */

			// Check if the user is allowed to make the request (rate limiting)
			if (!rateLimiter.isAllowed(userId)) {
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
						"Rate limit exceeded. Please try again later.");
			}

			Result result = translationalService.translateText(text, sourceLang, targetLang);
			return new ResponseEntity<Result>(result, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
		}
	}
}
