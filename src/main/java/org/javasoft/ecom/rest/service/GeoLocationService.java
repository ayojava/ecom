/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.rest.service;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.javasoft.ecom.entity.GeoLocation;
import org.javasoft.ecom.facade.GeoLocationFacade;
import org.javasoft.ecom.rest.intf.GeoLocationResource;

/**
 *
 * @author ayojava
 */
@Slf4j
@Stateless
@LocalBean
public class GeoLocationService implements GeoLocationResource{
    
    @EJB
    private GeoLocationFacade geoLocationFacade;
    
    @Override
    public Response createGeolocation(GeoLocation geoLocation) {
        try {
            geoLocationFacade.create(geoLocation);
        } catch (Exception ex) {
            log.error("An Exception has occured :: {}", ex);
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("An Exception has occured").build();
        }
        return Response.status(Response.Status.OK).build();
    }
    
}
