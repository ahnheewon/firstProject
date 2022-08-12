package com.pro.app;

import java.util.Scanner;

import com.pro.member.MemberService;

public class M3enuToCheck { /////////// 관리자의 회원조회 메뉴화면. >>> member 패키지 이용
							/////////// 회원의 출석체크 메뉴화면. >>> check 패키지 이용
	Scanner sc = new Scanner(System.in);

	MemberService ms = new MemberService();

	int menuNo = 0;

	public M3enuToCheck() {
		menuJob();
	}

	private void menuJob() {

		boolean run = true;
		while (run) {
			CheckInfo();

			if (MemberService.memberInfo.getRole().equals("1")) {
				if (menuNo == 1) {
					ms.getMember();
				} else if (menuNo == 2) {
					ms.getReportMember();
				} else if (menuNo == 3) {
					ms.getOneMember();
				} else if (menuNo == 4) {
					ms.kickMember();
				} else if (menuNo == 5) {
					run = false;
					System.out.println("뒤로 가기");
					break;
				}

			}
			// 일반 회원
			else if (MemberService.memberInfo.getRole().equals("0")) {
				if (menuNo == 1) {
					ms.attendMember();
				} else if (menuNo == 2) {
					run = false;
					System.out.println("뒤로 가기");
					break;
				}
			}
		}
	}

	private void CheckInfo() {
		// role ==1, 관리자
		if (MemberService.memberInfo.getRole().equals("1")) { // 관리자 전용 : 회원정보 자세히
			System.out.println("╭────────────────────────────────────────────────────────────────────────╮");
			System.out.println("│ 1. 전체 회원 조회 │ 2. 신고 회원 조희 │ 3. 회원 찾기 │ 4. 회원 강퇴 │ 5. 뒤로 가기 │");
			System.out.println("╰────────────────────────────────────────────────────────────────────────╯");
		}
		// role ==0, 일반 사용자
		else if (MemberService.memberInfo.getRole().equals("0")) { // 고객 전용
			System.out.println("╭──────────────────────────╮");
			System.out.println("│ (1) 출석하기 │ (2) 뒤로 가기 │");
			System.out.println("╰──────────────────────────╯");
		}
		System.out.println("입력> ");
		try {
			menuNo = Integer.parseInt(sc.nextLine());

		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
