package com.springboot.mongo_db.controller;


import com.springboot.mongo_db.model.Plays;
import com.springboot.mongo_db.model.User;
import com.springboot.mongo_db.repository.IPlaysMongoRepository;
import com.springboot.mongo_db.repository.IUserMongoRepository;
import com.springboot.mongo_db.service.PlaysMongoService;
import com.springboot.mongo_db.service.SequenceGeneratorService;
import com.springboot.mongo_db.service.UserMongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/joc_daus")
public class PlaysController {

    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @Qualifier("IPlaysMongoRepository")
    @Autowired
    private IPlaysMongoRepository iPlaysMongoRepository;

    @Qualifier("IUserMongoRepository")
    @Autowired
    private IUserMongoRepository iUserMongoRepository;

    @Autowired
    PlaysMongoService playsMongoService;
    @Autowired
    UserMongoService userMongoService;
    private HashMap<String,Object> map = new HashMap<>();

    @GetMapping("/listplays/{idUser}")
    public HashMap<String, Object> llistarPlays (@PathVariable String idUser){
        map.clear();
        try {
            List<Plays> playsList = (List<Plays>) iPlaysMongoRepository.findAllByUserId(Integer.parseInt(idUser));
            if (playsList.size() == 0 ) {
                map.put("success: ", "false");
                map.put("message: ", "Plays for this player is empty or Not exists player");
            } else {
                map.put("success: ", "true");
                map.put("message: ", playsList);
            }
        }catch (Exception e){
            map.put("success: ", "false");
            map.put("message: ", "Not exists Id");
        }
        return map;
    }
    @PostMapping("rolldice/{idUser}")
    public HashMap<String,Object> rollDice(@PathVariable String idUser ) throws Exception {
        map.clear();
        try {
            User user = iUserMongoRepository.findByIdUser(Integer.parseInt(idUser));

            if (user != null) {
                Plays plays = new Plays();
                plays = playsMongoService.rollDice(plays, Integer.parseInt(idUser));
                user = userMongoService.actualizarMarcadorUser(plays, user);
                plays.setIdPlay(sequenceGenerator.generateSequenceUser(Plays.SEQUENCE_PLAYS));
                iPlaysMongoRepository.save(plays);
                iUserMongoRepository.save(user);
                map.put("success", true);
                map.put("roll dice: ", plays);

            } else {
                map.put("success", false);
                map.put("message" , "User not exist");
            }
        } catch (Exception e){
            map.put("message", "Not exists Id");
            map.put("success", false);
        }
        return map;
    }
    @DeleteMapping("/deleteplays/{userId}")
    public HashMap<String, Object> deletePlays(@PathVariable String userId){
        map.clear();
        try {
            List<Plays> listPlays = iPlaysMongoRepository.findAllByUserId(Integer.parseInt(userId));
            iPlaysMongoRepository.deleteAll(listPlays);
            // Despues de borrar partidas, reiniciar contadores a zero.
            User user = iUserMongoRepository.findByIdUser(Integer.parseInt(userId));
            user = userMongoService.asignarValoresUser(user, "delete");
            iUserMongoRepository.save(user);
            map.put("success: ", "true");
            map.put("message: ", "deleted Plays");
        }catch (Exception e){
            map.put("message: ", "Not exists Id");
            map.put("success: ", HttpStatus.BAD_REQUEST);
        }
        return  map;
    }
}
