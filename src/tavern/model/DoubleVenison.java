package tavern.model;

public class DoubleVenison extends DishDecorator {

    public DoubleVenison(Dish dish) {
        super(dish);
    }

    public String getName() {
        return getDish().getName() + " + Двойная оленина";
    }

    public int getPrice() {
        return getDish().getPrice() + 20;
    }
}
