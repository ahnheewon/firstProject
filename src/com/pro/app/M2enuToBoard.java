package com.pro.app;

import java.util.List;
import java.util.Scanner;

import com.pro.board.Board;
import com.pro.board.BoardManage;
import com.pro.board.BoardService;
import com.pro.member.MemberManage;
import com.pro.member.MemberService;

public class M2enuToBoard { ///////////// 게시글 조회 메뉴화면.
	Scanner sc = new Scanner(System.in);
	BoardService bs = new BoardService();
	MemberService ms = new MemberService();
	int menuNo = 0;

	public M2enuToBoard() {

		bs.getBoard();
		BoardDeepMenu();
	}

	private void BoardDeepMenu() {

		boolean run = true;
		while (run) {
			boardInfo();

			if (MemberService.memberInfo.getRole().equals("1")) {
				if (menuNo == 1) {

					bs.boardDeepInfo();
				} else if (menuNo == 2) {
					bs.getReportBoard();
				} else if (menuNo == 3) {
					bs.deleteBoard();
				} else if (menuNo == 4) {
					System.out.println("글 목록 갱신!");
					bs.getBoard();
				} else if (menuNo == 5) {
					run = false;
					System.out.println("뒤로 가기");
					break;
				}

			}
			// 일반 회원
			else if (MemberService.memberInfo.getRole().equals("0")) {
				if (menuNo == 1) {
					List<Board> list = BoardManage.getInstance().getListBoard();
					if (list.size() == 0) {
						System.out.println("==========================================================================");
						System.out.println("	 ....... 조회할 수 있는 글이 없습니다 ┌⊙◇⊙┘ .......");
						System.out.println("==========================================================================");
					} else
						bs.boardDeepInfo();
				} else if (menuNo == 2) {
					if (MemberService.memberInfo.getMemberPoint() < 3) {
						System.out.println("포인트가 부족합니다!");
					} else
						bs.createBoard();
				} else if (menuNo == 3) {
					bs.deleteBoard();
				} else if (menuNo == 4) {
					// bs.getBoard(); 글목록 갱신 -> 신고로 변경
					System.out.println("신고 >");
					System.out.println("원하시는 신고 항목을 선택하세요."); // update
					System.out.println("※※※주의※※※");
					System.out.println("무분별한 신고는 제재 처리될 수 있습니다.");
					System.out.println("(1) 닉네임  | (2) 글 | (3) 취소 ");
					int num = Integer.parseInt(sc.nextLine());
					if (num == 1) { // 닉네임 신고
						System.out.println("닉네임을 입력하여 신고를 진행합니다.");
						System.out.println("신고하길 원하는 회원의 닉네임을 입력하세요.");
						String reportName = sc.nextLine();
						if (!reportName.equals(MemberService.memberInfo.getMemberName())) { // 신고자와 닉네임이 같지 않으면 신고 진행
						MemberManage.getInstance().ReportMember(reportName);
						bs.getBoard();
						System.out.println("신고가 접수되었습니다.");
						System.out.println("확인 후 처리 하겠습니다.");
						} else {
							System.out.println("자신을 신고할 수 없습니다.");
						} //

					} else if (num == 2) { // 글 신고
						System.out.println("글 번호를 입력하여 신고합니다.");
						System.out.println("신고하길 원하는 글 번호를 입력하세요.");
						int reportNum = Integer.parseInt(sc.nextLine());
						if (!MemberService.memberInfo.getMemberName()
								.equals(BoardManage.getInstance().searchMemberName(reportNum))) {// 신고자와 글작성자이 같지 않으면 신고 진행
							BoardManage.getInstance().ReportUp(reportNum);
							System.out.println("신고가 접수되었습니다.");
							System.out.println("확인 후 처리 하겠습니다.");
							
						} else {
							System.out.println("자신의 글을 신고할 수 없습니다.");
						}
					} else if (num == 3)
						continue;

				} else if (menuNo == 5) {
					run = false;
					System.out.println("뒤로 가기");
					break;
				}
			}
		}
	}

	private void boardInfo() {

		// role ==1, 관리자
		if (MemberService.memberInfo.getRole().equals("1")) { // 관리자 전용
			System.out.println("╭──────────────────────────────────────────────────────────────────╮");
			System.out.println("│ 1. 글 조회 │ 2. 신고 글 조회 │ 3. 글 삭제 │ 4. 글 목록 갱신 │ 5. 뒤로 가기 │");
			System.out.println("╰──────────────────────────────────────────────────────────────────╯");

		}
		// role ==0, 일반 사용자
		else if (MemberService.memberInfo.getRole().equals("0")) { // 고객 전용
			System.out.println("╭────────────────────────────────────────────────────────────────╮");
			System.out.println("│ (1) 글 조회 │ (2) 새 글 작성 │ (3) 글 삭제  │ (4) 신고 │ (5) 뒤로 가기 │");
			System.out.println("╰────────────────────────────────────────────────────────────────╯");
		}
		System.out.println("입력> ");
		try{menuNo = Integer.parseInt(sc.nextLine());
		
		}catch (Exception e) {
				}

	}
}
