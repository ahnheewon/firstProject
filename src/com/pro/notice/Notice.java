package com.pro.notice;

public class Notice {

	// 공지사항 >> 게시글과 흡사

	private int noticeId;
	private String noticeName;
	private String memberName;
	private String noticeContents;
	private String noticeDate;
	
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public String getNoticeName() {
		return noticeName;
	}
	public void setNoticeName(String noticeName) {
		this.noticeName = noticeName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getNoticeContents() {
		return noticeContents;
	}
	public void setNoticeContents(String noticeContents) {
		this.noticeContents = noticeContents;
	}
	public String getNoticeDate() {
		return noticeDate;
	}
	public void setNoticeDate(String noticeDate) {
		this.noticeDate = noticeDate;
	}
	
	@Override
	public String toString() {
		return "(" + noticeId + ")( " + noticeName + " )( " + memberName + " ) / " + noticeDate + " /";
	}
	
	

}
