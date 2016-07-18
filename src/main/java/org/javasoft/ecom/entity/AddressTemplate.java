/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ayojava
 */
@Data
@NoArgsConstructor
@Embeddable
public class AddressTemplate implements Serializable {
    
    private String streetDesc;
    
    private String city ;
    
    private String state ;
    
    private String zipCode ;
    
    private String country ;
}
