import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.alexsumin.nimnotes.model.Account;
import ru.alexsumin.nimnotes.model.Note;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class HibernateTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        emf = Persistence.createEntityManagerFactory("TestPersistenceUnit");
        em = emf.createEntityManager();
    }

    @After
    public void tearDown() throws Exception {
        if (em != null) em.close();
        if (emf != null) emf.close();
    }

    @Test
    public void testUser() throws Exception {
        em.getTransaction().begin();

        Account account = new Account();
        account.setLogin("root@root.ru");
        account.setEncryptedPassword("password");

        em.persist(account);

        em.getTransaction().commit();

        assertEquals("root@root.ru", em.find(Account.class, account.getId()).getLogin());
    }

    @Test
    public void testNotes() throws Exception {
        em.getTransaction().begin();

        Account account = new Account();
        account.setLogin("test@user.com");
        account.setEncryptedPassword("testpass");

        em.persist(account);

        Note note = new Note();
        note.setCaption("first note");
        note.setText("Hello this is test-note! Can you see me?");
        note.setLastEdit(new Date());
        note.setAccount(account);

        em.persist(note);


        em.getTransaction().commit();

        em.refresh(account);

        List<Note> notes = account.getNotes();
        assertNotNull(notes);
        assertEquals(1, notes.size());
        assertEquals(note, notes.get(0));
        assertEquals("Hello this is test-note! Can you see me?", notes.get(notes.size() - 1).getText());
    }

    @Test
    public void ShowAllUsers() {
        em.getTransaction().begin();


        for (Account u : em.createNamedQuery(Account.ALL_USERS, Account.class).getResultList()) {
            System.out.println(u.getId() + " email = " + u.getLogin());
        }
    }


    @Test
    public void findNoteById() {
        em.getTransaction().begin();

        Account account = new Account();
        account.setLogin("other1@example.com");
        account.setEncryptedPassword("examplepass1");


        em.persist(account);


        Note note = new Note();
        note.setAccount(account);
        note.setCaption("this is note");
        note.setText("some text");
        note.setLastEdit(new Date());

        em.persist(note);
        em.getTransaction().commit();

        Note otherNote = em.find(Note.class, note.getId());
        System.out.println(otherNote.getText());
        assertEquals("some text", otherNote.getText());

    }


    @Test
    public void updateTest() {
        em.getTransaction().begin();


        Note note = em.createNamedQuery(Note.FindById, Note.class)
                .setParameter("id", 1).getSingleResult();
        int temp = note.getId();
        System.out.println(note.getText());

        String s = "some changed text";
        note.setText(s);

        em.persist(note);
        em.getTransaction().commit();


        Note updatedNote = em.find(Note.class, temp);
        assertEquals(s, updatedNote.getText());


    }

    @Test
    public void findUserTest() {
        Account user = em.createNamedQuery(Account.FindById, Account.class).setParameter("id", 1).getSingleResult();

        System.out.println(user);
    }


    @Test
    public void testList() {
        List<Note> list = em.createQuery("from Note").getResultList();
        for (Note note : list) {
            System.out.println(note);
        }
    }
}

