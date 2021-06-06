/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.controllers;

import com.ecommerce.common.Constant;
import com.ecommerce.common.ErrorDefinition;
import com.ecommerce.entities.RestResponseEntity;
import com.ecommerce.model.data.mysql.Product;
import com.ecommerce.model.data.neo4j.NodeBrand;
import com.ecommerce.model.data.neo4j.NodeCategory;
import com.ecommerce.model.data.neo4j.NodeProduct;
import com.ecommerce.model.data.neo4j.NodeUser;
import com.ecommerce.model.data.neo4j.ProductRepository;
import com.ecommerce.model.data.neo4j.UserRepositoy;
import com.ecommerce.model.data.redis.UserSessionRepository;
import com.ecommerce.repository.mongodb.UserRespository;
import com.ecommerce.repository.mysql.MySQLAdapter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author chungnt
 */
@RestController
public class RecommendProductController {

    @Autowired
    private UserSessionRepository sessionRepo;

    @Autowired
    private UserRepositoy userNeo4jRepo;

    @Autowired
    private ProductRepository productNeo4jRepo;

    @Autowired
    private UserRespository userMongoRepo;

    @Autowired
    private com.ecommerce.model.data.neo4j.RankingProductRecommendModel rankingNeo4j;

    @CrossOrigin
    @GetMapping("/api/recommend/product")
    public RestResponseEntity getRecommendProducts(@CookieValue(value = Constant.AUTH_ECOM_SESSION_KEY, defaultValue = "") String sessionId) {
        int error = ErrorDefinition.ERR_SUCCESS;
        Object data = null;
        int userId = 1;

//        if (StringUtils.isNotBlank(sessionId)) {
//            Optional<UserSession> opUs = sessionRepo.findById(sessionId);
//            if (opUs.isPresent()) {
//                int userId = opUs.get().getUserId();
//
//                // get list productId recommend
//                // build list productItem
//                // set data response
//            } else {
//                error = ErrorDefinition.ERR_NO_SESSION;
//            }
//        } else {
//            error = ErrorDefinition.ERR_NO_SESSION;
//        }
        data = getListRecommendsProduct(userId, 10);
        RestResponseEntity resp = new RestResponseEntity(error, data);
        return resp;
    }

    public List<Product> getListRecommendsProduct(int userId, int nItems) {
        List<Product> productResult = null;
        List<NodeProduct> recommendProducts = productNeo4jRepo.getListRecommenedProducts(userId, nItems);
        if (recommendProducts != null && !recommendProducts.isEmpty()) {
            List<Long> productIds = recommendProducts.stream().map(item -> item.getProductId()).collect(Collectors.toList());
            productResult = MySQLAdapter.INSTANCE.multiGetProducts(productIds);
        }
        return productResult;
    }

    @CrossOrigin
    @GetMapping("/api/recommend/view")
    public RestResponseEntity viewProduct(@RequestParam int userId, @RequestParam long productId) {
        int error = ErrorDefinition.ERR_SUCCESS;
        NodeUser nodeUser = null;
        if (userId > 0 && productId > 0) {
//            nodeUser = userNeo4jRepo.viewProduct(userId, productId);
            rankingNeo4j.rankingCountViewProduct(userId, productId);
        }
        RestResponseEntity resp = new RestResponseEntity(error, nodeUser);
        return resp;
    }

}
