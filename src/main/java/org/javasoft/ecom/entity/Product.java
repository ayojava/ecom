/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
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
@NoArgsConstructor
@Entity
@GenericGenerator(name="UUID2Generator", strategy="uuid2")
@XmlRootElement 
@NamedNativeQueries({
    @NamedNativeQuery( name = "findAllProducts",  query = "{$query: {},$orderby : { name : 1 }}",  resultClass = Product.class )
})
@EqualsAndHashCode( of = "productId")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "UUID2Generator")
    private String productId;
    
    private String productCode ;

    private String name ;
    
    private String description;
    
    private double price;
        
    private String productColour;
        
    private String productType ;// Male or Female or unisex
    
    private String status ; //Available , Ordered , Sold
    
    @Temporal(TIMESTAMP)
    private Date createDate;
    
    //@JsonManagedReference
    //@JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
    
    @PrePersist
    public void beforePersist(){
        createDate = new Date();
    }
    
}
