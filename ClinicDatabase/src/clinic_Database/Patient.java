package clinic_Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dell
 */
//inherited class patient for storing patient records 
class Patient extends User{

    private int id;
    private Address patientAddress = new Address();

    //constructor to initialize the variables
    public Patient(int id, String firstName, String middleName, String lastName, String gender, long phone_no,int date ,
                   int month, int year, String houseNo, String society, String landmark, String city, String state, int pincode)
    {
        
        super(firstName, middleName, lastName, gender, phone_no, date, month, year);
        this.id = id;
        patientAddress.setAddress(houseNo, society, landmark, city, state, pincode);
    }
    
    public Patient(String firstName, String middleName, String lastName, String gender, long phone_no,int date ,
                   int month, int year, String houseNo, String society, String landmark, String city, String state, int pincode)
    {
        super(firstName, middleName, lastName, gender, phone_no, date, month, year);
        patientAddress.setAddress(houseNo, society, landmark, city, state, pincode);
    }
    
    public int getPatientId(){
        return this.id;
    }
    
    public Address getPatientAddress(){
        return this.patientAddress;
    }

    
    public void addAdressTable(int patientId) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/homeopathy";
        String username = "root";
        String sqlpassword = "root";
        Connection con = DriverManager.getConnection(url, username, sqlpassword);
        String sql = "Insert Into address (houseNo, society, landmark, city, state, pincode, userType, userid)" + "Values('" + this.patientAddress.houseNo + "', '" + this.patientAddress.society + "', '" + 
						 this.patientAddress.landmark + "', '" + this.patientAddress.city + "', '" + this.patientAddress.state + "', '" + this.patientAddress.pincode + "', '" + 
						 "Patient" + "', '" + patientId + "');";
        System.out.println(sql);
        Statement st = con.createStatement();
        st.executeUpdate(sql);
        st.close();
        con.close();
    }
}