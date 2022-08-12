package com.pro.member;

public class Member {

	/*
	 * MEMBER_ID VARCHAR2(20 BYTE) COLUMN1 VARCHAR2(20 BYTE) COLUMN2 VARCHAR2(20
	 * BYTE) ACCOUNT_ID VARCHAR2(20 BYTE) ROLE CHAR(1 BYTE)
	 */

	private String memberId;
	private String memberPw;
	private String memberName;
	private int memberCode;
	private int memberPoint;
	private String role; // 권한, 1이면 관리자, 0이면 일반회원
	private String memberDate; // 가입일자
	private String memberAttendate; // 출석일자
	private String memberNameDay; // 닉네임 변경 일자
	private String memberNameTime; // 닉네임 변경 시간
	private int memberReport; // 회원 신고당한수
	private String memberNum; // 회원조회 할때 순서


	public String getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}

	public int getMemberReport() {
		return memberReport;
	}

	public void setMemberReport(int memberReport) {
		this.memberReport = memberReport;
	}

	public String getMemberNameDay() {
		return memberNameDay;
	}

	public void setMemberNameDay(String memberNameDay) {
		this.memberNameDay = memberNameDay;
	}

	public String getMemberNameTime() {
		return memberNameTime;
	}

	public void setMemberNameTime(String memberNameTime) {
		this.memberNameTime = memberNameTime;
	}

	public String getMemberAttendate() {
		return memberAttendate;
	}

	public void setMemberAttendate(String memberAttendate) {
		this.memberAttendate = memberAttendate;
	}

	public String getMemberDate() {
		return memberDate;
	}

	public void setMemberDate(String memberDate) {
		this.memberDate = memberDate;
	}

	public int getMemberPoint() {
		return memberPoint;
	}

	public void setMemberPoint(int memberPoint) {
		this.memberPoint = memberPoint;
	}

	public int getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(int memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPw() {
		return memberPw;
	}

	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "( ID : " + memberId + " )( 닉네임 : " + memberName + " )"
			 + "( 포인트 :" + memberPoint + " )( 가입일자 : " + memberDate + " )";
	}

	public String toStringReport() {
		return "( " + memberNum +" )( ID : " + memberId +" )( 닉네임 : " + memberName + " )( 신고수 : "+memberReport +" )( 가입일자 : "+ memberDate + " )";
	}

}
