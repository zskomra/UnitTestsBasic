package pl.testing.order;

import pl.testing.meal.Meal;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private OrderStatus orderStatus;

    private List<Meal> meals = new ArrayList<>();

    public List<Meal> getMeals() {
        return meals;
    }

    public void changeOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }
}
