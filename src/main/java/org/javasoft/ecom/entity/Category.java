/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@XmlRootElement
@NoArgsConstructor
@NamedNativeQueries({
    @NamedNativeQuery( name = "findAllCategory",  query = "{$query: {},$orderby : { categoryName : 1 }}",  resultClass = Category.class )
})
@GenericGenerator(name="UUID2Generator", strategy="uuid2")
@EqualsAndHashCode( of = "categoryId")
@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "UUID2Generator")
    private String categoryId;
    
    private String categoryCode ;

    private String categoryName;  
    
    @Temporal(TIMESTAMP)
    private Date createDate;
    
    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
    private List<Product> products;
    
    @PrePersist
    public void beforePersist(){
        createDate = new Date();
    } 
}
