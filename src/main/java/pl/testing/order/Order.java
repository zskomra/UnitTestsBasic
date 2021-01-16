package pl.testing.order;

import pl.testing.Meal;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private List<Meal> meals = new ArrayList<>();

    public List<Meal> getMeals() {
        return meals;
    }

    public void addMealToOrder(Meal meal) {
        this.meals.add(meal);
    }

    public void removeMeal(Meal meal) {
        this.meals.remove(meal);
    }

    @Override
    public String toString() {
        return "Order{" +
                "meals=" + meals +
                '}';
    }
}
