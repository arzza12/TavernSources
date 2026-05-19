package tavern.model;

public class NordicFlatbread extends DishDecorator {

    public NordicFlatbread(Dish dish) {
        super(dish);
    }

    public String getName() {
        return getDish().getName() + " + Нордский лаваш";
    }

    public int getPrice() {
        return getDish().getPrice() + 7;
    }
}
