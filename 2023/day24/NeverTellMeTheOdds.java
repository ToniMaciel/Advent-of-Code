import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class NeverTellMeTheOdds {

    private static List<Particle> particles = new ArrayList<>();
    private static final long LOWER_BOUND = 200000000000000L;
    private static final long UPPER_BOUND = 400000000000000L;

    public static void main(String[] args) {
        String filePath = "input.txt";
        File file = new File(filePath);

        readInput(file, filePath);
        calculateIntersections();
    }

    private static void readInput(File file, String filePath) {
        
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] splitLine = line.split(" @ ");
                Particle newParticle = new Particle(parseCoordinates(splitLine[0]), parseCoordinates(splitLine[1]));
                particles.add(newParticle);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        }    
    }

    private static long[] parseCoordinates(String coordinateString) {
        return Arrays.stream(coordinateString.split(", ")).mapToLong(str -> Long.parseLong(str.strip())).toArray();
    }

    private static void calculateIntersections() {
        int occurrences = 0;
        for (int i = 0; i < particles.size(); i++) {
            Particle particle1 = particles.get(i);
            for (int j = i + 1; j < particles.size(); j++) {
                Particle particle2 = particles.get(j);
                if (particlesIntersect(particle1, particle2)) {
                    occurrences++;
                }
            }
        }
        System.out.println(occurrences);
    }

    private static boolean particlesIntersect(Particle particle1, Particle particle2) {
        double A = particle1.position[0] - particle2.position[0];
        double B = particle1.position[1] - particle2.position[1];

        double coeficient1 = particle2.velocity[1]*particle1.velocity[0];
        double coeficient2 = particle2.velocity[0]*particle1.velocity[1];

        double divisor = (coeficient1 - coeficient2);

        if (divisor == 0) {
            return false;
        }

        double t = (-A*particle2.velocity[1] + B*particle2.velocity[0]) / divisor;
        double t2 = (particle1.velocity[0]*t + A) / particle2.velocity[0];

        if (t < 0 || t2 < 0) {
            return false;
        }

        double x1 = particle1.position[0] + (particle1.velocity[0] * t);
        double y1 = particle1.position[1] + (particle1.velocity[1] * t);

        double x2 = particle2.position[0] + (particle2.velocity[0] * t2);
        double y2 = particle2.position[1] + (particle2.velocity[1] * t2);

        if (x1 < LOWER_BOUND || x1 > UPPER_BOUND || y1 < LOWER_BOUND || y1 > UPPER_BOUND || 
            x2 < LOWER_BOUND || x2 > UPPER_BOUND || y2 < LOWER_BOUND || y2 > UPPER_BOUND) {
            return false;
        }

        return true;
    }
}
