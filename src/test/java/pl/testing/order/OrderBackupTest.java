package pl.testing.order;

import org.junit.jupiter.api.*;
import pl.testing.meal.Meal;

import java.io.FileNotFoundException;
import java.io.IOException;

class OrderBackupTest {

    static OrderBackup cut;

    @BeforeAll
    static void setUp() throws FileNotFoundException {
        cut = new OrderBackup();
        cut.createFile();
    }
    @BeforeEach
    void appendAtTheStartOfTheLine() throws IOException {
        cut.getWriter().append("New Order: ");
    }
    @AfterEach
    void appendAtTheEndOfTheLine() throws IOException {
        cut.getWriter().append(" backed up");
    }

    @Test
    void backupOrderWithOneMeal () throws IOException {
        //given
        Meal meal = new Meal(6,"pizza");
        pl.testing.order.Order order = new Order();
        order.addMealToOrder(meal);
        //when
        cut.backupOrder(order);
        //then
        System.out.println("Order: " + order.toString() + " backed up");
    }

    @AfterAll
    static void tearDown() throws IOException {
        cut.closeFile();
    }

}