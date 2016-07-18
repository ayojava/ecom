/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.bean;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.omnifaces.cdi.Eager;

/**
 *
 * @author ayojava
 */
@Slf4j
@Eager
@Named("applicationInitBean")
@ApplicationScoped
public class ApplicationInitBean extends AbstractBean{
    
    @Getter
    private Date serverStartDate;
        
    @PostConstruct
    @Override
    public void init() {
        super.init();
        log.info("==================== ApplicationBean  Instantiated ================== ");
        serverStartDate = new Date();
    }

    @Override
    public void setPageResource(String pageResource) {
        
    }
    
}
