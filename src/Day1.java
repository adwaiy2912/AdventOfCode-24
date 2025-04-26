import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Day1 {
    private static void getInput() {
        String sessionCookie = "53616c7465645f5ff5c63fedbc5e7416e02ee1e9849ff30947302bff98d03fd95f862e6527bfa84682e1f3f64a558a97314961e65f16a38f1244f133c69dcac0";
        String inputUrl = "https://adventofcode.com/2020/day/1/input";
        String outFile = "input1.txt";

        InputManager.readWriteInput(sessionCookie, inputUrl, outFile);
    }

    public static void main(String[] args) {
        getInput();

        List<Integer> lst1 = new ArrayList<>();
        List<Integer> lst2 = new ArrayList<>();

        String filePath = "input/inp1.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {

                String[] numbers = line.split("\\s+");
                if (numbers.length == 2) {
                    int num1 = Integer.parseInt(numbers[0]);
                    int num2 = Integer.parseInt(numbers[1]);

                    lst1.add(num1);
                    lst2.add(num2);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        Collections.sort(lst1);
        Collections.sort(lst2);

        int len = lst1.size();

        // Part 1

        int totalDiff = 0;
        for (int i = 0; i < len; i++) {
            totalDiff += Math.abs(lst1.get(i) - lst2.get(i));
        }
        System.out.println("Total difference: " + totalDiff);

        // Part 2

        long similarityScore = 0;
        HashMap<Integer, Integer> occuranceMap = new HashMap<>();

        for (int i = 0; i < len; i++) {
            int occurance = occuranceMap.getOrDefault(lst2.get(i), 0);
            occuranceMap.put(lst2.get(i), occurance + 1);
        }

        for (int i = 0; i < len; i++) {
            int num = lst1.get(i);
            similarityScore += num * (occuranceMap.get(num) == null ? 0 : occuranceMap.get(num));
        }
        System.out.println("Similarity score: " + similarityScore);
    }
}
