package tavern.model;

public class FireSauce extends DishDecorator {

    public FireSauce(Dish dish) {
        super(dish);
    }

        @Override
    protected String getSaucename() {
        return " + Огненный соус аэаэа";
    }
    @Override
    protected int getSauceprice() {
        return 40;
    }
}
