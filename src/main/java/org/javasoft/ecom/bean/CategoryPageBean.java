/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.javasoft.ecom.entity.Category;
import org.javasoft.ecom.entity.Product;
import static org.javasoft.ecom.intf.RestUrl.CATEGORY_URL;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("categoryPageBean")
@ViewScoped
public class CategoryPageBean extends AbstractBean<Category> implements Serializable {

    @Getter
    private String subFolder;

    private Client client;

    @Getter
    @Setter
    private Category category;

    @Getter
    private List<Product> categoryProducts;

    @Getter
    private List<Category> allCategory;

    @Override
    @PostConstruct
    public void init() {
        subFolder = "category/";
    }

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(NEW_CATEGORY, pageResource)) {
            category = new Category();
            super.setContentPath(appendFolderPath(subFolder, NEW_CATEGORY));
        } else if (StringUtils.equals(VIEW_CATEGORY, pageResource)) {
            super.setContentPath(appendFolderPath(subFolder, VIEW_CATEGORY));
        } else if (StringUtils.equals(LIST_CATEGORIES, pageResource)) {
            loadAllCategories();
            super.setContentPath(appendFolderPath(subFolder, LIST_CATEGORIES));
        }
    }

    private void loadAllCategories() {
         try {
            client = ClientBuilder.newClient();
            allCategory = client.target(CATEGORY_URL).path("all").request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Category>>() { });
            client.close();
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
        }
    }

    public int showProductCount(Category categoryObj) {
        return (categoryObj.getProducts() != null) ? categoryObj.getProducts().size() : 0;
    }

    public void setPageResource(String pageResource, Category categoryObj) {
        category = categoryObj;
        categoryProducts = categoryObj.getProducts();
        setPageResource(pageResource);
    }

    public void saveCategory() {
        try {
            client = ClientBuilder.newClient();
            //category.setCategoryCode(RandomStringUtils.randomAlphanumeric(4).toUpperCase());
            category.setCategoryName(WordUtils.capitalizeFully(category.getCategoryName()));
            Response response = client.target(CATEGORY_URL).request(MediaType.APPLICATION_JSON).post(Entity.json(category));

            if (Response.Status.PRECONDITION_FAILED.getStatusCode() == response.getStatus()) {
                Messages.addGlobalWarn("Category Name already exists in the Database, Category Not Saved");
            } else if (Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() == response.getStatus()) {
                Messages.addGlobalError("An Error has occured, Category Not saved");
            } else if (Response.Status.OK.getStatusCode() == response.getStatus()) {
                Messages.addGlobalInfo("Category Successfully Added");
            }
            response.close();
            cleanup();
            setPageResource(LIST_CATEGORIES);
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
            Messages.addGlobalError("An Error Has occured");
        }
    }


    @PreDestroy
    public void cleanup() {

    }

}
