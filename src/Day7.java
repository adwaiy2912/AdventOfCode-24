import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day7 {
    private static boolean isPossible(List<Integer> lst, int idx, int n, long target, long temp) {
        if (idx == n) {
            return target == temp;
        }
        boolean case1 = isPossible(lst, idx + 1, n, target, temp + lst.get(idx));
        boolean case2 = isPossible(lst, idx + 1, n, target, temp * lst.get(idx));

        return case1 || case2;
    }

    private static boolean isPossible2(List<Integer> lst, int idx, int n, long target, long temp) {
        if (idx == n) {
            return target == temp;
        }
        boolean case1 = isPossible2(lst, idx + 1, n, target, temp + lst.get(idx));
        boolean case2 = isPossible2(lst, idx + 1, n, target, temp * lst.get(idx));
        boolean case3 = isPossible2(lst, idx + 1, n, target, concat(temp, lst.get(idx)));

        return case1 || case2 || case3;
    }

    private static long concat(long a, int b) {
        return Long.parseLong(String.valueOf(a) + String.valueOf(b));
    }

    public static void main(String[] args) {
        List<Long> ans = new ArrayList<>();
        List<List<Integer>> values = new ArrayList<>();

        String filePath = "input/inp7.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");

                ans.add(Long.parseLong(parts[0].substring(0, parts[0].length() - 1)));

                List<Integer> row = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    row.add(Integer.parseInt(parts[i]));
                }
                values.add(row);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        long sum = 0;
        long sum2 = 0;

        for (int i = 0; i < ans.size(); i++) {
            if (isPossible(values.get(i), 1, values.get(i).size(), ans.get(i), values.get(i).get(0))) {
                sum += ans.get(i);
            }
            if (isPossible2(values.get(i), 1, values.get(i).size(), ans.get(i), values.get(i).get(0))) {
                sum2 += ans.get(i);
            }
        }

        System.out.println("Answer: " + sum);
        System.out.println("Answer 2: " + sum2);

        // Answer: 5030892084481
        // Answer 2: 91377448644679
    }
}
