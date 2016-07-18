/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.bean;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("homePageBean")
@ViewScoped
public class HomePageBean extends AbstractBean  implements Serializable {

    
    
    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(DEFAULT_PAGE, pageResource)) {
            super.setContentPath(DEFAULT_PAGE);
        }
        
    }
    
}
