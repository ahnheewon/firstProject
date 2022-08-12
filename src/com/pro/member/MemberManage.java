package com.pro.member;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pro.board.Board;
import com.pro.common.DAO;

public class MemberManage extends DAO {

	// 싱글톤
	private static MemberManage mm = new MemberManage();

	// static 일치 시켜준다.
	private MemberManage() {

	}

	public static MemberManage getInstance() {
		return mm; // static 일치 시켜준다.
	}

	// 로그인 메소드
	public Member loginInfo(String id) {
		Member member = null;
		try {
			conn();
			// id -> PK
			// 1개의 row만 반환됨.
			String sql = "select * from member where member_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				// MemberManager -> 로그인 메소드
				// setAccountId -> setMemberId
				member = new Member();
				member.setMemberId(rs.getString("member_id"));
				member.setMemberPw(rs.getNString("member_pw"));
				member.setMemberName(rs.getNString("member_name"));
				member.setRole(rs.getString("member_role"));
				member.setMemberCode(rs.getInt("member_code"));
				member.setMemberPoint(rs.getInt("member_point"));
				member.setMemberDate(rs.getString("member_date")); // 가입일자
				member.setMemberAttendate(rs.getString("member_attendate")); // 최근출석일자
				member.setMemberNameDay(rs.getString("member_nameday")); // 닉네임변경일자
				member.setMemberNameTime(rs.getString("member_nametime")); // 닉네임변경시간
				member.setMemberReport(rs.getInt("member_report")); // 신고당한 횟수
				// 반환값을 객체에 넣음.
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return member;
	}

	// 등록 메소드
	public int registCustomer(Member member) {
		int result = 0;
		try {
			conn();
			String startDay = "";
			String sql0 = "select sysdate from dual";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql0); /// member_date 를 가져오기 위함.

			if (rs.next()) {
				startDay = rs.getString("SYSDATE");
			}
			member.setMemberDate(startDay);

			String sql = "insert into member (member_id, member_pw,"
					+ "member_name,member_role,member_Code,member_date) values (?,?,?,?,?,?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getRole());
			pstmt.setInt(5, member.getMemberCode());
			pstmt.setString(6, member.getMemberDate());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;

	}

	// 2-2 id/pw 찾기용!!
	public List<Member> getSearchCode() {
		List<Member> list = new ArrayList<>();
		Member mem;
		try {
			conn();
			String sql = "select * from member where member_role=0";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				mem = new Member();
				mem.setMemberId(rs.getString("Member_id"));
				mem.setMemberName(rs.getString("Member_name"));
				mem.setMemberPw(rs.getString("member_pw"));
				mem.setMemberCode(rs.getInt("member_Code"));
				list.add(mem); // 리스트에 계속 추가 되면서 반환.
			}
		} catch (SQLException e) {
			// ora-00001 : 어떤 이유로 오류가 났습니다.
			System.out.println("※※※※ Error ※※※※");
			System.out.println("code : " + e.getErrorCode());
			System.out.println(e.getMessage()); // 이유
			e.getMessage();
			e.getErrorCode();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	// 2-2 일반 회원 전체 상세 조회 /// 조회용
	public List<Member> getDetailProfile() {
		List<Member> list = new ArrayList<>();
		Member mem;

		try {
			conn();
			String sql = "select rownum,member_id,member_name,member_point,member_report,member_Date from member where member_role=0";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				mem = new Member();
				mem.setMemberNum(rs.getString("rownum"));
				mem.setMemberId(rs.getString("Member_id"));
				mem.setMemberName(rs.getString("Member_name"));
				mem.setMemberPoint(rs.getInt("member_point"));
				mem.setMemberReport(rs.getInt("member_report"));
				mem.setMemberDate(rs.getString("Member_date"));
				list.add(mem); // 리스트에 계속 추가 되면서 반환.
			}

		} catch (SQLException e) {
			// ora-00001 : 어떤 이유로 오류가 났습니다.
			System.out.println("※※※※ Error ※※※※");
			System.out.println("code : " + e.getErrorCode());
			System.out.println(e.getMessage()); // 이유
			e.getMessage();
			e.getErrorCode();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	// 2-2 회원 한명 상세 조회
	public List<Member> getOneDetailProfile(String memberName) {

		List<Member> list = new ArrayList<>();
		Member mem;

		try {
			conn();
			String sql = "select * from member where member_role=0 and member_name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberName);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				mem = new Member();
				mem.setMemberId(rs.getString("Member_id"));
				mem.setMemberPw(rs.getString("Member_pw"));
				mem.setMemberName(rs.getString("Member_name"));
				mem.setMemberCode(rs.getInt("Member_Code"));
				mem.setMemberPoint(rs.getInt("member_point"));
				mem.setMemberDate(rs.getString("member_date"));
				mem.setMemberReport(rs.getInt("member_report"));
				list.add(mem); // 리스트에 계속 추가 되면서 반환.
			}

		} catch (SQLException e) {
			// ora-00001 : 어떤 이유로 오류가 났습니다.
			System.out.println("※※※※ Error ※※※※");
			System.out.println("code : " + e.getErrorCode());
			System.out.println(e.getMessage()); // 이유
			e.getMessage();
			e.getErrorCode();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	// 회원 강퇴하기
	public int kickMember(String memberId) {
		int result = 0;
		try {
			conn();
			String sql = "delete from member where member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	// 회원 출첵하기
	public void AttendMember(Member member, String today) {

		try {
			conn();

			String sql0 = "select to_char(sysdate,'yy/mm/dd') from dual";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql0); // 오늘 날짜 가져오기.

			if (rs.next()) {
				today = rs.getString("to_char(sysdate,'yy/mm/dd')");
			}

			String sql1 = "update member set member_attendate=? where member_name=?";
			pstmt = conn.prepareStatement(sql1);
			pstmt.setString(1, today);
			pstmt.setString(2, member.getMemberName());

			if (today.equals(member.getMemberAttendate())) { // 오늘 날짜랑 멤버출석일과 같다면?
				System.out.println("오늘은 이미 출석 하셨습니다~");
				System.out.println("내일 또 해주세요!");
			} else if (!today.equals(member.getMemberAttendate())) { // 다르면 출석체크 됨.
				int result = pstmt.executeUpdate();
				if (result == 1) {
					member.setMemberAttendate(today);
					System.out.println("출석 체크 완료!");
					String sql2 = "update member set member_point=member_point+5 where member_name=?";
					pstmt = conn.prepareStatement(sql2);
					pstmt.setString(1, member.getMemberName());
					pstmt.executeUpdate();
					System.out.println("출석으로 5 포인트 지급되었습니다!");
				} else if (result == 0) {
					System.out.println("출석 체크 실패");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void MinusPoint(Member member, int whatcreate) { // 작성 시 포인트 차감!
		String sql = "";
		try {
			conn();
			if (whatcreate == 3) { // 3이면 게시물 작성
				sql = "update member set member_point=member_point-3 where member_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMemberId());
				pstmt.executeUpdate();

			} else if (whatcreate == 1) { // 1이면 덧글 작성
				sql = "update member set member_point=member_point-1 where member_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getMemberId());
				pstmt.executeUpdate();

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

	}

	public void changeNickname(Member member, String newName) { // 닉네임변경
		String today = "";
		String todayTime = "";
		String nextDay = "";
		// String nextDayandTime = "";
		// String todayandTime = "";

		try {
			conn();

			String sql0 = "select to_char(sysdate,'yymmdd')" + ",to_char(sysdate,'HH24MI')"
					+ ",to_char(sysdate+1,'yymmdd')"
					+ ",to_char(sysdate,'yy/mm/dd HH24:MI'),to_char(sysdate+1,'yy/mm/dd HH24:MI') from dual";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql0); // 오늘 날짜, 현재시간 , 내일 날짜만 가져오기. 예 220811

			if (rs.next()) {
				today = rs.getString("to_char(sysdate,'yymmdd')");// 오늘날짜 숫자
				todayTime = rs.getString("to_char(sysdate,'HH24MI')");// 오늘시간 숫자
				nextDay = rs.getString("to_char(sysdate+1,'yymmdd')");// 다음날 날짜 숫자
				// todayandTime = rs.getString("to_char(sysdate,'yy/mm/ddHH24MI')");// 양식 설정된 오늘
				// 날짜시간
				// nextDayandTime = rs.getString("to_char(sysdate+1,'yy/mm/ddHH24:MI')");// 양식
				// 설정된 다음날 날짜시간
			}

//220811 member_namedate
//220812 today 

			if (Integer.parseInt(today) > Integer.parseInt(member.getMemberNameDay())) { // 오늘날짜가 닉네임변경일보다 큰데
				if (Integer.parseInt(todayTime) < Integer.parseInt(member.getMemberNameTime())) { // 시간이 작다면 불가능
					System.out.println("ERROR : 닉네임 변경 실패, 24시간 후 다시시도 해주세요.");
					// System.out.println("변경 가능 시간 :" + nextDayandTime);
				} else { // 다르면 변경됨.
					String sql2 = "update member set member_name=?,member_nameday=?,member_nametime=? where member_id=?";
					pstmt = conn.prepareStatement(sql2);
					pstmt.setString(1, newName);
					pstmt.setString(2, today); // 닉네임 변경이 되면서 변경일자는 현재일자로.
					pstmt.setString(3, todayTime); // 닉네임 변경이 되면서 변경시간은 현재시간으로.
					pstmt.setString(4, member.getMemberId());
					int result = pstmt.executeUpdate();
					if (result == 1) {
						System.out.println("닉네임 변경 완료.");
					} else if (result == 0) {
						System.out.println("닉네임 변경 실패, 내일 다시 이용해주세요.");
					}
				}
			} else {
				
				System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
				System.out.println("▓ERROR : 닉네임 변경 실패, 24시간 후 다시시도 해주세요.▓			  ");
				System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n");

				// System.out.println("변경 가능 시간 :" + nextDayandTime);
			
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void changePw(Member member, String newPw) { // 패스워드 변경

		try {
			conn();

			String sql2 = "update member set member_pw=? where member_id=?";
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, newPw);
			pstmt.setString(2, member.getMemberId());
			int result = pstmt.executeUpdate();
			if (result == 1) {
				// System.out.println("패스워드 변경 완료.");
			} else if (result == 0) {
				System.out.println("에러 발생");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void ReportMember(String memberName) { // 멤버 신고수 증가
		try {
			conn();
			String sql = "update member set member_report=member_report+1 where member_name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberName);

			int result = pstmt.executeUpdate();
			if (result == 1) {

			} else
				System.out.println("실패하였습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public List<Member> getReportListMember() { //////// 신고된 회원 조희
		List<Member> list = new ArrayList<>();
		Member mem;

		try {
			conn();
			String sql = "select rownum,member_id,member_name,member_report,member_Date from member where member_report>0";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				mem = new Member();

				mem.setMemberNum(rs.getString("rownum"));
				mem.setMemberId(rs.getString("Member_id"));
				mem.setMemberName(rs.getString("Member_name"));
				// mem.setMemberPoint(rs.getInt("member_point"));
				mem.setMemberReport(rs.getInt("member_report"));
				mem.setMemberDate(rs.getString("member_date"));

				list.add(mem); // 리스트에 계속 추가 되면서 반환.
			}

		} catch (SQLException e) {
			// ora-00001 : 어떤 이유로 오류가 났습니다.
			System.out.println("※※※※ Error ※※※※");
			System.out.println("code : " + e.getErrorCode());
			System.out.println(e.getMessage()); // 이유
			e.getMessage();
			e.getErrorCode();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

}
