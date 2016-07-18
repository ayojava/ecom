/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.facade;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.ecom.entity.Category;
import org.javasoft.ecom.entity.Product;
import org.javasoft.ecom.intf.PageResource;
import static org.javasoft.ecom.intf.PageResource.AVAILABLE;
import static org.javasoft.ecom.intf.PageResource.ORDERED;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class ProductFacade extends AbstractFacade<Product> {

    @EJB
    private CategoryFacade categoryFacade;

    public ProductFacade() {
        super(Product.class);
    }

    public Product findProductByPdtCode(String productCode) {
        String nativeQry = "db.Product.find({'productCode':'" + productCode + "'})";
        //String nativeQry = "{$query :{productCode  : '" + productCode +"'}}";
        Query query = getEntityManager().createNativeQuery(nativeQry);
        return (Product) query.getSingleResult();
    }

    public List<Product> getAllProducts() {
        String nativeQry="{$query :{status  : {$in :['" + AVAILABLE +"','" +ORDERED+"']}}}";
        //Query query = getEntityManager().createNamedQuery("findAllProducts");
        Query query = getEntityManager().createNativeQuery(nativeQry,Product.class);
        return query.getResultList();
    }
    
    public List<Product> getProductsByStatus(String status){
        String nativeQry = "{$query :{status  : '" + status +"'}}";
        Query query = getEntityManager().createNativeQuery(nativeQry,Product.class);
        return query.getResultList();
    }
    
    public boolean deleteProduct(String productId,String categoryId){
        boolean output = false;
        log.info("Product ID :::: {} ----- Category ID :::: {} " , productId , categoryId);
        try{
            Category categoryObj = categoryFacade.find(categoryId);
            Product productObj = find(productId);
            getEntityManager().remove(productObj);
           // log.info("Size before removal ::: {}" ,categoryObj.getProducts().size());
            if(categoryObj.getProducts().remove(productObj)){
                //log.info("Size After removal ::: {}" ,categoryObj.getProducts().size());
                categoryFacade.edit(categoryObj);
                
            }
            output = true;
        }
        catch(Exception ex){
            log.error("Exception Occurred ::: {}" , ex);
        }
        return output;
    }

    public boolean createProduct(Product productObj, int productCount, String categoryId) {
        
        boolean output = false;
        try {
            Category categoryObj = categoryFacade.find(categoryId);
            List<Product> allProducts = categoryObj.getProducts();
            int count = allProducts.size() + 1;
            for (int i = 0; i < productCount; i++) {
                Product productEntity = new Product();
                productEntity.setProductId(null);
                productEntity.setName(productObj.getName());
                productEntity.setPrice(productObj.getPrice());
                productEntity.setDescription(productObj.getDescription());
                productEntity.setProductCode(categoryObj.getCategoryCode() + "-" + count);
                productEntity.setProductColour(productObj.getProductColour());
                productEntity.setProductType(productObj.getProductType());
                productEntity.setCategory(categoryObj);
                productEntity.setStatus(PageResource.AVAILABLE);
                productEntity = getEntityManager().merge(productEntity);
                allProducts.add(productEntity);
                count++;
            }
            categoryFacade.edit(categoryObj);
            output = true;
        } catch (Exception ex) {
            log.error("Exception Occurred ::: {}" , ex);
        }
        return output;
    }
}
