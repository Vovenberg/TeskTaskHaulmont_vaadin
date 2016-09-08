import TestHaulmont.jpa.dao.GroupDAO;
import org.junit.Test;

import java.util.*;

/**
 * Created by Vladimir on 23.08.2016.
 */
public class TestDAO {
    @Test
    public void testStudents(){

        Map<Integer,String> map=new HashMap<>();
        map.put(5,"a");
        map.put(4,"b");
        map.put(3,"c");
        System.out.println(map);

       try {
           throw new NullPointerException();
       }
       catch (NullPointerException e){
           throw e;
       }
        finally {
           throw new IllegalAccessError();
       }
    }




}
