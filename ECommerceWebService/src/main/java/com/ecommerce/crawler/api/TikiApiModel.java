package com.ecommerce.crawler.api;

import com.ecommerce.crawler.model.TikiProduct;
import com.ecommerce.crawler.model.TikiProductResponse;
import com.ecommerce.repository.mysql.MySQLAdapter;
import java.util.List;
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
        for (TikiProduct product : listTikiProduct) {
            long id = product.getId();
            String name = product.getName();
            int cateId = CATEGORY_LAPTOP;
            String brandName = product.getBrandName();
            String desc = product.getShortDescription();
            long price = product.getPrice();
            String thumbUrl = product.getThumbUrl();
            int quantity = 100;
            try {
                boolean insertProduct = MySQLAdapter.INSTANCE.insertProduct(id, name, cateId, brandName, desc, price, thumbUrl, quantity);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }

    }

    public static void main(String[] args) {
        INSTANCE.insertLaptop();
    }
}
