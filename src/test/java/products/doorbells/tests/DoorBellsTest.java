package products.doorbells.tests;

import base.pages.BaseOrderPage;
import base.pages.BasePaymentPage;
import base.pages.BasePlanPage;
import base.pages.BaseShoppingCartPage;
import base.tests.BasicTest;
import instances.Product;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import products.doorbells.DoorBellsExpectedProduct;
import products.doorbells.pages.DoorBellsOrderPage;
import products.doorbells.pages.DoorBellsPlanPage;
import utils.logs.LogForTest;
import utils.verify.Verify;

import java.util.ArrayList;

public class DoorBellsTest extends BasicTest {

    private DoorBellsPlanPage planPage;
    private DoorBellsOrderPage orderPage;
    private BaseShoppingCartPage shoppingCartPage;
    private BasePaymentPage paymentPage;

    @BeforeMethod
    @Override
    public void initPages() {
        planPage = new DoorBellsPlanPage(driver);
        orderPage = new DoorBellsOrderPage(driver);
        shoppingCartPage = new BaseShoppingCartPage(driver);
        paymentPage = new BasePaymentPage(driver);
    }

    private ArrayList<String> getProductNamesList() {
        ArrayList<String> productNames = new ArrayList<>();
        productNames.add("Video Doorbell");
        productNames.add("Door View Cam");
        productNames.add("Video Doorbell 2");
        productNames.add("Video Doorbell Pro");
        productNames.add("Video Doorbell Elite");
        return productNames;
    }

    @DataProvider
    public Object[][] getExpectedProductFeatures() {
        ArrayList<String> doorViewCamFeatures = new ArrayList<>();
        doorViewCamFeatures.add("Removable Battery");
        doorViewCamFeatures.add("1080p HD");
        doorViewCamFeatures.add("Adjustable Motion Detection");
        doorViewCamFeatures.add("5-min installation");


        ArrayList<String> videoDoorbellFeatures = new ArrayList<>();
        videoDoorbellFeatures.add("Internal Battery");
        videoDoorbellFeatures.add("720p HD");
        videoDoorbellFeatures.add("Adjustable Motion Detection");
        videoDoorbellFeatures.add("10-min installation");

        ArrayList<String> videoDoorbell2Features = new ArrayList<>();
        videoDoorbell2Features.add("Removable Battery");
        videoDoorbell2Features.add("1080p HD");
        videoDoorbell2Features.add("Adjustable Motion Detection");
        videoDoorbell2Features.add("10-min installation");

        ArrayList<String> videoDoorbellProFeatures = new ArrayList<>();
        videoDoorbellProFeatures.add("Hardwired");
        videoDoorbellProFeatures.add("1080p HD");
        videoDoorbellProFeatures.add("Customisable Motion Detection");
        videoDoorbellProFeatures.add("~ 15-min installation");

        ArrayList<String> videoDoorbellEliteFeatures = new ArrayList<>();
        videoDoorbellEliteFeatures.add("Power over Ethernet");
        videoDoorbellEliteFeatures.add("1080p HD");
        videoDoorbellEliteFeatures.add("Customisable Motion Detection");
        videoDoorbellEliteFeatures.add("~ 60-min installation");

        return new Object[][]{
                {"Door View Cam", doorViewCamFeatures},
                {"Video Doorbell", videoDoorbellFeatures},
                {"Video Doorbell 2", videoDoorbell2Features},
                {"Video Doorbell Pro", videoDoorbellProFeatures},
                {"Video Doorbell Elite", videoDoorbellEliteFeatures},
        };
    }

    @DataProvider
    public Object[][] getPlans() {
        return new Object[][]{
                {"Door View Cam", BASE_URL + "products/door-view-cam"},
                {"Video Doorbell", BASE_URL + "products/video-doorbell"},
                {"Video Doorbell 2", BASE_URL + "products/video-doorbell-2"},
                {"Video Doorbell Pro", BASE_URL + "products/video-doorbell-pro"},
                {"Video Doorbell Elite", BASE_URL + "products/video-doorbell-elite"},
        };
    }

