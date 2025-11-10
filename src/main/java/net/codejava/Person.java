package net.codejava;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "person_gen")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    protected Person() {}

    protected Person(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}