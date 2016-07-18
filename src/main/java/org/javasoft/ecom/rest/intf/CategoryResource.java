/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.rest.intf;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.javasoft.ecom.entity.Category;

/**
 *
 * @author ayojava
 */
@Path("/categories")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface CategoryResource {
    
    @GET
    @Path("/all")
    public List<Category> getAllCategories();
    
    @GET
    @Path("/categoryId/{databaseID}")
    public Category getCategoryById(@PathParam("databaseID") String databaseID);
    
    @GET
    @Path("/categoryName/{name}")
    public String confirmCategoryName(@PathParam("name") String name);
    
//    @POST
//    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//    public Category createCategory(Category categoryObj);
//    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createCategory(Category categoryObj);
}
