package service;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import controller.Command;
import dao.DataAccessObject;
import dao.implemetation.StudentDAO;
import entity.Student;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Service {

    private final String[] ROW_NAME = {"FIO", "birthday", "football team", "faculty", "composition", "role"};
    private final int ROW_FIO = 0;
    private final int ROW_BIRTHDAY = 1;
    private final int ROW_FOOTBALL_TEAM = 2;
    private final int ROW_FACULTY = 3;
    private final int ROW_COMPOSITION = 4;
    private final int ROW_ROLE = 5;
    
    public List<Student> studentList;


    protected final DataAccessObject dao = new StudentDAO();

    abstract public List<Student> execute(String filePath);
    
    abstract public List<Student> search(String filePath, Map<String, String> args, Command command);
    
    abstract public List<Student> save(String saveFile, Map<String, String> args);
    
    abstract public List<Student> add(String filePath, Map<String, String> args);
    
    abstract public List<Student> get(String filePath);
    
    abstract public TableModel getList(List<Student> list);
        
    protected class ServiceTableModel extends AbstractTableModel {

        public ServiceTableModel(List<Student> source) {
            this.source = new ArrayList<>(source);
        }

        private ArrayList<Student> source;

        @Override
        public int getRowCount() {
            return source.size();
        }

        @Override
        public int getColumnCount() {
            return ROW_NAME.length;
        }

        @Override
        public String getColumnName(int column) {
            return ROW_NAME[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Student student = source.get(rowIndex);
            switch (columnIndex) {
                case ROW_FIO:
                    return student.getFirstName() + " " + student.getSecondName() + " " + student.getThirdName();
                case ROW_BIRTHDAY:
                    return student.getBirthday().toString();
                case ROW_COMPOSITION:
                    return student.getComposition();
                case ROW_FACULTY:
                    return student.getFaculty();
                case ROW_FOOTBALL_TEAM:
                    return student.getFootballClub();
                case ROW_ROLE:
                    return student.getRole();
                default:
                    return null;
            }
        }
    }
}
