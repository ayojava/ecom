/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.facade;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless; 
import javax.ejb.LocalBean;
import javax.persistence.Query;
import org.javasoft.ecom.entity.Customer;

/**
 *
 * @author ayojava
 */
@Stateless
@LocalBean
public class CustomerFacade extends AbstractFacade<Customer>{
    
    @EJB
    private OrderFacade orderFacade;

    public CustomerFacade() {
        super(Customer.class);
    }
    
    public List<Customer> getAllCustomers(){
        Query query =getEntityManager().createQuery("FROM Customer");
        return query.getResultList();
    }
    
    public Customer getCustomerByRegNo(String customerNo){
        Query query =getEntityManager().createQuery("FROM Customer where regNo =:regNo");
        query.setParameter("regNo", customerNo);
        return (Customer) query.getSingleResult();
    }
    
    public List<Customer> getCustomersByFirstName(String firstName){
        Query query =getEntityManager().createQuery("FROM Customer where firstName =:firstName");
        query.setParameter("firstName", firstName);
        return query.getResultList();
    }
    
    public List<Customer> getCustomersByLastName(String lastName){
        Query query =getEntityManager().createQuery("FROM Customer where lastName =:lastName");
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }
    
    public Customer getCustomerByFullName(String firstName, String lastName) {
        Query query =getEntityManager().createQuery("FROM Customer where firstName =:firstName AND lastName =:lastName ");
        query.setParameter("firstName", firstName);
        query.setParameter("lastName", lastName);
        return (Customer) query.getSingleResult();
    }
    
    public Customer createCustomer(Customer customer) {
        return getEntityManager().merge(customer);
    }
    
    public Customer editCustomer(Customer customer) {
        return getEntityManager().merge(customer);
    }
    
    public void deleteCustomer(String databaseId) {
        orderFacade.deleteOrdersByCustomer(databaseId);
        getEntityManager().remove(find(databaseId));
    }
    /*
        Query query = em.createNativeQuery("db.ORDER.findOne({\"_id\":\"" + oid + "\"})", Order.class);
        String query1 = "db.Property.find({'value': 'value1'})";
    Query query = em.createNativeQuery(query1, Property.class);
    
    http://blog.eisele.net/2015/02/nosql-with-hibernate-ogm-part-two.html
    // Search for the hikes with a section that start from "Penzace" in MongoDB
List<Hike> hikes = entityManager.createNativeQuery("{ $query : { sections : { $elemMatch : { start: 'Penzance' } } } }", Hike.class ).getResultList();
    
    
    https://docs.mongodb.com/getting-started/shell/query/
    */
}
