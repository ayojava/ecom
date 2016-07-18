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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.javasoft.ecom.entity.AddressTemplate;
import org.javasoft.ecom.entity.Order;

/**
 *
 * @author ayojava
 */
@Path("/orders")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface OrderResource {
    
    @GET
    @Path("/all")
    public List<Order> getAllOrders();
    
//    @GET
//    @Path("/customer/{custID}")
//    public List<Order> getAllCustomerOrders(@PathParam("custID") String customerID);
// 
    @POST
    @Path("/createOrder")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createOrder(Order order,@Context UriInfo uriInfo);
    
    @POST
    @Path("/processOrder")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response processOrder(AddressTemplate addressTemplate,@Context UriInfo uriInfo);
    
    @DELETE
    @Path("/cancelOrder/{orderID}")
    public Response cancelOrder(@PathParam("orderID") String databaseId);
}
