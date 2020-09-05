package com.springboot.mongo_db.controller;


import com.springboot.mongo_db.model.Plays;
import com.springboot.mongo_db.model.User;
import com.springboot.mongo_db.repository.Game.ControlGame;
import com.springboot.mongo_db.repository.Game.VerificarDatos;
import com.springboot.mongo_db.service.IPlaysMongoService;
import com.springboot.mongo_db.service.IUserMongoService;
import com.springboot.mongo_db.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/joc_daus")
public class PlaysController {
    private ControlGame controlGame = new ControlGame();
    @Autowired
    private SequenceGeneratorService sequenceGenerator;
    @Autowired
    private IPlaysMongoService iPlaysService;
//    @Qualifier("userService")
    @Autowired
    private IUserMongoService iUserService;
    private VerificarDatos verificarDatos = new VerificarDatos();

    @GetMapping("/listPlays/{idUser}")
    public ResponseEntity<List<Plays>> llistarPlays (@PathVariable int idUser){
        List<Plays> playsList = (List<Plays>) iPlaysService.findAllByUserId(idUser);
        if (playsList == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(playsList);
        }
    }
    @PostMapping("rollDice/{idUser}")
    public ResponseEntity<Plays> rollDice(@PathVariable int idUser ) throws Exception {
        List<User> userList = iUserService.findByIdUser(idUser);
        Plays plays = new Plays();
//        if (result.hasErrors()) {
//            throw new Exception("No puede haber campos vacios !" );
//        }
        plays = controlGame.rollDice(plays, idUser);
        User user =  controlGame.actualizarMarcadorUser(plays, userList);
        plays.setIdPlay(sequenceGenerator.generateSequence(Plays.SEQUENCE_PLAYS));
        iPlaysService.save(plays);
        iUserService.save(user);
        return new ResponseEntity<Plays>(plays, HttpStatus.OK);
    }
    @DeleteMapping("/deletePlays/{userId}")
    public HttpStatus deletePlays(@PathVariable int userId){
        List <Plays> listPlays = iPlaysService.findAllByUserId(userId);
        iPlaysService.deleteAll(listPlays);
        // Despues de borrar partidas, reiniciar contadores a zero.
        List<User> userList = iUserService.findByIdUser(userId);
        User user = verificarDatos.asignarValoresUser(userList.get(0), "delete");
        iUserService.save(user);
        return HttpStatus.OK;
    }
}
