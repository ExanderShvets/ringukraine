package utils.reporting;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import utils.logs.LogForTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class PostSender {
    /**
     * @param anyObject FailedTestReport or FinishedSuiteReport or FailedTestEvent
     *                  method for sending POST request to Notifier,
     *                  dependence on class will be send mail or added event to DB
     */
    public void sendPost(Object anyObject) {
        String postUrl = "";
        if (anyObject instanceof FinishedSuiteReport) {
            postUrl = "http://localhost:4567/send-failed-test-report";
        } else if (anyObject instanceof FinishedSuiteReport) {
            postUrl = "http://localhost:4567/send-finished-suite-report";
        } else if (anyObject instanceof FailedTestEvent) {
            postUrl = "http://localhost:4567/add-new-event-to-db";
        }

        Gson gson = new Gson();
        StringEntity postingString = null;
        HttpPost post = new HttpPost(postUrl);
        try {
            postingString = new StringEntity(gson.toJson(anyObject));
        } catch (UnsupportedEncodingException e) {
            System.out.println("Can't parse to json((\n\n" + e.getMessage());
        }
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");

        if (postUrl.equals("http://localhost:4567/send-finished-suite-report")) {
            sendPost(post);
        } else {
            Thread thread = new Thread(() -> sendPost(post));
            thread.start();
        }
    }

    private void sendPost(HttpPost post) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpResponse response = httpClient.execute(post);
            String responseStatusCode = response.getStatusLine() + "";
            System.out.println("RESPONSE " + responseStatusCode);
            if (!responseStatusCode.contains("200")) {
                printPostWhenErrorOccur(post);
            }
        } catch (IOException e) {
            printPostWhenErrorOccur(post);
            LogForTest.LOGGER.warn("Can't send post ", e);
            e.printStackTrace();
        }
    }

    private void printPostWhenErrorOccur(HttpPost post) {
        try {
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(post.getEntity().getContent()));

            StringBuffer result = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            LogForTest.LOGGER.info("Not sent request:" +
                    System.lineSeparator() +
                    "___________________________________________" +
                    System.lineSeparator() +
                    result +
                    System.lineSeparator() +
                    "___________________________________________"
            );
        } catch (Exception err) {
            LogForTest.LOGGER.warn("Can't read post body, occur error", err);
        }
    }
}
