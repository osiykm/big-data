package com.osiykm.labs.bigdata;

import org.springframework.data.mongodb.repository.MongoRepository;

/***
 * @author osiykm
 * created 22.10.2017 23:31
 */
public interface UsersDAO extends MongoRepository<User, String> {
}
