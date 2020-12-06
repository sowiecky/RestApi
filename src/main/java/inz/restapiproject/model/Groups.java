package inz.restapiproject.model;



import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private long users_id;

    @ManyToMany
    @JoinTable(name = "groups_has_lights",
    joinColumns = @JoinColumn(name = "groups_id"),
    inverseJoinColumns = @JoinColumn(name = "lights_id"))
    Set<Lights> lights = new HashSet<>();


    public Groups(){

    }

    public Groups(String name) {
        super();
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Lights> getLights() {
        return lights;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUsers_id() {
        return users_id;
    }

    public void setUsers_id(long users_id) {
        this.users_id = users_id;
    }
}
