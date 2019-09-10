package products.doorbells;

import instances.BaseExpectedProduct;
import instances.Product;

import java.util.ArrayList;
import java.util.Collections;

public class DoorBellsExpectedProduct extends BaseExpectedProduct {
    /**
     * USE ONLY IN SHOPPING CART
     * return expected doorbell product
     *
     * @param productName selected plan name
     * @param count   selected product count
     * @return doorbell product
     */
    @Override
    public ArrayList<Product> getExpectedProduct(String productName, String count) {
        Product doorBell = new Product();
        doorBell.setName(productName);

        ArrayList<Product> products = new ArrayList<>();
        products.add(doorBell);
        //noinspection unchecked
        Collections.sort(products);
        return products;
    }
}
