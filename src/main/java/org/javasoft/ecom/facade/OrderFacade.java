/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.ecom.entity.AddressTemplate;
import org.javasoft.ecom.entity.Customer;
import org.javasoft.ecom.entity.Order;
import org.javasoft.ecom.entity.Product;
import static org.javasoft.ecom.intf.PageResource.COMPLETE;
import static org.javasoft.ecom.intf.PageResource.ORDERED;
import static org.javasoft.ecom.intf.PageResource.SOLD;


/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class OrderFacade extends AbstractFacade<Order>{

    @EJB
    public CustomerFacade customerFacade;
    
    @EJB
    public ProductFacade productFacade ;
    
    public OrderFacade() {
        super(Order.class);
    }
    
    public List<Order> getAllOrders() {
        Query query = getEntityManager().createNamedQuery("findAllOrders");
        return query.getResultList();
    }
    
//    public List<Order> getAllOrdersByCustomer(String customerId) {
//        Query query = getEntityManager().createNamedQuery("findAllOrders");
//        List<Order> allOrders = query.getResultList();
//        List<Order> customerOrders = new ArrayList<>();
//        for (Order aOrder : allOrders){      
//            if(StringUtils.equals(aOrder.getCustomer().getCustomerId(),customerId)){
//                customerOrders.add(aOrder);       
//            }
//        }
//        return customerOrders;
//    }
//    public List<Order> getAllOrdersByCustomer(String customerId) {
//        String nativeQry = "{$query :{customer_customerId  : '" + customerId +"'}}";
//        Query query = getEntityManager().createNativeQuery(nativeQry,Order.class);
//        return query.getResultList();
//    }
    
    public void deleteOrdersByCustomer(String customerId) {
        /* NOT WORKING 
        String jpaQry = "Select o from Order o where o.customer.customerId = :customerId";
        Query qry = getEntityManager().createQuery(jpaQry);
        qry.setParameter("customerId", customerId);
        */
        String nativeQry = "{$query :{customer_customerId  : '" + customerId +"'}}";
        Query query = getEntityManager().createNativeQuery(nativeQry,Order.class);
        List<Order> customersOrders = query.getResultList();
        for(Order orderEntity : customersOrders){
            getEntityManager().remove(orderEntity);
        }
    }
    
    public void createOrder(String customerID,List<String> productIDs, Order order){
        Customer customer = customerFacade.find(customerID);
        List<Product> products = new ArrayList<>();
        double totalAmount = 0.0;
        for(String productID : productIDs){
            Product aProduct = productFacade.find(productID);
            totalAmount= totalAmount + aProduct.getPrice();
            aProduct.setStatus(ORDERED);
            products.add(aProduct);
        }
        order.setCustomer(customer);
        order.setProducts(products);
        order.setSubtotal(totalAmount);
        Order entity = getEntityManager().merge(order);
//        customer.getCustomerOders().add(entity);
//        customerFacade.editCustomer(customer);    
    }
    
    public void processOrder(AddressTemplate addressTemplate,String orderID){
        Order orderObj = find(orderID);
        for(Product product : orderObj.getProducts()){
            product.setStatus(SOLD);
        }
        orderObj.setShippingAddress(addressTemplate);
        orderObj.setStatus(COMPLETE);
        orderObj.setPaymentDate(new Date());
        getEntityManager().merge(orderObj);
    }
    
    public void cancelOrder(String orderID){
        Order orderObj = find(orderID);
//        for(Product product : orderObj.getProducts()){
//            product.setStatus(AVAILABLE);
//            productFacade.edit(product);
//        }
        getEntityManager().remove(orderObj);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public Order createOrder_(String customerNo,String streetDesc ,String city ,String state ,String zipCode
            ,String country,List<String> productCodes){
                
        Customer customer = customerFacade.getCustomerByRegNo(customerNo);
        
        AddressTemplate addressTemplate = new AddressTemplate();
        addressTemplate.setCity(city);
        addressTemplate.setCountry(country);
        addressTemplate.setState(state);
        addressTemplate.setStreetDesc(streetDesc);
        addressTemplate.setZipCode(zipCode);
        
        double totalAmount = 0.0;
        
        List<Product> orderedProducts = new ArrayList<>();
        for(String productCode : productCodes){
            Product orderedProduct = productFacade.findProductByPdtCode(productCode);
            totalAmount = totalAmount + orderedProduct.getPrice();
            orderedProducts.add(orderedProduct);
        }
        
        Order orderObj = new Order();
        orderObj.setCustomer(customer);
        orderObj.setShippingAddress(addressTemplate);
        orderObj.setProducts(orderedProducts);
        orderObj.setSubtotal(totalAmount);
        orderObj.setStatus("BOOKED");
        
        return getEntityManager().merge(orderObj);
    }
}