    @DataProvider
    public Object[][] getExpectedProduct() {

        return new Object[][]{
                {"Video Doorbell", getVideoDoorbellProduct()},
                {"Door View Cam", getDoorViewCamProduct()},
                {"Video Doorbell 2", getVideoDoorbell2Product()},
                {"Video Doorbell Pro", getVideoDoorbellProProduct()},
                {"Video Doorbell Elite", getVideoDoorbellEliteProduct()},
        };
    }

    /**
     * PS: All tests working except checkGetBaseUrlAndCloseCookie, because I can not close cookie pop up. I am trying
     * use JS, xpath. Nothing can help me. Please click "Agree and Proceed" button manually. I disable headless mode
     * placed in BasicTest class.
     */

    @Test(priority = 1)
    public void checkGetBaseUrlAndCloseCookie() {
        //this test include step -Open Ring.com
        gotoPage(BASE_URL);
        waitForJSandJQueryToLoad();
        //planPage.checkAndCloseCookies(); - not working. Please paste your JS system script to use checkAndCloseCookies() method
    }

    @Test(priority = 2)
    public void checkProductNames () {
        //this test include steps -Open page with doorbells and check doorbell name fields
        gotoPage(BASE_URL + "pages/doorbells/");
        LogForTest.info("Checking Product names");
        Verify.verifyEquals(planPage.getProductNames(), getProductNamesList(),
                "Not expected plan names for Web Hosting");
    }

    @Test(priority = 3, dataProvider = "getExpectedProductFeatures")
    public void checkProductFeaturesTitles(String planName, ArrayList<String> featuresTextList) {
        //this test include steps -Open page with doorbells and doorbell name fields
        gotoPage(BASE_URL + "pages/doorbells/");
        Verify.verifyEquals(planPage.getFeaturesTextListByPlanName(planName), featuresTextList,
                "Not expected features for " + planName + " plan");
    }

    @Test(dataProvider = "getPlans", priority = 4)
    public void checkPlanPageBuyNowButtonUrl(String planName, String orderPageUrl) {
        //this additional test
        gotoPage(BASE_URL + "pages/doorbells/");
        LogForTest.info("Checking select plan button href attribute for " + planName);
        Verify.verifyEquals(planPage.getOrderPageUrlByPlanName(planName), orderPageUrl,
                "Wrong order page URL for " + planName);
    }

