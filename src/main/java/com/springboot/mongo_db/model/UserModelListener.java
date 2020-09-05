//package com.springboot.mongo_db.model;
//
//import com.springboot.mongo_db.service.SequenceGeneratorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
//import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserModelListener extends AbstractMongoEventListener<User> {
//
//    private SequenceGeneratorService sequenceGenerator;
//
//    @Autowired
//    public UserModelListener(SequenceGeneratorService sequenceGenerator) {
//        this.sequenceGenerator = sequenceGenerator;
//    }
//
//    @Override
//    public void onBeforeConvert(BeforeConvertEvent<User> event) {
//        if (event.getSource().getIdUser() < 1) {
//            event.getSource().setIdUser(sequenceGenerator.generateSequence(User.SEQUENCE_NAME));
//        }
//    }
//
//
//}
