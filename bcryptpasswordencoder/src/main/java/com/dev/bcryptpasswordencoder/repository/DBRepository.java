package com.dev.bcryptpasswordencoder.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dev.bcryptpasswordencoder.pojo.User;

@Repository
public interface DBRepository extends CrudRepository<User, String> {

}
