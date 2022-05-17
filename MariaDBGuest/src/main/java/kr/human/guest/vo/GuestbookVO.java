package kr.human.guest.vo;

import java.util.Date;

import lombok.Data;

@Data
public class GuestbookVO {
	private int idx;
	private String name;
	private String password;
	private String content;
	private Date   regDate;
	private String ip;
	
	// 하나를 추가하자!!! 저장/수정/삭제를 구분하기 위한 
	private String mode;
}
