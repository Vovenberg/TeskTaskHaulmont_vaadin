package TestHaulmont.jpa;

import TestHaulmont.jpa.dao.GroupDAO;
import TestHaulmont.jpa.dao.StudentDAO;

/**
 * Created by Vladimir on 23.08.2016.
 */
public class Factory {
     GroupDAO groupDAO;
     StudentDAO studentDAO;
    static Factory instance;


    public static Factory getInstance(){
        instance=new Factory();
        return instance;
    }

    public GroupDAO getGroupDAO(){
        groupDAO=new GroupDAO();
        return groupDAO;
    }
    public StudentDAO getStudentDAO(){
        studentDAO=new StudentDAO();
        return studentDAO;
    }

}
