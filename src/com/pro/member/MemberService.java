package com.pro.member;

import java.util.List;
import java.util.Scanner;

import com.pro.app.Application;
import com.pro.board.Board;
import com.pro.board.BoardManage;

public class MemberService {
	Scanner sc = new Scanner(System.in);

	public static Member memberInfo = null;

	// 로그인
	public void doLogin() {

		Member member = new Member();
		while (true) {
			System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 로그인 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
			System.out.println("취소를 원하시면 :q 를 입력해주세요. > ");
			System.out.println("ID > ");
			String id = sc.nextLine();
			if (id.equals(":q") || id.equals(":ㅂ")) {
				new Application();
			}

			System.out.println("PW > ");
			String pw = sc.nextLine();

			// loginInfo에서 받은 정보를 싱글톤으로 호출.
			// 로그인 정보가 member에 들어감.

			member = MemberManage.getInstance().loginInfo(id);
			if (member != null) {
				// DB에서 조회한 정보와 내가 입력한 PW를 비교
				if (member.getMemberPw().equals(pw)) { // DBpw 자바pw가 같으면
					memberInfo = member; // 전 역에서 사용할 수 있는 memberInfo 만들어짐.
					System.out.println("로그인 성공\n");
					break;
				} else {
					System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
					System.out.println("▓ERROR : ID 또는 PW를 확인해주세요.	▓");
					System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n");
					// memberInfo = null; 인 상태.
				}
			} else {
				System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓");
				System.out.println("▓ERROR : ID 또는 PW를 확인해주세요.	▓");
				System.out.println("▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓\n");

			}
		}
	}

	// 로그아웃
	public void doLogout() {

		if (memberInfo != null)
			memberInfo = null;

	}

	// 고객등록
	public void sign() {
		Member member = new Member();
		List<Member> list = MemberManage.getInstance().getDetailProfile();
		String id = "";
		String name = "";
		String startday = "";

		int cnt = 0;
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━ 회원가입 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━");

		boolean run = true;
		while (run) {
			System.out.println("사용하실 ID 를 입력하세요. > ");
			System.out.println("(취소를 원하시면 :q 를 입력해주세요.)");
			id = sc.nextLine();
			if (id.equals(":q") || id.equals(":ㅂ")) {
				new Application();
			}

			cnt = 0; // 카운트 초기화
			for (int i = 0; i < list.size(); i++) {
				if (!id.equals(list.get(i).getMemberId())) {
					cnt++;
				} // 동일한 ID가 없으면 CNT 증가
			}

			if (cnt == (list.size())) { // CNT가 list.size와 같다면 중복ID 없음.
				System.out.println("사용 가능한 ID 입니다!");
				member.setMemberId(id);
				run = false;
			} else {
				System.out.println("이미 사용 중인 ID 입니다.");
			}

		}
		System.out.println("Password 를 입력하세요. > ");
		String pw = sc.nextLine();
		member.setMemberPw(pw);
////////////////////////////////////////////////////// 닉네임
		run = true;
		while (run) {
			System.out.println("사용하실 닉네임 을 입력하세요. > ");
			name = sc.nextLine();
			cnt = 0; // 카운트 초기화
			for (int i = 0; i < list.size(); i++) {
				if (!name.equals(list.get(i).getMemberName())) {
					cnt++;
				} // 동일한 ID가 없으면 CNT 증가
			}

			if (cnt == (list.size())) { // CNT가 list.size와 같다면 중복ID 없음.
				System.out.println("사용 가능한 닉네임 입니다.\n");
				member.setMemberName(name);
				run = false;
			} else {
				System.out.println("이미 사용 중인 닉네임 입니다.");
			}

		}

///////////////////////////////////////////////////////////// 고유 코드
		int code = (int) ((Math.random() + 1) * 1000000000);
		run = true;
		while (run) {
			cnt = 0; // 카운트 초기화
			for (int i = 0; i < list.size(); i++) {
				if (code != list.get(i).getMemberCode())
					cnt++;
			}
			if (cnt == (list.size())) {
				System.out.println("ID/PW 분실 시 필요한 고유 코드를 발급 해드렸습니다.");
				member.setMemberCode(code);
				System.out.println(">>> " + code);
				System.out.println("※경고※ 코드를 저장하지 않을 경우, ID/PW 를 찾을 수 없습니다.");
				run = false;

			} else if (cnt != (list.size())) {
				code = code + (int) ((Math.random() + 100));
			}
		}

		// role == 0, 일반 사용자
		member.setRole("0");

		int result = MemberManage.getInstance().registCustomer(member);
		if (result == 1) {
			System.out.println("\n환영합니다!");
			System.out.println("톡톡이네 회원가입에 성공하였습니다!\n");
			System.out.println("회원 가입 기념 포인트 5점 적립 되었습니다!\n");
			member.setMemberDate(startday);
		} else if (result == 0) {
			System.out.println("error : 정보 등록 실패");
		}

	}

