package implementation;

import javax.swing.table.TableModel;

import controller.Command;
import entity.Composition;
import entity.Faculty;
import entity.Role;
import entity.Student;
import service.Service;
import view.InfoWindow;
import view.RemoveInfo;

import java.sql.Savepoint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataGetterService extends Service {
	private String role;
	private String first;
	private String second;
	private String third;
	private String composition;
	private String faculty;
	private String footballClub;
	private String birthday;
	private String file = "d:/workspace/Football/resources/file.xml";
	
    public List<Student> execute(String filePath) {
        List<Student> studentList = dao.getStudentList(filePath);
        dao.saveStudentList(file, studentList);
        
        return studentList;
    }
    
    public List<Student> get(String filePath) {
    	List<Student> studentList = dao.getStudentList(filePath);
    	
    	return studentList;
    }
    
    public List<Student> add(String filePath, Map<String, String> args) {
		List<Student> studentList = dao.getStudentList(file);
    	
    	Student student = new Student();
    	student.setFirstName(args.get("firstName"));
    	student.setSecondName(args.get("secondName"));
    	student.setThirdName(args.get("thirdName"));
    	student.setFootballClub(args.get("footballClub"));
    	DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
    	try {
    		 student.setBirthday(dateFormat.parse(args.get("date")));
        } catch (ParseException ex) {
             ex.printStackTrace();
             System.out.println("Date trouble!!!");
        }
    	
    	String faculty = args.get("faculty");
    	if (faculty.equals("FITY")) student.setFaculty(Faculty.FITY);
    	else if (faculty.equals("KSIS")) student.setFaculty(Faculty.KSIS);
    	else if (faculty.equals("FRE")) student.setFaculty(Faculty.FRE);
    	else if (faculty.equals("FTK")) student.setFaculty(Faculty.FTK);
    	else if (faculty.equals("FKP")) student.setFaculty(Faculty.FKP);
    	else if (faculty.equals("IEF")) student.setFaculty(Faculty.IEF);
        else System.out.println("Faculty trouble!!!!!!!!!!!");
    	
    	String role = args.get("role");
    	if (role.equals("GOALKEEPER")) student.setRole(Role.GOALKEEPER);
        else if (role.equals("FORWARD")) student.setRole(Role.FORWARD);
        else if (role.equals("HALFBACK")) student.setRole(Role.HALFBACK);
        else if (role.equals("QUARTERBACK")) student.setRole(Role.QUARTERBACK);
        else System.out.println("Role trouble!!!!!");
    	
    	String composition = args.get("composition");
    	if (composition.equals("MAIN")) student.setComposition(Composition.MAIN);
    	else if (composition.equals("RESERVE")) student.setComposition(Composition.RESERVE);
    	else System.out.println("Composition trouble!!!!!!!!");
    	
    	studentList.add(student);
    	System.out.println(studentList.size());
    	dao.saveStudentList(file, studentList);
    	return studentList;
    	
    }
    
    @Override
	public List<Student> search(String filePath, Map<String, String> args, Command command) {
    	List<Student> studentList = dao.getStudentList(file);
    	    	
    	if (args.get("firstName") != null & args.get("date") != null) {
    		if (command == Command.SEARCH)
    			searchByDate(studentList, args);
    		else removeByDate(studentList, args);
    	}
    	
    	else if (args.get("firstName") != null & args.get("faculty") != null) {
    		if (command == Command.SEARCH)
    			searchByFaculty(studentList, args);
        	else removeByFaculty(studentList, args);
    	}
    	
    	else if (args.get("firstName") != null & args.get("footballClub") != null) {
    		if (command == Command.SEARCH)
    			searchByFootballClub(studentList, args);
    		else removeByFootballClub(studentList, args);
    	}
    	
    	else if (args.get("role") != null & args.get("composition") != null) {
    		if (command == Command.SEARCH)
    			searchByRole(studentList, args);
    		else removeByRole(studentList, args);
    	}
    	
    	return studentList;
	}

	private void removeByRole(List<Student> studentList, Map<String, String> args) {
		role = args.get("role");
		composition = args.get("composition");
		List<Student> removeList = new ArrayList<>();
		removeList.addAll(studentList);
		Iterator<Student> iterator = removeList.iterator();
		int number = 0;
		
		while (iterator.hasNext()){
			if(!iterator.next().getRole().equals(role)){
				iterator.remove();
			}
			
		}
		
		Iterator<Student> iterator1 = removeList.iterator();
		
		while (iterator1.hasNext()){
			if(!iterator1.next().getComposition().equals(composition)){
				iterator1.remove();
			}
			else {
				number++;
			}
		}
		
		RemoveInfo remove = new RemoveInfo(removeList.size());
				
		studentList.removeAll(removeList);
		
	}

	private void removeByFootballClub(List<Student> studentList, Map<String, String> args) {
		first = args.get("firstName");
		second = args.get("secondName");
		third = args.get("thirdName");
		
		footballClub = args.get("footballClub");
		
		List<Student> removeList = new ArrayList<>();
		removeList.addAll(studentList);
		int number = 0;
		
		Iterator<Student> iterator1 = removeList.iterator();
		while (iterator1.hasNext()){
			if(!iterator1.next().getFirstName().equals(first)){
				iterator1.remove();
			}
		}
		Iterator<Student> iterator2 = removeList.iterator();
		while (iterator2.hasNext()){
			if(!iterator2.next().getSecondName().equals(second)){
				iterator2.remove();
			}
		}
		Iterator<Student> iterator3 = removeList.iterator();
		while (iterator3.hasNext()){
			if(!iterator3.next().getThirdName().equals(third)){
				iterator3.remove();
			}
		}
		
		Iterator<Student> iterator = removeList.iterator();
		while (iterator.hasNext()){
			if(!iterator.next().getFootballClub().equals(footballClub)){
				iterator.remove();
			}
			else {
				number++;
			}
		}
		RemoveInfo remove = new RemoveInfo(removeList.size());
		
		studentList.removeAll(removeList);
		
	}

	private void removeByFaculty(List<Student> studentList, Map<String, String> args) {
		first = args.get("firstName");
		second = args.get("secondName");
		third = args.get("thirdName");
		
		faculty = args.get("faculty");
		List<Student> removeList = new ArrayList<>();
		removeList.addAll(studentList);
		
		int number = 0;
		
		Iterator<Student> iterator1 = removeList.iterator();
		while (iterator1.hasNext()){
			if(!iterator1.next().getFirstName().equals(first)){
				iterator1.remove();
			}
		}
		
		Iterator<Student> iterator2 = removeList.iterator();
		while (iterator2.hasNext()){
			if(!iterator2.next().getSecondName().equals(second)){
				iterator2.remove();
			}
		}
		
		Iterator<Student> iterator3 = removeList.iterator();
		while (iterator3.hasNext()){
			if(!iterator3.next().getThirdName().equals(third)){
				iterator3.remove();
			}
		}
		
		Iterator<Student> iterator12 = removeList.iterator();
		
		while (iterator12.hasNext()){
			if(!iterator12.next().getFaculty().equals(faculty)){
				iterator12.remove();
			}
			else {
				number++;
			}
		}
		
		RemoveInfo remove = new RemoveInfo(removeList.size());		
		studentList.removeAll(removeList);
	}
	
	public void removeByDate(List<Student> studentList, Map<String, String> args) {
		first = args.get("firstName");
		second = args.get("secondName");
		third = args.get("thirdName");
		
		birthday = args.get("date");
		
		
		int number = 0;
		
		Iterator<Student> iterator1 = studentList.iterator();
		while (iterator1.hasNext()){
			if(iterator1.next().getFirstName().equals(first)){
				iterator1.remove();
				number++;
			}
		}
		
		Iterator<Student> iterator2 = studentList.iterator();
		while (iterator2.hasNext()){
			if(iterator2.next().getSecondName().equals(second)){
				iterator2.remove();
				number++;
			}
		}
		
		Iterator<Student> iterator3 = studentList.iterator();
		while (iterator3.hasNext()){
			if(iterator3.next().getThirdName().equals(third)){
				iterator3.remove();
				number++;
			}
		}
		
		Iterator<Student> iterator = studentList.iterator();
		while (iterator.hasNext()){
			if(iterator.next().getBirthday().equals(birthday)){
				iterator.remove();
				number++;
			}
		}

		RemoveInfo remove = new RemoveInfo(number);	
		
	}
	
	public void searchByDate(List<Student> studentList, Map<String, String> args) {
		first = args.get("firstName");
		second = args.get("secondName");
		third = args.get("thirdName");
		
		birthday = args.get("date");
		int number = 0;
		
		Iterator<Student> iterator1 = studentList.iterator();
		while (iterator1.hasNext()){
			if(!iterator1.next().getFirstName().equals(first)){
				iterator1.remove();
			}
		}
		
		Iterator<Student> iterator2 = studentList.iterator();
		while (iterator2.hasNext()){
			if(!iterator2.next().getSecondName().equals(second)){
				iterator2.remove();
			}
		}
		
		Iterator<Student> iterator3 = studentList.iterator();
		while (iterator3.hasNext()){
			if(!iterator3.next().getThirdName().equals(third)){
				iterator3.remove();
			}
		}
		
		Iterator<Student> iterator = studentList.iterator();
		while (iterator.hasNext()){
			if(!iterator.next().getBirthday().equals(birthday)){
				iterator.remove();
			}
		}
		
		
	}
	
	public void searchByFaculty(List<Student> studentList, Map<String, String> args) {
		first = args.get("firstName");
		second = args.get("secondName");
		third = args.get("thirdName");
		
		faculty = args.get("faculty");
		int number = 0;
		
		Iterator<Student> iterator1 = studentList.iterator();
		while (iterator1.hasNext()){
			if(!iterator1.next().getFirstName().equals(first)){
				iterator1.remove();
			}
		}
		
		Iterator<Student> iterator2 = studentList.iterator();
		while (iterator2.hasNext()){
			if(!iterator2.next().getSecondName().equals(second)){
				iterator2.remove();
			}
		}
		
		Iterator<Student> iterator3 = studentList.iterator();
		while (iterator3.hasNext()){
			if(!iterator3.next().getThirdName().equals(third)){
				iterator3.remove();
			}
		}
		
		
		Iterator<Student> iterator = studentList.iterator();
		
		while (iterator.hasNext()){
			if(!iterator.next().getFaculty().equals(faculty)){
				iterator.remove();
			}
			else {
				number++;
			}
		}
		
		
	}
	
	public void searchByRole(List<Student> studentList, Map<String, String> args) {
		role = args.get("role");
		composition = args.get("composition");
		Iterator<Student> iterator = studentList.iterator();
		int number = 0;
		
		
		
		while (iterator.hasNext()){
			if(!iterator.next().getRole().equals(role)){
				iterator.remove();
			}
			
		}
		
		
		Iterator<Student> iterator1 = studentList.iterator();
		
		while (iterator1.hasNext()){
			if(!iterator1.next().getComposition().equals(composition)){
				iterator1.remove();
			}
			else {
				number++;
			}
		}
	
	}
	
	public void searchByFootballClub(List<Student> studentList, Map<String, String> args) {		
		first = args.get("firstName");
		second = args.get("secondName");
		third = args.get("thirdName");
		
		footballClub = args.get("footballClub");
		int number = 0;
		
		Iterator<Student> iterator1 = studentList.iterator();
		while (iterator1.hasNext()){
			if(!iterator1.next().getFirstName().equals(first)){
				iterator1.remove();
			}
		}
		
		Iterator<Student> iterator2 = studentList.iterator();
		while (iterator2.hasNext()){
			if(!iterator2.next().getSecondName().equals(second)){
				iterator2.remove();
			}
		}
		
		Iterator<Student> iterator3 = studentList.iterator();
		while (iterator3.hasNext()){
			if(!iterator3.next().getThirdName().equals(third)){
				iterator3.remove();
			}
		}
		
		
		Iterator<Student> iterator = studentList.iterator();
		
		while (iterator.hasNext()){
			if(!iterator.next().getFootballClub().equals(footballClub)){
				iterator.remove();
			}
			else {
				number++;
			}
		}
		
	
	}

	public List<Student> save(String saveFile, Map<String, String> args) {
		List<Student> studentList = dao.getStudentList(file);
		dao.saveStudentList(saveFile, studentList);
		return null;
	}

	public TableModel getList(List<Student> list) {
		return new ServiceTableModel(list);
	}
	
	

	

	


	
	
}
