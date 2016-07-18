package dao.implemetation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import dao.DataAccessObject;
import dao.StudentListSAXHandler;
import entity.Student;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class StudentDAO implements DataAccessObject {

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

    private final static StudentListSAXHandler contextHandler = new StudentListSAXHandler();

    public List<Student> getStudentList(String filePath) {

        XMLReader reader;
        try {
            reader = XMLReaderFactory.createXMLReader();
        } catch (SAXException ex) {
            ex.printStackTrace();
            return null;
        }

        reader.setContentHandler(contextHandler);
        try {
            reader.parse(filePath);
        } catch (SAXException ex) {
            ex.printStackTrace();
            System.out.println("Sax excp!");
            return null;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("IO sax excp!!!");
            return null;
        }

        return contextHandler.getResultList();
    }

    @Override
    public void saveStudentList(String filePath, List<Student> studentList) {
        Document document;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
            System.out.println("Dom doc creation error");
            return;
        }

        Element root = document.createElement(STUDENTS_TAG);
        document.appendChild(root);

        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            Element studentElement = document.createElement(STUDENT_TAG);
            root.appendChild(studentElement);

            Student student = iterator.next();

            Element firsrNameElement = document.createElement(FIRST_NAME_TAG);
            firsrNameElement.setTextContent(student.getFirstName());
            studentElement.appendChild(firsrNameElement);
            
            Element secondNameElement = document.createElement(SECOND_NAME_TAG);
            secondNameElement.setTextContent(student.getSecondName());
            studentElement.appendChild(secondNameElement);

            Element thirdNameElement = document.createElement(THIRD_NAME_TAG);
            thirdNameElement.setTextContent(student.getThirdName());
            studentElement.appendChild(thirdNameElement);
            
            Element birthdayElement = document.createElement(BIRTHDAY_TAG);
            birthdayElement.setTextContent(student.getBirthday().toString());
            studentElement.appendChild(birthdayElement);

            Element teamElement = document.createElement(TEAM_TAG);
            teamElement.setTextContent(student.getFootballClub());
            studentElement.appendChild(teamElement);

            Element facultyElement = document.createElement(FACULTY_TAG);
            facultyElement.setTextContent(student.getFaculty());
            studentElement.appendChild(facultyElement);

            Element compositionElement = document.createElement(COMPOSITION_TAG);
            compositionElement.setTextContent(student.getComposition());
            studentElement.appendChild(compositionElement);

            Element roleElement = document.createElement(ROLE_TAG);
            roleElement.setTextContent(student.getRole());
            studentElement.appendChild(roleElement);
        }

        DOMSource source = new DOMSource(document);
        StreamResult result;
        try {
            result = new StreamResult(new FileWriter(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
        Transformer transformer;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException ex) {
            ex.printStackTrace();
            return;
        }
        try {
            transformer.transform(source, result);
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String args[]) {
        StudentDAO studentDAO = new StudentDAO();
        List<Student> studentList = studentDAO.getStudentList("resources\\example.xml");
        Iterator<Student> iterator = studentList.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
           
        }
        studentDAO.saveStudentList("resources\\example_writing.xml", studentList);
    }
}
