package com.springboot.mongo_db.controller;


import com.springboot.mongo_db.model.Plays;
import com.springboot.mongo_db.model.User;
import com.springboot.mongo_db.Game.ControlGame;
import com.springboot.mongo_db.Game.VerificarDatos;
import com.springboot.mongo_db.repository.IPlaysMongoRepository;
import com.springboot.mongo_db.repository.IUserMongoRepository;
import com.springboot.mongo_db.service.IPlaysMongoService;
import com.springboot.mongo_db.service.IUserMongoService;
import com.springboot.mongo_db.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/joc_daus")
public class PlaysController {
    private ControlGame controlGame = new ControlGame();
    @Autowired
    private SequenceGeneratorService sequenceGenerator;
    @Qualifier("IPlaysMongoRepository")
    @Autowired
    private IPlaysMongoRepository iPlaysMongoRepository;
    @Qualifier("IUserMongoRepository")
    @Autowired
    private IUserMongoRepository iUserMongoRepository;
    private VerificarDatos verificarDatos = new VerificarDatos();
    private HashMap<String,Object> map = new HashMap<>();

    @GetMapping("/listplays/{idUser}")
    public ResponseEntity<List<Plays>> llistarPlays (@PathVariable int idUser){
        List<Plays> playsList = (List<Plays>) iPlaysMongoRepository.findAllByUserId(idUser);
        if (playsList == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(playsList);
        }
    }
    @PostMapping("rolldice/{idUser}")
    public HashMap<String,Object> rollDice(@PathVariable int idUser ) throws Exception {
        map.clear();
        try {
            User user = iUserMongoRepository.findByIdUser(idUser);

            if (user != null) {
                Plays plays = new Plays();
                plays = controlGame.rollDice(plays, idUser);
                user = controlGame.actualizarMarcadorUser(plays, user);
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
            map.put("message", "Error in Roll Dice");
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
            user = verificarDatos.asignarValoresUser(user, "delete");
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
