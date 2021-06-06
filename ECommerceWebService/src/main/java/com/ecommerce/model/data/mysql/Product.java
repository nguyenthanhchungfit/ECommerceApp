package com.ecommerce.model.data.mysql;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Comparator;

/**
 *
 * @author ngoclt2
 */
public class Product {

    private long id;
    private String name;
    private int category;
    private String brandName;
    private String shortDescription;
    private long price;
    private String thumbUrl;
    private int remainQuantity;
    private double ratingAvg;

    public Product(long id, String name, int category, String brandName, String shortDescription, long price, String thumbUrl, int remainQuantity, double ratingAvg) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brandName = brandName;
        this.shortDescription = shortDescription;
        this.price = price;
        this.thumbUrl = thumbUrl;
        this.remainQuantity = remainQuantity;
        this.ratingAvg = ratingAvg;
    }

    public Product() {
    }

    @JsonProperty("id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public int getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(int remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public double getRatingAvg() {
        return ratingAvg;
    }

    public void setRatingAvg(double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", category=" + category + ", brandName=" + brandName + ", shortDescription=" + shortDescription + ", price=" + price + ", thumbUrl=" + thumbUrl + ", remainQuantity=" + remainQuantity + ", ratingAvg=" + ratingAvg + '}';
    }
}
