package com.pro.comment;

public class Comment {

//	private int CommentReport;
//	private int CommentRecommend;

	private int commentId; // 혹시나해서
	private int boardId; //보고 있는 게시물 번호
	private String memberName;
	private String commentContents;
	private String commentDate;

	public int getBoardId() {
		return boardId;
	}

	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCommentContents() {
		return commentContents;
	}

	public void setCommentContents(String commentContents) {
		this.commentContents = commentContents;
	}

	public String getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(String commentDate) {
		this.commentDate = commentDate;
	}

	@Override
	public String toString() {
		return " [ " + memberName + " : " + commentContents + " ] [ " + commentDate +  " ] ";
	}

}
