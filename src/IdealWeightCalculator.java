package src;
import java.util.Scanner;

public class IdealWeightCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== KALKULATOR BERAT BADAN IDEAL ===\n");
        
        UserData userData = getUserInput(scanner);
        WeightCalculator calculator = new WeightCalculator();
        
        displayResults(userData, calculator);
        
        scanner.close();
    }
    
    private static UserData getUserInput(Scanner scanner) {
        System.out.print("Masukkan nama Anda: ");
        String name = scanner.nextLine();
        
        System.out.print("Masukkan tinggi badan (cm): ");
        double height = scanner.nextDouble();
        
        System.out.print("Masukkan berat badan saat ini (kg): ");
        double currentWeight = scanner.nextDouble();
        
        System.out.print("Masukkan jenis kelamin (L/P): ");
        char gender = scanner.next().toUpperCase().charAt(0);
        
        return new UserData(name, height, currentWeight, gender);
    }
    
    private static void displayResults(UserData userData, WeightCalculator calculator) {
        System.out.println("\n=== HASIL PERHITUNGAN ===");
        System.out.println("Nama: " + userData.getName());
        System.out.println("Tinggi: " + userData.getHeight() + " cm");
        System.out.println("Berat saat ini: " + userData.getCurrentWeight() + " kg");
        System.out.println();
        
        double brocaWeight = calculator.calculateBrocaFormula(userData);
        double bmi = calculator.calculateBMI(userData);
        String bmiCategory = calculator.getBMICategory(bmi);
        
        System.out.printf("Berat badan ideal (Formula Broca): %.2f kg\n", brocaWeight);
        System.out.printf("BMI Anda: %.2f (%s)\n", bmi, bmiCategory);
        
        double difference = userData.getCurrentWeight() - brocaWeight;
        displayWeightRecommendation(difference);
    }
    
    private static void displayWeightRecommendation(double difference) {
        System.out.println();
        if (Math.abs(difference) < 2) {
            System.out.println("Status: Berat badan Anda sudah ideal! 🎉");
        } else if (difference > 0) {
            System.out.printf("Status: Anda kelebihan %.2f kg dari berat ideal\n", difference);
            System.out.println("Rekomendasi: Pertimbangkan program penurunan berat badan");
        } else {
            System.out.printf("Status: Anda kekurangan %.2f kg dari berat ideal\n", Math.abs(difference));
            System.out.println("Rekomendasi: Pertimbangkan program penambahan berat badan");
        }
    }
}

/**
 * Class untuk menyimpan data pengguna
 */
class UserData {
    private final String name;
    private final double height;
    private final double currentWeight;
    private final char gender;
    
    public UserData(String name, double height, double currentWeight, char gender) {
        this.name = name;
        this.height = height;
        this.currentWeight = currentWeight;
        this.gender = gender;
    }
    
    public String getName() {
        return name;
    }
    
    public double getHeight() {
        return height;
    }
    
    public double getCurrentWeight() {
        return currentWeight;
    }
    
    public char getGender() {
        return gender;
    }
    
    public double getHeightInMeters() {
        return height / 100.0;
    }
}

/**
 * Class untuk melakukan perhitungan berat badan ideal
 */
class WeightCalculator {
    
    private static final double BROCA_CONSTANT = 100.0;
    private static final double MALE_FACTOR = 0.90;
    private static final double FEMALE_FACTOR = 0.85;
    
    /**
     * Menghitung berat badan ideal menggunakan Formula Broca
     * Rumus: (Tinggi - 100) x faktor gender
     */
    public double calculateBrocaFormula(UserData userData) {
        double baseWeight = userData.getHeight() - BROCA_CONSTANT;
        double factor = (userData.getGender() == 'L') ? MALE_FACTOR : FEMALE_FACTOR;
        return baseWeight * factor;
    }
    
    /**
     * Menghitung Body Mass Index (BMI)
     * Rumus: Berat (kg) / (Tinggi (m))^2
     */
    public double calculateBMI(UserData userData) {
        double heightInMeters = userData.getHeightInMeters();
        return userData.getCurrentWeight() / (heightInMeters * heightInMeters);
    }
    
    /**
     * Mendapatkan kategori BMI berdasarkan standar WHO
     */
    public String getBMICategory(double bmi) {
        if (bmi < 18.5) {
            return "Kekurangan berat badan";
        } else if (bmi < 25.0) {
            return "Normal";
        } else if (bmi < 30.0) {
            return "Kelebihan berat badan";
        } else {
            return "Obesitas";
        }
    }
}