import java.io.*;
import java.util.*;

public class DyciUniformStock {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILEDATA = "C:\\Users\\pined\\OneDrive\\Desktop\\PROJECT NI TJ\\DATA TO\\StockData.txt";
    private static final String STOCKSDATA = "C:\\Users\\pined\\OneDrive\\Desktop\\PROJECT NI TJ\\DATA TO\\SizeData.txt";
    private static final String nursingdata = "C:\\Users\\pined\\OneDrive\\Desktop\\PROJECT NI TJ\\DATA TO\\NursingStock.txt";

    public static void login() {
        System.out.println("DYCI UNIFORMS STOCK LEVELS LOGIN");
        System.out.println("\tLogin");
        System.out.print("\nEnter user: ");
        String user = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (user.equals("useradmin") && password.equals("password123@")) {
            mainMenu();
        } else {
            System.out.println("\nInvalid Log-in");
            login();
        }
    }

    public static void mainMenu() {
        System.out.println("\nDYCI UNIFORMS STOCK LEVELS AND TRANSACTIONS");
        System.out.println("[1.] Manage Stocks");
        System.out.println("[2.] Make Transaction: ");
        System.out.println("[3.] Exchange Uniform: ");
        System.out.println("[4.] Return Uniform: ");
        System.out.println("[5.] Sale History: ");
        System.out.println("[6.] Exit");
        System.out.print("Enter your choice: ");
        int mainMenu = scanner.nextInt();
        scanner.nextLine();

        switch (mainMenu) {
            case 1 -> UpdatingModules();
            case 2 -> Maketransaction();
            case 3 -> Exchange();
            case 4 -> Return();
            case 5 -> SaleHistory();
            case 6 -> {
                System.out.println("Exiting...");
                System.exit(0);
            }
            default -> {
                System.out.println("Please enter a number between 1-2.");
                mainMenu();
            }
        }
    }

    public static void UpdatingModules() {
        System.out.println("\nManage Stocks");
        System.out.println("[1.] Stock In");
        System.out.println("[2.] Stock Out");
        System.out.println("[3.] View Stocks");
        System.out.println("[4.] Back to Main Menu");
        int UpdatingModules = scanner.nextInt();
        scanner.nextLine();

        switch (UpdatingModules) {
            case 1 -> addStocks();
            case 2 -> deleteStocks();
            case 3 -> viewStocks();
            case 4 -> mainMenu();
            default -> {
                System.out.println("Please enter a number between 1-4.");
                UpdatingModules();
            }
        }
    }

