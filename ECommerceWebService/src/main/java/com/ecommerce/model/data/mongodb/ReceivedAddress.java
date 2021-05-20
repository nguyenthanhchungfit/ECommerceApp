/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecommerce.model.data.mongodb;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author chungnt
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedAddress {

    private short order;
    private String province;
    private String district;
    private String ward;
    private String addrStreet;
}
