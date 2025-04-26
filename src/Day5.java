import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day5 {
    public static void main(String[] args) {
        Map<Integer, Set<Integer>> map = new HashMap<>();

        String filePath1 = "input/inp5.0.txt";
        String filePath2 = "input/inp5.1.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath1))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] numbers = line.split("\\|");

                int num1 = Integer.parseInt(numbers[0]);
                int num2 = Integer.parseInt(numbers[1]);

                if (!map.containsKey(num1)) {
                    HashSet<Integer> set = new HashSet<>();
                    set.add(num2);
                    map.put(num1, set);
                } else {
                    map.get(num1).add(num2);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        int pageSum = 0;
        int pageSum2 = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath2))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] numbers = line.split(",");
                int n = numbers.length;

                int midPage = Integer.parseInt(numbers[n / 2]);
                boolean flag = true;

                for (int i = 0; i < n; i++) {
                    int num = Integer.parseInt(numbers[i]);
                    int pageCount = 0;

                    for (int j = i - 1; j >= 0; j--) {
                        if (map.containsKey(num) && map.get(num).contains(Integer.parseInt(numbers[j]))) {
                            flag = false;
                            break;
                        }
                    }

                    for (int j = 0; j < n; j++) {
                        if (j != i && map.get(Integer.parseInt(numbers[j])).contains(num)) {
                            pageCount++;
                        }
                    }

                    if (pageCount == n / 2) {
                        midPage = num;
                    }
                }

                if (flag) {
                    pageSum += midPage;
                }
                pageSum2 += midPage;

            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        }

        System.out.println("Middle Page sum: " + pageSum);
        System.out.println("Middle Page sum 2: " + pageSum2);
    }
}
