package pl.testing;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.*;

class MealTest {

    @Test
    void shouldReturnDiscountedPrice() {
        //given
        Meal meal = new Meal(35);
        //when
        int discountedPrice = meal.getDiscount(10);
        //then
        assertEquals(25,discountedPrice);
    }
    @Test
    void referencesToTheSameObjectShouldBeEqual(){
        //given
        Meal meal1= new Meal(35);
        Meal meal2=meal1;
        //then
        assertSame(meal1,meal2);
        //lub
        assertThat(meal1, sameInstance(meal2));
    }
    @Test
    void referencesToTheDifferentbjectShouldNotBeEqual(){
        //given
        Meal meal1= new Meal(35);
        Meal meal2= new Meal(10);
        //then
        assertNotSame(meal1,meal2);
        //lub
        assertThat(meal1, not(sameInstance(meal2)));
        //lub
        Assertions.assertThat(meal1).isSameAs(meal2);

    }

    @Test
    void twoMealShouldBeEqualWhenPriceAndNameAreTheSame() {
        //given
        Meal meal1 = new Meal(10,"Pizza");
        Meal meal2 = new Meal(10,"Pizza");
        //then
        assertEquals(meal1,meal2);
        //lub
        Assertions.assertThat(meal1).isEqualTo(meal2);
    }
    @Test
    void exceptionShouldBeThrownIfDiscountIsHigherThanThePrice(){
        //given
        Meal meal1 = new Meal(10,"Pizza");
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> meal1.getDiscount(20));
    }
}