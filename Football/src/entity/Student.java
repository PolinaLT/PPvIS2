package entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Student {

	private String firstName;
    private String secondName;
    private String thirdName;
    private Date birthday;
    private String footballClub;
    private Faculty faculty;
    private Composition composition;
    private Role role;
        
    public String getFirstName() {
        return firstName;
    }
    
    public String getSecondName() {
        return secondName;
    }
    
    public String getThirdName() {
    	return thirdName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    
    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getBirthday() {
    	String stringDate;
    	DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
    	stringDate = dateFormat.format(birthday); 
		return stringDate;

    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getFootballClub() {
        return footballClub;
    }

    public void setFootballClub(String footballClub) {
        this.footballClub = footballClub;
    }

    public String getFaculty() {
        return faculty.toString();
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getComposition() {
        return composition.toString();
    }

    public void setComposition(Composition composition) {
        this.composition = composition;
    }

    public String getRole() {
        return role.toString();
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
