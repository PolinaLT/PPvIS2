package controller;

import javax.swing.table.TableModel;

import entity.Student;
import implementation.DataGetterService;
import service.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private Map<Command, Service> executors;

    public Controller() {
        executors = new HashMap<>();
        executors.put(Command.GET_DATA, new DataGetterService());
        executors.put(Command.SEARCH, new DataGetterService());
        executors.put(Command.REMOVE, new DataGetterService());
        executors.put(Command.ADD, new DataGetterService());
        executors.put(Command.SAVE, new DataGetterService());
        executors.put(Command.GET_STUDENTLIST, new DataGetterService());
        executors.put(Command.GET_LIST, new DataGetterService());
    }

    public List<Student> execute(Command command, String filePath, Map<String, String> args) {
        switch (command) {
            case GET_DATA:
                return executors.get(Command.GET_DATA).execute(filePath);
            case SEARCH:
            	return executors.get(Command.SEARCH).search(filePath, args, Command.SEARCH);
            case REMOVE:
            	return executors.get(Command.REMOVE).search(filePath, args, Command.REMOVE);
            case ADD:
            	return executors.get(Command.ADD).add(filePath, args);
            case SAVE:
            	return executors.get(Command.SAVE).save(filePath, args);
            default: return null;
        }
    }
    
    public TableModel getTableModel(Command command, List<Student> list) {
    	switch (command) {
    		case GET_LIST:
    			return executors.get(Command.GET_LIST).getList(list);
		    default: return null;
    	}
    }
    
    public List<Student> execute(Command command, String filePath) {
		switch (command) {
			case GET_STUDENTLIST:
				return executors.get(Command.GET_STUDENTLIST).get(filePath);
			default: return null;
		}
    }
}
