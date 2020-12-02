package clinic_Database;

/**
 *
 * @author dell
 */
abstract class Staff extends User{
    protected int employee_id;
    protected String designation;
    protected int join_date;
    protected int join_month;
    protected int join_year;

    public Staff(int employee_id, String designation, int join_date, int join_month, String firstName, String middleName, String lastName, String gender, long phoneNo, int birthDate, int birthMonth, int birthYear) {
        super(firstName, middleName, lastName, gender, phoneNo, birthDate, birthMonth, birthYear);
        this.employee_id = employee_id;
        this.designation = designation;
        this.join_date = join_date;
        this.join_month = join_month;
        this.join_year = join_year;
    }
    
    public Staff(String designation, int join_date, int join_month, String firstName, String middleName, String lastName, String gender, long phoneNo, int birthDate, int birthMonth, int birthYear) 
    {
        super(firstName, middleName, lastName, gender, phoneNo, birthDate, birthMonth, birthYear); 
        this.designation = designation;
        this.join_date = join_date;
        this.join_month = join_month;
        this.join_year = join_year;
    }
    
    
    
    
    public void display_details()
    {
        System.out.println("Employee Id -" + employee_id);
        System.out.println("Designation -" + designation);
        System.out.println("Joining date -" + join_date + "/" + join_month + "/" + join_year);   
    }
}
