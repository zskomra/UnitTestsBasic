package pl.testing.order;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pl.testing.meal.Meal;
import pl.testing.extension.BeforeAfterExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(BeforeAfterExtension.class)
class OrderTest {

    private Order order;

    @BeforeEach
    void setUp(){
        order = new Order();
    }

    @Test
    void testAssertArrayEquals() {
        //given
        int [] ints1 = {1,2,3};
        int [] ints2 = {1,2,3};
        //then
        assertArrayEquals(ints1,ints2);
    }

    @Test
    void mealListShouldBeEmptyAfterCreationOrder() {
        //then
        assertThat(order.getMeals(), empty());
        assertThat(order.getMeals().size(), equalTo(0));
        assertThat(order.getMeals(), hasSize(0));
        MatcherAssert.assertThat(order.getMeals(), emptyCollectionOf(Meal.class));
    }

    @Test
    void addingMealToOrderShouldIncreaseOrderSize() {
        //given
        Meal meal = new Meal(20);
        //when
        order.addMealToOrder(meal);
        //then
        assertThat(order.getMeals(), hasSize(1));
        assertThat(order.getMeals(), contains(meal));
        assertThat(order.getMeals(), hasItem(meal));
        assertThat(order.getMeals().get(0).getPrice(), equalTo(20));
    }

    @Test
    void removingMealFromOrderShouldDecreaseOrderSize() {
        //given
        Meal meal = new Meal(20);
        order.addMealToOrder(meal);
        //when
        order.removeMeal(meal);
        //then
        assertThat(order.getMeals(), hasSize(0));
        assertThat(order.getMeals(), not(contains(meal)));

    }
    @Test
    void mealsShouldBeInCorrectOrderAfterAddingThemToOrder() {
        //given
        Meal meal = new Meal(20, "sandwich");
        Meal meal2 = new Meal(15, "burger");
        //when
        order.addMealToOrder(meal);
        order.addMealToOrder(meal2);
        //then
        assertThat(order.getMeals(), containsInAnyOrder(meal, meal2));
    }
    @Test
    void testIfTwoMealListAreTheSame(){
        //given
        Meal meal = new Meal(20, "sandwich");
        Meal meal2 = new Meal(15, "burger");
        Meal meal3 = new Meal(30,"Pizza");
        //when
        List<Meal> meals1 = Arrays.asList(meal,meal2);
        List<Meal> meals2 = Arrays.asList(meal,meal2);
        //then
        assertThat(meals1, is(meals2));
    }

}