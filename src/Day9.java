import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Day9 {
    public static void main(String[] args) {
        String input = "";

        String filePath = "input/inp9.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                input = line;
                break;
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        Random random = new Random();

        StringBuilder sb = new StringBuilder();
        Map<Character, Integer> idMap = new HashMap<>();
        Map<Character, Integer> lenMap = new HashMap<>();
        int val = 0;
        char ch = '.';

        for (int i = 0; i < input.length(); i++) {
            int len = Integer.parseInt(input.charAt(i) + "");

            if (i % 2 == 0) {
                char randChar = (char) (random.nextInt(20000));
                while (idMap.containsKey(randChar) || randChar == ch) {
                    randChar = (char) (random.nextInt(20000));
                }
                idMap.put(randChar, val++);
                lenMap.put(randChar, len);

                for (int j = 0; j < len; j++) {
                    sb.append(randChar);
                }
            } else {
                for (int j = 0; j < len; j++) {
                    sb.append(ch);
                }
            }
        }

        StringBuilder sb2 = new StringBuilder(sb);

        // Part 1

        int n = sb.length();
        int left = 0, right = n - 1;

        while (left < right) {
            while (left < right && sb.charAt(left) != ch) {
                left++;
            }
            while (left < right && sb.charAt(right) == ch) {
                right--;
            }
            sb.setCharAt(left, sb.charAt(right));
            sb.setCharAt(right, ch);

            left++;
            right--;
        }

        // Part 2

        int ptr = n - 1;

        while (ptr > 0) {
            if (sb2.charAt(ptr) != ch) {
                int len = lenMap.get(sb2.charAt(ptr));
                int pos = 0, currLen = 0;

                while (currLen < len && pos < ptr) {
                    if (sb2.charAt(pos) == ch) {
                        currLen++;
                    } else {
                        currLen = 0;
                    }
                    pos++;
                }

                if (currLen == len) {
                    pos--;
                    for (int i = 0; i < len; i++, ptr--) {
                        sb2.setCharAt(pos - i, sb2.charAt(ptr));
                        sb2.setCharAt(ptr, ch);
                    }
                }
            }
            ptr--;
        }

        long checkSum = 0;
        long checkSum2 = 0;

        for (int i = 0; i < n && sb.charAt(i) != ch; i++) {
            checkSum += i * idMap.get(sb.charAt(i));
        }
        for (int i = 0; i < n; i++) {
            if (sb2.charAt(i) == ch) {
                continue;
            }
            checkSum2 += i * idMap.get(sb2.charAt(i));
        }

        System.out.println("Resulting checksum: " + checkSum);
        System.out.println("Resulting checksum2: " + checkSum2);
    }
}
