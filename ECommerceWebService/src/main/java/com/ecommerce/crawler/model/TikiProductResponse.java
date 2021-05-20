package com.ecommerce.crawler.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;

/**
 *
 * @author ngoclt2
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TikiProductResponse extends BaseModel {

    public List<TikiProduct> data;

    public List<TikiProduct> getData() {
        return data;
    }

    public void setData(List<TikiProduct> data) {
        this.data = data;
    }

}
