import org.junit.jupiter.api.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    RestaurantService service = new RestaurantService();
    Restaurant restaurant;

    @BeforeEach
    public void setupTestCase() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Alfredo Pasta", 200);
        restaurant.addToMenu("Vegetable Maggie", 50);
    }


    //>>>>>>>>>>>>>>>>>>>>>>SEARCHING<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        Restaurant searchedRestaurant = service.findRestaurantByName("Amelie's cafe");
        assertEquals(restaurant.getName(), searchedRestaurant.getName());
        assertEquals(restaurant.openingTime, searchedRestaurant.openingTime);
        assertEquals(restaurant.closingTime, searchedRestaurant.closingTime);
    }

    //You may watch the video by Muthukumaran on how to write exceptions in Course 3: Testing and Version control: Optional content
    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class, () -> service.findRestaurantByName("Pantry d'or"));
    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>




    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Amelie's cafe");
        assertEquals(initialNumberOfRestaurants-1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class,()->service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1(){
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales","Chennai",LocalTime.parse("12:00:00"),LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1,service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>TOTAL COST OF ORDER<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void get_total_order_value_should_return_total_cost_of_order_if_order_is_not_empty() throws restaurantNotFoundException, itemNotFoundException {
        String restaurantName = "Amelie's cafe";
        List<String> items = new ArrayList<>();
        items.add("Sweet corn soup");
        items.add("Alfredo Pasta");
        int totalCost = service.getTotalOrderValue(restaurantName, items);
        assertEquals(319, totalCost);
    }

    @Test
    public void get_total_order_value_should_return_updated_total_cost_of_order_if_an_item_is_added_in_the_order() throws restaurantNotFoundException, itemNotFoundException {
        String restaurantName = "Amelie's cafe";
        List<String> items = new ArrayList<>();
        items.add("Sweet corn soup");
        items.add("Alfredo Pasta");
        int totalCost = service.getTotalOrderValue(restaurantName, items);
        assertEquals(319, totalCost);

        //add more items
        items.add("Vegetable Maggie");
        int totalCostAfterAddingItem3 = service.getTotalOrderValue(restaurantName, items);
        assertEquals(369, totalCostAfterAddingItem3);
    }

    @Test
    public void get_total_order_value_should_return_updated_total_cost_of_order_if_an_item_is_removed_from_the_order() throws restaurantNotFoundException, itemNotFoundException {
        String restaurantName = "Amelie's cafe";
        List<String> items = new ArrayList<>();
        items.add("Sweet corn soup");
        items.add("Alfredo Pasta");
        items.add("Vegetable Maggie");
        int totalCost = service.getTotalOrderValue(restaurantName, items);
        assertEquals(369, totalCost);

        //remove an item
        items.remove("Alfredo Pasta");
        int totalCostAfterAddingItem3 = service.getTotalOrderValue(restaurantName, items);
        assertEquals(169, totalCostAfterAddingItem3);
    }


    //<<<<<<<<<<<<<<<<<<<<<<TOTAL COST OF ORDER>>>>>>>>>>>>>>>>>>>>>>>>>>
}