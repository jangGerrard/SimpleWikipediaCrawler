package kr.ac.kw.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CrawlerDao {
	
	private String keyword;
	
	
	public CrawlerDao(String keyword){
		this.keyword  = keyword;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void makeTable(){
		String sql = "create table "+this.keyword+" ( "
				+ " token varchar(1000), "
				+ " reference integer "
				+ " );";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		try{
		conn = DBFactory.getInstance().getConnection();
		
		pstmt = conn.prepareStatement(sql);
//		pstmt.setString(1, keyword);
		
		pstmt.executeUpdate();
		
		
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void insertTokens(List<String> tokens) throws SQLException{
		String insertSql = "insert into "+this.keyword+" (token, reference) values ( ? , ?  ) ";
		String selectSql = "select token, reference from "+this.keyword+" where token = ?   ";
		String updateSql = "update "+this.keyword+" set reference = ?  where token = \'?\' "; 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = DBFactory.getInstance().getConnection() ; 
		
		for (String token : tokens) {
			//일단 db에서 같은 토큰을 갖는 것이 있는지 확인하고,
			//있으면 referenceCount만 올려주고.
			//없으면 그냥 넣고 referencecount 는 1로 초기화하자.
			pstmt= conn.prepareStatement(selectSql);
			pstmt.setString(1, token);
			rs = pstmt.executeQuery();
			
			if(!(rs.next())){
				//같은게 있어 그러면 update 해야한다.
				pstmt = conn.prepareStatement(insertSql);
				pstmt.setString(1, token);
				pstmt.setInt(2, 1);
				pstmt.executeUpdate();
			}  else {
				//같은게 없으면 insert into 
				pstmt = conn.prepareStatement(updateSql);
				pstmt.setLong(1, (rs.getInt("reference")+1) );
				pstmt.executeUpdate();
			}
			
		}
		
		
		
		
	}
	
	public void registerKeyword(){
		
	}

	
	public static void main(String[] args){
//		makeTable("lee");
	}
	
}
