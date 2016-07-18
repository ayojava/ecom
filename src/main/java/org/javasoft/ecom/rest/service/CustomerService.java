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
import org.javasoft.ecom.entity.Customer;
import org.javasoft.ecom.facade.CustomerFacade;
import org.javasoft.ecom.rest.intf.CustomerResource;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class CustomerService implements CustomerResource{
    
    @EJB
    public CustomerFacade customerFacade;
    
    @Override
    public List<Customer> getAllCustomers() {
        return customerFacade.getAllCustomers();
    }

    @Override
    public Customer getCustomerByRegNo(String customerNo) {
        return customerFacade.getCustomerByRegNo(customerNo);
    }

    @Override
    public List<Customer> getCustomersByFirstName(String firstName) {
        return customerFacade.getCustomersByFirstName(firstName);
    }
    
    @Override
    public List<Customer> getCustomersByLastName(String lastName) {
        return customerFacade.getCustomersByLastName(lastName);
    }
    
    @Override
    public Customer createCustomer(Customer customer) {
        return customerFacade.createCustomer(customer);
    }

    @Override
    public Customer editCustomer(Customer customer) {
        return customerFacade.edit(customer);
    }

    @Override
    public Response deleteCustomer(String databaseId) {
        customerFacade.deleteCustomer(databaseId);
        return Response.status(Status.GONE).build();
    }
}
