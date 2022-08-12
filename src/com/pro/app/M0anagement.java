package com.pro.app;

import java.util.Scanner;

import com.pro.board.BoardService;
import com.pro.member.MemberService;

public class M0anagement { ///////// 로그인 성공 후 메뉴화면
	Scanner sc = new Scanner(System.in);

	MemberService ms = new MemberService();

	int menuNo = 0;

	public M0anagement() {
		menuJob();
	}

	private void menuJob() {

		while (true) {
			menuInfo(); //
			if (MemberService.memberInfo.getRole().equals("1")) {
				if (menuNo == 1) {
					new M1enuToNotice();
				} else if (menuNo == 2) {
					new M2enuToBoard();
				} else if (menuNo == 3) {
					new M3enuToCheck();
				} else if (menuNo == 4) {
					new M4enuToMyInfo();
				} else if (menuNo == 5) {

					ms.doLogout();
					System.out.println("관리자님, 정상 로그아웃 되었습니다.");
					break;

				}

			}
			// 일반 회원
			else if (MemberService.memberInfo.getRole().equals("0")) {
				if (menuNo == 1) {
					new M1enuToNotice();
				} else if (menuNo == 2) {
					new M2enuToBoard();
				} else if (menuNo == 3) {
					new M3enuToCheck();
				} else if (menuNo == 4) {
					new M4enuToMyInfo();
				} else if (menuNo == 5) {
					ms.doLogout();
					System.out.println("정상 로그아웃 되었습니다.");
					new Application();
					break;
				}
			}
		}

	}

//ⓛ②③④
	private void menuInfo() {
		// role ==1, 관리자
		if (MemberService.memberInfo.getRole().equals("1")) { // 관리자 전용
			System.out.println("╭──────────────────────────────────────────────────────────╮");
			System.out.println("│ 1. 공지사항 │ 2. 게시글 │ 3. 회원 정보 │ 4. 내 정보 │ 5. 로그아웃 │ ");
			System.out.println("╰──────────────────────────────────────────────────────────╯");
		}
		// role ==0, 일반 사용자
		else if (MemberService.memberInfo.getRole().equals("0")) { // 고객 전용
			System.out.println("╭──────────────────────────────────────────────────────────────╮");
			System.out.println("│ (1) 공지사항 │ (2) 게시글 │ (3) 출석체크 │ (4) 내 정보 │ (5) 로그아웃 │");
			System.out.println("╰──────────────────────────────────────────────────────────────╯");
		}
		System.out.println("입력> ");
		try {
			menuNo = Integer.parseInt(sc.nextLine());

		} catch (Exception e) {
			
		}

	}

}
