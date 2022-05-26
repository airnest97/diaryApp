package diary;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.*;

public class DiaryTest {
    private User ernest;
    private Diary diary;
    DiaryApp dukunDiary;
    @BeforeMethod
    public void setUp() {
        ernest = new User();
        diary = new Diary(ernest);
        dukunDiary= new DiaryApp();
    }

    @Test
    void diaryCanBeCreatedTest(){
        assertNotNull(diary);
    }

    @Test
    public void createEntryTest(){
        diary.registerEntry("Hello World", "Sending a big hello to the world");
        diary.registerEntry("Hello Jerusalem", "We are coming to build our city");
        assertEquals(2, diary.numberOfEntries());
    }

    @Test
    public void findEntryTest(){
        diary.registerEntry("Hello World", "Sending a big hello to the world");
        diary.registerEntry("Hello Jerusalem", "We are coming to build our city");
        Entries entry = diary.findEntryBy("Hello World");
        assertEquals("Hello World", entry.getTitle());
        assertEquals("Sending a big hello to the world", entry.getBody());
    }

    @Test
    public void deleteEntryTest(){
        diary.registerEntry("Hello World", "Sending a big hello to the world");
        diary.registerEntry("Hello Jerusalem", "We are coming to build our city");
        diary.deleteEntry(diary.findEntryBy("Hello World"));
        assertNull(diary.findEntryBy("Hello World"));
    }

    @Test
    public void signIntoDiaryTest(){
        dukunDiary.createAccount("kay@gmail.com", "kayCool", 1234);
        assertTrue(dukunDiary.userExists("kay@gmail.com", "kayCool", 1234));
    }

    @Test
    public void noDuplicateEmailTest(){
        dukunDiary.createAccount("kay@gmail.com", "kayCool", 1234);
        assertTrue(dukunDiary.userExists("kay@gmail.com", "kayCool", 1234));
        dukunDiary.createAccount("kay@gmail.com", "metSol", 4321);
        assertFalse(dukunDiary.userExists("kay@gmail.com", "metSol", 4321));
    }

    @Test
    public void lockAndUnlockTest(){
        dukunDiary.createAccount("kay@gmail.com", "kayCool", 1234);
        Diary diary = dukunDiary.myDiary("kay@gmail.com");
        assertFalse(diary.isLocked());

        diary.lock();
        assertTrue(diary.isLocked());

        diary.unlock(1234);
        assertFalse(diary.isLocked());
    }

    @Test
    public void diaryAppCanHaveMultipleUsers(){
        dukunDiary.createAccount("kay@gmail.com", "kayCool", 1234);
        dukunDiary.createAccount("tola@gmail.com", "tolly", 4321);
        assertEquals(2, dukunDiary.numberOfUsers());
    }

    @Test
    public void unlockBeforeCreatingEntry(){
        Diary myDiary = dukunDiary.createAccount("kay@gmail.com", "kayCool", 1234);
        myDiary.lock();
        myDiary.registerEntry("Hello World", "Sending a big hello to the world");
        assertEquals(0, myDiary.numberOfEntries());
    }

    @Test
    public void unlockedBeforeFindingEntry(){
        Diary myDiary = dukunDiary.createAccount("kay@gmail.com", "kayCool", 1234);
        myDiary.registerEntry("Hello World", "Sending a big hello to the world");
        assertEquals(1, myDiary.numberOfEntries());
        myDiary.lock();
        Entries entry = myDiary.findEntryBy("Hello World");
        assertNull(entry);
    }
}
