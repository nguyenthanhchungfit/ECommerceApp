package com.ecommerce.repository.mongodb;

import com.ecommerce.model.data.mongodb.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ngoclt2
 */
@Repository
public class UserReposistoryCustomImpl implements UserRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    public int getMaxUserId() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.DESC, "id"));
        query.limit(1);
        User maxObject = mongoTemplate.findOne(query, User.class);
        if (maxObject == null) {
            return 0;
        }
        return maxObject.getId();
    }

}
