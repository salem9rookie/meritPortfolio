package com.techelevator.controller;

import com.techelevator.dao.CatCardDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.CatCard;
import com.techelevator.model.CatFact;
import com.techelevator.model.CatPic;
import com.techelevator.services.CatFactService;
import com.techelevator.services.CatPicService;
import com.techelevator.services.RestCatFactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin
public class CatController {

    private CatCardDao catCardDao;
    private CatFactService catFactService;
    private CatPicService catPicService;

    public CatController(CatCardDao catCardDao, CatFactService catFactService, CatPicService catPicService) {
        this.catCardDao = catCardDao;
        this.catFactService = catFactService;
        this.catPicService = catPicService;
    }

    @RequestMapping(path = "/random", method = RequestMethod.GET)
    public CatCard getRandomCard() {
        CatCard newCard = new CatCard();

        newCard.setCatFact(catFactService.getFact().getText());
        newCard.setImgUrl(catPicService.getPic().getFile());

        return newCard;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List <CatCard> getCatCards() {
        List<CatCard> catCardList = new ArrayList<>();
        try {
            catCardList = catCardDao.getCatCards();
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Cat Card ID", e);
        }
        return catCardList;
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public CatCard getCatCardById(@PathVariable int id) {
        try {
            CatCard catCard = catCardDao.getCatCardById(id);
            return catCard;
        }catch(DaoException e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Cat Card ID", e);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public CatCard update(@Valid @RequestBody CatCard catCard, @PathVariable int id) {
        try {
            return catCardDao.updateCatCard(catCard);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Cat Card ID", e);
        }
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCatCardById(@PathVariable int id){
        catCardDao.deleteCatCardById(id);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public CatCard saveCatCard(@RequestBody @Valid CatCard catCard){
       List<CatCard> catCardList = new ArrayList<>();
       try{

        return catCardDao.createCatCard(catCard);
    }catch(DaoException e){
           throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot save cat card", e);
       } catch (ResponseStatusException rs){
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot save cat card", rs);
       }
}
}