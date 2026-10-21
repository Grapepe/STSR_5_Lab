package org.example.Model;

@Data
@ToSring
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    private int id;
    private String login;
    private byte[] password;

    public User(String login, byte[] password)
    {
        this.login = login;
        this.password = password;
    }
}
