import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

public class Day11 {
    private static int getNumDigits(long num) {
        int count = 0;

        while (num > 0) {
            num /= 10;
            count++;
        }

        return count;
    }

    public static void main(String[] args) {
        LinkedList<Long> stones = new LinkedList<>();

        String filePath = "input/inp11.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] nums = line.split(" ");
                for (String num : nums) {
                    stones.add(Long.parseLong(num));
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing a number: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }

        int times = 25, times2 = 75;

        while (times > 0) {
            ListIterator<Long> stonesIter = stones.listIterator();

            while (stonesIter.hasNext()) {
                long stone = stonesIter.next();
                int numDigits = getNumDigits(stone);

                if (stone == 0) {
                    stonesIter.set(1L);
                } else if (numDigits % 2 == 0) {
                    stonesIter.remove();

                    long firstHalf = stone / (long) Math.pow(10, numDigits / 2);
                    long secondHalf = stone % (long) Math.pow(10, numDigits / 2);

                    stonesIter.add(firstHalf);
                    stonesIter.add(secondHalf);
                } else {
                    stonesIter.set(stone * 2024);
                }
            }

            System.out.println("Number of stones: " + stones.size() + " -> " + times2);

            times--;
            times2--;
        }

        System.out.println("---- Number of stones ---- " + stones.size());

        while (times2 > 0) {
            ListIterator<Long> stonesIter = stones.listIterator();

            while (stonesIter.hasNext()) {
                long stone = stonesIter.next();
                int numDigits = getNumDigits(stone);

                if (stone == 0) {
                    stonesIter.set(1L);
                } else if (numDigits % 2 == 0) {
                    stonesIter.remove();

                    long firstHalf = stone / (long) Math.pow(10, numDigits / 2);
                    long secondHalf = stone % (long) Math.pow(10, numDigits / 2);

                    stonesIter.add(firstHalf);
                    stonesIter.add(secondHalf);
                } else {
                    stonesIter.set(stone * 2024);
                }
            }
            System.out.println("Number of stones 2: " + stones.size() + " -> " + times2);

            times2--;
        }

        System.out.println("Number of stones 2: " + stones.size());
    }
}