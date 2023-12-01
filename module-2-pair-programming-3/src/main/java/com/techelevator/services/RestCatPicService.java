package com.techelevator.services;

import com.techelevator.model.CatFact;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.techelevator.model.CatPic;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Component
public class RestCatPicService implements CatPicService {
	private final String CATPICAPIURL = "https://cat-data.netlify.app/api/pictures/random";
	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public CatPic getPic() {
		// TODO Auto-generated method stub
		try {
			CatPic catPic = restTemplate.getForObject(CATPICAPIURL, CatPic.class); //use restTemp to make GET request to url
			return catPic;
		}catch (RestClientException e){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
		}
	}
}