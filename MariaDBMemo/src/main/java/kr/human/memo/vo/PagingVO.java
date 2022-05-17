package kr.human.memo.vo;

import java.util.List;
// 페이지 계산을 하고 1페이지 분량의 글 목록을 가지는 VO
public class PagingVO<T> {
	private List<T> list; // 글목록
	
	// 4가지는 넘겨서 받자!!!
	private int totalCount; // 전체 개수는 DB에서 받아서
	private int currentPage; // 현재 페이지
	private int pageSize;	 // 페이지당 글 수
	private int blockSize;   // 하단의 페이지 번호 개수
	
	// 나머지는 계산해서 넣자
	private int totalPage; // 전체 페이지 수
	private int startNo;   // 시작 글번호
	private int endNo;     // 마지막 글번호 (오라클에서만 사용)
	private int startPage; // 시작 페이지 번호
	private int endPage; // 마지막 페이지 번호
	
	// 4개 값을 넘겨 받는 생성자
	public PagingVO(int totalCount, int currentPage, int pageSize, int blockSize) {
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.blockSize = blockSize;
		
		calc();
	}

	private void calc() {
		if(totalCount>0) {
			if(currentPage<=0) currentPage=1;
			if(pageSize<=1) pageSize=10;
			if(blockSize<=1) blockSize=10;
		
			// 전체 페이지 수 = (전체 개수 -1)/페이지당 개수 + 1
			// 총 : 123개 , 페이지당 : 10개 ==> 13페이지
			// (123-1)/10 + 1 = 13
			// 총 : 120개 , 페이지당 : 10개 ==> 12페이지
			// (120-1)/10 + 1 = 12
			totalPage = (totalCount-1)/pageSize + 1;
			
			// 시작 글번호 = (현재페이지-1)*페이지당 글수
			// 오라클은 +1을 해준다. 인덱스가 1부터이다.
			// 67개 10개씩
			// 1페이지(0~9) = (1-1)*10 = 0 
			// 2페이지(10~19) = (2-1)*10 = 10
			// 7페이지(60~66) = (7-1)*10 = 60
			startNo = (currentPage-1) * pageSize;
			// 끝 글번호 = 시작글번호 + 페이지당 개수 - 1
			endNo = startNo + pageSize - 1;
			// 끝 글번호는 전체 개수를 넘을 수 없다. (오라클의 경우 조건을 > 로 
			if(endNo>=totalCount) {
				endNo = totalCount-1; // 오라클의 경우는 -1을 하지 않는다.
			}
			// 시작페이지 번호 = (현재페이지-1)/페이지목록개수 * 페이지목록개수 + 1 
			// 현재 7페이지를 본다면 1 ~ 10페이지까지보이기
			// 현재 13페이지를 본다면 11 ~ 20페이지까지보이기
			startPage = (currentPage-1)/blockSize * blockSize + 1;
			//  끝 페이지 = 시작페이지 + 페이지목록개수 - 1
			endPage = startPage + blockSize - 1;
			// 끝페이지는 전체페이지를 넘을 수 없다.
			if(endPage>totalPage) endPage = totalPage;
			
		}
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getBlockSize() {
		return blockSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getStartNo() {
		return startNo;
	}

	public int getEndNo() {
		return endNo;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}
	
	// 메서드 2개를 추가하자
	public String getPageInfo() {
		// 전체 : 5 개(?/?페이지)
		String pageInfo = "전체 : " + totalCount + "개";
		if(totalCount>=1) {
			pageInfo += "(" + currentPage + "/" + totalPage + "Page)";
		}
		return pageInfo;
	}
	
	public String getPageList() {
		String pageList = "";
		// 시작페이지가 1이라면 이전은 없다.
		pageList += "  <ul class='pagination pagination-sm justify-content-center'>";
		if(startPage>1) {
			pageList += "    <li class='page-item'>";
			pageList += "      <a class='page-link' href='?p="+(startPage-1)+"&s="+pageSize+"&b="+blockSize+"' aria-label='Previous'>";
			pageList += "        <span aria-hidden='true'>&laquo;</span>";
			pageList += "      </a>";
			pageList += "    </li>";
		}
		// 페이지 번호 출력
		for(int i=startPage;i<=endPage;i++) {
			if(currentPage==i)
				pageList += "   <li class='page-item active'><a class='page-link' href='?p=" + i +"&s="+pageSize+"&b="+blockSize+"'>" + i + "</a></li>";
			else
				pageList += "   <li class='page-item'><a class='page-link' href='?p=" + i +"&s="+pageSize+"&b="+blockSize+"'>" + i + "</a></li>";
		}
		// 마지막페이지가 전체페이지수 보다 적을 경우에만 다음이 있다
		if(endPage<totalPage) {
			pageList += "	<li class='page-item'>";
			pageList += "      <a class='page-link' href='?p="+(endPage+1)+"&s="+pageSize+"&b="+blockSize+"' aria-label='Next'>";
			pageList += "        <span aria-hidden='true'>&raquo;</span>";
			pageList += "      </a>";
			pageList += "    </li>";
		}
		pageList += "  </ul>";

		return pageList;
	}
	
	
	@Override
	public String toString() {
		return "PagingVO [list=" + list + ", totalCount=" + totalCount + ", currentPage=" + currentPage + ", pageSize="
				+ pageSize + ", blockSize=" + blockSize + ", totalPage=" + totalPage + ", startNo=" + startNo
				+ ", endNo=" + endNo + ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
	

}
