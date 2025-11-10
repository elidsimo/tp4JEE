package net.codejava;

import javax.persistence.*;

@Entity
@Table(name = "author")
public class Author extends Person {


    public Author() {}

    public Author(String name) {
        super(name);
    }

}


