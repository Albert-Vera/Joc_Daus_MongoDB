package com.springboot.mongo_db.controller;

import com.springboot.mongo_db.model.User;
import com.springboot.mongo_db.repository.Game.ControlGame;
import com.springboot.mongo_db.repository.Game.VerificarDatos;
import com.springboot.mongo_db.service.IUserMongoService;
import com.springboot.mongo_db.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/joc_daus")
public class UserMongoController {
    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @Qualifier("IUserMongoService")
    @Autowired
    private IUserMongoService iUserMongoService;
    private ControlGame controlGame = new ControlGame();
    VerificarDatos verificarDatos = new VerificarDatos();

    @GetMapping("/list")
    public ResponseEntity<List<User>> llistar (){
        List<User> userList = iUserMongoService.findAll();
        if (userList == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userList);
        }
    }
    @PostMapping("/insert")
    public ResponseEntity<User> insert(@Validated User user)  {
        // user = controlGame.asignarValoresUser(user);



        user = verificarDatos.verificarDatosUser(user);
        user = verificarDatos.asignarValoresUser(user, "");
        user.setIdUser(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
        iUserMongoService.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/actualizar/{idUser}")
    public ResponseEntity<User> actualizar(@Validated User user, @PathVariable int idUser){
        Optional<User> userOriginal = iUserMongoService.findById(idUser);// obtener User original
        user = verificarDatos.editarUser(userOriginal.get(), user); // Solo modifica el userName
        iUserMongoService.save(user);
        return  new ResponseEntity<User>(user, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{idUser}")
    public HttpStatus deleteUser(@PathVariable int idUser){
        iUserMongoService.deleteById(idUser);
        return HttpStatus.OK;
    }

    @GetMapping("/rankingMigTotal")
    public ResponseEntity<Integer> listRanking (){
        List<User> userList = iUserMongoService.findAll();
        int percentatge = controlGame.calcularRankingTotal(userList);
        return  ResponseEntity.ok(percentatge);
    }
    @GetMapping("/rankingLoser")
    public ResponseEntity<User> rankingLoser (){
        List<User> userList = iUserMongoService.findAll();
        User  user = controlGame.rankingLoserWinner(userList, "min");
        return  ResponseEntity.ok(user);
    }
    @GetMapping("/rankingWinner")
    public ResponseEntity<User> rankingWinner (){
        List<User> userList = iUserMongoService.findAll();
        User  user = controlGame.rankingLoserWinner(userList, "max");
        return  ResponseEntity.ok(user);
    }
}
