import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Incidence {
    
    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        processInput(file, filePath);
    }

    private static void processInput(File file, String filePath) {
        
        try {
            Scanner scanner = new Scanner(file);
            long sum = 0;
            List<String> lines = new ArrayList<>();

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                
                if (line.isEmpty()) {
                    sum += findMirroed(lines);
                    lines.clear();
                    continue;
                }

                lines.add(line);
            }

            scanner.close();
            sum += findMirroed(lines);
            System.out.println(sum);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }

    }

    private static long findMirroed(List<String> data) {
        return ((findHorizontalMirror(data) + 1) * 100) + (findVerticalMirror(data) + 1);
    }

    private static long findVerticalMirror(List<String> data) {
        long length = data.get(0).length();
        List<String> rotetedData = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < data.size(); j++) {
                sb.append(data.get(j).charAt(i));
            }
            rotetedData.add(sb.toString());
        }

        return findHorizontalMirror(rotetedData);
    }

    private static long findHorizontalMirror(List<String> data) {
        long index = -1;

        for (int i = 1; i < data.size(); i++) {
            if (data.get(i).equals(data.get(i - 1))) {
                if (checkCondtions(i - 1, data)) {
                    index = i - 1;
                    break;
                }
            }
        }

        return index;
    }

    private static boolean checkCondtions(int i, List<String> data) {
        int mirroredLine = i + 1;

        while (i >= 0 && mirroredLine < data.size()) {
            if (!data.get(i).equals(data.get(mirroredLine)))  {
                return false;
            }

            i--;
            mirroredLine++;
        }

        return true;
    }
}
