package clinic_Database;

/**
 *
 * @author dell
 */
import java.sql.*;
import java.util.*;

public class clinic_Database {
		
	public static Admin adminLogin(int id, String password) throws SQLException, Exception{
		String url = "jdbc:mysql://localhost:3306/homeopathy";
		String username = "root";
		String sqlpassword = "root";
		Connection con = DriverManager.getConnection(url, username, sqlpassword);
		System.out.println("Succesfull");
		String sql = "Select * from Admins Where idAdmin = " + id;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(!rs.next()){
			st.close();
			con.close();
			throw new SQLException("No admin wih id " + id);
		}
		else
		{
			if(password.equals(rs.getString("adminPassword")))
			{
				System.out.println("Welcome " + rs.getString("firstName"));
				Admin currentAdmin = new Admin(Integer.parseInt(rs.getString("idAdmin")),rs.getString("adminPassword"),rs.getString("firstName"),
																rs.getString("middleName"),rs.getString("lastName"), rs.getString("gender"),
																Long.parseLong(rs.getString("phoneNo")),Integer.parseInt(rs.getString("birthDate")),
																Integer.parseInt(rs.getString("birthMonth")), Integer.parseInt(rs.getString("birthYear")), 
																"","", "","", "",311001,Integer.parseInt(rs.getString("joinDate")), 
																Integer.parseInt(rs.getString("joinMonth")), Integer.parseInt(rs.getString("joinYear")),
																"Admin");
				st.close();
				con.close();
				return currentAdmin;
			}
			else
			{
				st.close();
				con.close();
				throw new Exception("Invalid login credentials");	
			}
			
		}
 	}
        
	public static Doctor doctorLogin(int id, String password) throws SQLException, Exception{
		
		String url = "jdbc:mysql://localhost:3306/homeopathy";
		String username = "root";
		String sqlpassword = "root";
		Connection con = DriverManager.getConnection(url, username, sqlpassword);
		String sql = "Select * from Doctors Where idDoctors = " + id;
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		if(!rs.next()){
			st.close();
			con.close();
			throw new SQLException("No doctor wih id " + id);
		}
		else
		{
			if(password.equals(rs.getString("doctorPassword")))
			{
				System.out.println("Welcome " + rs.getString("firstName"));
				Doctor currentDoctor = new Doctor(Integer.parseInt(rs.getString("idDoctors")),rs.getString("doctorPassword"),rs.getString("doctorPassword"),rs.getString("firstName"),
																rs.getString("middleName"),rs.getString("lastName"), rs.getString("gender"),
																Long.parseLong(rs.getString("phoneNo")),Integer.parseInt(rs.getString("birthDate")),
																Integer.parseInt(rs.getString("birthMonth")), Integer.parseInt(rs.getString("birthYear")), 
																"","", "","", "",311001,Integer.parseInt(rs.getString("joinDate")), 
																Integer.parseInt(rs.getString("joinMonth")), Integer.parseInt(rs.getString("joinYear")),
																"Doctor");
				st.close();
				con.close();
				return currentDoctor;
			}
			else
			{
				st.close();
				con.close();
				throw new Exception("Invalid login credentials");	
			}
			
		}
	}
        
        public static boolean checkDoctorExist(String licenseNo) throws SQLException{
            String url = "jdbc:mysql://localhost:3306/homeopathy";
            String username = "root";
            String sqlpassword = "root";
            Connection con = DriverManager.getConnection(url, username, sqlpassword);
            String sql = "Select * from Doctors Where licenseNo = '" + licenseNo + "';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(!rs.next()){
                return false;
            }
            else{
                return true;
            }
        }
	 
	public static void main(String[] args) {
		new User_Select().setVisible(true);		
	}

}
