package tavern.model;

public class DoubleVenison extends DishDecorator {

    public DoubleVenison(Dish dish) {
        super(dish);
    }
    
      @Override
    protected String getSaucename() {
        return " + Двойная порция оленины";
    }
    @Override
    protected int getSauceprice() {
        return 20;
    }
}
