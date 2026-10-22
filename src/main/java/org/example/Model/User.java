package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable
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