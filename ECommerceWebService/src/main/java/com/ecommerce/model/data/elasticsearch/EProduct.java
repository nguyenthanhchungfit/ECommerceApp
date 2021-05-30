/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.elasticsearch;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 *
 * @author chungnt
 */
@Getter
@Setter
@Document(indexName = "ecommerce-webapp")
public class EProduct {

    @Id
    @Field("app.product.id")
    private long id;

    @Field("app.product.name")
    private String name;

    @Field("app.product.category.id")
    private int categoryId;

    @Field("app.product.category.name")
    private String categoryName;

    @Field("app.product.brand_name")
    private String brandName;

    @Field("app.product.description")
    private String description;

    @Field("app.product.price")
    private long price;

    @Field("app.product.thumb")
    private String thumb;

    @Field("app.product.remain")
    private int remain;

    @Field("app.product.avg-rating")
    private double avgRating;

    public EProduct() {
    }

    public EProduct(long id, String name, int categoryId, String categoryName, String brandName, String description, long price, String thumb, int remain, double avgRating) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.description = description;
        this.price = price;
        this.thumb = thumb;
        this.remain = remain;
        this.avgRating = avgRating;
    }

    @Override
    public String toString() {
        return "EProduct{" + "id=" + id + ", name=" + name + ", categoryId=" + categoryId + ", categoryName=" + categoryName + ", brandName=" + brandName + ", description=" + description + ", price=" + price + ", thumb=" + thumb + ", remain=" + remain + ", avgRating=" + avgRating + '}';
    }
}
