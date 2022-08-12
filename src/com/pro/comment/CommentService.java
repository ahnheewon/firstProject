package com.pro.comment;

import java.util.List;
import java.util.Scanner;

import com.pro.board.BoardService;
import com.pro.member.MemberManage;
import com.pro.member.MemberService;

public class CommentService {
	Scanner sc = new Scanner(System.in);
	MemberService ms = new MemberService();
	BoardService bs = new BoardService();

	public void createComment() {

		Comment comment = new Comment();
		System.out.println("※※ 주의 ※※");
		System.out.println("덧글은 작성 후 삭제할 수 업습니다.");
		System.out.println("신중히 적어주세요.");
		System.out.println("덧글 작성 중......");
		System.out.println("덧글 내용 > ");

		System.out.println("내용을 입력하세요. 종료를 원하면 엔터를 쳐주세요.");
		System.out.println("입력>");
		String commentContents = sc.nextLine();

		comment.setCommentContents(commentContents);
		
	
		comment.setMemberName(MemberService.memberInfo.getMemberName()); //댓글작성자
		if(comment.getMemberName().equals(BoardService.boardInfo.getMemberName())) {
			comment.setMemberName(" *글작성자* "+MemberService.memberInfo.getMemberName()); 
		}else {}
		
		comment.setBoardId(BoardService.boardInfo.getBoardId()); //게시글 제목
		
		int result = CommentManage.getInstance().makeComment(comment);
		if (result == 1) {
			System.out.println("덧글 등록 성공");
			MemberManage.getInstance().MinusPoint(MemberService.memberInfo,1);	
			System.out.println("덧글 등록으로 1 포인트가 차감되었습니다.");
			ms.memberInfo = MemberManage.getInstance().loginInfo(ms.memberInfo.getMemberId()); ////////터질 수도 있음.
		} else
			System.out.println("덧글 등록 실패");

	}

	public void getComment() {
		List<Comment> list = CommentManage.getInstance().getListComment(BoardService.boardInfo.getBoardId());
		
		System.out.println("==========================================================================");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
			System.out.println("==========================================================================");
		}
		if (list.size() == 0) {
			System.out.println("	 ....... 덧글이 없습니다 관심을 가져주세요 ┌⊙◇⊙┘ .......");
			System.out.println("==========================================================================");
		}

	}

}