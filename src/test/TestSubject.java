import model.Report;
import model.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestSubject {
    Subject testSubject;
    Report testReport;
    Report testReport1;
    Report testReport2;

    @BeforeEach
    void setUp() {
        testSubject = new Subject();
    }


    @Test
    void testAddObserver() {
        testReport = new Report();
        testSubject.addObserver(testReport);
    }

//    @Test
//    void testRemoveobserver() {
//        testReport1 = new Report();
//        testReport2 = new Report();
//        testSubject.addObserver(testReport1);
//        testSubject.addObserver(testReport2);
//        testSubject.removeobserver(testReport1);
//        assertEquals(0, testReport2.getItemCount());
//    }

    @Test
    void testNotifyObservers() {
    }
}
