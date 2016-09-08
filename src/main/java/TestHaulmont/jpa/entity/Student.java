package TestHaulmont.jpa.entity;

import javax.persistence.*;
import java.util.Date;

/**

 * Created by Vladimir on 22.08.2016.
 */
@Entity
@Table(name = "STUDENTS")
public class Student {
    @Id
    @Column(name = "id_student")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    private Date age;

    @ManyToOne
    @JoinColumn(name = "id_gang")
    Group group;

    private Integer groupNumber;

    public Student() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getAge() {
        return age;
    }

    public void setAge(Date age) {
        this.age = age;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
        this.groupNumber=group.getNumber();
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj==this) return true;
        if (!(obj instanceof Student))return false;
        Student s=(Student)obj;
        return id==s.id&&name.hashCode()==s.name.hashCode()&&surname.hashCode()==s.surname.hashCode();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result+Long.hashCode(id) ;
        result=prime*result+name.hashCode();
        result=prime*result+surname.hashCode();

        return result;
    }
}
