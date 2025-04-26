import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day3 {
    // mul(_,_) to mul(___,___) -> mul = 0-3, ( = 3, ) = 7-11
    static int num1 = 0;
    static int num2 = 0;

    private static boolean correctSeq(String line, int idx, int n) {
        if (!line.substring(idx, idx + 4).equals("mul(")) {
            return false;
        }
        if (line.indexOf(")", idx + 4) < idx + 7 || line.indexOf(")", idx + 4) > idx + 11) {
            return false;
        }

        int start = idx + 4, end = line.indexOf(")", idx + 4);
        String[] nums = line.substring(start, end).split(",");

        if (nums.length != 2 || nums[0].length() == 0 || nums[1].length() == 0 || nums[0].length() > 3
                || nums[1].length() > 3) {
            return false;
        } else {
            try {
                num1 = Integer.parseInt(nums[0]);
                num2 = Integer.parseInt(nums[1]);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<Integer[]> nums1 = new ArrayList<>();
        List<Integer[]> nums2 = new ArrayList<>();

        String filePath = "input/inp3.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isEnabled = true;

            while ((line = br.readLine()) != null) {
                int n = line.length();

                for (int i = 0; i < n - 7; i++) {
                    if (line.substring(i, i + 7).equals("don't()")) {
                        isEnabled = false;
                    }
                    if (line.substring(i, i + 4).equals("do()")) {
                        isEnabled = true;
                    }

                    if (correctSeq(line, i, n)) {
                        nums1.add(new Integer[] { num1, num2 });

                        if (isEnabled) {
                            nums2.add(new Integer[] { num1, num2 });
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        long totalSum1 = 0;
        long totalSum2 = 0;

        for (Integer[] pair : nums1) {
            totalSum1 += pair[0] * pair[1];
        }
        for (Integer[] pair : nums2) {
            totalSum2 += pair[0] * pair[1];
        }

        System.out.println("Total sum: " + totalSum1);
        System.out.println("Total sum 2: " + totalSum2);

        // Total sum: 161289189
        // Total sum 2: 83595109
    }
}
