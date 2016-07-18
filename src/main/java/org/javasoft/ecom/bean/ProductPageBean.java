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
import org.javasoft.ecom.entity.Category;
import org.javasoft.ecom.entity.Product;
import static org.javasoft.ecom.intf.RestUrl.CATEGORY_URL;
import static org.javasoft.ecom.intf.RestUrl.PRODUCT_URL;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("productPageBean")
@ViewScoped
public class ProductPageBean extends AbstractBean<Product> implements Serializable {

    @Getter
    private String subFolder;

    @Getter
    @Setter
    private int productQnty;

    @Getter
    @Setter
    private String categoryId;

    private Client client;

    @Getter
    @Setter
    private Product product;

    @Getter
    private List<Product> allProducts;

    @Getter
    private List<Category> allCategories;

    @Override
    @PostConstruct
    public void init() {
        subFolder = "product/";
    }

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(NEW_PRODUCT, pageResource)) {
            product = new Product();
            loadAllCategories();
            super.setContentPath(appendFolderPath(subFolder, NEW_PRODUCT));
        } else if (StringUtils.equals(LIST_PRODUCTS, pageResource)) {
            loadAllProducts();
            super.setContentPath(appendFolderPath(subFolder, LIST_PRODUCTS));
        } else if (StringUtils.equals(EDIT_PRODUCT, pageResource)) {
            super.setContentPath(appendFolderPath(subFolder, EDIT_PRODUCT));
        } else if (StringUtils.equals(VIEW_PRODUCT, pageResource)) {
            super.setContentPath(appendFolderPath(subFolder, VIEW_PRODUCT));
        }
    }

    private void loadAllProducts() {
        try {
            client = ClientBuilder.newClient();
            allProducts = client.target(PRODUCT_URL).path("all").request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Product>>() {
                    });
            client.close();
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
        }
    }

    private void loadAllCategories() {
        try {
            client = ClientBuilder.newClient();
            allCategories = client.target(CATEGORY_URL).path("all").request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Category>>() {
                    });
            client.close();
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
        }
    }

    public void setPageResource(String pageResource, Product productObj) {
        product = productObj;
        setPageResource(pageResource);
    }

    public boolean isAvailable(Product productObj) {
        return StringUtils.equalsIgnoreCase(productObj.getStatus(), AVAILABLE);
    }

    public void saveProduct() {
        log.info("Product Count :::: {} , CategoryID :::: {}", productQnty, categoryId);
        try {
            client = ClientBuilder.newClient();
            product.setStatus(AVAILABLE);
            Response response = client.target(PRODUCT_URL).path("createProduct").queryParam("count", productQnty).queryParam("id", categoryId)
                    .request(MediaType.APPLICATION_JSON).post(Entity.json(product));
            if (Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() == response.getStatus()) {
                Messages.addGlobalError("An Error has occured, Product Not saved");
            } else if (Response.Status.OK.getStatusCode() == response.getStatus()) {
                Messages.addGlobalInfo("Product Successfully Added");
            }
            response.close();
            cleanup();
            setPageResource(LIST_PRODUCTS);
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
            Messages.addGlobalError("An Error Has occured");
        }
    }

    public String capitalize(String value){
        return StringUtils.capitalize(value);
    }

    public void deleteProduct() {
        try {
            client = ClientBuilder.newClient();
            Response response = client.target(PRODUCT_URL).path("product").
                    path("{productId}").resolveTemplate("productId", product.getProductId()).
                    path("{categoryId}").resolveTemplate("categoryId", categoryId).
                    request().delete();
            int status = response.getStatus();
            log.info("Response :: {}", status);

            if (Response.Status.GONE == response.getStatusInfo()) {
                Messages.addGlobalInfo("Product Successfully Deleted");
            } else {
                Messages.addGlobalError("Delete Operation not successful");
            }
            response.close();
            cleanup();
            setPageResource(LIST_PRODUCTS);
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
            Messages.addGlobalError("An Error Has occured");
        }
    }

    @PreDestroy
    public void cleanup() {
        product = null;
        allCategories = null;
    }
}
