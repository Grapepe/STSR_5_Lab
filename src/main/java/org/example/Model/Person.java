package org.example.Model;

@Data
@ToSring
@AllArgsConstructor
@NoArgsConstructor
public class Person
{
    private int id;
    private String name;
    private String phone;
    private String email;

    public Person(String name, String phone, String email)
    {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public Person(int id, String name, String phone, String email)
    {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
}
