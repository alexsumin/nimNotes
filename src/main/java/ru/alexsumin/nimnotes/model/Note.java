package ru.alexsumin.nimnotes.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notes")
@NamedQueries({
        @NamedQuery(
                name = Note.FindById,
                query = "select n from Note n where n.id = :id"
        )})
public class Note {

    public static final String FindById = "Note.findById";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id", updatable = false, insertable = false)
    private int id;

    @Column(columnDefinition = "text")
    private String caption;

    @Column(columnDefinition = "text")
    private String text;

    @Column(name = "last_edit", nullable = false)
    private Date lastEdit;

    @ManyToOne(cascade = {CascadeType.ALL})
    private Account account;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(Date lastEdit) {
        this.lastEdit = lastEdit;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


}
