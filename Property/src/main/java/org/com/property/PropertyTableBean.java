package org.com.property;

import java.util.*;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
 
@ManagedBean(name="tableBean")
@SessionScoped
public class PropertyTableBean{
	private int propertyId;
	private String propertyTitle;
    private String description;
    private String propertyType;
    
    // EFH, MFH, REH, RMH, Bungalow
    
    public String getPropertyTitle() {
		return propertyTitle;
	}

	public int getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(int propertyId) {
		this.propertyId = propertyId;
	}

	public void setPropertyTitle(String propertyTitle) {
		this.propertyTitle = propertyTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
 
    public List<PropertyTableBean> getAllProperties() {
        return PropertyDAO.getProperties();
    }
}