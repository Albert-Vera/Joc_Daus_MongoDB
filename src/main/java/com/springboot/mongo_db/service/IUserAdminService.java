package com.springboot.mongo_db.service;


import com.springboot.mongo_db.model.UserAdmin;
import com.springboot.mongo_db.repository.IUserAdminRepository;
import org.springframework.stereotype.Service;

@Service
public interface IUserAdminService extends IUserAdminRepository {

    UserAdmin findByUser (String userAdmin);
}
