package kr.human.memo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.human.jdbc.JDBCUtil;
import kr.human.memo.vo.CommentVO;
public class CommentDAOImpl implements CommentDAO{
	// ----------------------------------------------------------------------
	// 싱글톤 클래스로 만들자!!!
	// 1. 자기 자신의 객체를 정적 멤버로 만든다.
	private static CommentDAO instance = new CommentDAOImpl();
	// 2. 밖에서 객체를 생성하지 못하도록 생성자를 private으로 만든다.
	private CommentDAOImpl() {
		;
	}
	// 3. 자신의 객체를 리턴하는 메서드를 만들어 준다.
	public static CommentDAO getInstance() {
		return instance;
	}
	// ----------------------------------------------------------------------
	@Override
	public int selectCount(Connection conn) throws SQLException {
		int count = 0; // 리턴 타입의 변수 선언
		String sql = "select count(*) from comment"; // 실행할 SQL명령을 만들고
		Statement stmt = conn.createStatement(); // 명령 객체 생성
		ResultSet rs = stmt.executeQuery(sql); // 명령 실행
		rs.next(); // 다음 레코드(행)로 이동
		count = rs.getInt(1); // 값 얻기
		JDBCUtil.close(rs); // 닫기
		JDBCUtil.close(stmt);
		return count; // 결과 리턴
	}
	@Override
	public CommentVO selectByIdx(Connection conn, int idx) throws SQLException {
		CommentVO commentVO = null; // 리턴타입 변수 만들기
		String sql = "select * from comment where idx=?"; // SQL 명령어 작성
		PreparedStatement pstmt = conn.prepareStatement(sql); // 명령 객체 생성
		pstmt.setInt(1, idx); // 명령어 완성
		ResultSet rs = pstmt.executeQuery(); // 명령어 실행
		if(rs.next()) { // 데이터 있다면
			commentVO = new CommentVO(); // 객체를 생성하고
			commentVO.setIdx(rs.getInt("idx")); // 각각의 필드에 값을 저장
			commentVO.setName(rs.getString("name"));
			commentVO.setPassword(rs.getString("password"));
			commentVO.setContent(rs.getString("content"));
			commentVO.setRegDate(rs.getTimestamp("regDate"));
			commentVO.setIp(rs.getString("ip"));
		}
		JDBCUtil.close(rs); // 닫기
		JDBCUtil.close(pstmt);
		return commentVO; // 결과 리턴
	}
	@Override
	public List<CommentVO> selectList(Connection conn, int startNo, int pageSize) throws SQLException {
		List<CommentVO> list = null; // 리턴타입 변수 만들기
		String sql = "select * from comment order by idx desc limit ?, ?"; // SQL 명령어 작성
		PreparedStatement pstmt = conn.prepareStatement(sql); // 명령 객체 생성
		pstmt.setInt(1, startNo); // 몇번째 부터 
		pstmt.setInt(2, pageSize); // 몇개
		ResultSet rs = pstmt.executeQuery(); // 명령어 실행
		if(rs.next()) {
			list = new ArrayList<>();
			do {
				CommentVO commentVO = new CommentVO(); // 객체를 생성하고
				commentVO.setIdx(rs.getInt("idx")); // 각각의 필드에 값을 저장
				commentVO.setName(rs.getString("name"));
				commentVO.setPassword(rs.getString("password"));
				commentVO.setContent(rs.getString("content"));
				commentVO.setRegDate(rs.getTimestamp("regDate"));
				commentVO.setIp(rs.getString("ip"));
				
				list.add(commentVO); // 리스트에 추가
			}while(rs.next());
		}
		JDBCUtil.close(rs); // 닫기
		JDBCUtil.close(pstmt);		
		return list;
	}
	@Override
	public int insert(Connection conn, CommentVO vo) throws SQLException {
		int count = 0; // 리턴 타입의 변수 선언
		String sql = "insert into comment (name,password,content,regDate,ip) values (?,?,?,now(),?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getName());
		pstmt.setString(2, vo.getPassword());
		pstmt.setString(3, vo.getContent());
		pstmt.setString(4, vo.getIp());
		count  = pstmt.executeUpdate();
		JDBCUtil.close(pstmt);
		return count;
	}
	@Override
	public int update(Connection conn, CommentVO vo) throws SQLException {
		int count = 0; // 리턴 타입의 변수 선언
		String sql = "update comment set content=?, regDate=now(), ip=? where idx=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, vo.getContent());
		pstmt.setString(2, vo.getIp());
		pstmt.setInt(3, vo.getIdx());
		count  = pstmt.executeUpdate();
		JDBCUtil.close(pstmt);
		return count;
	}
	@Override
	public int delete(Connection conn, int idx) throws SQLException {
		int count = 0; // 리턴 타입의 변수 선언
		String sql = "delete from comment where idx=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, idx);
		count  = pstmt.executeUpdate();
		JDBCUtil.close(pstmt);
		return count;	
	}
}

