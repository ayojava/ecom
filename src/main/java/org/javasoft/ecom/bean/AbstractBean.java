/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.bean;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.text.StrBuilder;
import org.javasoft.ecom.intf.PageResource;

/**
 *
 * @author ayojava
 */
@Slf4j
public abstract class AbstractBean<T> implements PageResource {

    @Inject
    private NavigatorBean navigatorBean;
        
    protected void init() {
        
    }

    protected void setContentPath(String pageResource) {
        StrBuilder builder = new StrBuilder();
        builder = builder.append(DEFAULT_INCLUDE_PATH).append(pageResource).append(PAGE_EXTENSION);
        navigatorBean.setSelectedContentPath(builder.toString());
    }

    protected String appendFolderPath(String parentFolder, String childFolder) {
        StringBuilder builder = new StringBuilder();
        builder = builder.append(parentFolder).append(childFolder);
        return builder.toString();
    }

    @Override
    public String getPageResource() {
        return "";
    }
}