	public void forgot() { /////////////////////////////////////// id/pw찾기
		List<Member> list = MemberManage.getInstance().getSearchCode();
		System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ID/PW 찾기 ━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
		System.out.println("ID/PW 분실 시 필요한 고유 코드를 입력해주세요.");
		String id = "";
		String pw = "";
		int cnt = 0;
		int code = Integer.parseInt(sc.nextLine());
		for (int i = 0; i < list.size(); i++) {
			if (code == list.get(i).getMemberCode()) {
				id = list.get(i).getMemberId();
				pw = list.get(i).getMemberPw();
			} else {
				cnt++;
			}
		}
		if (cnt != list.size()) {
			System.out.println(" ID >> " + id);
			System.out.println(" PW >> " + pw);
			System.out.println("※찾으신 비밀번호는 즉시 변경 바랍니다.");
		} else {
			for (int i = 0; i < list.size(); i++)
				System.out.println("찾을 수 없습니다.");
		}
	}

	public void getMember() { // 전체 일반 회원 조회
		List<Member> list = MemberManage.getInstance().getDetailProfile();
		for (int i = 0; i < list.size(); i++) {
			System.out.println("=====================================================================");
			System.out.println(list.get(i).toString());
			System.out.println("=====================================================================");
		}
	}

	public void kickMember() { /// 강퇴하기
		getMember();
		List<Member> list = MemberManage.getInstance().getDetailProfile();
		System.out.println("강퇴 할 회원 ID 입력 >");
		System.out.println("취소를 원하시면 :q 를 입력해주세요.");

		String memberId = sc.nextLine();

		if (memberId.equals(":q") || memberId.equals(":ㅂ")) {
			return; // 실행 취소 : 메소드 탈출
		}
		if (memberId.equals("admin")) {
			System.out.println("관리자는 강퇴할 수 없습니다!");
			return; // 실행 취소 : 메소드 탈출
		}
		int cnt = 0;
		for (int i = 0; i < list.size(); i++) {
			if (!memberId.equals(list.get(i).getMemberId()))
				cnt++;
		}
		if (cnt == list.size()) {
			System.out.println("해당 회원은 존재하지 않습니다.");
			return;
		}
		System.out.println(memberId + "님을 강퇴하겠습니까?");
		System.out.println("╭──────────────────╮");
		System.out.println("│ 1. 예 │ 2. 아니오  │"); // 한번 더 물어보기?
		System.out.println("╰──────────────────╯");
		int yesorno = Integer.parseInt(sc.nextLine());

		if (yesorno == 1) {
			int result = MemberManage.getInstance().kickMember(memberId);

			if (result == 1) {
				System.out.println("강퇴 완료.");
			} else
				System.out.println("강퇴 실패.");
		} else
			return;

	}

	public void byebye() {
		int yesorno = 0;
		System.out.println("탈퇴 하기 >");
		System.out.println("정말 탈퇴하시겠습니까? >");
		System.out.println("╭───────────────────╮");
		System.out.println("│ (1) 예 │ (2) 아니오 │");
		System.out.println("╰───────────────────╯");
		try {
			yesorno = Integer.parseInt(sc.nextLine());
		} catch (Exception e) {
			System.out.println("잘못 입력 하였습니다.");
			return;
		}
		if (yesorno == 1) {
			System.out.println("탈퇴를 원하시면 톡톡이네탈퇴 라고 입력해주세요.");
			String insert = sc.nextLine();
			if (insert.equals("톡톡이네탈퇴")) {
				int result = MemberManage.getInstance().kickMember(memberInfo.getMemberId());
				if (result == 1) {
					System.out.println("탈퇴가 완료되었습니다. 이용해주셔서 감사합니다.");
					new Application();

				} else
					System.out.println("탈퇴되지 않았습니다.");
			} else
				System.out.println("탈퇴되지 않았습니다.");
		} else {
		}

	}

	public void attendMember() {

		System.out.println("출석하기 >");
		MemberManage.getInstance().AttendMember(memberInfo, memberInfo.getMemberAttendate());

		memberInfo = MemberManage.getInstance().loginInfo(memberInfo.getMemberId()); // 출석수 갱신
	}

