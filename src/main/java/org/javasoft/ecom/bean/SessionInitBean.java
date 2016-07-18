/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.bean;

import java.io.Serializable;
import java.util.Enumeration;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    private Client client;

    @Override
    @PostConstruct
    public void init() {
        super.init();
        log.info("==================== SessionBean Instantiated ================== ");
        locateIPAddress();
        if (StringUtils.isNotBlank(ipAddress) && !StringUtils.equals(ipAddress, "127.0.0.1")) {
            geolocateIPAddress();
        }
    }

    private void locateIPAddress() {
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            
            Enumeration<String> headers = httpServletRequest.getHeaders(headerName);
            while (headers.hasMoreElements()) {
                String headerValue = headers.nextElement();
                log.info("Header Name --- {} :::: Header Value --- {}" , headerName , headerValue);
            }
        }
        ipAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
        if (StringUtils.isBlank(ipAddress)) {
            ipAddress = httpServletRequest.getRemoteAddr();
        }
        log.info("IP Address Obtained ::: {} ", ipAddress);

    }

    private void geolocateIPAddress() {
        try {
            client = ClientBuilder.newClient();
            GeoLocationVO geoLocationObj = client.target(GEOLOCATION_URL).path("json").path(ipAddress).request(MediaType.APPLICATION_JSON)
                    .get(GeoLocationVO.class);
            log.info("\nLocation :: {}", geoLocationObj);
            client.close();
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

*/
