package TestHaulmont.jpa.entity;

import javax.persistence.*;

/**
 * Created by Vladimir on 22.08.2016.
 */
@Entity
@Table(name = "GANGS")
public class Group {
    @Id
    @Column(name = "id_gang")
    private Long id;
    @Column(name = "number")
    private Integer number;
    @Column(name = "fac")
    private String facultet;

    public Group() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFacultet() {
        return facultet;
    }

    public void setFacultet(String facultet) {
        this.facultet = facultet;
    }
}
