package kr.human.guest.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import kr.human.guest.dao.GuestbookDAO;
import kr.human.guest.vo.GuestbookVO;
import kr.human.guest.vo.PagingVO;
import kr.human.jdbc.JDBCUtil;

public class GuestbookService {
	// 싱글톤
	private static GuestbookService instance = new GuestbookService();
	private GuestbookService() {}
	public static GuestbookService getInstance() {
		return instance;
	}
	//---------------------------------------------------------------------
	// 목록보기
	public PagingVO<GuestbookVO> selectList(int currentPage, int pageSize, int blockSize){
		PagingVO<GuestbookVO> pagingVO = null;
		Connection conn = null;
		try{
			conn = JDBCUtil.getConnection();
			conn.setAutoCommit(false);
			//-----------------------------------------------
			// 명령을 실행한다.
			// 전체 개수 얻기
			int totalCount = GuestbookDAO.getInstance().selectCount(conn);
			// 페이지 계산
			pagingVO = new PagingVO<>(totalCount, currentPage, pageSize, blockSize);
			// 글목록 얻기
			List<GuestbookVO> list = 
					GuestbookDAO.getInstance().selectList(conn, pagingVO.getStartNo(), pagingVO.getPageSize());
			// 글목록을 객체에 주입한다.
			pagingVO.setList(list);
			//-----------------------------------------------
			conn.commit();
		}catch(SQLException e){
			JDBCUtil.rollback(conn);
			e.printStackTrace();
		}finally{
			JDBCUtil.close(conn);
		}		
		return pagingVO;
	}
	// 1개 얻기
	public GuestbookVO selectByIdx(int idx) {
		GuestbookVO vo = null;
		Connection conn = null;
		try{
			conn = JDBCUtil.getConnection();
			conn.setAutoCommit(false);
			//-----------------------------------------------
			// 명령을 실행한다.
			vo = GuestbookDAO.getInstance().selectByIdx(conn, idx);
			//-----------------------------------------------
			conn.commit();
		}catch(SQLException e){
			JDBCUtil.rollback(conn);
			e.printStackTrace();
		}finally{
			JDBCUtil.close(conn);
		}		
		System.out.println("서비스 : " + vo);
		return vo;
	}
	
	// 저장하기
	public int insert(GuestbookVO vo) {
		int count = 0;
		Connection conn = null;
		try{
			conn = JDBCUtil.getConnection();
			conn.setAutoCommit(false);
			//-----------------------------------------------
			// 명령을 실행한다.
			if(vo!=null) {
				if(vo.getName()!=null && vo.getName().trim().length()>0) {
					if(vo.getPassword()!=null && vo.getPassword().trim().length()>0) {
						if(vo.getContent()!=null && vo.getContent().trim().length()>0) {
							count = GuestbookDAO.getInstance().insert(conn, vo);
						}
					}
				}
			}
			//-----------------------------------------------
			conn.commit();
		}catch(SQLException e){
			JDBCUtil.rollback(conn);
			e.printStackTrace();
		}finally{
			JDBCUtil.close(conn);
		}		
		return count;
	}
	
	// 수정하기
	public int update(GuestbookVO vo) {
		int count = 0;
		Connection conn = null;
		try{
			conn = JDBCUtil.getConnection();
			conn.setAutoCommit(false);
			//-----------------------------------------------
			// 명령을 실행한다.
			if(vo!=null) {
				if(vo.getPassword()!=null && vo.getPassword().trim().length()>0) {
					if(vo.getContent()!=null && vo.getContent().trim().length()>0) {
						// DB에서 해당 글번호를 가져와서
						GuestbookVO dbVO = GuestbookDAO.getInstance().selectByIdx(conn, vo.getIdx());
						// 데이터가 있으면서 비번이 일치하면
						if(dbVO!=null && dbVO.getPassword().equals(vo.getPassword())) {
							count = GuestbookDAO.getInstance().update(conn, vo);
						}
					}
				}
			}
			//-----------------------------------------------
			conn.commit();
		}catch(SQLException e){
			JDBCUtil.rollback(conn);
			e.printStackTrace();
		}finally{
			JDBCUtil.close(conn);
		}		
		return count;
	}
	
	// 삭제하기
	public int delete(GuestbookVO vo) {
		int count = 0;
		Connection conn = null;
		try{
			conn = JDBCUtil.getConnection();
			conn.setAutoCommit(false);
			//-----------------------------------------------
			// 명령을 실행한다.
			if(vo!=null) {
				if(vo.getPassword()!=null && vo.getPassword().trim().length()>0) {
					if(vo.getContent()!=null && vo.getContent().trim().length()>0) {
						// DB에서 해당 글번호를 가져와서
						GuestbookVO dbVO = GuestbookDAO.getInstance().selectByIdx(conn, vo.getIdx());
						// 데이터가 있으면서 비번이 일치하면
						if(dbVO!=null && dbVO.getPassword().equals(vo.getPassword())) {
							count = GuestbookDAO.getInstance().delete(conn, vo.getIdx());
						}
					}
				}
			}
			//-----------------------------------------------
			conn.commit();
		}catch(SQLException e){
			JDBCUtil.rollback(conn);
			e.printStackTrace();
		}finally{
			JDBCUtil.close(conn);
		}		
		return count;
	}
}