	public void changeNickname() { //////////////// 닉네임 변경하기!
		Member member = new Member();
		int yesorno = 0;
		List<Member> list = MemberManage.getInstance().getDetailProfile();
		System.out.println("닉네임 변경 >");
		System.out.println("닉네임 변경을 하시겠습니까?");
		System.out.println("※주의※ 닉네임 변경 시 24시간 이후 변경 가능합니다.");
		System.out.println("╭───────────────────╮");
		System.out.println("│ (1) 예 │ (2) 아니오 │");
		System.out.println("╰───────────────────╯");

		try {
			yesorno = Integer.parseInt(sc.nextLine());

		} catch (Exception e) {

		}
		if (yesorno == 1) {

			boolean run = true;
			int cnt = 0;
			while (run) {
				System.out.println("원하시는 닉네임을 적어주세요.");
				String newName = sc.nextLine();
				cnt = 0; // 카운트 초기화
				for (int i = 0; i < list.size(); i++) {
					if (!newName.equals(list.get(i).getMemberName())) {
						cnt++;
					} // 동일한 ID가 없으면 CNT 증가
				}
				if (cnt == (list.size())) { // CNT가 list.size와 같다면 중복ID 없음.
					System.out.println("사용 가능한 닉네임 입니다.\n");
					MemberManage.getInstance().changeNickname(memberInfo, newName);
					memberInfo = MemberManage.getInstance().loginInfo(memberInfo.getMemberId()); // 터질 수도 있음.
					run = false;
				} else {
					System.out.println("이미 사용 중인 닉네임 입니다.");
				}

			}

		} else if (yesorno == 2) {
			return;
		} else {
			System.out.println("잘못 입력하셨습니다.\n");
		}

	}

	public void getOneMember() { // 관리자 - 회원찾기 >> 닉네임검색후 id 조회
		System.out.println("회원 찾기 >");
		System.out.println("회원의 닉네임을 검색 후 해당하는 ID를 조회합니다. ");
		String MemberName = sc.nextLine();
		if (MemberName.equals(":q") || MemberName.equals(":ㅂ")) {
			return;
		}

		List<Member> list = MemberManage.getInstance().getOneDetailProfile(MemberName);

		for (int i = 0; i < list.size(); i++) {
			System.out.println("=====================================================================");
			System.out.println(list.get(i).toString());
			System.out.println("=====================================================================");
		}
		if (list.size() == 0) {
			System.out.println("입력하신 닉네임에 해당하는 회원을 찾을 수 없습니다.");
		}
	}

	public void changePw() { //////////////// pw 변경하기!!!
		Member member = new Member();
		int yesorno = 0;
		System.out.println("패스워드 변경 >");
		System.out.println("현재 PW을 적어주세요.");
		System.out.println("취소를 원하시면 :q 를 입력해주세요.");
		String oldPw = sc.nextLine();
		if (oldPw.equals(":q")) {
			return; // 실행 취소 : 메소드 탈출
		}
		if (oldPw.equals(memberInfo.getMemberPw())) {

			System.out.println("새로 바꿀 PW을 적어주세요.");
			String newPw = sc.nextLine();
			if (newPw.equals(":q")) {
				return; // 실행 취소 : 메소드 탈출
			}

			System.out.println("입력하신 패스워드로 변경을 하시겠습니까?");
			System.out.println("╭───────────────────╮");
			System.out.println("│ (1) 예 │ (2) 아니오 │");
			System.out.println("╰───────────────────╯");
			try {
				yesorno = Integer.parseInt(sc.nextLine());

			}

			catch (Exception e) {

			}
			if (yesorno == 1) {
				MemberManage.getInstance().changePw(memberInfo, newPw);
				memberInfo = MemberManage.getInstance().loginInfo(memberInfo.getMemberId()); // 터질 수도 있음.
				System.out.println("패스워드 변경에 성공하였습니다!\n");

			} else if (yesorno == 2) {
				return;
			} else {
				System.out.println("잘못 입력하셨습니다.\n");
			}
		} else {
			System.out.println("현재 PW가 일치하지 않습니다.\n");
		}
	}

	public void getReportMember() { // 신고된 회원리스트 조회
		List<Member> list = MemberManage.getInstance().getReportListMember();

		System.out.println("==========================================================================");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toStringReport());
			System.out.println("==========================================================================");
		}
		if (list.size() == 0) {
			System.out.println("	 ....... 신고된 회원이 없습니다 ┌⊙◇⊙┘ .......");
			System.out.println("==========================================================================");
		}

	}

}