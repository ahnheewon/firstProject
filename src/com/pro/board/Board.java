package com.pro.board;

import java.sql.Date;

public class Board {

	/*
	 * BOARD_ID NUMBER BOARD_NAME VARCHAR2(100 BYTE) BOARD_MAKER VARCHAR2(40 BYTE)
	 * BOARD_CONTENTS VARCHAR2(3000 BYTE)
	 */

	private int boardId;
	private int boardView;
	private int boardReport;
	private int boardRecommend;
	private String boardName;
	private String memberName;
	private String boardContents;
	private String boardDate;

	

	public int getBoardView() {
		return boardView;
	}

	public void setBoardView(int boardView) {
		this.boardView = boardView;
	}

	public int getBoardReport() {
		return boardReport;
	}

	public void setBoardReport(int boardReport) {
		this.boardReport = boardReport;
	}

	public int getBoardRecommend() {
		return boardRecommend;
	}

	public void setBoardRecommend(int boardRecommend) {
		this.boardRecommend = boardRecommend;
	}

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public String getBoardName() {
		return boardName;
	}

	public void setBoardName(String boardName) {
		this.boardName = boardName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getBoardContents() {
		return boardContents;
	}

	public void setBoardContents(String boardContents) {
		this.boardContents = boardContents;
	}

	public String getBoardDate() {
		return boardDate;
	}

	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}

	@Override
	public String toString() {
		return "(" + boardId + ")( " + memberName + " )( " + boardName + " )( 조회수: "+boardView +" ) / "+ boardDate + "/";
	}
	
	public String toStringReport() {
		return "(" + boardId + ")( " + boardName + " )( 작성자 : " + memberName + " )( 신고수 : "+boardReport +" ) / "+ boardDate + "/";
	}

}
