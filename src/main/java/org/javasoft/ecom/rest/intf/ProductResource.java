/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.rest.intf;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.javasoft.ecom.entity.Product;

/**
 *
 * @author ayojava
 */
@Path("/products")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface ProductResource {

    @GET
    @Path("/all")
    public List<Product> getAllProducts();
    
    @GET
    @Path("/status/{productStatus}")
    public List<Product> getAllProductsByStatus(@PathParam("productStatus")String status);
    
    @POST
    @Path("/createProduct")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createProduct(Product productObj, @QueryParam("count")String productCount , @QueryParam("id")String categoryId);

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Product editProduct(Product productObj);

    @DELETE
    @Path("/product/{productId}/{categoryId}")
    public Response deleteProduct(@PathParam("productId") String pId ,@PathParam("categoryId") String catId );
}