    public static void viewStocks() {
        try (
            BufferedReader reader = new BufferedReader(new FileReader(FILEDATA));
            BufferedReader readerforStocks = new BufferedReader(new FileReader(STOCKSDATA));
            
        ) {
            String line1, line2;
    
            while ((line1 = reader.readLine()) != null && (line2 = readerforStocks.readLine()) != null) {
                // Splitting by commas instead of spaces
                String[] parts1 = line1.split(",");
                String[] parts2 = line2.split(" ");
              
                if (parts1.length < 7 || parts2.length < 4) continue; // Ensure valid data
    
                String code = parts1[0];
                String uniform = parts1[1] + ", " + parts1[2];
                String sizes = "S: " + parts1[3] + ", M: " + parts1[4] +
                               ", L: " + parts1[5] + ", XL: " + parts1[6];
                String stocks = "S: " + parts2[0] + ", M: " + parts2[1] +
                                ", L: " + parts2[2] + ", XL: " + parts2[3];
    
                System.out.println("\nUNIFORM CODE: " + code);
                System.out.println("UNIFORM: " + uniform);
                System.out.println("SIZES: " + sizes);
                System.out.println("STOCKS: " + stocks);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        nursingstocks();
        UpdatingModules();
    }
    
    private static void nursingstocks() {
        try (
            BufferedReader nursingstocks = new BufferedReader(new FileReader(nursingdata));
            BufferedReader readerforStocks = new BufferedReader(new FileReader(STOCKSDATA));
            
        ) {
            String line3, line4;

            while ((line3 = nursingstocks.readLine()) != null && (line4 = readerforStocks.readLine()) != null) {
                String[] parts3 = line3.split(",");
                String[] parts2 = line4.split(" ");

                if (parts3.length < 9 || parts2.length < 4) continue;

                String nursingcode = parts3[0];
                String nursinguniform = parts3[1] + ", " + parts3[3];
                String nursinglower = parts3[2] + ", " + parts3[4];
                String stocks = "S: " + parts2[0] + ", M: " + parts2[1] +
                                ", L: " + parts2[2] + ", XL: " + parts2[3];

                System.out.println("\nNursing Code:" + nursingcode);
                System.out.println("Nursing Top Uniforms: " + nursinguniform);
                System.out.println("Nursing Lower Uniforms: " + nursinglower);
                System.out.println("Stocks: " + stocks);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

 public static void addStocks() {
        System.out.println("\nUniform Codes: NU = Nursing, CCSU = CCS, AU = Accounting");
        System.out.print("Enter the code of the uniform to add: ");
        String targetCode = scanner.nextLine();
        String[] fileLines = new String[100];
        int lineCount = 0;
        boolean found = false;
    
        try (
            BufferedReader reader = new BufferedReader(new FileReader(FILEDATA));
            BufferedReader readerforStocks = new BufferedReader(new FileReader(STOCKSDATA));
            BufferedReader nursingReader = new BufferedReader(new FileReader(nursingdata));
        ) {
            String line1, line2, line3;
    
            while ((line1 = reader.readLine()) != null && (line2 = readerforStocks.readLine()) != null) {
                fileLines[lineCount++] = line1;
                fileLines[lineCount++] = line2;
            }
            while ((line3 = nursingReader.readLine()) != null) {
                fileLines[lineCount++] = line3;
            }
    
            for (int i = 0; i < lineCount; i++) {
                String[] parts = fileLines[i].split(",");
    
                if (parts.length < 9 || !isNumeric(parts[5]) || !isNumeric(parts[6]) || !isNumeric(parts[7]) || !isNumeric(parts[8])) {
                    continue;
                }
    
 
                if (parts[0].equalsIgnoreCase(targetCode)) {
                    found = true;
    
                    System.out.println("Current stock details for " + parts[0] + ":");
                    if (parts[0].equals("NU")) {
                        // Handling Nursing uniform which includes extra columns for Blouse and Skirts
                        System.out.println("Nursing Top: " + parts[1] + ", " + parts[2]);
                        System.out.println("Nursing Bottom: " + parts[3] + ", " + parts[4]);
                        System.out.println("SMALL: " + parts[5] + ", MEDIUM: " + parts[6] + 
                                           ", LARGE: " + parts[7] + ", EXTRA LARGE: " + parts[8]);
                    } else {
                        System.out.println("SMALL: " + parts[3] + ", MEDIUM: " + parts[4] +
                                           ", LARGE: " + parts[5] + ", EXTRA LARGE: " + parts[6]);
                    }
                    System.out.println("Which size would you like to add?");
                    System.out.println("[1] SMALL");
                    System.out.println("[2] MEDIUM");
                    System.out.println("[3] LARGE");
                    System.out.println("[4] EXTRA LARGE");
                    System.out.print("Enter your choice (1-4): ");
                    int sizeChoice = scanner.nextInt();
                    System.out.print("How many stocks you want to add? ");
                    int add = scanner.nextInt();
                    scanner.nextLine();
    
                    switch (sizeChoice) {
                        case 1 -> parts[3] = String.valueOf(Integer.parseInt(parts[3]) + add);
                        case 2 -> parts[4] = String.valueOf(Integer.parseInt(parts[4]) + add);
                        case 3 -> parts[5] = String.valueOf(Integer.parseInt(parts[5]) + add);
                        case 4 -> parts[6] = String.valueOf(Integer.parseInt(parts[6]) + add);
                        default -> {
                            System.out.println("Invalid choice.");
                            return;
                        }
                    }
    
                    fileLines[i] = String.join(",", parts);
                    System.out.println("Stock added successfully.");
                    break;
                }
            }
    
            if (!found) {
                System.out.println("Uniform with code '" + targetCode + "' not found.");
            }
    
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEDATA))) {
            for (int i = 0; i < lineCount; i++) {
                writer.write(fileLines[i]);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
        UpdatingModules();
    }
       

    public static void deleteStocks() {
        System.out.println("\nUniform Codes: NU = Nursing, CCSU = CCS, AU = Accounting");
        System.out.print("Enter the code of the uniform to remove: ");
        String targetCode = scanner.nextLine();
        String[] fileLines = new String[100];
        int lineCount = 0;
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILEDATA))) {
            String line;

            while ((line = reader.readLine()) != null) {
                fileLines[lineCount++] = line;
            }

            for (int i = 0; i < lineCount; i++) {
                String[] parts = fileLines[i].split(",");

                if (parts[0].equalsIgnoreCase(targetCode)) {
                    found = true;

                    System.out.println("Current stock details for " + parts[1] + ":");
                    System.out.println("SMALL: " + parts[6] + ", MEDIUM: " + parts[7] +
                                       ", LARGE: " + parts[8] + ", EXTRA LARGE: " + parts[9]);

                    System.out.println("Which size would you like to remove?");
                    System.out.println("[1] SMALL");
                    System.out.println("[2] MEDIUM");
                    System.out.println("[3] LARGE");
                    System.out.println("[4] EXTRA LARGE");
                    System.out.print("Enter your choice (1-4): ");
                    int sizeChoice = scanner.nextInt();
                    System.out.print("How many stocks to remove? ");
                    int quantityToRemove = scanner.nextInt();
                    scanner.nextLine();

                    switch (sizeChoice) {
                        case 1 -> {
                            if (Integer.parseInt(parts[6]) >= quantityToRemove) {
                                parts[6] = String.valueOf(Integer.parseInt(parts[6]) - quantityToRemove);
                                System.out.println("SMALL size stock reduced successfully.");
                            } else {
                                System.out.println("Not enough stock to delete.");
                            }
                        }
                        case 2 -> {
                            if (Integer.parseInt(parts[7]) >= quantityToRemove) {
                                parts[7] = String.valueOf(Integer.parseInt(parts[7]) - quantityToRemove);
                                System.out.println("MEDIUM size stock reduced successfully.");
                            } else {
                                System.out.println("Not enough stock to delete.");
                            }
                        }
                        case 3 -> {
                            if (Integer.parseInt(parts[8]) >= quantityToRemove) {
                                parts[8] = String.valueOf(Integer.parseInt(parts[8]) - quantityToRemove);
                                System.out.println("LARGE size stock reduced successfully.");
                            } else {
                                System.out.println("Not enough stock to delete.");
                            }
                        }
                        case 4 -> {
                            if (Integer.parseInt(parts[9]) >= quantityToRemove) {
                                parts[9] = String.valueOf(Integer.parseInt(parts[9]) - quantityToRemove);
                                System.out.println("EXTRA LARGE size stock reduced successfully.");
                            } else {
                                System.out.println("Not enough stock to delete.");
                            }
                        }
                        default -> {
                            System.out.println("Invalid choice.");
                            return;
                        }
                    }

                    fileLines[i] = String.join(",", parts);
                    break;
                }
            }

            if (!found) {
                System.out.println("Uniform with code '" + targetCode + "' not found.");
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILEDATA))) {
                for (int i = 0; i < lineCount; i++) {
                    writer.write(fileLines[i]);
                    writer.newLine();
                }
            }

            System.out.println("Stock updated successfully.");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        UpdatingModules();
    }
    
    public static void Maketransaction() {
    }

    public static void Exchange(){

    }

    public static void Return(){

    }

    public static void SaleHistory() {
        
    }
    public static void main(String[] args) {
        login();
    }
}