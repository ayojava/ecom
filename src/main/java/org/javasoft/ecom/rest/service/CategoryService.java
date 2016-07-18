/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.rest.service;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.ecom.entity.Category;
import org.javasoft.ecom.facade.CategoryFacade;
import org.javasoft.ecom.rest.intf.CategoryResource;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class CategoryService implements CategoryResource {

    @EJB
    private CategoryFacade categoryFacade;

    @Override
    public List<Category> getAllCategories() {
        return categoryFacade.getAllCategories();
    }

    @Override
    public Category getCategoryById(String databaseID) {
        return categoryFacade.find(databaseID);
    }

    @Override
    public String confirmCategoryName(String name) {
        return categoryFacade.findCategoryByName(name) ? "T" : "F";
    }

    @Override
    public Response createCategory(Category categoryObj) {
        try {
            if (categoryFacade.findCategoryByName(categoryObj.getCategoryName())) {
                categoryFacade.createCategory(categoryObj);
            } else {
                log.warn("::: Category Name already exists in the Database");
                return Response.status(Status.PRECONDITION_FAILED).entity("Duplicate Category Name ").build();
            }
        } catch (Exception ex) {
            log.error("An Exception has occured :: {}", ex);
            Response.status(Status.INTERNAL_SERVER_ERROR).entity("An Exception has occured").build();
        }
        return Response.status(Status.OK).build();
    }
}
