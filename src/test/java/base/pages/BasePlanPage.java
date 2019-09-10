package base.pages;

import org.openqa.selenium.support.events.EventFiringWebDriver;

public class BasePlanPage extends BasePage {

    public BasePlanPage (EventFiringWebDriver driver) {super(driver);}

    public class Plan {

        private String planName;
        private double price;

        public String getPlanName() {
            return planName;
        }

        public void setPlanName(String planName) {
            this.planName = planName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }
    }
}
