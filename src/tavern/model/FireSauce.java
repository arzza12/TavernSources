package tavern.model;

public class FireSauce extends DishDecorator {

    public FireSauce(Dish dish) {
        super(dish);
    }

    public String getName() {
        return getDish().getName() + " + Огненный соус";
    }

    public int getPrice() {
        return getDish().getPrice() + 40;
    }
}
