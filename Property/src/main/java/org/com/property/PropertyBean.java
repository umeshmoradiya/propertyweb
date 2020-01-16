package org.com.property;

import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
 
@ManagedBean 
@SessionScoped
public class PropertyBean implements Serializable{
 
	private static final long serialVersionUID = 8079990930068539050L;
	
	private String propertyTitle;
    private String description;
    private String propertyType;
    
    // EFH, MFH, REH, RMH, Bungalow
 
    
    public String createStudentForm() {
        System.out.println("Reading Student Details - Name: " + propertyTitle + " " + description + ", Standard: " + propertyType);
        return "output";
    }
    
    public String getPropertyTitle() {
		return propertyTitle;
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
	
	// EFH, MFH, REH, RMH, Bungalow
	private static Map<String,Object> propertyTypeMap;
	static{
		propertyTypeMap = new LinkedHashMap<String,Object>();
		propertyTypeMap.put("EFH", "EFH"); //label, value
		propertyTypeMap.put("MFH", "MFH");
		propertyTypeMap.put("REH", "REH");
		propertyTypeMap.put("RMH", "RMH");
		propertyTypeMap.put("Bungalow", "Bungalow");
	}
	
	public Map<String,Object> getPropertyTypeMap() {
		return propertyTypeMap;
	}

	private UploadedFile file;
	 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
 
    public String upload() {
        
        if (file != null) {
            try {
                System.out.println(file.getFileName());
                InputStream fin2 = file.getInputstream();
                
                PropertyDAO.storeProperty(propertyTitle, description, propertyType, fin2);
                
                System.out.println("Inserting Successfully! : " +file.getFileName().toString() );
                
               // FacesMessage msg = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
                System.out.println("Reading Student Details - Name: " + propertyTitle + " " + description + ", Standard: " + propertyType);
                
                //FacesContext.getCurrentInstance().addMessage(null, msg);
 
                setDescription(null);
                setPropertyTitle(null);
                
            } catch (Exception e) {
                System.out.println("Exception-File Upload." + e.getMessage());
            }
        }
        else{
        FacesMessage msg = new FacesMessage("Please select image!!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        
        return null;
    }
	
}