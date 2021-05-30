package com.ecommerce.repository.mysql;

import com.ecommerce.model.data.mysql.Category;
import com.ecommerce.model.data.mysql.OrderItem;
import com.ecommerce.model.data.mysql.Product;
import com.ecommerce.model.data.mysql.ProductCategory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author ngoclt2
 */
public class MySQLAdapter {

    private static final Class CLASS = MySQLAdapter.class;
    private static final Logger logger = LogManager.getLogger(CLASS);
    private static final MySQLClient MYSQL_CLIENT;

    private static final int MAX_PRODUCT_PER_PAGE = 50;

    private static final String COL_PRODUCT_ID = "product_id";
    private static final String COL_PRODUCT_NAME = "product_name";
    private static final String COL_PRODUCT_CATEGORY = "category_id";
    private static final String COL_PRODUCT_BRAND_NAME = "brand_name";
    private static final String COL_PRODUCT_SHORT_DESC = "short_description";
    private static final String COL_PRODUCT_PRICE = "price";
    private static final String COL_PRODUCT_THUMB_URL = "thumb_url";
    private static final String COL_PRODUCT_QUANTITY = "remain_quantity";
    private static final String COL_PRODUCT_RATING_AVG = "ratingAvg";
    private static final String COL_CATEGORY_NAME = "category_name";

    private static final String COL_ORDER_ID = "invoice_id";
    private static final String COL_ORDER_USER_ID = "user_id";
    private static final String COL_ORDER_PHONE = "phone";
    private static final String COL_ORDER_ADDRESS = "address";
    private static final String COL_ORDER_TOTAL_PRICE = "total_price";
    private static final String COL_ORDER_CREATE_TIME = "create_time";

    private static final String COL_ORDER_DETAIL_QUANTITY = "quantity";
    private static final String COL_ORDER_DETAIL_PRICE = "price";

    public static MySQLAdapter INSTANCE = new MySQLAdapter();

    static {
        MYSQL_CLIENT = new MySQLClient();
    }

    public void init() {
        doPingMySQL();
    }

    protected void doPingMySQL() {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "SELECT 1";
            Statement statement = conn.createStatement();
            statement.executeQuery(sqlQuery);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
    }

    public boolean insertProduct(long id, String name, int categoryId, String brandName, String shortDescription, long price, String thumbUrl, int remainQuantity, double ratingAvg) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "insert into Product (product_id,product_name, category_id, brand_name, short_description, price, thumb_url, remain_quantity, ratingAvg) values (?,?,?,?,?,?,?,?,?)";

