package com.pro.board;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.pro.member.Member;
import com.pro.member.MemberManage;
import com.pro.member.MemberService;

public class BoardService { // 게시판 관련 서비스
	Scanner sc = new Scanner(System.in);

	MemberService ms = new MemberService();

	public static Board boardInfo = null;

	// 조회한 글 캐시삭제.
	public void boardout() {

		if (boardInfo != null)
			boardInfo = null;

	}

	public void getBoard() {
		Board board = new Board();
		List<Board> list = BoardManage.getInstance().getListBoard();

		System.out.println("==========================================================================");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
			System.out.println("==========================================================================");
		}
		if (list.size() == 0) {
			// System.out.println(" ....... 조회할 수 있는 글이 없습니다 ┌⊙◇⊙┘ .......");
			System.out.println("     ⢻⣶⠢⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ ⢀⣀\r\n" + "     ⠀⢻⡀⠀⠈⠢⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠤⠒⠉⣿⡟\r\n"
					+ "⠀     ⠀⠣⡀⠀⠀⠀⠑⢄⢀⣀⣀⣀⣀⣀⣀⠤⠒⠉⠀⠀⠀⢠⠟⠀\r\n" + "⠀     ⠀⠀⠑⢄⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠋⠀⠀\r\n"
					+ "⠀⠀     ⠀⠀⠀⡟⠀⠀⢀⠀⠀⠀⠀⠀⠀⢀⠀⠀⠈⢤⠊⠀⠀⠀⠀\r\n" + "⠀     ⠀⠀⠀⡘⠀⠀⢮⣽⠀⠀⠀⠀⠀⠰⣭⡇⠀⠀⠈⢆⠀⠀⠀⠀ 여기는 너무 조용합니다... \r\n"
					+ "⠀     ⠀⠀⠀⢃⠠⢄⠈⠁⠀⠰⠶⠀⠀⠀⠈⠀⡀⣀⠀⠸⡀⠀⠀  ⠀글을 작성해주세요... \r\n" + "⠀     ⠀⠀⢸⠠⣀⣠⠇⠀⠀⡠⠐⠒⡄⠀⠀⠨⡀⢀⠅⠀⡇⠀⠀⠀\r\n"
					+ "⠀     ⠀⠀⠈⢆⠀⠀⠀⠀⠀⠘⠤⠠⠃⠀⠀⠀⠀⠀⠀⠀⢠⠀⠀⠀\r\n" + "⠀⠀     ⠀⠀⠈⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⠀⠀\r\n"
					+ "⠀⠀     ⠀⠀⠐⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀ ⠀⠀⠀⠀ ⠑⠀⠀⠀");
			System.out.println("==========================================================================");
		}

	}

	public void watchBoard(int num) { /// 게시글 내용물
		List<Board> list = BoardManage.getInstance().getListBoard();
		Collections.reverse(list); /// 내림차순이라서 역순으로 만드는 메소드 이용ㅠㅠ
		System.out.println("--------------------------------------------------------------------------");
		System.out.println(list.get(num - 1).toString());
		System.out.println("--------------------------------------------------------------------------");
		System.out.println(list.get(num - 1).getBoardContents());
		System.out.println("--------------------------------------------------------------------------");
		System.out.println("추천 수: " + BoardService.boardInfo.getBoardRecommend());
		System.out.println("--------------------------------------------------------------------------");

	}

	public void boardDeepInfo() { ///// 글 자세히 들어가기
		getBoard();
		System.out.println("조회하고 싶은 글 번호 입력 >");
		System.out.println("(취소를 원하시면 777을 입력하세요)");

		Board board = new Board();
		int num = 0;

		try {
			num = Integer.parseInt(sc.nextLine());
		} catch (Exception e) {
			System.out.println("잘못입력하셨습니다. >");
			return;}
			if (num == 777) {
				System.out.println("취소 >");
				return;
			}

			board = BoardManage.getInstance().boardInfo(num);
		
			if (board.getBoardId() == (num)) { // DBpw 자바pw가 같으면
				boardInfo = board; // 전 역에서 사용할 수 있는 boardInfo 만들어짐.
				// System.out.println("조회 성공\n");
				if (!BoardService.boardInfo.getMemberName().equals(MemberService.memberInfo.getMemberName()))
					BoardManage.getInstance().ViewUp(BoardService.boardInfo.getBoardId());
				///// 자신이 쓴글이면 조회수 안올라가도록..
			} 
			watchBoard(num);

		
		new BoardReaction();
	}

	public void createBoard() {
		Board board = new Board();
		System.out.println("글 작성 중......");
		System.out.println("글 제목 > ");
		System.out.println("(취소를 원하면 :q를 쳐주세요.)");
		String boardName = sc.nextLine();
		if (boardName.equals(":q") || boardName.equals(":ㅂ")) {
			System.out.println("취소 >");
			return;
		}

		System.out.println("글 내용 > ");

		String result0 = "";
		String boardContents = "\n";
		System.out.println("내용을 입력하세요. 종료를 원하면 :wq를 쳐주세요.");
		System.out.println("작성 취소를 원하면 :q를 쳐주세요.");

		System.out.println("입력>");
		while (true) { // 엔터 쳐도 이어서 쓸 수 있게 해야 함.
			String plus = sc.nextLine();
			if (plus.equals(":wq") || plus.equals(":ㅈㅂ")) {
				result0 = boardContents;
				break;
			} else if (plus.equals(":q") || plus.equals(":ㅂ")) {
				System.out.println("취소 >");
				return;
			} else
				plus = plus + "\n";
			boardContents = boardContents + plus;

		}
		board.setBoardName(boardName);
		board.setBoardContents(boardContents); // 엔터 쳐도 이어서 쓸 수 있게 해야 함.
		board.setMemberName(MemberService.memberInfo.getMemberName());

		int result = BoardManage.getInstance().makeBoard(board);
		if (result == 1) {
			System.out.println("등록 성공");
			MemberManage.getInstance().MinusPoint(MemberService.memberInfo, 3);
			System.out.println("게시물 등록으로 3 포인트가 차감되었습니다.");
			ms.memberInfo = MemberManage.getInstance().loginInfo(ms.memberInfo.getMemberId()); //////// 터질 수도 있음.
			getBoard();
		} else
			System.out.println("등록 실패");

	}

	// 글 삭제 그리고 덧글 삭제
	public void deleteBoard() {
		System.out.println("삭제할 글 번호 입력 >");
		System.out.println("취소하려면 777 을 입력하세요.>");
		int boardId = Integer.parseInt(sc.nextLine());

		if (boardId == 777) {
			return;
		}
		System.out.println("정말 삭제하시겠습니까? >");
		System.out.println("(1) 예 | (2) 아니오 ");
		int num = Integer.parseInt(sc.nextLine());
		if (num == 2) {
			return;
		} else if (num == 1) {
			// 관리자이거나 로그인 닉네임과 작성자 닉네임이 일치해야 삭제 가능하다.
			if (MemberService.memberInfo.getMemberName().equals(BoardManage.getInstance().searchMemberName(boardId))
					|| MemberService.memberInfo.getRole().equals("1")) {
				int result = BoardManage.getInstance().deleteBoard(boardId);
				// System.out.println("result 값"+result); 확인용
				if (result != 0) {
					getBoard();
					System.out.println("삭제 성공! ");

				} else
					System.out.println("삭제 실패! ");
			} else {
				System.out.println("남의 글은 삭제할 수 없습니다!");
			}
		}
	}

	public void getReportBoard() {
		List<Board> list = BoardManage.getInstance().getReportListBoard();

		System.out.println("==========================================================================");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toStringReport());
			System.out.println("==========================================================================");
		}
		if (list.size() == 0) {
			System.out.println("⢻⣶⠢⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀\r\n" + "⠀⢻⡀⠀⠈⠢⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⠤⠒⠉⣿⡟\r\n"
					+ "⠀⠀⠣⡀⠀⠀⠀⠑⢄⢀⣀⣀⣀⣀⣀⣀⠤⠒⠉⠀⠀⠀⢠⠟⠀\r\n" + "⠀⠀⠀⠑⢄⠀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡠⠋⠀⠀\r\n"
					+ "⠀⠀⠀⠀⠀⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⠀⠈⢤⠊⠀⠀⠀⠀\r\n" + "⠀⠀⠀⠀⡘⠀⠀⢮⣽⠀⠀⠀⠀⠀⠰⣭⡇⠀⠀⠈⢆⠀⠀⠀⠀\r\n"
					+ "⠀⠀⠀⠀⢃⠠⢄⠈⠁⠀⠰⠶⠀⠀⠀⠈⠀⡀⣀⠀⠸⡀⠀⠀⠀\r\n" + "⠀⠀⠀⢸⠠⣀⣠⠇⠀⠀⡠⠐⠒⡄⠀⠀⠨⡀⢀⠅⠀⡇⠀⠀⠀\r\n"
					+ "⠀⠀⠀⠈⢆⠀⠀⠀⠀⠀⠘⠤⠠⠃⠀⠀⠀⠀⠀⠀⠀⢠⠀⠀⠀\r\n" + "⠀⠀⠀⠀⠈⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⠀⠀\r\n" + "⠀⠀⠀⠀⠐⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠐⠀⠀⠀⠀⠰⠀⠀⠀");
			System.out.println("==========================================================================");
		}

	}

}