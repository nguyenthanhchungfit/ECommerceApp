package com.ecommerce.crawler.api;

import com.ecommerce.crawler.model.TikiProduct;
import com.ecommerce.crawler.model.TikiProductResponse;
import com.ecommerce.model.data.mysql.Product;
import com.ecommerce.repository.mysql.MySQLAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author ngoclt2
 */
public class TikiApiModel {
    
    private static final Logger logger = LogManager.getLogger(TikiApiModel.class);
    private static final String TIKI_PRODUCT_API_URL = "https://tiki.vn/api/v2/products?limit=50";
    private static final int CATEGORY_LAPTOP = 8095;
    private static final int CATEGORY_MOBILE = 1789;
    private static final int CATEGORY_ELECTRICAL = 1882;
    public static TikiApiModel INSTANCE = new TikiApiModel();
    
    public List<TikiProduct> getListTikiProduct(int categoryId) {
        if (categoryId <= 0) {
            return null;
        }
        String url = TIKI_PRODUCT_API_URL + "&category=" + categoryId;
        RestTemplate restTemplate = new RestTemplate();
        TikiProductResponse response = restTemplate.getForObject(url, TikiProductResponse.class);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return null;
    }
    
    public List<TikiProduct> getListTikiProductByUrl(String url) {
        RestTemplate restTemplate = new RestTemplate();
        TikiProductResponse response = restTemplate.getForObject(url, TikiProductResponse.class);
        if (response != null && response.getData() != null) {
            return response.getData();
        }
        return null;
    }
    
    public void insertLaptop() {
        
        List<TikiProduct> listTikiProduct = INSTANCE.getListTikiProduct(CATEGORY_LAPTOP);
        for (TikiProduct tikiProduct : listTikiProduct) {
            Product product = TikiProduct.toProductEntity(tikiProduct);
            product.setCategory(CATEGORY_LAPTOP);
            try {
                MySQLAdapter.INSTANCE.insertProduct(product);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
        
    }
    
    public void insertWithCategory(int category) {
        List<TikiProduct> listTikiProduct = INSTANCE.getListTikiProduct(category);
        for (TikiProduct tikiProduct : listTikiProduct) {
            Product product = TikiProduct.toProductEntity(tikiProduct);
            product.setCategory(category);
            try {
                MySQLAdapter.INSTANCE.insertProduct(product);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
    
    public List<Product> getALlProduct(String baseUrl, int page, int nItemPerPages) {
        List<Product> allProducts = new ArrayList<>();
        int offset = 0;
        boolean isMore = true;
        do {
            String url = baseUrl + offset;
            List<TikiProduct> products = INSTANCE.getListTikiProductByUrl(url);
            if (products != null && !products.isEmpty()) {
                List<Product> convertProducts = products.stream()
                    .map(product -> TikiProduct.toProductEntity(product))
                    .collect(Collectors.toList());
                allProducts.addAll(convertProducts);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(TikiApiModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                isMore = false;
            }
            offset += nItemPerPages;
            if (offset >= nItemPerPages * page) {
                isMore = false;
            }
        } while (isMore);
        return allProducts;
    }
    
    public Map<Long, Product> getMapProducts(String baseUrl, int page, int nItemPerPages) {
        Map<Long, Product> mapProducts = new HashMap<>();
        int offset = 0;
        boolean isMore = true;
        do {
            String url = baseUrl + offset;
            List<TikiProduct> products = INSTANCE.getListTikiProductByUrl(url);
            if (products != null && !products.isEmpty()) {
                List<Product> convertProducts = products.stream()
                    .map(product -> TikiProduct.toProductEntity(product))
                    .collect(Collectors.toList());
                for (Product product : convertProducts) {
                    mapProducts.put(product.getId(), product);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    java.util.logging.Logger.getLogger(TikiApiModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                isMore = false;
            }
            offset += nItemPerPages;
            if (offset >= nItemPerPages * page) {
                isMore = false;
            }
        } while (isMore);
        return mapProducts;
    }
    
    public void benchMark() {
        int maxPage = 200;
        int nItemPerPage = 50;
        String baseUrl = TIKI_PRODUCT_API_URL + "&offset=";
//        List<Product> allProducts = INSTANCE.getALlProduct(baseUrl, maxOffset);
        Map<Long, Product> mapProducts = INSTANCE.getMapProducts(baseUrl, maxPage, nItemPerPage);
        List<Product> allProducts = mapProducts.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
        System.out.println("sizeProduct: " + allProducts.size());
        
        int countReq = 0;

        // test insert single
        long startTime = System.currentTimeMillis();
        for (Product product : allProducts) {
            countReq++;
            MySQLAdapter.INSTANCE.insertProduct(product);
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("totalTime: " + duration + ", countReq: " + countReq + ", avgReq: " + (duration / countReq));
        Scanner scanner = new Scanner(System.in);
        scanner.nextInt();

        // test insert batch
        countReq = 0;
        int batchSize = 20;
        long startTime2 = System.currentTimeMillis();
        List<Product> batchProducts = new ArrayList<>(batchSize);
        for (Product product : allProducts) {
            batchProducts.add(product);
            if (batchProducts.size() == batchSize) {
                countReq++;
                // Insert to mysql
                MySQLAdapter.INSTANCE.insertMultiProducts(batchProducts);
                // clear
                batchProducts.clear();
            }
        }
        
        if (!batchProducts.isEmpty()) {
            countReq++;
            // Insert to mysql
            MySQLAdapter.INSTANCE.insertMultiProducts(batchProducts);
        }
        long endTime2 = System.currentTimeMillis();
        long duration2 = endTime2 - startTime2;
        System.out.println("totalTime: " + duration2 + ", countReq: " + countReq + ", avgReq: " + (duration2 / countReq));
    }
    
    public static void main(String[] args) {
//        INSTANCE.insertLaptop();
        INSTANCE.insertWithCategory(CATEGORY_MOBILE);
        
    }
}