            PreparedStatement prepareStatement = conn.prepareStatement(sqlQuery);
            prepareStatement.setLong(1, id);
            prepareStatement.setString(2, name);
            prepareStatement.setLong(3, categoryId);
            prepareStatement.setString(4, brandName);
            prepareStatement.setString(5, shortDescription);
            prepareStatement.setLong(6, price);
            prepareStatement.setString(7, thumbUrl);
            prepareStatement.setInt(8, remainQuantity);
            prepareStatement.setDouble(9, ratingAvg);
            int result = prepareStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return false;
    }

    public int insertProduct(Product product) {
        int ret = 0;
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "insert into Product (product_id,product_name, category_id, brand_name, short_description, price, thumb_url, remain_quantity, ratingAvg) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setLong(1, product.getId());
            pstm.setString(2, product.getName());
            pstm.setLong(3, product.getCategory());
            pstm.setString(4, product.getBrandName());
            pstm.setString(5, product.getShortDescription());
            pstm.setLong(6, product.getPrice());
            pstm.setString(7, product.getThumbUrl());
            pstm.setInt(8, product.getRemainQuantity());
            pstm.setDouble(9, product.getRatingAvg());
            ret = pstm.executeUpdate();
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return ret;
    }

    public int[] insertMultiProducts(List<Product> products) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            conn.setAutoCommit(false);
            String sqlQuery = "insert into Product (product_id,product_name, category_id, brand_name, short_description, price, thumb_url, remain_quantity, ratingAvg) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            for (Product product : products) {
                pstm.setLong(1, product.getId());
                pstm.setString(2, product.getName());
                pstm.setLong(3, product.getCategory());
                pstm.setString(4, product.getBrandName());
                pstm.setString(5, product.getShortDescription());
                pstm.setLong(6, product.getPrice());
                pstm.setString(7, product.getThumbUrl());
                pstm.setInt(8, product.getRemainQuantity());
                pstm.setDouble(9, product.getRatingAvg());
                pstm.addBatch();
            }
            int[] insertRet = pstm.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
            return insertRet;
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return new int[0];
    }

    public List<Product> getAllProduct(int page, int nItems) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select * from Product limit ?,?";

            PreparedStatement prepareStatement = conn.prepareStatement(sqlQuery);
            prepareStatement.setInt(1, (page - 1) * nItems);
            prepareStatement.setInt(2, nItems);

            ResultSet resultSet = prepareStatement.executeQuery();
            List<Product> listVerificationInfo = extractListProduct(resultSet);
            return listVerificationInfo;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return null;
    }

    public int getTotalOfProducts() {
        int total = 0;
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select count(*) as count from Product";

            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            ResultSet resultSet = pstm.executeQuery();
            total = extractCount(resultSet);
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return total;
    }

    public List<Product> getListProductsByCategoryId(int categoryId, int page, int nItems) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select * from Product where category_id=? limit ?,?";

            PreparedStatement prepareStatement = conn.prepareStatement(sqlQuery);
            prepareStatement.setInt(1, categoryId);
            prepareStatement.setInt(2, (page - 1) * nItems);
            prepareStatement.setInt(3, nItems);
            ResultSet resultSet = prepareStatement.executeQuery();
            List<Product> listVerificationInfo = extractListProduct(resultSet);
            return listVerificationInfo;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return null;
    }

    public List<Product> multiGetProducts(List<Long> productIds) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String collectionStr = _buildCollection(productIds);
            String sqlQuery = "select * from Product where product_id in " + collectionStr + " order by ratingAvg desc";
            System.out.println("********* sqlQuery: " + sqlQuery);
            PreparedStatement prepareStatement = conn.prepareStatement(sqlQuery);
            ResultSet resultSet = prepareStatement.executeQuery();
            List<Product> listVerificationInfo = extractListProduct(resultSet);
            return listVerificationInfo;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return null;
    }

    private String _buildCollection(List<Long> productIds) {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(productIds.get(0));
        for (int i = 1; i < productIds.size(); i++) {
            sb.append(", ").append(productIds.get(i));
        }
        sb.append(")");
        return sb.toString();
    }

    public ProductCategory getProductCategory(long productId) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select pd.*, ct.category_name from Product as pd\n"
                + "INNER JOIN Category as ct on pd.category_id = ct.category_id"
                + "WHERE pd.product_id = ?;";

            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setLong(1, productId);
            ResultSet resultSet = pstm.executeQuery();
            List<ProductCategory> listProductCategory = extractListProductCategory(resultSet);
            if (listProductCategory != null && !listProductCategory.isEmpty()) {
                return listProductCategory.get(0);
            } else {
                return null;
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return null;
    }

    public List<ProductCategory> getListProductCategory(int page, int nItems) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select pd.*, ct.category_name from Product as pd\n"
                + "INNER JOIN Category as ct on pd.category_id = ct.category_id limit ?,?;";

            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setInt(1, (page - 1) * nItems);
            pstm.setInt(2, nItems);
            ResultSet resultSet = pstm.executeQuery();
            List<ProductCategory> listProductCategory = extractListProductCategory(resultSet);
            return listProductCategory;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return null;
    }

    public int getTotalOfProductsByCategoryId(int categoryId) {
        int total = 0;
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select count(*) as count from Product where category_id=?";

            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setInt(1, categoryId);
            ResultSet resultSet = pstm.executeQuery();
            total = extractCount(resultSet);
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return total;
    }

    public List<Product> searchProducsByName(String ssearch, int page, int nItems) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select * from Product where product_name like ? limit ?,?";

            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setString(1, "%" + ssearch + "%");
            pstm.setInt(2, page);
            pstm.setInt(3, nItems);

            ResultSet resultSet = pstm.executeQuery();
            List<Product> listVerificationInfo = extractListProduct(resultSet);
            return listVerificationInfo;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return null;
    }

    public List<Product> searchProducsByName(String ssearch) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select * from Product where product_name like ? ";

            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setString(1, "%" + ssearch + "%");

            ResultSet resultSet = pstm.executeQuery();
            List<Product> listVerificationInfo = extractListProduct(resultSet);
            return listVerificationInfo;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return null;
    }

    public int getTotalOfProductsByName(String ssearch) {
        int total = 0;
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select count(*) as count from Product where product_name like %?%";

            PreparedStatement pstm = conn.prepareStatement(sqlQuery);
            pstm.setString(1, ssearch);
            ResultSet resultSet = pstm.executeQuery();
            total = extractCount(resultSet);
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return total;
    }

    public Product getProduct(int category, int productId) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select * from Product where category_id=" + category + " and product_id=" + productId;

            PreparedStatement prepareStatement = conn.prepareStatement(sqlQuery);

            ResultSet resultSet = prepareStatement.executeQuery();
            List<Product> listProduct = extractListProduct(resultSet);
            if (listProduct != null && !listProduct.isEmpty()) {
                return listProduct.get(0);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return null;
    }

    public Product getProductById(long productId) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select * from Product where product_id=" + productId;

            PreparedStatement prepareStatement = conn.prepareStatement(sqlQuery);

            ResultSet resultSet = prepareStatement.executeQuery();
            List<Product> listProduct = extractListProduct(resultSet);
            if (listProduct != null && !listProduct.isEmpty()) {
                return listProduct.get(0);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return null;
    }

    public int findProductCateId(String type) {
        if (type == null || type.isEmpty()) {
            return 0;
        }
        switch (type) {
            case "laptop":
                return 8095;

        }
        return 0;
    }

    public List<Product> extractListProduct(ResultSet resultSet) {
        List<Product> ret = new ArrayList<>();
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(COL_PRODUCT_ID);
                    String name = resultSet.getString(COL_PRODUCT_NAME);
                    int categoryId = resultSet.getInt(COL_PRODUCT_CATEGORY);
                    String brandName = resultSet.getString(COL_PRODUCT_BRAND_NAME);
                    String shortDesc = resultSet.getString(COL_PRODUCT_SHORT_DESC);
                    long price = resultSet.getLong(COL_PRODUCT_PRICE);
                    String thumbUrl = resultSet.getString(COL_PRODUCT_THUMB_URL);
                    int quantity = resultSet.getInt(COL_PRODUCT_QUANTITY);
                    double ratingAvg = resultSet.getDouble(COL_PRODUCT_RATING_AVG);
                    ret.add(new Product(id, name, categoryId, brandName, shortDesc, price, thumbUrl, quantity, ratingAvg));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return ret;
    }

    public List<ProductCategory> extractListProductCategory(ResultSet resultSet) {
        List<ProductCategory> ret = new ArrayList<>();
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    long id = resultSet.getLong(COL_PRODUCT_ID);
                    String name = resultSet.getString(COL_PRODUCT_NAME);
                    int categoryId = resultSet.getInt(COL_PRODUCT_CATEGORY);
                    String brandName = resultSet.getString(COL_PRODUCT_BRAND_NAME);
                    String shortDesc = resultSet.getString(COL_PRODUCT_SHORT_DESC);
                    long price = resultSet.getLong(COL_PRODUCT_PRICE);
                    String thumbUrl = resultSet.getString(COL_PRODUCT_THUMB_URL);
                    int quantity = resultSet.getInt(COL_PRODUCT_QUANTITY);
                    double ratingAvg = resultSet.getDouble(COL_PRODUCT_RATING_AVG);
                    String categoryName = resultSet.getString(COL_CATEGORY_NAME);
                    Product product = new Product(id, name, categoryId, brandName, shortDesc, price, thumbUrl, quantity, ratingAvg);
                    Category category = new Category(categoryId, categoryName);
                    ret.add(new ProductCategory(product, category));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return ret;
    }

    public int extractCount(ResultSet resultSet) {
        int count = 0;
        try {
            if (resultSet != null && resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return count;
    }

    public List<OrderItem> extractListOrder(ResultSet resultSet) {
        List<OrderItem> ret = new ArrayList<>();
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    int orderId = resultSet.getInt(COL_ORDER_ID);
                    int productId = resultSet.getInt(COL_PRODUCT_ID);
                    int category = resultSet.getInt(COL_PRODUCT_CATEGORY);
                    int quantity = resultSet.getInt(COL_ORDER_DETAIL_QUANTITY);
                    int price = resultSet.getInt(COL_ORDER_DETAIL_PRICE);
                    ret.add(new OrderItem(orderId, productId, category, quantity, price));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return ret;
    }

    public boolean insertOrder(int orderId, int userId, int subTotal) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "insert into Invoice (invoice_id,user_id,phone,address,total_price,create_time) values (?,?,?,?,?,?)";

            PreparedStatement prepareStatement = conn.prepareStatement(sqlQuery);
            prepareStatement.setLong(1, orderId);
            prepareStatement.setLong(2, userId);
            prepareStatement.setString(3, null);
            prepareStatement.setString(4, null);
            prepareStatement.setInt(5, subTotal);
            prepareStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            int result = prepareStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return false;
    }

    public boolean insertOrderDetail(int orderId, int productId, int category, int quantity, int price) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "insert into Invoice_Detail (invoice_id,product_id,category_id,quantity,price) values (?,?,?,?,?)";

            PreparedStatement prepareStatement = conn.prepareStatement(sqlQuery);
            prepareStatement.setInt(1, orderId);
            prepareStatement.setInt(2, productId);
            prepareStatement.setInt(3, category);
            prepareStatement.setInt(4, quantity);
            prepareStatement.setInt(5, price);
            int result = prepareStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return false;
    }

    public int countOrder() {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select count(*) as count from Invoice";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlQuery);
            if (rs != null) {
                while (rs.next()) {
                    return rs.getInt("count");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return -1;
    }

    public List<Integer> getAllOrderIdByUserId(int userId) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select invoice_id from Invoice where user_id=" + userId;

            PreparedStatement prepareStatement = conn.prepareStatement(sqlQuery);

            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet != null) {
                List<Integer> orderIds = new ArrayList<>();
                while (resultSet.next()) {
                    int orderId = resultSet.getInt(COL_ORDER_ID);
                    if (orderId > 0) {
                        orderIds.add(orderId);
                    }
                }
                return orderIds;
            }

        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return null;
    }

    public List<OrderItem> getAllOrderItemByOrderId(int orderId) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select * from Invoice_Detail where invoice_id=" + orderId;

            PreparedStatement prepareStatement = conn.prepareStatement(sqlQuery);

            ResultSet resultSet = prepareStatement.executeQuery();
            List<OrderItem> orderItems = extractListOrder(resultSet);
            return orderItems;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return null;
    }

    public static void main(String[] args) {
        INSTANCE.doPingMySQL();
        int userid = 1111;
        int subTotal = 10;
        int orderId = 2;
//        INSTANCE.insertOrder(orderId, userid, subTotal);
//        int countOrder = INSTANCE.countOrder();
//        System.out.println("countOrder = " + countOrder);
//        System.out.println("orderId = " + INSTANCE.insertOrderDetail(2, 1, 1, 1, 1));
        System.out.println("orderId = " + INSTANCE.getAllOrderItemByOrderId(3));
        System.exit(0);
    }
}
