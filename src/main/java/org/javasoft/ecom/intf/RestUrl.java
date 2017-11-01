/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.intf;

/**
 *
 * @author ayojava
 */
public interface RestUrl {
    
//    public final String CATEGORY_URL = "https://ecom-emsproj.rhcloud.com/rest/categories/";
//    
//    public final String PRODUCT_URL = "https://ecom-emsproj.rhcloud.com/rest/products/";
//    
//    public final String CUSTOMER_URL = "https://ecom-emsproj.rhcloud.com/rest/customers/";
//    
//    public final String ORDER_URL = "https://ecom-emsproj.rhcloud.com/rest/orders/";
//    
//    public final String COMMENT_URL = "http://ecom-emsproj.rhcloud.com/rest/comments/";
//    public final String GEOLOCATION_URL = "http://ecom-emsproj.rhcloud.com/rest/geoLocation/";
    
    public final String GEOLOCATION_URL = "http://localhost:8080/ecom/rest/geoLocation/";
    
    public final String FREEGEOIP_URL = "http://freegeoip.net/";
    
    public final String CATEGORY_URL = "http://localhost:8080/ecom/rest/categories/";
    
    public final String PRODUCT_URL = "http://localhost:8080/ecom/rest/products/";
    
    public final String CUSTOMER_URL = "http://localhost:8080/ecom/rest/customers/";
    
    public final String ORDER_URL = "http://localhost:8080/ecom/rest/orders/";
    
    public final String COMMENT_URL = "http://localhost:8080/ecom/rest/comments/";
}
