package clinic_Database;

/**
 *
 * @author dell
 */
import java.util.*;
import java.sql.*;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;

class Doctor extends Staff{
	
    Scanner sc = new Scanner(System.in);
    private String doctor_password;
    private String licence_no;
    private Address doctorAddress = new Address();

    public Doctor(int doctor_id,String doctor_licenceno,String doctor_password,String doctor_firstname,String doctor_middlename,
                  String doctor_lastname, String doctor_gender,long doctor_phoneno,int  birth_date,int birth_month, int birth_year, 
                  String doctor_houseno,String doctor_society, String doctor_landmark,String doctor_city, String doctor_state,int city_pin,
                  int join_date,int join_month,int join_year, String designation){

                    super(doctor_id, designation, join_date, join_month, doctor_firstname, doctor_middlename, doctor_lastname, doctor_gender, 
                            doctor_phoneno, birth_date, birth_month, birth_year);        
                    
                    this.licence_no = doctor_licenceno;
                    this.doctor_password = doctor_password;
                    doctorAddress.setAddress(doctor_houseno, doctor_society, doctor_landmark, doctor_city, doctor_state, city_pin);
                }
    
    public Doctor(String doctor_licenceno,String doctor_password,String doctor_firstname,String doctor_middlename,
                  String doctor_lastname, String doctor_gender,long doctor_phoneno,int  birth_date,int birth_month, int birth_year, 
                  String doctor_houseno,String doctor_society, String doctor_landmark,String doctor_city, String doctor_state,int city_pin,
                  int join_date,int join_month,int join_year, String designation){
    	
                    super(designation, join_date, join_month, doctor_firstname, doctor_middlename, doctor_lastname, doctor_gender, 
                            doctor_phoneno, birth_date, birth_month, birth_year);
                   
                    this.licence_no = doctor_licenceno;
                    this.doctor_password = doctor_password;
                    doctorAddress.setAddress(doctor_houseno, doctor_society, doctor_landmark, doctor_city, doctor_state, city_pin);
                }
        
    public String getDoctor_password() {
        return doctor_password;
    }

    public String getLicence_no() {
        return licence_no;
    }

    public Address getDoctorAddress() {
        return doctorAddress;
    }
    
    public int getDoctorId() {
    	return this.employee_id;
    }
    

    public int addNewPatient(Patient newPatient) throws SQLException{
        int patientID;
        String url = "jdbc:mysql://localhost:3306/homeopathy";
        String username = "root";
        String sqlpassword = "root";
        Connection con = DriverManager.getConnection(url, username, sqlpassword);
        
        String sql = "Insert Into patients (firstName, middleName, lastName, gender, phoneNo, birthDate, birthMonth, birthYear)" + "Values('" + newPatient.firstName + "', '" + newPatient.middleName + "', '" + 
						 newPatient.lastName + "', '" + newPatient.gender + "', '" + newPatient.phoneNo + "', '" + newPatient.birthDate + "', '" + 
						 newPatient.birthMonth + "', '" + newPatient.birthYear + "');";
        
        System.out.println(sql);
        Statement st = con.createStatement();
        st.executeUpdate(sql);
        sql = "select last_insert_id()";
        st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(!rs.next()){
            st.close();
            con.close();
            throw new SQLException("Couldn't add Patient");
        }
        else{
            patientID = Integer.parseInt(rs.getString("last_insert_id()"));
            System.out.println(patientID);
            newPatient.addAdressTable(patientID);
        }
        con.close();
        st.close();
        return patientID;
    }
    
