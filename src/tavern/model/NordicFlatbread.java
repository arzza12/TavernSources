package tavern.model;

public class NordicFlatbread extends DishDecorator {

    public NordicFlatbread(Dish dish) {
        super(dish);
    }

        @Override
    protected String getSaucename() {
        return " +  Нордская лепёшка";
    }
    @Override
    protected int getSauceprice() {
        return 7;
    }
}
