package com.ecommerce;

import com.ecommerce.controllers.IndexController;
import com.ecommerce.model.data.mongodb.UserRepository;
import com.ecommerce.model.data.mongodb.User;
import com.ecommerce.model.data.redis.UserSessionRepository;
import com.ecommerce.model.data.redis.UserSession;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
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

@SpringBootApplication

@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    DataSourceTransactionManagerAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class})
public class ECommerceWebServiceApplication {
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private UserSessionRepository sessionRepo;
    
    public static void main(String[] args) {
        SpringApplication.run(ECommerceWebServiceApplication.class, args);
    }
    
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            System.out.println("userRepo: " + userRepo);
            System.out.println("sessionRepo: " + sessionRepo);
            
//            UserSession userSession = new UserSession(System.currentTimeMillis(), System.currentTimeMillis() + 10000000, "chungnt");
//            sessionRepo.save(userSession);
//            System.out.println("getId: " + userSession.getId());
//            System.out.println("getSession: " + sessionRepo.findById(userSession.getId()));

//            User user = new User("Nguyễn Thành Chung", "0329009486", "nguyenthanhchungfit@gmail.com",
//                true, "19/06/1997", "915/15/5/8 Lê Văn Lương, Phước Kiểng, Nhà Bè, TPHCM");
//            userRepo.save(user); //           
//             System.out.println("get: " + userRepo.findByEmail("nguyenthanhchungfit@gmail.com"));
            // String[] beanNames = ctx.getBeanDefinitionNames();
            //            Arrays.sort(beanNames);
            //            for (String beanName : beanNames) {
            //                System.out.println(beanName);
            //            }
        };
    }
}
