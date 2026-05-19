package tavern.model;

public abstract class DishDecorator implements Dish {

    private Dish dish;
    public DishDecorator(Dish dish) {
        this.dish = dish;
    }

    public Dish getDish(){
        return dish;
    }

}
