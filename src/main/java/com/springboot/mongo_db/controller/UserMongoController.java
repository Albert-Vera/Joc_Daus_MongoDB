package com.springboot.mongo_db.controller;

import com.springboot.mongo_db.model.JsonCall;
import com.springboot.mongo_db.model.User;
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
import java.util.Optional;

@RestController
@RequestMapping("/joc_daus")
public class UserMongoController {
    @Autowired
    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    UserMongoService userMongoService;
    @Autowired
    PlaysMongoService playsMongoService;

    @Qualifier("IUserMongoRepository")
    @Autowired
    private IUserMongoRepository iUserMongoRepository;

    private HashMap<String, Object> map = new HashMap<>();

    @GetMapping("/list")
    public HashMap<String, Object> llistar (){
        map.clear();

        List<User> userList = iUserMongoRepository.findAll();
        if (userList == null) {
            map.put("success: ", "false");
            map.put("message: ", "Database is empty");
        } else {
            map.put("success: ", "true");
            map.put("message: ", userList);
        }
        return map;
    }
    @PostMapping("/insert")
    public HashMap<String, Object> insert(@RequestBody JsonCall id)  {
        map.clear();
        User user = new User();

        try {
            user.setUserName(userMongoService.verificarUserName(id.getId()));
            user = userMongoService.asignarValoresUser(user, "");
            user.setIdUser(sequenceGenerator.generateSequenceUser(User.SEQUENCE_NAME));
            iUserMongoRepository.save(user);
            map.put("success: ", "true");
            map.put("message: ", user);
        }catch (Exception e){
            map.put("success: ", "false");
            map.put("message: ", "Algo ocurrio...");
        }
        return map;
    }

    @PutMapping("/actualizar/user")
    public HashMap<String, Object> actualizar(@RequestBody JsonCall jsonCall){
        map.clear();
        map = userMongoService.verifyIds(jsonCall.getId(), map);

        if ( map.size() == 0) {
            User user = new User();
            Optional<User> userOriginal = iUserMongoRepository.findById(Integer.parseInt(jsonCall.getId()));// obtener User original
            user = userMongoService.editarUser(userOriginal.get(), user, jsonCall); // Solo modifica el userName
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
            map = userMongoService.verifyIds(id.getId(), map);

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
            int percentatge = userMongoService.calcularRankingTotal(userList);
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
            User  user = userMongoService.rankingLoserWinner(userList, "min");
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
            User user = userMongoService.rankingLoserWinner(userList, "max");
            map.put("success: ", "true");
            map.put("percentatge: ", user);
        }catch (Exception e){
            map.put("message: ", "algo salio mal");
            map.put("success: ", HttpStatus.BAD_REQUEST);
        }
        return  map;
    }
}
