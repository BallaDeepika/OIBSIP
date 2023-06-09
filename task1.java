package oasis;

import java.util.*;
import java.sql.*;

//Name - Balla Deepika//
 
public class task1{

	static Scanner sc=new Scanner(System.in);
	
	static String register() throws ClassNotFoundException, SQLException {
		System.out.print("Enter Username:");
		String user=sc.next();
		System.out.print("Enter account number:");
		int acc=sc.nextInt();
		System.out.print("Enter ATM PIN:");
		int pin=sc.nextInt();
		System.out.print("Enter Money to Deposit:");
		int bal=sc.nextInt();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","devayani");
		String sql="INSERT INTO atm(username,account,pin,transchis,bal) VALUES (?,?,?,?,?)";
		PreparedStatement statement1 = con.prepareStatement(sql);
	    statement1.setString(1, user);
	    statement1.setInt(2, acc);
	    statement1.setInt(3, pin);
	    statement1.setInt(4,0);
	    statement1.setInt(5, bal);
	    int rowsInserted = statement1.executeUpdate();
	    statement1.close();
	    con.close();
	    
	    System.out.println("Registration successfull");
		return user;
		
	}
	
	
	static void withdraw(String user) throws ClassNotFoundException, SQLException {
		System.out.println("Enter amount for withdraw");
		int dep=sc.nextInt();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","devayani");
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from ATM where username='"+user+"'");
		if(resultSet.next()==true) {
			if(dep>resultSet.getInt(5)) {
				System.out.println("Insufficient funds");
			}
			else {
				System.out.println("Withdrawl successfull");
				int b=resultSet.getInt(5)-dep;
				PreparedStatement statement1 = con.prepareStatement("UPDATE ATM SET bal=?  WHERE username=?");
				statement1.setInt(1, b);
				statement1.setString(2,user );
				statement1.executeUpdate();
				int t=resultSet.getInt(4)+1;
				PreparedStatement statement2 = con.prepareStatement("UPDATE ATM SET transchis=?  WHERE username=?");
				statement2.setInt(1, t);
				statement2.setString(2, user);
		
				
				statement2.executeUpdate();
				System.out.println("Remaining Balance:"+b);
				statement1.close();
				statement2.close();
			}
		}
		resultSet.close();
		statement.close();
		con.close();
	}
	
	
	static void checkbal(String user) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","devayani");
		Statement statement = con.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from ATM where username='"+user+"'");
		if(resultSet.next()==true) {
			System.out.println("Your Balance is:"+resultSet.getInt(5));
		}
		resultSet.close();
		statement.close();
		con.close();
	}
	
	
	static String login() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","devayani");
		for(int i=0;i<3;i++) {
			System.out.println("Enter username:");
			String id=sc.next();
			System.out.println("Enter ATM PIN:");
			int pwsd=sc.nextInt();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("select * from ATM where username='"+id+"' and pin='"+pwsd+"'");
			if(rs.next()) {
				System.out.println("Successfully logged in!!");
				return id;
			}
			else {
				System.out.println("Invalid details TRY AGAIN")
	;			continue;
			}
			
		}
		System.out.println("Exceeded ur limit contact BANK");
		return null;
	}
	 
	
	static void deposit(String user) throws ClassNotFoundException, SQLException {
		System.out.print("Enter the amount to be deposited:");
		int am=sc.nextInt();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","devayani");
		Statement st1=con.createStatement();
		ResultSet rs1=st1.executeQuery("select * from ATM where username='"+user+"'");
		int bal=-1;
		if(rs1.next()) {bal=rs1.getInt(5);};
		PreparedStatement pt=con.prepareStatement("update ATM set bal=? where username=?");
		pt.setInt(1,bal+am );
		pt.setString(2, user);
		pt.executeUpdate();
		System.out.println("Deposited Succesfully");
	}
	
	
	static void thist(String user) throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","devayani");
		Statement st1=con.createStatement();
		ResultSet rs1=st1.executeQuery("select * from ATM where username='"+user+"'");
		int bal=-1;
		if(rs1.next()) {bal=rs1.getInt(4);};
		System.out.println("Number of Transactions:"+bal);
	}
	
	
	 
	static void transfer(String user) throws ClassNotFoundException, SQLException {
		System.out.println("Enter the account number you want to tranfer:");
		int id=sc.nextInt();
		System.out.println("Enter the amount to be transferred:");
		int am=sc.nextInt();
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","devayani");
		Statement st=con.createStatement();
		ResultSet rs=st.executeQuery("select * from ATM where account='"+id+"'");
		int transbal=-1;
		if(rs.next()) { 
			transbal=rs.getInt(5);
			Statement st1=con.createStatement();
			ResultSet rs1=st1.executeQuery("select * from ATM where username='"+user+"'");
			int bal=-1;
			if(rs1.next()) {bal=rs1.getInt(5);};
			if( am<=bal) {
				PreparedStatement pt=con.prepareStatement("update ATM set bal=? where account=?");
				pt.setInt(1, am+transbal);
				pt.setInt(2, id);
				pt.executeUpdate();
				PreparedStatement pt1=con.prepareStatement("update ATM set bal=? where username=?");
				pt1.setInt(1, bal-am);
				pt1.setString(2, user);
				pt1.executeUpdate();
				System.out.println("Amount"+am+"/- succefully transferred to Account "+id);
				System.out.println("Remaining Balance is:"+(bal-am));
			}
			else {
				System.out.println("Account number invalid or Insufficiant balance");
			}
		}   
		else {
			System.out.println("Account number not found");  
		}
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
//
	System.out.println("Welcome to ATM Interface!!");
	System.out.print("If you have exsisting account press 1 or press 0 to register:");
	int a=sc.nextInt();
	if(a==0) {
		register();
	}								
	System.out.println("LOGIN");
	String user=login();
	if(user==null) {
		System.out.println("INVALID login credentials");
	}
	while(true) {
		System.out.println("Select any one of the options");
		System.out.println("1.WITHDRAWAL"+"\n"+"2.DEPOSIT"+"\n"+"3.CHECK BALANCE"+"\n"+"4.TRANSFER"
		                             +"\n"+"5.TRANSACTION HISTORY"+"\n"+"6.QUIT");
		int ch=sc.nextInt();
		switch(ch) {
		case 1:withdraw(user);
		break;
		case 2:deposit(user);              
		break;
		case 3:checkbal(user);
		break;
		case 4:transfer(user);
		break;
		case 5:thist(user);
		break;
		case 6:System.out.println("Remove your card"+"\n"+"THANK YOU!!");  
		     System.exit(0);
		default: System.out.println("Enter a valid option!!");
		
	 }
	}
}
}