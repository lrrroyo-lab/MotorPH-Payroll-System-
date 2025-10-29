/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package motorphpayrollsystem;

import java.util.Scanner;
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.List;


/**
 *
 * @author Ally
 */
public class MotorPHPayrollSystem {

/*
    static  boolean loggedIn = false;
    static Scanner scan = new Scanner(System.in);
    static int choice = -1;
    static String empNumber = "10000";
    static String empLastName = "Garcia";
    static String empFirstName = "Manuel III";
    static String empDob = "10/11/1983";
    static String address = "Valero Carpark Building Valero Street 1227, Makati City";
    static String phoneNumber = "966-860-270";
    static String sssNumber = "44-4506057-3";
    static String philHealthNumber = "9820126853951";
    static String tinNumber = "442-605-657-000";
    static String pagIbigNumber = "691295330870";
    static String empStatus = "Regular";
    static String empPosition = "Chief Executive Officer";
    static String password = "password";    
    static double basicSalary = 90000;
    static double riceSubsidy = 1500;
    static double phoneAllowance = 2000;
    static double clothingAllowance = 1500;
    static double semiMonthlyRate = 45000;
    static double dailyRate = 535.71;
*/   
    static List<String[]> records;  // all rows from CSV
    static String csvFile = "MotorPH_Employee Data.csv";
    static Scanner scan = new Scanner(System.in);
    static int choice = -1;

    
    
    /**
     * @param args the command line arguments
     */
    
          // TODO code application logic here
    
   
    public static void main(String[] args) {
        
        loadEmployeeData();
        loginPage();
        
    }    
        
