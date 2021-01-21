package pl.testing;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import pl.testing.extension.IAExceptionIgnoreExtension;
import pl.testing.order.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;

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
        Assertions.assertThat(meal1).isNotSameAs(meal2);

    }
    @Tag("pizza")
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

    @ExtendWith(IAExceptionIgnoreExtension.class)
    @ParameterizedTest
    @ValueSource(ints = {1, 3, 5, 8})
    void mealPricesShouldBeLowerThan10(int price) {
        if(price > 5) {
            throw new IllegalArgumentException();
        }
        assertThat(price, greaterThan(0));
    }

    @TestFactory
    Collection<DynamicTest> calculateMealPrices() {
        Order order = new Order();
        order.addMealToOrder(new Meal(10,2,"pizza"));
        order.addMealToOrder(new Meal(14,3,"burger"));
        order.addMealToOrder(new Meal(22,5,"cake"));

        Collection<DynamicTest> dynamicTests = new ArrayList<>();

        for (int i = 0; i < order.getMeals().size(); i++) {
            int price = order.getMeals().get(i).getPrice();
            int quantity = order.getMeals().get(i).getQuantity();
            Executable executable = () -> {
                assertThat(calculatePrice(price,quantity), lessThan(120));
            };
            String name = "Test name : " + i;
            DynamicTest dynamicTest = DynamicTest.dynamicTest(name,executable);
            dynamicTests.add(dynamicTest);
        }

        return dynamicTests;
    }

    @Test
    void testMealSumPriceWithSpy() {
        //given
        Meal meal = spy(Meal.class);

        given(meal.getPrice()).willReturn(22);
        given(meal.getQuantity()).willReturn(2);
        //when
        int result = meal.sumPrice();
        //then
        assertThat(result, equalTo(44));
    }

    private int calculatePrice(int price, int quantity) {
        return price*quantity;
    }

//    @TestFactory
//    Collection<DynamicTest> dynamicTestCollection() {
//        return Arrays.asList(
//                DynamicTest.dynamicTest("dynamic test 1", () -> assertThat(5,lessThan(6))),
//                DynamicTest.dynamicTest("dynamic test 2",() ->assertEquals(4,2*2)));
//
//
//    }
}