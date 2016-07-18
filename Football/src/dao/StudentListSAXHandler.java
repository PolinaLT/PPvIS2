package dao;

import entity.Composition;
import entity.Faculty;
import entity.Role;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import entity.Faculty;
import entity.Student;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class StudentListSAXHandler extends DefaultHandler {

    private final String STUDENTS_TAG = "students";
    private final String STUDENT_TAG = "student";
    private final String FIRST_NAME_TAG = "first_name";
    private final String SECOND_NAME_TAG = "second_name";
    private final String THIRD_NAME_TAG = "third_name";
    private final String BIRTHDAY_TAG = "birthday";
    private final String TEAM_TAG = "team";
    private final String FACULTY_TAG = "faculty";
    private final String COMPOSITION_TAG = "composition";
    private final String ROLE_TAG = "role";

    private DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");

    
    private List<Student> resultList;
    private Student student;
    private SAXHandlerState currentState = SAXHandlerState.NONE;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (localName) {
            case STUDENTS_TAG:
                resultList = new LinkedList<>();
                break;
            case STUDENT_TAG:
                student = new Student();
                resultList.add(student);
                break;
            case FIRST_NAME_TAG:
                currentState = SAXHandlerState.FIRST_NAME;
                break;
            case SECOND_NAME_TAG:
                currentState = SAXHandlerState.SECOND_NAME;
                break;
            case THIRD_NAME_TAG:
                currentState = SAXHandlerState.THIRD_NAME;
                break;
            case BIRTHDAY_TAG:
                currentState = SAXHandlerState.BIRTHDAY;
                break;
            case TEAM_TAG:
                currentState = SAXHandlerState.TEAM;
                break;
            case FACULTY_TAG:
                currentState = SAXHandlerState.FACULTY;
                break;
            case COMPOSITION_TAG:
                currentState = SAXHandlerState.COMPOSITION;
                break;
            case ROLE_TAG:
                currentState = SAXHandlerState.ROLE;
                break;
            default:
                System.out.println("Bad tag!!!");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length);
        
        switch (currentState) {
            case FIRST_NAME:
                student.setFirstName(value);
                break;
            case SECOND_NAME:
                student.setSecondName(value);
                break;
            case THIRD_NAME:
                student.setThirdName(value);
                break;
            case BIRTHDAY:
                Date date = null;
                try {
                    date = dateFormat.parse(value);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                    System.out.println("Date trouble!!!");
                }
                student.setBirthday(date);
                break;
            case TEAM:
                student.setFootballClub(value);
                break;
            case FACULTY:
            	if (value.equals("FITY")) student.setFaculty(Faculty.FITY);
            	else if (value.equals("KSIS")) student.setFaculty(Faculty.KSIS);
            	else if (value.equals("FRE")) student.setFaculty(Faculty.FRE);
            	else if (value.equals("FTK")) student.setFaculty(Faculty.FTK);
            	else if (value.equals("FKP")) student.setFaculty(Faculty.FKP);
            	else if (value.equals("IEF")) student.setFaculty(Faculty.IEF);
                else System.out.println("Faculty trouble!!!!!!!!!!!");
                break;
            case COMPOSITION:
            	if (value.equals("MAIN")) student.setComposition(Composition.MAIN);
            	else if (value.equals("RESERVE")) student.setComposition(Composition.RESERVE);
            	else System.out.println("Composition trouble!!!!!!!!");
                break;
            case ROLE:
                if (value.equals("GOALKEEPER")) student.setRole(Role.GOALKEEPER);
                else if (value.equals("FORWARD")) student.setRole(Role.FORWARD);
                else if (value.equals("HALFBACK")) student.setRole(Role.HALFBACK);
                else if (value.equals("QUARTERBACK")) student.setRole(Role.QUARTERBACK);
                else System.out.println("Role trouble!!!!!");
                break;
            case NONE:
                break;
            default:
                System.out.println("Bad state!!!");
        }
    }

    @Override
    public void endDocument() throws SAXException {
        currentState = SAXHandlerState.NONE;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentState = SAXHandlerState.NONE;
    }

    public List<Student> getResultList() {
        return resultList;
    }
}
