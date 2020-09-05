package com.springboot.mongo_db.repository;

import com.springboot.mongo_db.model.Plays;
import com.springboot.mongo_db.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PlaysMongoRepository implements IPlaysMongoRepository{

    @Override
    public <S extends Plays> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Plays> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Plays> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Plays> findAll() {
        return null;
    }

    @Override
    public Iterable<Plays> findAllById(Iterable<Integer> integers) {
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
    public void delete(Plays entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Plays> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Plays> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Plays> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Plays> S insert(S s) {
        return null;
    }

    @Override
    public <S extends Plays> List<S> insert(Iterable<S> iterable) {
        return null;
    }

    @Override
    public <S extends Plays> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Plays> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Plays> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Plays> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Plays> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Plays> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public List<Plays> findAllByUserId(Integer id) {
        return findAllByUserId(id);
    }
}
