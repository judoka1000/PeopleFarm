package com.amazone.peoplefarm.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String gender;
    int sprite;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person", cascade = CascadeType.ALL)
    List<Status> status = new ArrayList<Status>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person", cascade = CascadeType.ALL)
    List<Abilities> abilities = new ArrayList<Abilities>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getSprite() {
        return sprite;
    }

    public void setSprite(int sprite) {
        this.sprite = sprite;
    }

    public List<Status> getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        status.setPerson(this);
        this.status.add(status);
    }

    public List<Abilities> getAbilities() {
        return abilities;
    }

    public void setAbilities(Abilities abilities) {
        abilities.setPerson(this);
        this.abilities.add(abilities);
    }


}
