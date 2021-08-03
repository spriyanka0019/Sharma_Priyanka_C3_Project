import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void setUp(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("18:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant restaurantSpy = Mockito.spy(restaurant);
        LocalTime givenTime = LocalTime.now().plusHours(3);
        Mockito.when(restaurantSpy.getCurrentTime()).thenReturn(givenTime);
        boolean isRestaurantOpenOrNot = restaurantSpy.isRestaurantOpen();

        assertTrue(isRestaurantOpenOrNot);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant restaurantSpy = Mockito.spy(restaurant);
        LocalTime givenTime = LocalTime.now().plusHours(10);
        Mockito.when(restaurantSpy.getCurrentTime()).thenReturn(givenTime);
        boolean isRestaurantOpenOrNot = restaurantSpy.isRestaurantOpen();

        assertFalse(isRestaurantOpenOrNot);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    private void getAddToMenu() {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);

    }

    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        getAddToMenu();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        getAddToMenu();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        getAddToMenu();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French Fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //<<<<<<<<<<<<<<<<<<<<<<<SHOW TOTAL AMOUNT>>>>>>>>>>>>>>>>>
    @Test
    public void adding_items_in_the_list_should_show_the_total_amount_to_be_paid() {
        getAddToMenu();

        List<String> listOfSelectedItems = new ArrayList<>();
        listOfSelectedItems.add("Sweet corn soup");
        listOfSelectedItems.add("Vegetable lasagne");
        int expectedAmount = 388;
        int actualAmount = restaurant.showTotalAmount(listOfSelectedItems);

        assertEquals(expectedAmount,actualAmount);

    }

}