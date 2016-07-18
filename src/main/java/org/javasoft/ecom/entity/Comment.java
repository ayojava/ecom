/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@EqualsAndHashCode(of = "commentId") 
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "UUID2Generator")
    private String commentId;

    private String fullName ;
    
    private String phoneNumber ;
    
    private String emailAddress ;
    
    private String country;
    
    private String commentTxt;
    
    private String geolocation;
    
    @Temporal(TIMESTAMP)
    private Date createDate;
       
    @PrePersist
    public void beforePersist(){
        createDate = new Date();
    }
}
