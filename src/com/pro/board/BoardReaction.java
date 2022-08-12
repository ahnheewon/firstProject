package com.pro.board;

import java.util.Scanner;

import com.pro.comment.CommentService;
import com.pro.member.MemberManage;
import com.pro.member.MemberService;

public class BoardReaction { //////////// 조회한 글 추천 / 신고 / 덧글 /조회수
	int menuNo = 0;
	Scanner sc = new Scanner(System.in);
	int num = 0;
	String react = "";
	CommentService cs = new CommentService();
	BoardService bs = new BoardService();

	public BoardReaction() {
		cs.getComment();

		while (true) {
			BoardReactionMenu();
			if (menuNo == 1) { /// 추천 자신글 추천 불가, 중복 추천가능...
				System.out.println(BoardService.boardInfo.getBoardId());
				System.out.println("이 글을 추천하시겠습니까? >");
				System.out.println("1. 예  | 2. 아니오 ");
				num = Integer.parseInt(sc.nextLine());
				if (num == 1) {
					if (!BoardService.boardInfo.getMemberName().equals(MemberService.memberInfo.getMemberName())) {
						BoardManage.getInstance().RecommendUp(BoardService.boardInfo.getBoardId());
						BoardService.boardInfo = BoardManage.getInstance()
								.boardInfo(BoardService.boardInfo.getBoardId()); // 실시간 갱신!!
						bs.watchBoard(BoardService.boardInfo.getBoardId());
						System.out.println("추천 완료!");
					} else {
						System.out.println("자신의 글에 추천할 수 없습니다!");
					} //
				} // update
				else if (num == 2)
					continue;

			} else if (menuNo == 2) { // 덧글 작성
				System.out.println("댓글을 작성하겠습니까? >");
				if (MemberService.memberInfo.getMemberPoint() > 1) {
					System.out.println("1. 예  | 2. 아니오 ");
					num = Integer.parseInt(sc.nextLine());
					if (num == 1) {
						if (MemberService.memberInfo.getMemberPoint() < 1) {
							System.out.println("포인트가 부족합니다!");
						} else
							cs.createComment();
						bs.watchBoard(BoardService.boardInfo.getBoardId());
						MemberManage.getInstance().MinusPoint(MemberService.memberInfo, 1);
						System.out.println("덧글 등록으로 1 포인트가 차감되었습니다.");
						cs.getComment();
					} else
						System.out.println("포인트가 부족합니다.");
				} else if (num == 2) {
					continue;

				}

			} else if (menuNo == 3) { // 신고
				System.out.println("신고 >");
				System.out.println("원하시는 신고 항목을 선택하세요."); // update
				System.out.println("※※※주의※※※");
				System.out.println("무분별한 신고는 제재 처리될 수 있습니다.");
				System.out.println("(1) 닉네임 | (2) 글 | (3) 취소 ");
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
					if (num == 2) { // 해당 글 신고
						num = 0; // 초기화
						System.out.println("이 글을 신고하시겠습니까?");
						System.out.println("1. 예  | 2. 아니오 ");
						num = Integer.parseInt(sc.nextLine());
						if (num == 1) { // 해당 글 신고진행
							if (!BoardService.boardInfo.getMemberName()
									.equals(MemberService.memberInfo.getMemberName())) { //글작성자랑 접속자랑 다르면
								BoardManage.getInstance().ReportUp(BoardService.boardInfo.getBoardId());
								bs.watchBoard(BoardService.boardInfo.getBoardId());
								cs.getComment();
								System.out.println("신고가 접수되었습니다.");
								System.out.println("확인 후 처리 하겠습니다.");
							} else {
								System.out.println("자신의 글에 신고할 수 없습니다.");
							}
						} else if (num == 2)
							continue;

					}

				} else if (num == 3)
					continue;

			} else if (menuNo == 4) {
				bs.boardout();
				bs.getBoard();
				System.out.println("뒤로 가기");
				break;

			}

		}
	}

	private void BoardReactionMenu() {
		// 공통 메뉴
		System.out.println("╭──────────────────────────────────────╮");
		System.out.println("│ 1. 추천 │ 2. 덧글 │ 3. 신고 │ 4. 뒤로가기 │");
		System.out.println("╰──────────────────────────────────────╯");

		System.out.println("입력> ");
		menuNo = Integer.parseInt(sc.nextLine());
	}
}
