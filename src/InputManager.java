import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;

public class InputManager {

    private static HttpResponse<String> fetchProblemInput(String sessionCookie, String inputUrl) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(inputUrl))
                .header("Cookie", "session=" + sessionCookie)
                .header("User-Agent", "Java Advent Input Manager")
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static void writeInputToFile(String input, String fileName) throws IOException {
        Path inputDirectory = Path.of("input");
        Path filePath = inputDirectory.resolve(fileName);

        // Ensure the input directory exists
        Files.createDirectories(inputDirectory);

        Files.writeString(filePath, input, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void readWriteInput(String sessionCookie, String inputURL, String outFile) {
        try {
            // Fetch input from Advent of Code
            HttpResponse<String> response = fetchProblemInput(sessionCookie, inputURL);

            if (response.statusCode() == 200) {
                // Write input to file
                writeInputToFile(response.body(), outFile);
                System.out.println("Input successfully written to " + outFile);
            } else {
                System.err.println("Failed to fetch input. HTTP Status Code: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
