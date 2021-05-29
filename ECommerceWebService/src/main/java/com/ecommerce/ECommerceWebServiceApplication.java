package com.ecommerce;

import com.ecommerce.model.data.mysql.Product;
import com.ecommerce.model.data.redis.UserSession;
import com.ecommerce.model.data.redis.UserSessionRepository;
import com.ecommerce.model.data.neo4j.*;
import com.ecommerce.model.data.mysql.ProductCategory;
import com.ecommerce.repository.mysql.MySQLAdapter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Component
@ComponentScan({"com.ecommerce"})
@EnableMongoRepositories({"com.ecommerce"})
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class})
public class ECommerceWebServiceApplication {

    @Autowired
    private com.ecommerce.model.data.neo4j.UserRepositoy userNeo4jRepo;

    @Autowired
    private com.ecommerce.model.data.neo4j.ProductRepository productNeo4jRepo;

    @Autowired
    private com.ecommerce.model.data.redis.UserSessionRepository sessionRepo;

    public static void main(String[] args) {
        SpringApplication.run(ECommerceWebServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
//            _mockInsertProductNeo4j();
//            _mockNeo4j();
//            System.out.println("firstUser: " + firstUser);
//            System.out.println("Let's inspect the beans provided by Spring Boot:");
//            String[] beanNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanNames);
//            for (String beanName : beanNames) {
//                System.out.println(beanName);
//            }
        };
    }

    private void _mockInsertProductNeo4j() {
        int page = 1;
        int nItem = 50;
        boolean isMore = true;
        do {
            System.out.println("page: " + page);
            List<ProductCategory> listProductCategory = MySQLAdapter.INSTANCE.getListProductCategory(page, nItem);
            if (listProductCategory != null && !listProductCategory.isEmpty()) {
                for (ProductCategory productCate : listProductCategory) {
                    NodeBrand nodeBrand = new NodeBrand(productCate.getProduct().getBrandName());
                    NodeCategory nodeCategory = new NodeCategory(productCate.getCategory().getCategoryId(), productCate.getCategory().getCategoryName());
                    Product product = productCate.getProduct();
                    NodeProduct nodeProduct = new NodeProduct(product.getId(), product.getRatingAvg(), nodeCategory, nodeBrand);
                    System.out.println("put: " + productNeo4jRepo.save(nodeProduct));
                }
                page++;
            } else {
                isMore = false;
            }
        } while (isMore);

    }

    private void _mockNeo4j() {
        System.out.println("****************** mockNeo4j ******************");
        NodeCategory nodeCateMobile = new NodeCategory(1789, "Mobile");
        NodeCategory nodeCateLatop = new NodeCategory(8095, "Laptop");
        NodeCategory nodeCateElectrical = new NodeCategory(1882, "Electrical");

        NodeBrand brandXiaomi = new NodeBrand("Xiaomi");
        NodeBrand brandDeerma = new NodeBrand("Deerma");
        NodeBrand brandAssus = new NodeBrand("Asus");
        NodeBrand brandLenovo = new NodeBrand("Lenovo");
        NodeBrand brandSamsung = new NodeBrand("Samsung");
        NodeBrand brandOppo = new NodeBrand("Oppo");
        NodeBrand brandOukitel = new NodeBrand("Oukitel");
        NodeBrand brandOEM = new NodeBrand("OEM");

        NodeProduct nodeProduct_65492829 = new NodeProduct(65492829, 5, nodeCateMobile, brandOukitel);
        NodeProduct nodeProduct_95994081 = new NodeProduct(95994081, 0, nodeCateElectrical, brandOEM);
        Set<NodeProduct> boughtProductsUser1 = new HashSet<>(Arrays.asList(nodeProduct_65492829, nodeProduct_95994081));

        NodeProduct nodeProduct_92072034 = new NodeProduct(92072034, 5, nodeCateLatop, brandLenovo);
        ViewRelationship viewed_user1_92072034 = new ViewRelationship("1-92072034", 2, nodeProduct_92072034);

        NodeProduct nodeProduct_92330966 = new NodeProduct(92330966, 5, nodeCateLatop, brandLenovo);
        ViewRelationship viewed_user1_92330966 = new ViewRelationship("1-92330966", 1, nodeProduct_92330966);

        NodeProduct nodeProduct_88100602 = new NodeProduct(88100602, 5, nodeCateMobile, brandXiaomi);
        ViewRelationship viewed_user1_88100602 = new ViewRelationship("1-88100602", 1, nodeProduct_88100602);

        Set<ViewRelationship> viewedRelationShipUser1 = new HashSet<>(Arrays.asList(viewed_user1_92072034, viewed_user1_92330966, viewed_user1_88100602));

        NodeUser user01 = new NodeUser(1, "chungnt", boughtProductsUser1, viewedRelationShipUser1);
        userNeo4jRepo.save(user01);

//        NodeProduct nodeProduct_74489817 = new NodeProduct(74489817, 5, nodeCateMobile, brandOppo);
//        ViewRelationship viewed_user2_74489817 = new ViewRelationship("2-74489817", 3, nodeProduct_74489817);
//
//        NodeProduct nodeProduct_83492084 = new NodeProduct(83492084, 5, nodeCateMobile, brandSamsung);
//        ViewRelationship viewed_user2_83492084 = new ViewRelationship("2-83492084", 1, nodeProduct_83492084);
//        Set<ViewRelationship> viewedRelationShipUser2 = new HashSet<>(Arrays.asList(viewed_user2_74489817, viewed_user2_83492084));
//
//        NodeUser user02 = new NodeUser(2, "ngoclt2", false, viewedRelationShipUser2);
//        userNeo4jRepo.save(user02);
    }
}
