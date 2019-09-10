package instances;

import java.util.ArrayList;

public abstract class BaseExpectedProduct {

    public abstract ArrayList<Product> getExpectedProduct(String planName, String count);

    public ArrayList<Product> getPaymentPageItems(String planName, String count) {
        return new ArrayList<>(getExpectedProduct(planName, count));
    }
}
