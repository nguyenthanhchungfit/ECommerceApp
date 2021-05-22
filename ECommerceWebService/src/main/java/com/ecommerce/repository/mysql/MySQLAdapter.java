package com.ecommerce.repository.mysql;

import com.ecommerce.model.data.mysql.OrderItem;
import com.ecommerce.model.data.mysql.Product;
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

    public boolean insertProduct(long id, String name, int categoryId, String brandName, String shortDescription, long price, String thumbUrl, int remainQuantity) {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "insert into Product (product_id,product_name, category_id, brand_name, short_description, price, thumb_url, remain_quantity) values (?,?,?,?,?,?,?,?)";

            PreparedStatement prepareStatement = conn.prepareStatement(sqlQuery);
            prepareStatement.setLong(1, id);
            prepareStatement.setString(2, name);
            prepareStatement.setLong(3, categoryId);
            prepareStatement.setString(4, brandName);
            prepareStatement.setString(5, shortDescription);
            prepareStatement.setLong(6, price);
            prepareStatement.setString(7, thumbUrl);
            prepareStatement.setInt(8, remainQuantity);
            int result = prepareStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            MYSQL_CLIENT.releaseConnection(conn);
        }
        return false;
    }

    public List<Product> getAllProduct() {
        Connection conn = null;
        try {
            conn = MYSQL_CLIENT.getConnection();
            String sqlQuery = "select * from Product";

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
                    ret.add(new Product(id, name, categoryId, brandName, shortDesc, price, thumbUrl, quantity));
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return ret;
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
