package tavern.model;

public class SnowBerries extends DishDecorator {

    public SnowBerries(Dish dish) {
        super(dish);
    }

    public String getName() {
        return getDish().getName() + " + Снежные ягоды";
    }

    public int getPrice() {
        return getDish().getPrice() + 6;
    }
}