    public Patient getpatientDetails(int patientID) throws SQLException{
    	String url = "jdbc:mysql://localhost:3306/homeopathy";
	String username = "root";
	String sqlpassword = "root";
        Connection con = DriverManager.getConnection(url, username, sqlpassword);
        String sql = "Select * from Patients Where patientsId = " + patientID;
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(!rs.next()){
            st.close();
            con.close();
            throw new SQLException("No patient wih id " + patientID);
        }
        else{
            sql = "Select * from address where userType = " + "'Patient'" + " and userId = " + patientID;
            st = con.createStatement();
            ResultSet rsaddress = st.executeQuery(sql);
            System.out.println("Here");
            Patient newPatient;
            if(!rsaddress.next()){
                newPatient = new Patient(Integer.parseInt(rs.getString("patientsId")), rs.getString("firstName"), rs.getString("middleName"),
                                                rs.getString("lastName"), rs.getString("gender"), Long.parseLong(rs.getString("phoneNo")),
                                                Integer.parseInt(rs.getString("birthDate")), Integer.parseInt(rs.getString("birthMonth")),
                                                Integer.parseInt(rs.getString("birthYear")), "", "", "", "", "", 0);
            }
            else{
                newPatient = new Patient(Integer.parseInt(rs.getString("patientsId")), rs.getString("firstName"), rs.getString("middleName"),
                                                rs.getString("lastName"), rs.getString("gender"), Long.parseLong(rs.getString("phoneNo")),
                                                Integer.parseInt(rs.getString("birthDate")), Integer.parseInt(rs.getString("birthMonth")),
                                                Integer.parseInt(rs.getString("birthYear")),rsaddress.getString("houseNo"), rsaddress.getString("society"),
                                                rsaddress.getString("landmark"), rsaddress.getString("city"), rsaddress.getString("state"), 
                                                Integer.parseInt(rsaddress.getString("pincode")));
            }
            st.close();
            con.close();
            return newPatient;
        }
    }

    public ArrayList<Patient_History> getPatientHistory(Patient currentPatient) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/homeopathy";
	String username = "root";
	String sqlpassword = "root";
        Connection con = DriverManager.getConnection(url, username, sqlpassword);
        String sql = "Select * from patienthistories Where patient = " + currentPatient.getPatientId();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        ArrayList<Patient_History> list = new ArrayList<Patient_History>();
        Patient_History patHist;
        if(!rs.next()){
            st.close();
            con.close();
            throw new SQLException("No patient history wih id " + currentPatient.getPatientId());
        }
        else{
            do{
                patHist = new Patient_History(rs.getString("lastVisitDate"), rs.getString("presentIllness"), rs.getString("medication"), Integer.parseInt(rs.getString("patient")));
                list.add(patHist);
                System.out.println(list.size());
            }while(rs.next());
            return list;
        }
    }

    public void addPatientVisitDetails(Patient_History currentVisit) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/homeopathy";
        String username = "root";
        String sqlpassword = "root";
        Connection con = DriverManager.getConnection(url, username, sqlpassword);
        
        String sql = "Insert Into patienthistories(lastVisitDate, presentIllness, medication, patient) " + "Values('"+ currentVisit.getVisitDate() + "', '" + currentVisit.getPresent_Illness() + "', '" + 
						 currentVisit.getPrescribedmedication() + "', '" + currentVisit.getPatientId() + "');";
        System.out.println(sql);
        Statement st = con.createStatement();
        st.executeUpdate(sql);
        con.close();
        st.close();
    }
    
    public void updatePatientAddress(Patient currentPatient) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/homeopathy";
        String username = "root";
        String sqlpassword = "root";
        Connection con = DriverManager.getConnection(url, username, sqlpassword);
        String sql = "Select addressId from address where userType = 'Patient' and userId = " + currentPatient.getPatientId();
        System.out.println(sql);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(!rs.next()){
            st.close();
            con.close();
            throw new SQLException("No address found for given user id");
        }
        else{
            sql = "Update address Set houseNo = '" + currentPatient.getPatientAddress().houseNo +
                      "', society ='"  + currentPatient.getPatientAddress().society + "', landmark ='" + currentPatient.getPatientAddress().landmark +
                      "', city ='" + currentPatient.getPatientAddress().city + "', state ='" + currentPatient.getPatientAddress().state + "', pincode ='" + 
                      currentPatient.getPatientAddress().pincode + "' Where(addressId = '" + rs.getInt("addressId") + "')";
            System.out.println(sql);
            con.createStatement();
            st.executeUpdate(sql);
        }
    }
    
    public void addAdressTable(int doctorId) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/homeopathy";
        String username = "root";
        String sqlpassword = "root";
        Connection con = DriverManager.getConnection(url, username, sqlpassword);
        String sql = "Insert Into address (houseNo, society, landmark, city, state, pincode, userType, userid)" + "Values('" + this.doctorAddress.houseNo + "', '" + this.doctorAddress.society + "', '" + 
						 this.doctorAddress.landmark + "', '" + this.doctorAddress.city + "', '" + this.doctorAddress.state + "', '" + this.doctorAddress.pincode + "', '" + 
						 "Doctor" + "', '" + doctorId + "');";
        System.out.println(sql);
        Statement st = con.createStatement();
        st.executeUpdate(sql);
        st.close();
        con.close();
    }
    
}