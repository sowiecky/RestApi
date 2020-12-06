package inz.restapiproject.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lights")
public class Lights {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String serial;
    private String name;

    @ManyToMany(mappedBy = "lights")
    private Set<Groups> groups = new HashSet<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
