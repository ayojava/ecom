/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.ecom.entity.Category;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
public class CategoryFacade extends AbstractFacade<Category> {

    public CategoryFacade() {
        super(Category.class);
    }
    
    public List<Category> getAllCategories(){
        Query query =getEntityManager().createNamedQuery("findAllCategory");
        return query.getResultList();
    }
    
    public Category createCategory(Category categoryObj){
        return getEntityManager().merge(categoryObj);
    }
    
    public boolean findCategoryByName(String categoryName){
        String categoryQry = "{$query :{categoryName  : '" + categoryName +"'}}";
        List<Category> resultList = getEntityManager().createNativeQuery(categoryQry, Category.class).getResultList();
        return resultList.isEmpty();
    }
}
