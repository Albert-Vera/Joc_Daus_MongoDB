package com.springboot.mongo_db.repository;


import com.springboot.mongo_db.model.UserAdmin;
import org.springframework.data.repository.CrudRepository;

public interface IUserAdminRepository extends CrudRepository<UserAdmin, Integer> {
    UserAdmin findByUser(String userAdmin);
}
