package com.pro.app;

import java.util.Scanner;

import com.pro.member.MemberService;

public class Application {
	Scanner sc = new Scanner(System.in);
	MemberService ms = new MemberService();

	int menuNo = 0;

	public Application() {

		MemberService.memberInfo = null;
		run();
	}

	private void run() { /// 게시판 로그인 및 접속을 담당.
		boolean run = true;
		System.out.println("︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵");
		System.out.println(" ~생활정보 공유 커뮤니티 			   	            ");     				
		System.out.println("	           『 톡톡이네 └(＾∇＾)┘ 		            ");			
		System.out.println("			              서로 나누고 베풀어가요~    ");
		System.out.println("‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵‿");
		while (run) {
			System.out.println("");
			System.out.println("톡톡이네 입장을 원하시면 로그인 해주세요 > ");
			System.out.println("");
			System.out.println("╭───────────────────────────────────────────────╮");
			System.out.println("│ 1. 로그인 | 2. 회원가입 | 3. ID/PW 찾기 | 4. 나가기 │");
			System.out.println("╰───────────────────────────────────────────────╯");
			try {
				menuNo = Integer.parseInt(sc.nextLine());

			} catch (Exception e) {
				System.out.println("잘못 입력하셨습니다.");
			}
			switch (menuNo) {
			case 1:
				// 로그인 하는 기능
				ms.doLogin();
				System.out.println("︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵");
				System.out.println("");
				System.out.println("   "+MemberService.memberInfo.getMemberName() + "님 어서오세요 ~");
				System.out.println("   오늘도 톡톡이네 에서 즐거운 시간 보내세요~ └(>∇<)┘ ");
				System.out.println("");
				System.out.println("‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵︵‿︵‿");
				if (MemberService.memberInfo != null) {
					new M0anagement();
				}
				break;
			case 2:
				ms.sign();
				break;
			case 3:
				ms.forgot();
				break;
			case 4:
				run = false;
				System.out.println("BYE BYE");
				break;
				
			default:System.out.println("해당하는 번호를 입력해주세요.");
				

			}
		}
	}
}
