package com.ecommerce.repository.mongodb;

import com.ecommerce.model.data.mongodb.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author ngoclt2
 */
@Service
public interface UserRespository extends MongoRepository<User, Integer> {

    public User findById(int id);

    public User findById(String id);

    public User findByEmailAndPassword(String email, String password);

}
