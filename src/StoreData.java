
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class StoreData {
	private PrintWriter pw;

	void outputCsv(String FileName, double Data[][]){
		if(FileName.indexOf(".csv") != -1){
			try {
				pw = new PrintWriter(new FileWriter(FileName, false));

				for(int i=0;i<Data.length;i++){
					pw.print(i+1);
					pw.print(",");
					for(int j=0;j<Data[i].length;j++){
						pw.print(Data[i][j]);
						pw.print(",");
					}
					pw.println();
				}
				pw.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}else{
			System.out.println("拡張子を確認してください");
		}
	}

	void outputCsv(String FileName, String Data[][]){
		if(FileName.indexOf(".csv") != -1){
			try {
				pw = new PrintWriter(new FileWriter(FileName, false));

				for(int i=0;i<Data.length;i++){
					pw.print(i+1);
					pw.print(",");
					for(int j=0;j<Data[i].length;j++){
						pw.print(Data[i][j]);
						pw.print(",");
					}
					pw.println();
				}
				pw.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
		}else{
			System.out.println("拡張子を確認してください");
		}
	}


	void insertColumn(PrintWriter PW, String clumn[]){
		for(int i=0; i<clumn.length;i++){
			PW.print(clumn[i]);
			PW.print(",");
		}
		PW.println();
	}
}

class DB{
	static String p; //DB price
	static String f; //DB file name
	Connection con=null;
	DatabaseMetaData dm=null;
	Statement st=null;

	//constracter
	public DB(String price,String file){
		p =price;
		f =file;
	}

	//DB connection
	public boolean DBconnection(){
		try{
			Class.forName("org.sqlite.JDBC"); //JDBC�ڑ�
			con =DriverManager.getConnection("jdbc:sqlite:"+p+f); //connect dbms
			dm=con.getMetaData();
			st=con.createStatement();
			return true;
		}catch(Exception e){
			e.printStackTrace(); //�G���[�\��
			return false;
		}
	}

	public void DBclose(){
		try {
			st.close();
			con.close();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	public boolean booleanExistTable(String tableName){
		boolean judge = false;
		try{
			ResultSet tb=dm.getTables(null, null, tableName, null);
			if(tb.next()){
				judge=true;
			}else{
				judge=false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return judge;
	}


	//create table for INTEGER
	public void creTableIn(String tableName,String column){
		try {

			st.executeUpdate("CREATE TABLE "+tableName+" ( "+column+" INTEGER "+" ) ");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
		}
	}
	//create table for REAL
	public void creTableFlo(String tableName,String column){
		try {
			st.executeQuery("CREATE TABLE "+tableName+" ( "+column+" REAL "+" ) ");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
		}
	}

	//create table for text
	public void creTableTxt(String tableName,String column){
		try {
			st.executeQuery("CREATE TABLE "+tableName+" ( "+column+" TEXT "+" ) ");
		} catch (SQLException e) {
		}
	}

	//add column for integer
	public void addColumnIn(String tableName,String column){
		try {
			st.executeUpdate("ALTER TABLE "+tableName+" ADD COLUMN "+column+"[ INTEGER] ");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	//add column for real
	public void addColumnFlo(String tableName,String column){
		try {
			st.executeUpdate("ALTER TABLE "+tableName+" ADD COLUMN "+column+"[ REAL] ");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//add column for text
	public void addColumnTxt(String tableName,String column){
		try {
			st.executeUpdate("ALTER TABLE "+tableName+" ADD COLUMN "+column+"[ TEXT] ");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//insert data
	public void addData(String tableName,String columns,String data){
		try {
			st.executeUpdate("INSERT INTO "+tableName+" ( "+columns+" ) "+" VALUES "+" ( "+data+" ) ");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}
