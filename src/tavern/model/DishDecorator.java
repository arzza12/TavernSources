package tavern.model;

public abstract class DishDecorator implements Dish {
    protected abstract String getSaucename();
    protected abstract int getSauceprice();

    private final Dish dish;
    
    public DishDecorator(Dish dish) {
        this.dish = dish;
    }

    @Override
    public final String getName() {
        return dish.getName() + getSaucename();
    }
     @Override
    public final int getPrice() {
        return dish.getPrice() + getSauceprice();
    }

}
