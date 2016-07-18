/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import static org.javasoft.ecom.intf.PageResource.DEFAULT_INCLUDE_PATH;
import static org.javasoft.ecom.intf.PageResource.DEFAULT_PAGE;
import static org.javasoft.ecom.intf.PageResource.PAGE_EXTENSION;

/**
 *
 * @author ayojava
 */
@Named("navigatorBean")
@SessionScoped
public class NavigatorBean implements Serializable {
    
    @PostConstruct
    public void init() {

    }

    private String selectedContentPath;

    public String getSelectedContentPath() {
        if (StringUtils.isBlank(selectedContentPath)) {
            //customerPageBean.loadAllCustomers();
            StrBuilder builder = new StrBuilder();
            builder = builder.append(DEFAULT_INCLUDE_PATH).append(DEFAULT_PAGE).append(PAGE_EXTENSION);
            selectedContentPath = builder.build();
        }
        return selectedContentPath;
    }

    public void setSelectedContentPath(String selectedContentPath) {
        this.selectedContentPath = selectedContentPath;
    }
}
