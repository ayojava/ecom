/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author ayojava
 */
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class GeoLocationVO {

    @JsonProperty("ip")
    private String ipAddress;
    
    @JsonProperty("country_code")
    private String countryCode;
    
    @JsonProperty("country_name")
    private String countryName;
    
    @JsonProperty("region_code")
    private String regionCode;
    
    @JsonProperty("region_name")
    private String regionName;
   
    @JsonProperty("city")
    private String city;
    
    @JsonProperty("zip_code")
    private String zipCode;
    
    @JsonProperty("time_zone")
    private String timezone;

    @JsonProperty("latitude")
    private String latitude;
    
    @JsonProperty("longitude")
    private String longitude;

    @JsonProperty("metro_code")
    private String metroCode;
}
