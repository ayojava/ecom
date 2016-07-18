/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
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
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.ecom.entity.AddressTemplate;
import org.javasoft.ecom.entity.Order;
import org.javasoft.ecom.entity.Product;
import static org.javasoft.ecom.intf.RestUrl.ORDER_URL;
import static org.javasoft.ecom.intf.RestUrl.PRODUCT_URL;
import org.javasoft.ecom.vo.OrderVO;
import org.omnifaces.util.Messages;

/**
 *
 * @author ayojava
 */
@Slf4j
@Named("orderPageBean")
@ViewScoped
public class OrderPageBean extends AbstractBean<Order> implements Serializable {

    @Getter
    private String subFolder, fullName;
    
    @Getter
    private int productCount;
    
    @Getter
    private boolean pending;

    @Getter
    private double grandTotal;

    @Getter @Setter
    private String customerID;
    
    @Getter @Setter
    private AddressTemplate addressTemplate;

    private Client client;

    @Getter @Setter
    private Order order;

    @Getter
    private List<Product> availableProducts;

    @Getter @Setter
    private List<Product> selectedProducts;

    @Getter @Setter
    private List<Order> allOrders;

    @Getter
    private List<OrderVO> selectedOrders;

    @Inject
    private CustomerPageBean customerPageBean;

    private List<String> selectedProductIds;

    private Map<String, OrderVO> sortProductsMap;

    @Override
    @PostConstruct
    public void init() {
        subFolder = "order/";
    }

    @Override
    public void setPageResource(String pageResource) {
        if (StringUtils.equals(NEW_ORDER, pageResource)) {
            selectedProducts = new ArrayList<>();
            grandTotal = 0d;
            loadAllAvailableProducts();
            super.setContentPath(appendFolderPath(subFolder, NEW_ORDER));
        } else if (StringUtils.equals(LIST_ORDERS, pageResource)) {
            cleanup();
            loadAllOrders();
            super.setContentPath(appendFolderPath(subFolder, LIST_ORDERS));
        } else if (StringUtils.equals(VIEW_ORDER, pageResource)) {
            fullName = customerPageBean.showFullName(order.getCustomer());
            selectedProducts =order.getProducts();
            productCount=( selectedProducts!= null)? selectedProducts.size():0;
            pending = StringUtils.equals(order.getStatus(),PENDING);
            if(pending){
                addressTemplate = new AddressTemplate();
            }
            super.setContentPath(appendFolderPath(subFolder, VIEW_ORDER));
        }
    }

    private void loadAllOrders() {
        try {
            client = ClientBuilder.newClient();
            allOrders = client.target(ORDER_URL).path("all").request(MediaType.APPLICATION_JSON)
                    .get(new GenericType<List<Order>>() { });
            client.close();
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
        }
    }

    private void loadAllAvailableProducts() {
        try {
            client = ClientBuilder.newClient();
            availableProducts = client.target(PRODUCT_URL).path("status").
                    path("{productStatus}").resolveTemplate("productStatus", AVAILABLE).request(MediaType.APPLICATION_JSON).
                    get(new GenericType<List<Product>>() {
                    });
            client.close();
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
        }
    }

    public void sortProductsForDisplay() {
        sortProductsMap = new HashMap<>();
        selectedProductIds = new ArrayList<>();
        for (Product aProduct : selectedProducts) {
            grandTotal = grandTotal + aProduct.getPrice();
            String hashKey = aProduct.getProductColour() + aProduct.getProductType();
            selectedProductIds.add(aProduct.getProductId());
            OrderVO aOrderVO = sortProductsMap.get(hashKey);
            if (aOrderVO == null) {
                aOrderVO = new OrderVO();
                aOrderVO.setProduct(aProduct);
                aOrderVO.setQuantity(1);
                aOrderVO.setTotalPrice(aProduct.getPrice());
                sortProductsMap.put(hashKey, aOrderVO);
            } else {
                aOrderVO.setQuantity(aOrderVO.getQuantity() + 1);
                aOrderVO.setTotalPrice(aProduct.getPrice() * aOrderVO.getQuantity());
            }
        }
        
        selectedOrders = new ArrayList<>();
        Iterator<String> iterator = sortProductsMap.keySet().iterator();
        while (iterator.hasNext()) {
            selectedOrders.add(sortProductsMap.get(iterator.next()));
        }
        sortProductsMap = null;
    }

    public void saveOrder() {
        if (selectedProducts == null || selectedProducts.isEmpty()) {
            Messages.addGlobalWarn("No Product Selected , Order Cannot be processed");
            return;
        }
        Order orderObj = new Order();
        orderObj.setStatus(PENDING);
        orderObj.setOrderCode("ORD-" + RandomStringUtils.randomNumeric(5));
        try {
            client = ClientBuilder.newClient();
            Response response = client.target(ORDER_URL).path("createOrder").queryParam("customerID", customerID)
                    .queryParam("productIDs", selectedProductIds.toArray(new Object[selectedProductIds.size()]))
                    .request(MediaType.APPLICATION_JSON).post(Entity.json(orderObj));
            if (Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() == response.getStatus()) {
                Messages.addGlobalError("An Error has occured, Order Not saved");
            } else if (Response.Status.OK.getStatusCode() == response.getStatus()) {
                Messages.addGlobalInfo("Order Successfully Saved, Your Order Code is [ " + orderObj.getOrderCode() + " ]");
            }
            response.close();
            cleanup();
            setPageResource(LIST_ORDERS);
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
            Messages.addGlobalError("An Error Has occured");
        }
        selectedOrders = null; // not needed again
    }

    public boolean isPending(Order orderObj) {
        return StringUtils.equalsIgnoreCase(orderObj.getStatus(), PENDING);
    }
    
    public void processOrder(){
        // customerID , Address Template , 
         try {
            client = ClientBuilder.newClient();
            Response response = client.target(ORDER_URL).path("processOrder").queryParam("orderID", order.getOrderId())
                    .request(MediaType.APPLICATION_JSON).post(Entity.json(addressTemplate));
            if (Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() == response.getStatus()) {
                Messages.addGlobalError("An Error has occured, Order Not Processed");
            } else if (Response.Status.OK.getStatusCode() == response.getStatus()) {
                Messages.addGlobalInfo(" Order Successfully Processed ");
            }
            response.close();
            cleanup();
            setPageResource(LIST_ORDERS);
         }catch (Exception ex) {
            log.error("Exception ::: ", ex);
            Messages.addGlobalError("An Error Has occured");
        }
    }
    
    public void cancelOrder() {
        try {
            client = ClientBuilder.newClient();
            Response response = client.target(ORDER_URL).path("cancelOrder").path("{orderID}").
                    resolveTemplate("orderID", order.getOrderId()).request().delete();

            if (Response.Status.GONE == response.getStatusInfo()) {
                Messages.addGlobalInfo("Order Successfully Cancelled");
            } else {
                Messages.addGlobalError("Cancel Operation not successful");
            }
            response.close();
            cleanup();
            setPageResource(LIST_ORDERS);
         }catch (Exception ex) {
            log.error("Exception ::: ", ex);
            Messages.addGlobalError("An Error Has occured");
        }
    }

    public int showProductCount(Order orderObj) {
        if (orderObj.getProducts() != null) {
            return orderObj.getProducts().size();
        } else {
            return 0;
        }
    }

    public String capitalize(String value) {
        return StringUtils.capitalize(value);
    }

    @PreDestroy
    public void cleanup() {
        selectedProducts = null;
        addressTemplate = null;
    }
}
