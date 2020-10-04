package com.springboot.mongo_db.controller;

import com.springboot.mongo_db.model.JsonCall;
import com.springboot.mongo_db.model.User;
import com.springboot.mongo_db.Game.ControlGame;
import com.springboot.mongo_db.Game.VerificarDatos;
import com.springboot.mongo_db.repository.IUserMongoRepository;
import com.springboot.mongo_db.service.IUserMongoService;
import com.springboot.mongo_db.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/joc_daus")
public class UserMongoController {
    @Autowired
    private SequenceGeneratorService sequenceGenerator;


    @Qualifier("IUserMongoRepository")
    @Autowired
    private IUserMongoRepository iUserMongoRepository;
    private ControlGame controlGame = new ControlGame();
    VerificarDatos verificarDatos = new VerificarDatos();
    private HashMap<String, Object> map = new HashMap<>();

    @GetMapping("/list")
    public ResponseEntity<List<User>> llistar (){
        List<User> userList = iUserMongoRepository.findAll();
        if (userList == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userList);
        }
    }
    @PostMapping("/insert")
    public ResponseEntity<User> insert(@RequestBody JsonCall id)  {
        User user = new User();

        try {
            user.setUserName(verificarDatos.verificarUserName(id.getId()));
            user = verificarDatos.asignarValoresUser(user, "");
            user.setIdUser(sequenceGenerator.generateSequenceUser(User.SEQUENCE_NAME));
            iUserMongoRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PutMapping("/actualizar/user")
    public HashMap<String, Object> actualizar(@RequestBody JsonCall jsonCall){
        map.clear();
        map = verificarDatos.verifyIds(jsonCall.getId(), map);

        if ( map.size() == 0) {
            User user = new User();
            Optional<User> userOriginal = iUserMongoRepository.findById(Integer.parseInt(jsonCall.getId()));// obtener User original
            user = verificarDatos.editarUser(userOriginal.get(), user, jsonCall); // Solo modifica el userName
            iUserMongoRepository.save(user);
            map.put("success: ", "true");
            map.put("message: ", user);
        }
        return  map;
    }
    @DeleteMapping("/delete/id")
    public HashMap<String, Object> deleteUser(@RequestBody JsonCall id){
        map.clear();
        try {
            map = verificarDatos.verifyIds(id.getId(), map);

            if ( map.size() == 0) {
                if (iUserMongoRepository.findById(Integer.parseInt(id.getId())).isPresent()) {
                    // no se por que da problemas.. si no existe no salta error
                    iUserMongoRepository.deleteById(Integer.parseInt(id.getId()));
                    map.put("success: ", "true");
                    map.put("message: ", "deleted user");
                }else{
                    map.put("message: ", "Not exists ID");
                    map.put("success: ", HttpStatus.BAD_REQUEST);
                }
            }
        }catch (Exception e){
            map.put("message: ", "Algo salió mal");
            map.put("success: ", HttpStatus.BAD_REQUEST);
        }
        return map;
    }

    @GetMapping("/rankingmigtotal")
    public HashMap<String, Object> listRanking (){
        map.clear();
        try {
            List<User> userList = iUserMongoRepository.findAll();
            int percentatge = controlGame.calcularRankingTotal(userList);
            map.put("success: ", "true");
            map.put("percentatge: ", percentatge);
        }catch (Exception e){
            map.put("message: ", "algo salio mal");
            map.put("success: ", HttpStatus.BAD_REQUEST);
        }
        return  map;
    }
    @GetMapping("/rankingloser")
    public HashMap<String, Object> rankingLoser (){
        map.clear();
        try{
            List<User> userList = iUserMongoRepository.findAll();
            User  user = controlGame.rankingLoserWinner(userList, "min");
            map.put("success: ", "true");
            map.put("percentatge: ", user);
        }catch (Exception e){
            map.put("message: ", "algo salio mal");
            map.put("success: ", HttpStatus.BAD_REQUEST);
        }
        return  map;
    }
    @GetMapping("/rankingwinner")
    public HashMap<String, Object> rankingWinner (){
        map.clear();
        try {
            List<User> userList = iUserMongoRepository.findAll();
            User user = controlGame.rankingLoserWinner(userList, "max");
            map.put("success: ", "true");
            map.put("percentatge: ", user);
        }catch (Exception e){
            map.put("message: ", "algo salio mal");
            map.put("success: ", HttpStatus.BAD_REQUEST);
        }
        return  map;
    }
}
