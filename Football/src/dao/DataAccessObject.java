package dao;

import java.util.List;

import entity.Student;

public interface DataAccessObject {

    List<Student> getStudentList(String filePath);

    void saveStudentList(String filePath, List<Student> studentList);
}
