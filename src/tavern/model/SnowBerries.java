package tavern.model;

public class SnowBerries extends DishDecorator {

    public SnowBerries(Dish dish) {
        super(dish);
    }

        @Override
    protected String getSaucename() {
        return " + Снежные ягоды";
    }
    @Override
    protected int getSauceprice() {
        return 5;
    }
}
