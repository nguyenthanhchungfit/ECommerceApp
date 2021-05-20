package com.ecommerce.model.data.mysql;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public Product(long id, String name, int category, String brandName, String shortDescription, long price, String thumbUrl, int remainQuantity) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.brandName = brandName;
        this.shortDescription = shortDescription;
        this.price = price;
        this.thumbUrl = thumbUrl;
        this.remainQuantity = remainQuantity;
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

}
