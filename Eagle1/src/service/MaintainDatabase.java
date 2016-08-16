package service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class MaintainDatabase{

	public void importClientData(){
		try{
			CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir")+"\\src\\client_data.csv"));
			List<String[]> totalClientList=reader.readAll();
			int pointer=0;
			for(String[] client : totalClientList){
				if(pointer!=0){
					saveClientData(client[0],client[1],client[2],client[3],client[4],client[5],client[6],client[7],client[8],client[9],client[10],client[11]);
				}
				pointer++;
			}								
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void importProjectData(){
		try{
			CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir")+"\\src\\project_data.csv"));
			List<String[]> totalProjectList=reader.readAll();
			int pointer=0;
			for(String[] project : totalProjectList){
				if(pointer!=0){
					saveProjectData(project[0],project[1],project[2],project[3],project[4],project[5],project[6],project[7],project[8]);
				}
				pointer++;
			}								
		}catch(Exception ex){
			ex.printStackTrace();
		}	
	}
	
	public void importEmployeeData(){
		try{
			CSVReader reader = new CSVReader(new FileReader(System.getProperty("user.dir")+"\\src\\people_data.csv"));
			List<String[]> totalEmployeeList=reader.readAll();
			int pointer=0;
			for(String[] employee : totalEmployeeList){
				if(pointer!=0){
					saveEmployeeData(employee[0], employee[1], employee[2], employee[3]);
				}
				pointer++;
			}								
		}catch(Exception ex){
			ex.printStackTrace();
		}	
	}
	
	public Connection dbconnection(){
		Connection connection=null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/consultancy","root","1234");  
		}catch (Exception e){
			e.printStackTrace();
		}
		return connection;
	}	
	
	public void saveClientData(String clientnumber, String clientname, String addressline1, String addressline2, String city, String state, String zip, String email, String contactperson, String invoicefrequency, String billingterms, String invoicegrouping) throws SQLException {
		Connection connection=null;
		PreparedStatement statement=null;
		try{
			connection=dbconnection();
			if(connection!=null){
	    		statement=connection.prepareStatement("insert into clients (clientnumber,clientname,addressline1,addressline2,city,state,zip,email,contactperson,invoicefrequency,billingterms,invoicegrouping,inactiveflag) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
	    		statement.setInt(1,Integer.parseInt(clientnumber));
	    		statement.setString(2,clientname);
	    		statement.setString(3,addressline1);
	    		statement.setString(4,addressline2);
	    		statement.setString(5,city);
	    		statement.setString(6,state);
	    		statement.setString(7,zip);
	    		statement.setString(8,email);
	    		statement.setString(9,contactperson);
	    		statement.setString(10,invoicefrequency);
	    		statement.setString(11,billingterms);
	    		statement.setString(12,invoicegrouping);
	    		statement.setString(13,"N");    		
	    		statement.addBatch();
	    		int[] rows=statement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void saveProjectData(String projectnumber, String clientnumber, String projectname, String startdate, String enddate, String status, String projectmanagername, String clientcontactname, String projectbudget) throws SQLException {
		Connection connection=null;
		PreparedStatement statement=null;
		try{
			connection=dbconnection();
			if(connection!=null){
	    		statement=connection.prepareStatement("insert into projects (project_number,client_id,project_name,start_date,end_date,status,project_manager,client_contact,budget,inactiveflag) values (?,?,?,?,?,?,?,?,?,?)");
	    		statement.setInt(1,Integer.parseInt(projectnumber));
	    		statement.setInt(2,Integer.parseInt(clientnumber));
	    		statement.setString(3,projectname);
	    		statement.setString(4,startdate);
	    		statement.setString(5,enddate);
	    		statement.setString(6,status);
	    		statement.setString(7,projectmanagername);
	    		statement.setString(8,clientcontactname);
	    		statement.setDouble(9,Double.parseDouble(projectbudget));
	    		statement.setString(10,"N");    		
	    		statement.addBatch();
	    		int[] rows=statement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void saveEmployeeData(String name, String title, String bill_rate, String role) throws SQLException{
		Connection connection=null;
		PreparedStatement statement=null;
		try{
			connection=dbconnection();
			if(connection!=null){
	    		statement=connection.prepareStatement("insert into employees (name,title,bill_rate,role,inactiveflag) values (?,?,?,?,?)");
	    		statement.setString(1,name);
	    		statement.setString(2,title);
	    		statement.setString(4,role);
	    		statement.setInt(3,Integer.parseInt(bill_rate));
	    		statement.setString(5,"N");    		
	    		statement.addBatch();
	    		int[] rows=statement.executeBatch();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public Vector employeesData(){
		Connection connection=null;
		Statement statement=null;
		Vector employees=null;
		try{
			connection=dbconnection();
			if(connection!=null){
				statement = connection.createStatement( );
	            ResultSet rs = statement.executeQuery("select * from employees");
	    		if(rs!=null){
	    			employees=new Vector();
	    			while(rs.next()){
	    				String[] employeeInformation=new String[4];
	    				employeeInformation[0]=rs.getString("name");
	    				employeeInformation[1]=rs.getString("title");
	    				employeeInformation[2]=rs.getString("role");
	    				employeeInformation[3]=""+rs.getInt("bill_rate");
	    				employees.add(employeeInformation);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return employees;
    }
	
	public Vector projectsData(){
		Connection connection=null;
		Statement statement=null;
		Vector employees=null;
		try{
			connection=dbconnection();
			if(connection!=null){
				statement = connection.createStatement( );
	            ResultSet rs = statement.executeQuery("select * from projects");
	    		if(rs!=null){
	    			employees=new Vector();
	    			while(rs.next()){
	    				String[] employeeInformation=new String[9];
	    				employeeInformation[0]=""+rs.getInt("projectnumber");
	    				employeeInformation[1]=""+rs.getInt("clientnumber");
	    				employeeInformation[2]=rs.getString("projectname");
	    				employeeInformation[3]=rs.getString("startdate");
	    				employeeInformation[4]=rs.getString("enddate");
	    				employeeInformation[5]=rs.getString("status");
	    				employeeInformation[6]=rs.getString("projectmanagername");
	    				employeeInformation[7]=rs.getString("clientcontactname");
	    				employeeInformation[8]=""+rs.getDouble("projectbudget");
	    				employees.add(employeeInformation);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return employees;
    }
	
	public Vector clientsData(){
		Connection connection=null;
		Statement statement=null;
		Vector employees=null;
		try{
			connection=dbconnection();
			if(connection!=null){
				statement = connection.createStatement( );
	            ResultSet rs = statement.executeQuery("select * from clients");
	    		if(rs!=null){
	    			employees=new Vector();
	    			while(rs.next()){
	    				String[] employeeInformation=new String[12];
	    				employeeInformation[0]=""+rs.getInt("clientnumber");
	    				employeeInformation[1]=rs.getString("clientname");
	    				employeeInformation[2]=rs.getString("addressline1");
	    				employeeInformation[3]=rs.getString("addressline2");
	    				employeeInformation[4]=rs.getString("city");
	    				employeeInformation[5]=rs.getString("state");
	    				employeeInformation[6]=""+rs.getInt("zip");
	    				employeeInformation[7]=rs.getString("email");
	    				employeeInformation[8]=rs.getString("contactperson");
	    				employeeInformation[9]=rs.getString("invoicefrequency");
	    				employeeInformation[10]=rs.getString("billingterms");
	    				employeeInformation[11]=rs.getString("invoicegrouping");
	    				employees.add(employeeInformation);
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return employees;
    }
	
	public String[] checkEmployeeData(String employeename){
		Connection connection=null;
		Statement statement=null;
		String[] employee=null;
		try{
			connection=dbconnection();
			if(connection!=null){
				statement = connection.createStatement( );
	            ResultSet rs = statement.executeQuery("select * from employees where name='"+employeename+"'");
	    		if(rs!=null){
	    			while(rs.next()){
	    				employee=new String[4];
	    				employee[0]=rs.getString("name");
	    				employee[1]=rs.getString("title");
	    				employee[2]=rs.getString("role");
	    				employee[3]=""+rs.getInt("bill_rate");
	    			}
	    		}
			}
        }catch(SQLException err){
        	err.printStackTrace();
        }
        return employee;
    }
}
