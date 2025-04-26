import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
    private static boolean isUnsafe(List<Integer> nums, int i, int j, int n) {
        return (i < n && j < n) && (nums.get(i) >= nums.get(j) || nums.get(j) - nums.get(i) > 3);
    }

    public static void main(String[] args) {
        List<List<Integer>> numsList = new ArrayList<>();

        String filePath = "input/inp2.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] numbers = line.split("\\s+");
                List<Integer> nums = new ArrayList<>();

                for (String num : numbers) {
                    nums.add(Integer.parseInt(num));
                }
                numsList.add(nums);
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        // Part 1

        int safeLevels = 0;

        for (List<Integer> nums : numsList) {
            int n = nums.size();
            boolean isSafe = true;
            boolean isIncreasing = nums.get(0) < nums.get(n - 1);

            for (int i = 0; i < n - 1; i++) {
                if (isIncreasing && isUnsafe(nums, i, i + 1, n)) {
                    isSafe = false;
                    break;
                } else if (!isIncreasing && isUnsafe(nums, i + 1, i, n)) {
                    isSafe = false;
                    break;
                }
            }

            if (isSafe) {
                safeLevels++;
            }
        }

        // Part 2

        int safeLevels2 = 0;

        for (List<Integer> nums : numsList) {
            int n = nums.size();
            boolean isIncreasing = nums.get(0) < nums.get(n - 1);
            boolean hasSkipped = false;

            boolean[] dp = new boolean[n];
            dp[0] = true;

            // if (isSafeDP(nums, 0, isIncreasing, hasSkipped, dp, n)) {
            // safeLevels2++;
            // }
        }

        System.out.println("Safe levels: " + safeLevels);
        System.out.println("Safe levels 2: " + safeLevels2);

        // Safe levels: 369
        // Safe levels 2: 428
    }
}