    @Test(priority = 5, dataProvider = "getExpectedProduct")
    public void buyProductTest(String planName, ArrayList<String> expectedOrderPageDetails) {

        //this test includes steps - Open Page with doorbells, - Check Price and doorbell name fields, - Push "Buy Now"
        //button for doorbell, - Change quantity, - Check correct price and name mapping for doorbell, - Push "Add to cart",
        //Check correct price and name mapping for doorbell, - Open cart and check that price, name of doorbell,
        //final amount and total price are correct.

        ArrayList<String> actualOrderPageDetails = new ArrayList<>();
        BasePlanPage.Plan currentPlan;
        BaseOrderPage.Plan currentOrderPagePlan;

        gotoPage(BASE_URL + "pages/doorbells/");

        //Get Product name and product price from plan page for compare in future
        currentPlan = planPage.getSelectedPlan(planName);
        planPage.selectPlanByName(planName);

        orderPage.changeProductQuantity();
        orderPage.clickAddToCartButton();
        String productQuantity = orderPage.getSelectedProductQuantity();
        //Get Product name and product price from order page for compare in future
        currentOrderPagePlan = orderPage.getSelectedPlan(planName);

        //Init product details on order page
        actualOrderPageDetails.add("Order page Product name:" + orderPage.getProductName());
        actualOrderPageDetails.add("Order page product quantity:" + productQuantity);
        orderPage.setTotalPrice(orderPage.calculatePlanPrice());

        ArrayList<Product> shoppingCartProducts;
        ArrayList<Product> paymentPageProducts;

        orderPage.clickShoppingCartButton();
        orderPage.waitURLContainsText("cart", "checkouts");

        LogForTest.info("Checking shopping-cart page");
        if (!driver.getCurrentUrl().contains("cart/")) {
            driver.get(BASE_URL + "cart/");
        }
        //Get Product name and product quantity from shopping cart page for compare in future
        shoppingCartProducts = shoppingCartPage.getShoppingCartItemsDescription();
        shoppingCartPage.setTotalPrice(shoppingCartPage.calculatePrice()); //Set total price to comparing
        shoppingCartPage.clickCheckOutButton();


        LogForTest.info("Checking payment page URL");
        Assert.assertTrue(driver.getCurrentUrl().contains("checkouts/"),
                "Not expected current URL");

        //Get Product name and product quantity from payment page for compare in future
        paymentPageProducts = paymentPage.getShoppingCartItemsDescription();
        paymentPage.setTotalPrice(paymentPage.calculatePrice()); //Set total price to comparing

        LogForTest.info("Checking Order page product details");
        Verify.verifyEquals(actualOrderPageDetails, expectedOrderPageDetails,
                "Wrong Order page product description!!");

        LogForTest.info("Checking plan monthly price from plan page and order page");

        comparePrices(currentPlan.getPrice(), currentOrderPagePlan.getPrice(),
                "Not equals plan monthly price from plan page and order page, " +
                        "on plan page: " + currentPlan.getPrice() + " and " + currentOrderPagePlan.getPrice() +
                        " on order page without discount");
        LogForTest.info("Checking color for price");

        LogForTest.info("Checking Shopping cart items");
        Verify.verifyEquals(shoppingCartProducts, new DoorBellsExpectedProduct()
                        .getExpectedProduct(planName, productQuantity),
                "SHOPPING CART PAGE: Wrong shopping cart items!");

        LogForTest.info("Checking Shopping cart items on Payment page");
        ArrayList<Product> expectedProducts = new DoorBellsExpectedProduct().
                getPaymentPageItems(planName, productQuantity);
        Verify.verifyEquals(paymentPageProducts, expectedProducts, "PAYMENT PAGE: Wrong shopping cart items!");

        LogForTest.info("Comparing Order page and Shopping cart total price");
        comparePrices(orderPage, shoppingCartPage);

        LogForTest.info("Comparing Shopping cart and Payment page total price");
        comparePrices(shoppingCartPage, paymentPage);

        LogForTest.info("Clear shopping cart");
        gotoPage(BASE_URL + "cart/");
        shoppingCartPage.clickRemoveButton();
    }

    private ArrayList<String> getVideoDoorbellProduct() {
        ArrayList<String> videoDoorbellProduct = new ArrayList<>();
        videoDoorbellProduct.add("Order page Product name:Video Doorbell");
        videoDoorbellProduct.add("Order page product quantity:3");
        return videoDoorbellProduct;
    }

    private ArrayList<String> getDoorViewCamProduct() {
        ArrayList<String> doorViewCamProduct = new ArrayList<>();
        doorViewCamProduct.add("Order page Product name:Door View Cam");
        doorViewCamProduct.add("Order page product quantity:3");
        return doorViewCamProduct;
    }

    private ArrayList<String> getVideoDoorbell2Product() {
        ArrayList<String> videoDoorbell2Product = new ArrayList<>();
        videoDoorbell2Product.add("Order page Product name:Video Doorbell 2");
        videoDoorbell2Product.add("Order page product quantity:3");
        return videoDoorbell2Product;
    }

    private ArrayList<String> getVideoDoorbellProProduct() {
        ArrayList<String> videoDoorbellProProduct = new ArrayList<>();
        videoDoorbellProProduct.add("Order page Product name:Video Doorbell Pro");
        videoDoorbellProProduct.add("Order page product quantity:3");
        return videoDoorbellProProduct;
    }

    private ArrayList<String> getVideoDoorbellEliteProduct() {
        ArrayList<String> videoDoorbellEliteProduct = new ArrayList<>();
        videoDoorbellEliteProduct.add("Order page Product name:Video Doorbell Elite");
        videoDoorbellEliteProduct.add("Order page product quantity:3");
        return videoDoorbellEliteProduct;
    }
}
