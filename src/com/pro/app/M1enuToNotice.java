package com.pro.app;

import java.util.Scanner;

import com.pro.member.MemberService;
import com.pro.notice.NoticeManage;
import com.pro.notice.NoticeService;

public class M1enuToNotice { /////////// 공지사항 메뉴화면. >> Board랑 비슷하게
	Scanner sc = new Scanner(System.in);

	MemberService ms = new MemberService();
	NoticeService ns = new NoticeService();

	int menuNo = 0;

	public M1enuToNotice() {
		ns.getNotice();
		menuJob();
	}

	private void menuJob() {

		boolean run = true;
		while (run) {
			NoticeInfo();

			if (MemberService.memberInfo.getRole().equals("1")) {
				if (menuNo == 1) {
					ns.getNotice();
					ns.noticeDeepInfo();
				} else if (menuNo == 2) {
					ns.createNotice();
				} else if (menuNo == 3) {
					ns.deleteNotice();
				} else if (menuNo == 4) {
					run = false;
					System.out.println("뒤로 가기");
					break;
				}

			}
			// 일반 회원
			else if (MemberService.memberInfo.getRole().equals("0")) {
				if (menuNo == 1) {
					ns.noticeDeepInfo();
				} else if (menuNo == 2) {
					run = false;
					System.out.println("뒤로 가기");
					break;
				}
			}

		}
	}

	private void NoticeInfo() {
		// role ==1, 관리자
		if (MemberService.memberInfo.getRole().equals("1")) { // 관리자 전용
			System.out.println("╭─────────────────────────────────────────────────────╮");
			System.out.println("│ 1. 글 조회 │ 2. 새 공지 등록 │ 3. 공지 삭제  │ 4. 뒤로 가기 │");
			System.out.println("╰─────────────────────────────────────────────────────╯");
		}
		// role ==0, 일반 사용자
		else if (MemberService.memberInfo.getRole().equals("0")) { // 고객 전용
			System.out.println("╭───────────────────────────╮");
			System.out.println("│  (1) 공지조회 │ (2) 뒤로가기  │");
			System.out.println("╰───────────────────────────╯");

		}
		System.out.println("입력> ");
		try{menuNo = Integer.parseInt(sc.nextLine());
		
		}catch (Exception e) {
		}

	}

}
