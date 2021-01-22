package pl.testing.meal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.testing.meal.Meal;
import pl.testing.meal.MealRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.not;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

public class MealRepositoryTest {


    MealRepository mealRepository = new MealRepository();

    @BeforeEach
    void cleanUp() {
        mealRepository.getAllMeals().clear();
    }

    @Test
    void shouldBeAbleToAddMealToRepository() {
        //given
        Meal meal = new Meal(12,"hot-dog");
        //when
        mealRepository.add(meal);
        //then
        assertThat(mealRepository.getAllMeals().get(0), is(meal));

    }

    @Test
    void shouldBeAbleToRemoveMealFromRepository() {
        //given
        Meal meal = new Meal(12,"hot-dog");
        mealRepository.add(meal);
        //when
        mealRepository.delete(meal);
        //then
        assertThat(mealRepository.getAllMeals(), Matchers.not(contains(meal)));
    }

    @Test
    void shouldBeAbleToFindMealByExactName() {
        //given
        Meal meal = new Meal(12,"hot-dog");
        Meal meal2 = new Meal(12,"ho");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        //when
        List<Meal> results = mealRepository.findByName("hot-dog",true);
        //then
        assertThat(results.size(),is(1));
    }
    @Test
    void shouldBeAbleToFindMealByStartingLetters() {
        //given
        Meal meal = new Meal(12,"hot-dog");
        Meal meal2 = new Meal(12,"ho");
        mealRepository.add(meal);
        mealRepository.add(meal2);
        //when
        List<Meal> results = mealRepository.findByName("ho",false);
        //then
        assertThat(results.size(),is(2));

    }

    @Test
    void shouldBeAbleToFindMealByPrice(){
        //given
        Meal meal = new Meal(12,"hot-dog");
        mealRepository.add(meal);
        //when
        List<Meal> results = mealRepository.findByPrice(12);
        //then
        assertThat(results.size(),is(1));
    }
    


}
