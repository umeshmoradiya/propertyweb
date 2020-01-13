package org.com.property;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
 
@ManagedBean @SessionScoped
public class StudentBean {
 
    private String firstName;
    private String lastName;
    private String standard;
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getStandard() {
        return standard;
    }
 
    public void setStandard(String standard) {
        this.standard = standard;
    }
 
    public String createStudentForm() {
        System.out.println("Reading Student Details - Name: " + firstName + " " + lastName + ", Standard: " + standard);
        return "output";
    }
    
    private UploadedFile uploadedFile; // +getter+setter
    

    public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public void upload() {
		System.out.println("upload");
	    String fileName = uploadedFile.getFileName();
	    String contentType = uploadedFile.getContentType();
	    byte[] contents = uploadedFile.getContents(); // Or getInputStream()
	    System.out.println(fileName);
	    // ... Save it, now!
	} 
	/*public void upload(FileUploadEvent event) {
		System.out.println("Listening");
	    uploadedFile = event.getFile();
	    String fileName = uploadedFile.getFileName();
	    String contentType = uploadedFile.getContentType();
	    byte[] contents = uploadedFile.getContents(); // Or getInputStream()
	    
	    // ... Save it, now!
	}*/
	
}