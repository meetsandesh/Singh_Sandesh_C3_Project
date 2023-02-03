import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName) throws restaurantNotFoundException {
        for(Restaurant restaurant : restaurants) {
            if(restaurant.getName().equals(restaurantName)) {
                return restaurant;
            }
        }
        throw new restaurantNotFoundException(restaurantName);
    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public int getTotalOrderValue(String restaurantName, List<String> items) throws restaurantNotFoundException, itemNotFoundException {
        Restaurant restaurant = findRestaurantByName(restaurantName);
        return getTotalOrderValue(restaurant, items);
    }

    private int getTotalOrderValue(Restaurant restaurant, List<String> items) throws itemNotFoundException {
        int totalCost = 0;
        for(String itemName : items) {
            totalCost += findItemByName(restaurant, itemName).getPrice();
        }
        return totalCost;
    }

    private Item findItemByName(Restaurant restaurant, String itemName) throws itemNotFoundException {
        for(Item item : restaurant.getMenu()) {
            if(item.getName().equals(itemName)) {
                return item;
            }
        }
        throw new itemNotFoundException(itemName);
    }
}
