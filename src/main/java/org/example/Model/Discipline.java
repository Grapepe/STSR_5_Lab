package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Discipline implements Serializable {
    private int id;
    private String name;
    private String teacher;

    public Discipline(String name, String teacher) {
        this.name = name;
        this.teacher = teacher;
    }
}