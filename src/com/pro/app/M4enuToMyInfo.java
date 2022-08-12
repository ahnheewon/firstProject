package com.pro.app;

import java.util.Scanner;

import com.pro.member.MemberService;

public class M4enuToMyInfo { /////////// 회원 및 내 정보조회 메뉴 >>> member 패키지 이용

	Scanner sc = new Scanner(System.in);

	MemberService ms = new MemberService();

	int menuNo = 0;

	public M4enuToMyInfo() {
		// 내 정보 리스트 나오게
		menuJob();

	}

	private void menuJob() {

		boolean run = true;
		while (run) {
			MyDeepInfo();
			// 관리자
			if (MemberService.memberInfo.getRole().equals("1")) {
				if (menuNo == 1) {
					ms.changePw();
				} else if (menuNo == 2) {
					run = false;
					System.out.println("뒤로 가기");
					break;
				}

			}
			// 일반 회원
			else if (MemberService.memberInfo.getRole().equals("0")) {
				if (menuNo == 1) {
					ms.changeNickname();
				} else if (menuNo == 2) {
					ms.changePw();
				} else if (menuNo == 3) {
					ms.byebye();		
					break;			
				} else if (menuNo == 4) {
					run = false;
					System.out.println("뒤로 가기");
					break;
				}
			}

		}
	}

	private void MyInfo() {

		// 관리자, 회원 공통

		// role ==1, 관리자
		if (MemberService.memberInfo.getRole().equals("1")) { // 관리자 전용
			System.out.println("╭────────────────────────╮");
			System.out.println("│ 1. PW 변경 │ 2. 뒤로 가기 │");
			System.out.println("╰────────────────────────╯");
		}
		// role ==0, 일반 사용자
		else if (MemberService.memberInfo.getRole().equals("0")) { // 고객 전용
			System.out.println("╭───────────────────────────────────────────────────────╮");
			System.out.println("│ (1) 별명 변경 │ (2) PW 변경 │ (3) 회원 탈퇴 │ (4) 뒤로 가기  │");
			System.out.println("╰───────────────────────────────────────────────────────╯");
		}

		System.out.println("입력> ");
		menuNo = Integer.parseInt(sc.nextLine());

	}

	private void MyDeepInfo() {

		System.out.println(MemberService.memberInfo.getMemberName() + "님의 정보 입니다.");
		System.out.println("==========================================================================");
		System.out.println("ID : " + MemberService.memberInfo.getMemberId());
		System.out.println("PW : *******");
		System.out.println("닉네임 : " + MemberService.memberInfo.getMemberName());
		System.out.println("포인트 : " + MemberService.memberInfo.getMemberPoint());

		if (MemberService.memberInfo.getRole().equals("1")) {
			System.out.println("나의 등급 : 관리자 ");
		} else
			System.out.println("나의 등급 : 일반회원 ");
		System.out.println("가입 일자 : " + MemberService.memberInfo.getMemberDate()); //// 가입일자 나오게
		System.out.println("==========================================================================");
		MyInfo();

	}

}
