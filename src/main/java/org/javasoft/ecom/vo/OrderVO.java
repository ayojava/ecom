/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.javasoft.ecom.entity.Product;

/**
 *
 * @author ayojava
 */
@Data
@NoArgsConstructor
public class OrderVO {
    
    private int quantity ;
    
    private Product product ;
        
    private double totalPrice ;
    
}
