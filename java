import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JenkinsWebhookTrigger {
    public static void main(String[] args) {
        try {
            // Jenkins webhook/job URL (replace with your Jenkins job URL)
            String jenkinsUrl = "http://localhost:8080/job/MyJob/build";

            // If Jenkins requires authentication, include username:token in the URL
            // Example: http://username:apitoken@localhost:8080/job/MyJob/build

            URL url = new URL(jenkinsUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configure connection
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // Optional: send JSON payload if your Jenkins job expects parameters
            String jsonPayload = "{\"parameter\": [{\"name\":\"BRANCH\",\"value\":\"main\"}]}";

            connection.setRequestProperty("Content-Type", "application/json");
            OutputStream os = connection.getOutputStream();
            os.write(jsonPayload.getBytes());
            os.flush();
            os.close();

            // Get response
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == 201 || responseCode == 200) {
                System.out.println("Jenkins job triggered successfully!");
            } else {
                System.out.println("Failed to trigger job. Check Jenkins logs.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
