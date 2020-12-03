package clinic_Database;

import java.sql.*;

/**
 *
 * @author dell
 */
//abstract parent class
abstract class User{

    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String gender;
    protected long phoneNo;
    protected int birthDate;
    protected int birthMonth;
    protected int birthYear;

    public User(String firstName, String middleName, String lastName, String gender, long phoneNo, int birthDate, int birthMonth, int birthYear) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.birthDate = birthDate;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
    }
    
    //nested class for storing various fields of address
    protected class Address{

        String houseNo;
        String society;
        String landmark;
        String city;
        String state;
        int pincode;

        public void setAddress(String houseNo, String society, String landmark, String city, String state, int pincode)
        {
            this.houseNo = houseNo;
            this.society = society;
            this.landmark = landmark;
            this.city = city;
            this.state =state;
            this.pincode = pincode;
        }
    }
    
    abstract public void addAdressTable(int UserID) throws SQLException;    
}