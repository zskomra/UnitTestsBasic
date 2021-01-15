package pl.testing;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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
    @ParameterizedTest
    @ValueSource(ints = {5,10,15,18})
    void mealPriceShouldBeLowerThan20(int price){
        assertThat(price, lessThan(20));
    }

    @ParameterizedTest
    @MethodSource("createMealsWithNameAndPrice")
    void burgerShouldHaveCorrectNameAndPrice(String name, int price) {
        assertThat(name, containsString("Pizza"));
        assertThat(price, greaterThan(15));
    }

    private static Stream<Arguments> createMealsWithNameAndPrice() {
        return Stream.of(
                Arguments.of("Pizza",20),
                Arguments.of("PizzaBurger",25)
        );
    }

    @ParameterizedTest
    @MethodSource("createCakeNames")
    void cakeNameShouldEndWithCake(String name) {
        assertThat(name, notNullValue());
        assertThat(name, endsWith("cake"));
    }

    private static Stream<String> createCakeNames(){
        List<String> cakeNames = Arrays.asList("Cheesecake","Puncake");
        return cakeNames.stream();
    }
}