package com.gbuxss.journalApp.repository;

import com.gbuxss.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    User  findByUserName(String userName);
    User deleteByUserName(String userName);
}
