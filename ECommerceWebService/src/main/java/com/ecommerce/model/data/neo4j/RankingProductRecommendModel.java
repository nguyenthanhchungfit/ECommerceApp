/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.neo4j;

import com.ecommerce.repository.mongodb.UserRespository;
import com.ecommerce.repository.mysql.MySQLAdapter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author chungnt
 */
@Service
public class RankingProductRecommendModel {

    private static final ThreadPoolExecutor _executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
    @Autowired
    private UserRepositoy userNeo4jRepo;

    @Autowired
    private ProductRepository productNeo4jRepo;

    @Autowired
    private UserRespository userMongoRepo;

    public void rankingCountViewProduct(int userId, long productId) {
        _executor.execute(new Runnable() {
            @Override
            public void run() {
                _updateRelationshipViewProduct(userId, productId);
            }
        });
    }

    private NodeUser _updateRelationshipViewProduct(int userId, long productId) {
        System.out.println("updateRelationShip - userId:, " + userId + ", productId: " + productId);
        NodeUser nodeUser = userNeo4jRepo.getUserById(userId);
        if (nodeUser == null) {
            // get user
            com.ecommerce.model.data.mongodb.entity.User userMongo = userMongoRepo.findById(userId);
            if (userMongo != null) {
                NodeUser newNodeUser = new NodeUser(userId, userMongo.getAccount());
                userNeo4jRepo.save(newNodeUser);
            } else {
                NodeUser newNodeUser = new NodeUser(userId, "user-generate-" + userId);
                userNeo4jRepo.save(newNodeUser);
            }
        }

        NodeProduct nodeProduct = productNeo4jRepo.getProductByProductId(productId);
        if (nodeProduct == null) {
            // get product
            com.ecommerce.model.data.mysql.ProductCategory productCate = MySQLAdapter.INSTANCE.getProductCategory(productId);

            if (productCate != null && productCate.getProduct() != null && productCate.getCategory() != null) {
                com.ecommerce.model.data.mysql.Product product = productCate.getProduct();
                com.ecommerce.model.data.mysql.Category category = productCate.getCategory();
                NodeBrand nodeBrand = new NodeBrand(product.getBrandName());
                NodeCategory nodeCategory = new NodeCategory(category.getCategoryId(), category.getCategoryName());

                NodeProduct newNodeProduct = new NodeProduct(productId, product.getRatingAvg(), nodeCategory, nodeBrand);
                newNodeProduct = productNeo4jRepo.save(newNodeProduct);
            }
            // put node product to neo4j
        }
        nodeUser = userNeo4jRepo.viewProduct(userId, productId);
        return nodeUser;
    }
}
