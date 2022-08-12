package com.pro.notice;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.pro.member.MemberService;

public class NoticeService { // 공지사항 관련 서비스

	Scanner sc = new Scanner(System.in);

	public void getNotice() {
		Notice notice = new Notice();
		List<Notice> list = NoticeManage.getInstance().getListNotice();
		System.out.println("공지사항을 불러옵니다...");
		System.out.println("=====================================================================");
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).toString());
			System.out.println("=====================================================================");
		}
		if (list.size() == 0) {
			System.out.println("   ....... 조회할 수 있는 글이 없습니다 ┌⊙◇⊙┘ .......");
			System.out.println("=====================================================================");
		}

	}

	public void createNotice() {
		Notice notice = new Notice();
		System.out.println("공지사항 작성 중...");
		System.out.println("공지사항 제목 > ");
		System.out.println("(취소를 원하면 :q 를 쳐주세요.)");
		String boardName = sc.nextLine();
		if (boardName.equals(":q")) {
			System.out.println("취소 >");
			return;
		}

		System.out.println("공지사항 내용 > ");

		String result0 = "";
		String noticeContents = "\n";
		System.out.println("내용을 입력하세요. 종료를 원하면 :wq 를 쳐주세요.");
		System.out.println("(취소를 원하면 :q 를 쳐주세요.)");
		System.out.println("입력>");
		while (true) { // 엔터 쳐도 이어서 쓸 수 있게 해야 함.
			String plus = sc.nextLine();
			if (plus.equals(":wq") || plus.equals(":ㅈㅂ")) {
				result0 = noticeContents;
				break;
			} else if (plus.equals(":q") || plus.equals(":ㅂ")) {
				System.out.println("취소 >");
				return;
			} else
				plus = plus + "\n";
			noticeContents = noticeContents + plus;

		}
		notice.setNoticeName(boardName);
		notice.setNoticeContents(noticeContents); // 엔터 쳐도 이어서 쓸 수 있게 해야 함.
		notice.setMemberName(MemberService.memberInfo.getMemberName());

		int result = NoticeManage.getInstance().makeNotice(notice);
		if (result == 1) {
			System.out.println("공지사항 등록 성공!");
		} else
			System.out.println("공지사항 등록 실패");

	}

	public void noticeDeepInfo() { ///// 글 자세히 조회
		//getNotice();
		List<Notice> list = NoticeManage.getInstance().getListNotice();
		if (list.size() == 0) {

			return;
		}
		System.out.println("조회하고 싶은 글 번호 입력 >");
		System.out.println("(취소를 원하시면 777 을 입력하세요.)");
		
		try{int num = Integer.parseInt(sc.nextLine());
		if (num == 777) {
			System.out.println("취소 >");
			return;
		}
		Collections.reverse(list);
		System.out.println("-------------------------------------------------------------------");
		System.out.println(list.get(num - 1).toString());
		System.out.println("-------------------------------------------------------------------");
		System.out.println(list.get(num - 1).getNoticeContents());
		System.out.println("-------------------------------------------------------------------");

		}catch (Exception e) {
			System.out.println("잘못입력하셨습니다. >");
		}
	}

	public void deleteNotice() {
		System.out.println("삭제할 공지 번호 입력 >");
		int noticeId = Integer.parseInt(sc.nextLine());

		int result = NoticeManage.getInstance().deleteNotice(noticeId);
		if (result != 0) {
			System.out.println("해당 공지사항을 삭제 했습니다.");
		} else
			System.out.println("삭제 실패 ");
	}
}
