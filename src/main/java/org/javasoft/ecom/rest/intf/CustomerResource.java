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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.javasoft.ecom.entity.Customer;

/**
 *
 * @author ayojava
 */
@Path("/customers")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface CustomerResource {
    
    @GET
    @Path("/all")
    public List<Customer> getAllCustomers();
    
    @GET
    @Path("/regNo/{customerNo}")
    public Customer getCustomerByRegNo(@PathParam("customerNo") String customerNo);

    @GET
    @Path("/firstName/{name}")
    public List<Customer> getCustomersByFirstName(@PathParam("name") String firstName);
    
    @GET
    @Path("/lastName/{name}")
    public List<Customer> getCustomersByLastName(@PathParam("name") String lastName);
    
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Customer createCustomer(Customer customer);

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Customer editCustomer(Customer customer);

    @DELETE
    @Path("/customerID/{id}")
    public Response deleteCustomer(@PathParam("id") String databaseId);

}
