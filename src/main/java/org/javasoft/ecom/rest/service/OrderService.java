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
import javax.ws.rs.core.UriInfo;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.ecom.entity.AddressTemplate;
import org.javasoft.ecom.entity.Order;
import org.javasoft.ecom.facade.OrderFacade;
import org.javasoft.ecom.rest.intf.OrderResource;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class OrderService implements OrderResource {
    
    @EJB
    public OrderFacade orderFacade;

    @Override
    public Response createOrder(Order order, UriInfo uriInfo) {
        try {
            String customerID = uriInfo.getQueryParameters().getFirst("customerID");
            List<String> productIDs = uriInfo.getQueryParameters().get("productIDs");
            log.info("ProductIDs :::::: {}", uriInfo.getQueryParameters().get("productIDs"));
            orderFacade.createOrder(customerID, productIDs, order);
        } catch (Exception ex) {
            log.error("An Exception has occured :: {}", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An Exception has occured").build();
        }
        return Response.status(Response.Status.OK).entity("Order ID ").build();
    }

    @Override
    public Response processOrder(AddressTemplate addressTemplate, UriInfo uriInfo) {
        try{
            String orderID = uriInfo.getQueryParameters().getFirst("orderID");
            orderFacade.processOrder(addressTemplate, orderID);
        }catch (Exception ex) {
            log.error("An Exception has occured :: {}", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An Exception has occured").build();
        }
        return Response.status(Response.Status.OK).entity("Order Processed Successfully ").build();
    }
    
    @Override
    public Response cancelOrder(String orderID) {
        try{
            orderFacade.cancelOrder(orderID);
        }catch (Exception ex) {
            log.error("An Exception has occured :: {}", ex);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An Exception has occured").build();
        }
        return Response.status(Response.Status.GONE).entity("Order Deleted Successfully ").build();
    }
    
    @Override
    public List<Order> getAllOrders() {
        return orderFacade.getAllOrders();
    }

//    @Override
//    public List<Order> getAllCustomerOrders(String customerID) {
//        return orderFacade.getAllOrdersByCustomer(customerID);
//    }

    

}

//http://www.mkyong.com/webservices/jax-rs/jax-rs-queryparam-example/

//https://examples.javacodegeeks.com/enterprise-java/rest/jersey/jax-rs-queryparam-example/
