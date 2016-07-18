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
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javasoft.ecom.entity.GeoLocation;
import static org.javasoft.ecom.intf.RestUrl.FREEGEOIP_URL;
import static org.javasoft.ecom.intf.RestUrl.GEOLOCATION_URL;
import org.javasoft.ecom.vo.GeoLocationVO;
import org.omnifaces.cdi.Eager;

/**
 *
 * @author ayojava
 */
@Slf4j
@Eager
@Named("sessionInitBean")
@SessionScoped
public class SessionInitBean extends AbstractBean implements Serializable {

    private String ipAddress;

    @Inject
    private HttpServletRequest httpServletRequest;

    @Inject
    private ApplicationInitBean applicationInitBean;

    private Client client;

    private boolean output;

    private GeoLocationVO geoLocationVO;

    @Override
    @PostConstruct
    public void init() {
        super.init();
        log.info(":::: SessionBean Instantiated :::: ");
        applicationInitBean.increment();
        locateIPAddress();
        if (StringUtils.isNotBlank(ipAddress) && !StringUtils.equals(ipAddress, "127.0.0.1")) {
            geolocateIPAddress();
            if (output) {
                GeoLocation geoLocation = new GeoLocation();
                geoLocation.setCountryCode(geoLocationVO.getCountryCode());
                geoLocation.setCountryName(geoLocationVO.getCountryName());
                geoLocation.setIpAddress(geoLocationVO.getIpAddress());
                geoLocation.setTimezone(geoLocationVO.getTimezone());
                geoLocation.setCity(geoLocationVO.getCity());
                geoLocation.setLatitude(geoLocationVO.getLatitude());
                geoLocation.setRegionCode(geoLocationVO.getRegionCode());
                geoLocation.setRegionName(geoLocationVO.getRegionName());
                geoLocation.setZipCode(geoLocationVO.getZipCode());
                saveLocation(geoLocation);
            }
        }
    }

    private void locateIPAddress() {
        ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isBlank(ipAddress)) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }
        log.info("IP Address Obtained ::: {} ", ipAddress);
    }

    private void geolocateIPAddress() {
        try {
            client = ClientBuilder.newClient();
            geoLocationVO = client.target(FREEGEOIP_URL).path("json").path(ipAddress).request(MediaType.APPLICATION_JSON)
                    .get(GeoLocationVO.class);
            log.info("\nLocation :: {}", geoLocationVO);
            output = true;
            client.close();
        } catch (Exception ex) {
            output = false;
            log.error("Exception ::: ", ex);
        }
    }

    private void saveLocation(GeoLocation geoLocation) {
        try {
            client = ClientBuilder.newClient();
            Response response = client.target(GEOLOCATION_URL).request(MediaType.APPLICATION_JSON).post(Entity.json(geoLocation));
            if (Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() == response.getStatus()) {
                log.error(":::: Internal Server Error , Location Not Saved ::: ");
            } else if (Response.Status.OK.getStatusCode() == response.getStatus()) {
                log.info(":::: Location Successfully Saved :::: ");
            }
        } catch (Exception ex) {
            log.error("Exception ::: ", ex);
        }
    }

    @Override
    public void setPageResource(String pageResource) {

    }
}

/*
https://freegeoip.net/
http://ip-api.com/docs/api:json

Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            
            Enumeration<String> headers = httpServletRequest.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                log.info("Header Name --- {} :::: Header Value --- {}" , headerName , headerValue);
            }
        }

 */