        public static void loadEmployeeData() {
            
            try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
                records = reader.readAll();
                System.out.println("Employee Data Loaded!");
                System.out.println("Total Number of Employees " + (records.size() - 1));
            }catch(Exception e) {
                System.out.println("There is an error reading the file " +e.getMessage());
                e.printStackTrace();
            }    
        }
            
    
        // log in page
        public static void loginPage() {  
            
            if (records == null || records.size() <2 ) {
                System.out.println("No Employee data loaded.  Closing the application. ");
                return;
            }
            
            System.out.println("=================================================");
            System.out.println("              MOTORPH PAYROLL SYSTEM");
            System.out.println("=================================================");
            System.out.print("Employee Number: ");
            String empNo = scan.nextLine();
            
            String[] emp = findEmployee(empNo);

                if (emp == null) {
                     System.out.println("Employee not found!");
                } else {
                    showEmployeeDetails(emp);
                    computePayroll(emp);
                }    
        }        
        
        public static String[] findEmployee(String empNo) {
            
            for (int i = 1; i < records.size(); i++) {
            String[] row = records.get(i);
                
                if (row[0].trim().equals(empNo.trim())) {
                return row;
                }
            }
            return null;    
        }
        
        
        public static void showEmployeeDetails(String[] row) {
            
            System.out.println("Login successful!");
            System.out.println("--------------------------------------------------");
            System.out.println("EMPLOYEE DETAILS");
            System.out.println("Employee #: " + row[0]);
            System.out.println("Name: " + row[2] + " " + row[1]);
            System.out.println("Position: " + row[11]);
            System.out.println("Status: " + row[10]);
            System.out.println("Basic Salary: " + row[13]);
            System.out.println("Rice Subsidy: " + row[14]);
            System.out.println("Phone Allowance: " + row[15]);
            System.out.println("Clothing Allowance: " + row[16]);
            System.out.println("Semi-Monthly Rate: " + row[17]);
            System.out.println("Hourly Rate: " + row[18]);
            System.out.println("--------------------------------------------------");
           
        }
    
        public static void computePayroll(String[] emp){
            
            double basicSalary = Double.parseDouble(emp[13].replace(",", "").trim());
            double riceSubsidy = Double.parseDouble(emp[14].replace(",", "").trim());
            double phoneAllowance = Double.parseDouble(emp[15].replace(",", "").trim());
            double clothingAllowance = Double.parseDouble(emp[16].replace(",", "").trim());
            double semiMonthly = Double.parseDouble(emp[17].replace(",", "").trim());
            double hourlyRate = Double.parseDouble(emp[18].replace(",", "").trim());
            
            
            System.out.println("Number of days worked : ");
            int daysWorked = scan.nextInt();
            scan.nextLine();
            System.out.println("Number of overtime hours : ");
            int overtime = scan.nextInt();
            scan.nextLine();
            
            double dailyRate = basicSalary / 22; //assumed workdays per month
            double grossPay = (daysWorked * dailyRate)
                                + riceSubsidy + phoneAllowance + clothingAllowance 
                                + (overtime * hourlyRate * 1.25); 
            
            double totalDeductions = sss(grossPay) + philHealth(basicSalary) + pagIbig(basicSalary) + tax(grossPay); 
            
            double netPay = grossPay - totalDeductions;

            showPayslip(emp, daysWorked, overtime, grossPay, totalDeductions, netPay);
        }

        public static void showPayslip(String[] emp, int daysWorked, int overtime, double grossPay, 
                                        double totalDeductions, double netPay){
        
            System.out.println("\n================== PAYSLIP ==================");
            System.out.println("Employee #: " + emp[0]);
            System.out.println("Name: " + emp[2] + " " + emp[1]);
            System.out.println("Position: " + emp[11]);
            System.out.println("Days Worked: " + daysWorked);
            System.out.println("Overtime Hours: " + overtime);
            System.out.printf("Gross Pay: %.2f%n", grossPay);
            System.out.printf("Total Deductions: %.2f%n", totalDeductions);
            System.out.printf("Net Pay: %.2f%n", netPay);
            System.out.println("=============================================");
        }

        public static double sss(double grossPay){
            
            double sssDeductions = 0;
            
                if (grossPay < 325.00){
                    sssDeductions = 135.00;
                }else if (grossPay <= 3649.99){
                    sssDeductions = 157.00;
                }else if (grossPay <= 4149.99){
                    sssDeductions = 180.00;
                }else if (grossPay <= 4649.99){
                    sssDeductions = 202.50;
                }else if (grossPay <= 5449.99){
                    sssDeductions = 225.00;
                }else if (grossPay <= 5649.99){
                    sssDeductions = 147.50;
                }else if (grossPay <= 6149.99){
                    sssDeductions = 270.00;
                }else if (grossPay <= 6649.99){
                    sssDeductions = 292.50;
                }else if (grossPay <= 7149.99){
                    sssDeductions = 315.00;
                }else if (grossPay <= 7649.99){
                    sssDeductions = 337.50;
                }else if (grossPay <= 8149.99){
                    sssDeductions = 360.00;
                }else if (grossPay <= 8649.99){
                    sssDeductions = 382.50;
                }else if (grossPay <= 9149.99){
                    sssDeductions = 405.00;
                }else if (grossPay <= 9649.99){
                    sssDeductions = 427.50;
                }else if (grossPay <= 10149.99){
                    sssDeductions = 450.00;
                }else if (grossPay <= 10649.99){
                    sssDeductions = 472.50;
                }else if (grossPay <= 11149.99){
                    sssDeductions = 495.00;
                }else if (grossPay <= 11649.99){
                    sssDeductions = 517.50;
                }else if (grossPay <= 12149.99){
                    sssDeductions = 540;
                }else if (grossPay <= 12649.99){
                    sssDeductions = 562.50;
                }else if (grossPay <= 13149.99){
                    sssDeductions = 585.00;
                }else if (grossPay <= 13649.99){
                    sssDeductions = 605.50;
                }else if (grossPay <= 14149.99){
                    sssDeductions = 630.00;
                }else if (grossPay <= 14649.99){
                    sssDeductions = 652.50;
                }else if (grossPay <= 15149.99){
                    sssDeductions = 675.00;
                }else if (grossPay <= 15649.99){
                    sssDeductions = 698.50;
                }else if (grossPay <= 16149.99){
                    sssDeductions = 720.00;
                }else if (grossPay <= 16649.99){
                    sssDeductions = 742.50;
                }else if (grossPay <= 17149.99){
                    sssDeductions = 765.00;
                }else if (grossPay <= 17649.99){
                    sssDeductions = 787.50;
                }else if (grossPay <= 18149.99){
                    sssDeductions = 810.00;
                }else if (grossPay <= 18649.99){
                    sssDeductions = 832.50;
                }else if (grossPay <= 19149.99){
                    sssDeductions = 855.00;
                }else if (grossPay <= 19649.99){
                    sssDeductions = 877.50;
                }else if (grossPay <= 20149.99){
                    sssDeductions = 900.00;
                }else if (grossPay <= 20649.99){
                    sssDeductions = 922.50;
                }else if (grossPay <= 21149.99){
                    sssDeductions = 945.00;
                }else if (grossPay <= 21649.99){
                    sssDeductions = 967.50;
                }else if (grossPay <= 22149.99){
                    sssDeductions = 990.00;
                }else if (grossPay <= 22649.99){
                    sssDeductions = 1012.50;
                }else if (grossPay <= 23149.99){
                    sssDeductions = 1035.00;
                }else if (grossPay <= 23649.99){
                    sssDeductions = 1057.50;
                }else if (grossPay <= 24149.99){
                    sssDeductions = 1080.00;
                }else if (grossPay <= 24649.99){
                    sssDeductions = 1102.50;
                }else {
                    sssDeductions = 1125.00;
                  }

            return sssDeductions;
        }       

        public static double philHealth(double basicSalary) {
            //phil health contribution 
            double philHealthCont = 0;
                if (basicSalary <= 10000){
                    philHealthCont = 300;
                }else if (basicSalary <=60000){
                    philHealthCont = basicSalary * 0.03;
                }else {
                    philHealthCont = 1800;
                      }
            return philHealthCont;
        }       

        public static double pagIbig(double basicSalary) {
            //pag-ibig contribution    
            double pagIbigCont = 0;
                
                if (basicSalary <=1500){
                    pagIbigCont = basicSalary * 0.01;
                } else {
                    pagIbigCont = basicSalary * 0.02;
                    }
            return pagIbigCont;        
        }       

        public static double tax(double grossPay){
            //tax calculation   
            double withholdingTax = 0;
                
                if (grossPay <= 20832.00){
                    withholdingTax = 0;
                }else if (grossPay <=33333.00){
                    withholdingTax = (grossPay-20833) * 0.20;
                }else if (grossPay <=66667.00){
                    withholdingTax = ((grossPay-33333) * 0.25) + 2500; 
                }else if (grossPay <= 166667.00){
                    withholdingTax = ((grossPay-66667) * 0.30) + 10833;
                }else if (grossPay <= 666667.00){
                    withholdingTax = ((grossPay-166667) * 0.32) + 40833.33;
                }else {
                    withholdingTax = ((grossPay-666667) * 0.35) + 200833.33;
                      }   
                
                return withholdingTax;        
            }  

}
        
 