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
public interface PageResource {
    
    public static final String DISPLAY_DATE_FORMAT_DAYS = "dd-MM-yyyy";
    
    public static final String DEFAULT_INCLUDE_PATH = "/WEB-INF/includes/";
    
    public static final String DEFAULT_PAGE = "welcome";

    public static final String PAGE_EXTENSION = ".xhtml";
    
    public static final String AVAILABLE = "Available";
    
    public static final String ORDERED = "Ordered";
    
    public static final String SOLD = "Sold";
    
    public static final String PENDING="Pending";
    
    public static final String COMPLETE="Complete";
    
    /*=========== CUSTOMERS ===========*/
    public static final String LIST_CUSTOMERS = "list-customers";
    
    public static final String VIEW_CUSTOMER = "view-customer";
    
    public static final String EDIT_CUSTOMER = "edit-customer";
    
    public static final String NEW_CUSTOMER = "new-customer";
    
    public static final String DELETE_CUSTOMER = "delete-customer";
    
    /*=========== CATEGORIES ==========*/
    
    public static final String LIST_CATEGORIES = "list-categories";
    
    public static final String VIEW_CATEGORY = "view-category";
       
    public static final String NEW_CATEGORY = "new-category";
    
    /*=========== PRODUCTS ==========*/
    
    public static final String LIST_PRODUCTS = "list-products";
    
    public static final String VIEW_PRODUCT= "view-product";
       
    public static final String NEW_PRODUCT = "new-product";
    
    public static final String EDIT_PRODUCT = "edit-product";
    
    public static final String DELETE_PRODUCT = "delete-product";
    
    /*=========== ORDERS ==========*/
    
    public static final String LIST_ORDERS = "list-orders";
    
    public static final String VIEW_ORDER= "view-order";
       
    public static final String NEW_ORDER = "new-order";
    
    public static final String EDIT_ORDER = "edit-order";
    
    public static final String DELETE_ORDER = "delete-order";
    
     /*=========== COMMENTS ==========*/
    
    public static final String NEW_COMMENT = "new-comment";
    
    void setPageResource(String pageResource);

    String getPageResource();
}
