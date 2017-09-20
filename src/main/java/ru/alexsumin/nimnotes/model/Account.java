package ru.alexsumin.nimnotes.model;


import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = Account.FindByLogin,
                query = "select a from Account a where a.login = :login"
        ),
        @NamedQuery(name = Account.ALL_USERS,
                query = "select a from Account a"),
        @NamedQuery(
                name = Account.FindById,
                query = "select a from Account a where a.id = :id"
        )

})
@Table(name = "accounts")
public class Account {
    public static final String ALL_USERS = "Account.allUsers";
    public static final String FindByLogin = "Account.findByEmail";
    public static final String FindById = "Account.findById";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", updatable = false, insertable = false)
    private int id;

    @Column(unique = true)
    private String login;

    //TODO: Spring security!
    @Column(name = "encrypted_password")
    private String encryptedPassword;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private List<Note> notes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
