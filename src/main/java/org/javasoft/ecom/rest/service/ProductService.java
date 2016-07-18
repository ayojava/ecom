/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.rest.service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.ecom.entity.Product;
import org.javasoft.ecom.facade.ProductFacade;
import org.javasoft.ecom.rest.intf.ProductResource;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class ProductService implements ProductResource {
    
    @EJB
    private ProductFacade productFacade;

    @Override
    public List<Product> getAllProducts() {
        return productFacade.getAllProducts();
    }

    @Override
    public Response createProduct(Product productObj,String productCount,String categoryId) {
    
        boolean output = productFacade.createProduct(productObj, Integer.parseInt(productCount), categoryId);
        if(output){
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An Exception has occured").build();
    }

    @Override
    public Product editProduct(Product productObj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response deleteProduct(String productID, String categoryID) {
        if(productFacade.deleteProduct(productID, categoryID)){
            return Response.status(Status.GONE).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An Exception has occured").build();
    }

    @Override
    public List<Product> getAllProductsByStatus(String status) {
        return productFacade.getProductsByStatus(status);
    }
}
