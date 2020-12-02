package clinic_Database;

/**
 *
 * @author D3MV48S2
 */
import java.sql.*;
import java.util.*;

class Admin extends Staff{
	
	Scanner sc = new Scanner(System.in);
	
    private Address adminAddress = new Address();
    private String admin_password;
    
    public Admin(int admin_id,String admin_password,String admin_firstname,String admin_middlename,
                  String admin_lastname, String admin_gender,long admin_phoneno,int  birth_date,int birth_month, int birth_year, 
                  String admin_houseno,String admin_society, String admin_landmark,String admin_city, String admin_state,int city_pin,int join_date,int join_month,
                  int join_year, String designation) 
    {
    	super(admin_id, designation, join_date, join_month, admin_firstname, admin_middlename, admin_lastname, admin_gender, 
                            admin_phoneno, birth_date, birth_month, birth_year);
        
         this.admin_password = admin_password;
         adminAddress.setAddress(admin_houseno, admin_society, admin_landmark, admin_city, admin_state, city_pin);
    	
    }

    public String getAdmin_password() {
        return admin_password;
    }
    
    public int addNewDoctor(Doctor newDoctor) throws SQLException{
        int doctorID;
        String url = "jdbc:mysql://localhost:3306/homeopathy";
        String username = "root";
        String sqlpassword = "root";
        Connection con = DriverManager.getConnection(url, username, sqlpassword);

        String sql = "Insert Into doctors (firstName, middleName, lastName, gender, phoneNo, birthDate, birthMonth, birthYear, doctorPassword, licenseNo, joinDate, joinMonth, joinYear)" + 
						 "Values('" + newDoctor.firstName + "', '" + newDoctor.middleName + "', '" +
						 newDoctor.lastName + "', '" + newDoctor.gender + "', '" + newDoctor.phoneNo + "', '" + newDoctor.birthDate + "', '" +
						 newDoctor.birthMonth + "', '" + newDoctor.birthYear + "', '" + newDoctor.getDoctor_password() + "', '" + newDoctor.getLicence_no() + "', '" +
						 newDoctor.join_date + "', '" + newDoctor.join_month + "', '" + newDoctor.join_year + "');";
        Statement st = con.createStatement();
        st.executeUpdate(sql);
        System.out.println("Succesfully saved doctor."); 
        sql = "select last_insert_id()";
        st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if(!rs.next()){
            st.close();
            con.close();
            throw new SQLException("Couldn't add new Doctor");
        }
        else{
            doctorID = Integer.parseInt(rs.getString("last_insert_id()"));
            System.out.println(doctorID);
            newDoctor.addAdressTable(doctorID);
        }
        con.close();
        st.close();
        return doctorID;
    }
        
    public Doctor getdoctorDetails(int doctorId) throws SQLException{
    	String url = "jdbc:mysql://localhost:3306/homeopathy";
    	String username = "root";
    	String sqlpassword = "root";
    	Connection con = DriverManager.getConnection(url, username, sqlpassword);
    	String sql = "Select * from Doctors where idDoctors = " + doctorId;
    	Statement st = con.createStatement();
    	ResultSet rs = st.executeQuery(sql);
    	if(!rs.next()) {
    		st.close();
    		con.close();
    		throw new SQLException("No doctor with id " + doctorId);
    	}
    	else {
            sql = "Select * from address where userType = " + "'Doctor'" + " and userId = " + doctorId;
            st = con.createStatement();
            ResultSet rsaddress = st.executeQuery(sql);
            Doctor currentDoctor;
            if(!rsaddress.next()){
               
            
                currentDoctor = new Doctor(Integer.parseInt(rs.getString("idDoctors")), rs.getString("licenseNo"), rs.getString("doctorPassword"), rs.getString("firstName"), rs.getString("middleName"), rs.getString("lastName"), 
                                                     rs.getString("gender"), Long.parseLong(rs.getString("phoneNo")), Integer.parseInt(rs.getString("birthDate")), Integer.parseInt(rs.getString("birthMonth")), 
                                                     Integer.parseInt(rs.getString("birthYear")), "", "", "", "", "", 0, rs.getInt("joinDate"), rs.getInt("joinMonth"),
                                                     rs.getInt("joinYear"), "");
            }
            else{
                currentDoctor = new Doctor(Integer.parseInt(rs.getString("idDoctors")), rs.getString("licenseNo"), rs.getString("doctorPassword"), 
                                                            rs.getString("firstName"), rs.getString("middleName"), rs.getString("lastName"), rs.getString("gender"), 
                                                            Long.parseLong(rs.getString("phoneNo")), Integer.parseInt(rs.getString("birthDate")), 
                                                            Integer.parseInt(rs.getString("birthMonth")), Integer.parseInt(rs.getString("birthYear")), 
                                                            rsaddress.getString("houseNo"), rsaddress.getString("society"), rsaddress.getString("landmark"), 
                                                            rsaddress.getString("city"), rsaddress.getString("state"), Integer.parseInt(rsaddress.getString("pincode")), 
                                                            rs.getInt("joinDate"), rs.getInt("joinMonth"), rs.getInt("joinYear"), "Doctor");
            }
            st.close();
            con.close();
            return currentDoctor;
    	}
    }

    
    
    public void addAdressTable(int adminId) throws SQLException{
        String url = "jdbc:mysql://localhost:3306/homeopathy";
        String username = "root";
        String sqlpassword = "root";
        Connection con = DriverManager.getConnection(url, username, sqlpassword);
        String sql = "Insert Into address (houseNo, society, landmark, city, state, pincode, userType, userid)" + "Values('" + this.adminAddress.houseNo + "', '" + this.adminAddress.society + "', '" + 
						 this.adminAddress.landmark + "', '" + this.adminAddress.city + "', '" + this.adminAddress.state + "', '" + this.adminAddress.pincode + "', '" + 
						 "Admin" + "', '" + adminId + "');";
        System.out.println(sql);
        Statement st = con.createStatement();
        st.executeUpdate(sql);
        st.close();
        con.close();
    }
}

