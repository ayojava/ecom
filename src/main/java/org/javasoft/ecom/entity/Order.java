/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.TIMESTAMP;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author ayojava
 */
@Data
@Entity
@NoArgsConstructor
@GenericGenerator(name="UUID2Generator", strategy="uuid2")
@XmlRootElement 
@EqualsAndHashCode(of = "orderId") 
@NamedNativeQueries({
    @NamedNativeQuery( name = "findAllOrders",  query = "{$query: {},$orderby : { orderdate : 1 }}",  resultClass = Order.class )
})
//@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="orderId")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "UUID2Generator")
    private String orderId;
    
    private String orderCode;

    private String status;
    
    private double subtotal;
    
    @Temporal(TIMESTAMP)
    private Date orderdate;
    
    @Temporal(TIMESTAMP)
    private Date paymentDate;
    
    @Column
    @Embedded
    private AddressTemplate shippingAddress;
    
    @ManyToOne
    private Customer customer;
    
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.REMOVE})
    private List<Product> products;
    
    @PrePersist
    public void beforePersist(){
        orderdate = new Date();
    }
    
}
