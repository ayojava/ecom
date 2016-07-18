/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.ecom.entity.AddressTemplate;
import org.javasoft.ecom.entity.Customer;
import org.javasoft.ecom.entity.Order;
import static org.javasoft.ecom.intf.RestUrl.CUSTOMER_URL;
import static org.javasoft.ecom.intf.RestUrl.ORDER_URL;
import org.omnifaces.util.Messages;


/**
 *
 * @author ayojava
 */
@Slf4j
@Named("customerPageBean")
@ViewScoped
public class CustomerPageBean extends AbstractBean<Customer> implements Serializable {

    @Getter
    private String subFolder;
    
    @Getter
    private String fullName ;
    
    @Getter
    private int totalCount ;

    @Getter
    private List<Customer> customers;
    
    @Getter
    private List<Order> orders;

    @Getter @Setter
    private Customer customer;

    private Client client;

    @Override
    @PostConstruct
    public void init() {
        subFolder = "customer/";
    }

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(NEW_CUSTOMER, pageResource)) {
            customer = new Customer();
            customer.setCustomerAddress(new AddressTemplate());
            super.setContentPath(appendFolderPath(subFolder, NEW_CUSTOMER));
        } else if (StringUtils.equals(LIST_CUSTOMERS, pageResource)) {
            loadAllCustomers();
            super.setContentPath(appendFolderPath(subFolder, LIST_CUSTOMERS));
        } else if (StringUtils.equals(VIEW_CUSTOMER, pageResource)) {
           // loadCustomerOrders();Not working 
            super.setContentPath(appendFolderPath(subFolder, VIEW_CUSTOMER));
        } else if (StringUtils.equals(EDIT_CUSTOMER, pageResource)) {
            super.setContentPath(appendFolderPath(subFolder, EDIT_CUSTOMER));
        }
    }

    private void loadCustomerOrders(){
        try {
            client = ClientBuilder.newClient();
            orders = client.target(ORDER_URL).path("customer").
                    path("{custID}").resolveTemplate("custID", customer.getCustomerId()).request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<Order>>(){ });
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
        }
    }
    
    public void loadAllCustomers() {
        try {
            client = ClientBuilder.newClient();
            customers = client.target(CUSTOMER_URL).path("all").request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<Customer>>() { });
            client.close();
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
        }
    }

    public void saveCustomer() {
        customer.setRegNo(RandomStringUtils.randomNumeric(8));
        try {
            client = ClientBuilder.newClient();
            Response response = client.target(CUSTOMER_URL).request(MediaType.APPLICATION_JSON).post(Entity.json(customer));
            int status = response.getStatus();
            log.info("Response :: {}", status);
            if (status == 200) {
                Messages.addGlobalInfo("Customer Successfully Added");
            } else {
                Messages.addGlobalError("Save Operation not successful");
            }
            response.close();
            cleanup();
            setPageResource(LIST_CUSTOMERS);
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
            Messages.addGlobalError("An Error Has occured");
        }
    }

    public String showFullName(Customer customerObj) {
        StringBuilder builder = new StringBuilder();
        builder = builder.append(customerObj.getTitle()).append(" ").append(customerObj.getFirstName()).append(" ").append(customerObj.getLastName());
        return builder.toString();
    }
    
    
    public String getGender(){
        return StringUtils.equals(customer.getGender(),"M")?"Male":"Female";
    }
    
    public void setPageResource(String pageResource, Customer customerObj) {
        customer = customerObj;
        StringBuilder builder = new StringBuilder();
        builder = builder.append(customer.getTitle()).append(" ").append(customer.getFirstName()).append(" ").append(customer.getLastName());
        fullName = builder.toString();
        setPageResource(pageResource);
    }

    public void editCustomer(){
        try {
            client = ClientBuilder.newClient();
            Response response = client.target(CUSTOMER_URL).request(MediaType.APPLICATION_JSON).put(Entity.json(customer));
            int status = response.getStatus();
            log.info("Response :: {}", status);
            if (status == 200) {
                Messages.addGlobalInfo("Customer Successfully updated");
            } else {
                Messages.addGlobalError("Update Operation not successful");
            }
            response.close();
            cleanup();
            setPageResource(LIST_CUSTOMERS);
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
            Messages.addGlobalError("An Error Has occured");
        }
    }
    
    public void deleteCustomer() {
        try {
            client = ClientBuilder.newClient();
            Response response = client.target(CUSTOMER_URL).path("customerID").path("{id}").
                    resolveTemplate("id", customer.getCustomerId()).request().delete();
            int status = response.getStatus();
                        
            if (Response.Status.GONE == response.getStatusInfo()) {
                Messages.addGlobalInfo("Customer Successfully Deleted");
            } else {
                Messages.addGlobalError("Delete Operation not successful");
            }
            response.close();
            cleanup();
            setPageResource(LIST_CUSTOMERS);
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
            Messages.addGlobalError("An Error Has occured");
        }
    }

    @PreDestroy
    public void cleanup() {
        customers = null;
        customer = null;
    }
}
//
//<p:column headerText=" Order Count " style="text-align: center;">
//                            <h:outputText value="#{customerPageBean.showOrderCount(aCustomer)}"/>
//                        </p:column>