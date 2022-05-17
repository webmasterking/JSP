package kr.human.guest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.human.guest.vo.GuestbookVO;
import kr.human.jdbc.JDBCUtil;

public class GuestbookDAO {
	// 싱글톤으로 만들자
	private static GuestbookDAO instance = new GuestbookDAO();
	private GuestbookDAO() {}
	public static GuestbookDAO getInstance() {
		return instance;
	}
	//--------------------------------------------------------------
	// 1. 전체 개수 구하기
	public int selectCount(Connection conn) throws SQLException {
		int count = 0; // 리턴타입의 변수를 만든다.
		String sql = "select count(*) as cnt from guestbook"; // 실행할 SQL명령어를 만든다.
		Statement stmt = conn.createStatement(); // 명령 객체를 만든다.
		ResultSet rs = stmt.executeQuery(sql); // 명령실행
		rs.next();
		count = rs.getInt("cnt"); // 결과 얻기
		JDBCUtil.close(rs); // 닫기
		JDBCUtil.close(stmt);
		return count; // 결과 보내기
	}
	// 2. 1개 얻기
	public GuestbookVO selectByIdx(Connection conn, int idx) throws SQLException {
		GuestbookVO vo = null;
		String sql = "select * from guestbook where idx=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, idx);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			vo = new GuestbookVO();
			vo.setIdx(rs.getInt("idx"));
			vo.setName(rs.getString("name"));
			vo.setPassword(rs.getString("password"));
			vo.setContent(rs.getString("content"));
			vo.setRegDate(rs.getTimestamp("regDate"));
			vo.setIp(rs.getString("ip"));
		}
		JDBCUtil.close(rs);
		JDBCUtil.close(pstmt);
		return vo;
	}
	// 3. 1페이지 분량의 데이터 얻기
	public List<GuestbookVO> selectList(Connection conn, int startNo, int pageSize) throws SQLException{
		List<GuestbookVO> list = null;
		String sql = "select * from guestbook order by idx desc limit ?, ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, startNo);
		pstmt.setInt(2, pageSize);
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			list = new ArrayList<>();
			do {
				GuestbookVO vo = new GuestbookVO();
				vo.setIdx(rs.getInt("idx"));
				vo.setName(rs.getString("name"));
				vo.setPassword(rs.getString("password"));
				vo.setContent(rs.getString("content"));
				vo.setRegDate(rs.getTimestamp("regDate"));
				vo.setIp(rs.getString("ip"));
				
				list.add(vo);
			}while(rs.next());
		}
		JDBCUtil.close(rs);
		JDBCUtil.close(pstmt);
		return list;
	}
	// 4. 저장하기
	public int insert(Connection conn, GuestbookVO vo) throws SQLException {
		int count = 0; // 리턴타입의 변수를 만든다.
		String sql = "insert into guestbook (name,password,content,ip) values(?,?,?,?)"; // 실행할 SQL명령어를 만든다.
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getName());
		pstmt.setString(2, vo.getPassword());
		pstmt.setString(3, vo.getContent());
		pstmt.setString(4, vo.getIp());
		count = pstmt.executeUpdate();
		JDBCUtil.close(pstmt); // 닫기
		return count; // 결과 보내기
	}
	// 5. 수정하기
	public int update(Connection conn, GuestbookVO vo) throws SQLException {
		int count = 0; // 리턴타입의 변수를 만든다.
		String sql = "update guestbook set content=?, regdate=now(), ip=? where idx=?"; // 실행할 SQL명령어를 만든다.
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getContent());
		pstmt.setString(2, vo.getIp());
		pstmt.setInt(3, vo.getIdx());
		count = pstmt.executeUpdate();
		JDBCUtil.close(pstmt); // 닫기
		return count; // 결과 보내기
	}
	
	// 6. 삭제하기
	public int delete(Connection conn, int idx) throws SQLException {
		int count = 0; // 리턴타입의 변수를 만든다.
		String sql = "delete from guestbook where idx=?"; // 실행할 SQL명령어를 만든다.
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, idx);
		count = pstmt.executeUpdate();
		JDBCUtil.close(pstmt); // 닫기
		return count; // 결과 보내기
	}	
}
