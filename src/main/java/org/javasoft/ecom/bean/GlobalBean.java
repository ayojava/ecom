/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.javasoft.ecom.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import lombok.Getter;
import org.omnifaces.cdi.Eager;

/**
 *
 * @author ayojava
 */
@Eager
@Named(value = "globalBean")
@ApplicationScoped
public class GlobalBean {

    @Getter
    private List<SelectItem> titles;

    @Getter
    private List<SelectItem> countries;
    
    @Getter
    private List<SelectItem> colours;
    
    @Getter
    private List<SelectItem> productType;

    private SelectItem defaultSel;

    @PostConstruct
    public void init() {
        defaultSel = new SelectItem("", "- Select One -");
        populateTitles();
        populateCountries();
        populateColours();
        populateProductType();
    }
    
    private void populateProductType(){
        productType = new ArrayList<>();
        
        //productType.add(defaultSel);
        productType.add(new SelectItem("Male", "Male"));
        productType.add(new SelectItem("Female", "Female"));
        productType.add(new SelectItem("Unisex", "Unisex"));
        
    }
    
    private void populateColours(){
        colours = new ArrayList<>();
        
        colours.add(defaultSel);
        colours.add(new SelectItem("Blue", "Blue"));
        colours.add(new SelectItem("Cyan", "Cyan"));
        colours.add(new SelectItem("Green", "Green"));
        colours.add(new SelectItem("Orange", "Orange"));
        colours.add(new SelectItem("Red", "Red"));
        colours.add(new SelectItem("Violet", "Violet"));
        colours.add(new SelectItem("Yellow", "Yellow"));
    }

    private void populateCountries(){
        countries = new ArrayList<>();
       
        countries.add(defaultSel);
        countries.add(new SelectItem("Australia", "Australia"));
        countries.add(new SelectItem("Belgium", "Belgium"));
        countries.add(new SelectItem("Cameroon", "Cameroon"));
        countries.add(new SelectItem("Ghana", "Ghana"));
        countries.add(new SelectItem("India", "India"));
        countries.add(new SelectItem("Malawi", "Malawi"));
        countries.add(new SelectItem("Nigeria", "Nigeria"));
        countries.add(new SelectItem("Uganda", "Uganda"));
        
    }
    
    
    private void populateTitles() {
        titles = new ArrayList<>();

        titles.add(new SelectItem("Mr", "Mr"));
        titles.add(new SelectItem("Mrs", "Mrs"));
        titles.add(new SelectItem("Miss", "Miss"));
        titles.add(new SelectItem("Dr", "Dr"));
        titles.add(new SelectItem("Prof", "Prof"));
    }
}
