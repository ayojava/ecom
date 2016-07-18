/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.facade;

import javax.ejb.Stateless;
import org.javasoft.ecom.entity.GeoLocation;

/**
 *
 * @author ayojava
 */
@Stateless
public class GeoLocationFacade extends AbstractFacade<GeoLocation>{

    public GeoLocationFacade() {
        super(GeoLocation.class);
    }
    
    public GeoLocation createGeoLocation(GeoLocation geoLocation){
        return getEntityManager().merge(geoLocation);
    }
}
