package com.techelevator.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.techelevator.model.CatFact;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class RestCatFactService implements CatFactService {

	private final String CATFACTAPIURL = "https://cat-data.netlify.app/api/facts/random";
	private RestTemplate restTemplate = new RestTemplate();


	@Override
	public CatFact getFact() {
		// TODO Auto-generated method stub
		try {
			CatFact catFact = restTemplate.getForObject(CATFACTAPIURL, CatFact.class); //use restTemp to make GET request to url
			return catFact;
	}	catch (RestClientException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
		}
}
}
