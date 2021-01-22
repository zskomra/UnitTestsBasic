package pl.testing.meal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MealRepository {

    private List<Meal> meals = new ArrayList<>();

    public void add(Meal meal) {
        meals.add(meal);
    }

    public List<Meal> getAllMeals() {
        return meals;
    }

    public void delete(Meal meal) {
        meals.remove(meal);
    }


    public List<Meal> findByName(String mealName, boolean exactMatch) {
        List<Meal> result;
        if(exactMatch) {
            result = meals.stream().filter(meal -> meal.getName()
                    .equals(mealName))
                    .collect(Collectors.toList());
        }else {
            result = meals.stream().filter(meal -> meal.getName()
                    .startsWith(mealName))
                    .collect(Collectors.toList());
        }
        return result;
    }

    public List<Meal> findByPrice(int price, SearchType type) {
        List<Meal> result = new ArrayList<>();
        result = getMeals(price, type, result);
        return result;
    }

    private List<Meal> getMeals(int price, SearchType type, List<Meal> result) {
        switch (type) {
            case HIGHER:
                result = meals.stream().filter(meal -> meal.getPrice() > price)
                        .collect(Collectors.toList());
                break;
            case EXACT:
                result = meals.stream().filter(meal -> meal.getPrice() == price)
                        .collect(Collectors.toList());
                break;
            case LESS:
                result = meals.stream().filter(meal -> meal.getPrice() < price)
                        .collect(Collectors.toList());
                break;
        }
        return result;
    }

    public List<Meal> find(String mealName, boolean exactMatch, int price, SearchType type) {
        List<Meal> nameMatches = findByName(mealName,exactMatch);
        List<Meal> result = getMeals(price,type,nameMatches);
        return result;
    }
//        List<Meal> result = new ArrayList<>();
//        if(exactMatch) {
//            result = meals.stream().filter(meal -> meal.getName()
//                    .equals(mealName))
//                    .collect(Collectors.toList());
//        }else {
//            result = meals.stream().filter(meal -> meal.getName()
//                    .startsWith(mealName))
//                    .collect(Collectors.toList());
//        }
//        return getMeals(price,type,result);
//    }
}
