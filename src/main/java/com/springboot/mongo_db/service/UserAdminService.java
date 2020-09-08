package com.springboot.mongo_db.service;


import com.springboot.mongo_db.model.UserAdmin;
import com.springboot.mongo_db.repository.IUserAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@Service
public class UserAdminService implements IUserAdminService{

    @Qualifier("IUserAdminRepository")
    @Autowired
    IUserAdminRepository repo;

    @Override
    public <S extends UserAdmin> S save(S entity) {
        return null;
    }

    @Override
    public <S extends UserAdmin> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }



    @Override
    public Optional<UserAdmin> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<UserAdmin> findAll() {
        return null;
    }

    @Override
    public Iterable<UserAdmin> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(UserAdmin entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserAdmin> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public UserAdmin findByUser(String userAdmin) {
        return repo.findByUser(userAdmin);
    }
}
