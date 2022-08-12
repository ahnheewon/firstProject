package com.pro.board;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pro.comment.Comment;
import com.pro.comment.CommentManage;
import com.pro.common.DAO;

public class BoardManage extends DAO {

	// 싱글톤
	private static BoardManage bm = new BoardManage();

	// static 일치 시켜준다.
	private BoardManage() {

	}

	public static BoardManage getInstance() {
		return bm; // static 일치 시켜준다.
	}

	public Board boardInfo(int boardId) { // 글번호로 글정보 쫘라락
		Board board = null;
		try {
			conn();
			// id -> PK
			// 1개의 row만 반환됨.
			String sql = "select * from board where board_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				board = new Board();
				board.setBoardId(rs.getInt("Board_Id"));
				board.setBoardName(rs.getNString("member_name"));
				board.setMemberName(rs.getString("Member_Name"));
				board.setBoardDate(rs.getString("Board_Date"));
				board.setBoardView(rs.getInt("Board_View"));
				board.setBoardRecommend(rs.getInt("Board_Recommend"));
				board.setBoardReport(rs.getInt("Board_Report"));

				// 반환값을 객체에 넣음.
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return board;
	}

	// 글쓰기 메소드
	public int makeBoard(Board board) {
		int result = 0;
		int cnt = 0;

		try {
			conn();

			String sql0 = "select to_char(sysdate,'yy/mm/dd HH24:MI') from dual";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql0); /// Board_date 를 가져오기 위함.

			if (rs.next()) {
				board.setBoardDate(rs.getString("to_char(sysdate,'yy/mm/ddHH24:MI')"));

			}

			String sql1 = "select count(*) from board";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1); /// Board_id 를 가져오기 위함.

			if (rs.next()) {
				cnt = rs.getInt("count(*)");

			}

			String sql = "insert into board (Board_Id,Board_name,member_name,Board_contents,Board_date) values (?,?,?,?,?)";
			board.setBoardId((cnt + 1));
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, board.getBoardId());
			pstmt.setString(2, board.getBoardName());
			pstmt.setString(3, board.getMemberName());
			pstmt.setString(4, board.getBoardContents());
			pstmt.setString(5, board.getBoardDate());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;

	}

	// 2-2 현재 게시글 목록 조회 // 내용 제외
	public List<Board> getListBoard() {

		List<Board> list = new ArrayList<>();
		Board brd;

		try {
			conn();
			String sql = "select * from board order by board_id desc";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				brd = new Board();

				brd.setBoardId(rs.getInt("Board_id"));
				brd.setBoardName(rs.getString("Board_name"));
				brd.setMemberName(rs.getString("Member_Name"));
				brd.setBoardContents(rs.getString("Board_contents"));
				brd.setBoardDate(rs.getString("Board_date"));
				brd.setBoardView(rs.getInt("Board_view"));//////////// 조회수

				list.add(brd); // 리스트에 계속 추가 되면서 반환.
			}

		} catch (SQLException e) {
			// ora-00001 : 어떤 이유로 오류가 났습니다.
			System.out.println("※※※※ Error ※※※※");
			System.out.println("code : " + e.getErrorCode());
			System.out.println(e.getMessage()); // 이유
			e.getMessage();
			e.getErrorCode();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}
	

	// 글 삭제
	public int deleteBoard(int boardId) {
		List<Board> blist = BoardManage.getInstance().getListBoard();
		List<Comment> clist = CommentManage.getInstance().getListComment(boardId);
		int result = 0;
		int result2 = 0;
		try {
			conn();
			String sql0 = "DELETE FROM BOARD where BOARD_ID=?";
			pstmt = conn.prepareStatement(sql0);
			pstmt.setInt(1, boardId);
			result = pstmt.executeUpdate();
			if (result == 1) { // 1이면 삭제가 완료됨.
				if (boardId == 1 && boardId == (blist.size() + 1)) {
					// 글이 하나밖에 없는데 지운 경우, list.size=0 이 된 상태
					// 더 이상 변화X
				} else if (boardId < blist.size()) { // 제일 최신글 순번을 제외 하고는 삭제 시 업데이트 해줘야함.
					String sql1 = "UPDATE BOARD SET BOARD_ID=BOARD_ID-1 WHERE BOARD_ID>?";
					pstmt = conn.prepareStatement(sql1);
					pstmt.setInt(1, boardId);
					result = pstmt.executeUpdate();
					// 1개이상의 값이 업데이트 될 것이다.

				}
				// 제일 최신글 순번인 경우 업데이트가 없으므로 return 값 1

				// 글이 삭제가 되면 추가로 덧글도 삭제....
				String sql2 = "DELETE FROM BOARD_comment where BOARD_ID=?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setInt(1, boardId);
				result2 = pstmt.executeUpdate();
				if (result2 == 1) { // 1이면 덧글 삭제가 완료됨.
					if (boardId == 1 && boardId == (blist.size() + 1)) {
						// 덧글이 하나밖에 없는데 지운 경우, list.size=0 이 된 상태
					} else if (boardId < blist.size()) { // 제일 최신글 순번을 제외 하고는 삭제 시 업데이트 해줘야함.
						// 덧글 번호도 보드와 같이 삭제된놈들번호 다운..
						String sql3 = "UPDATE BOARD_comment SET BOARD_ID=BOARD_ID-1 WHERE BOARD_ID>?";
						pstmt = conn.prepareStatement(sql3);
						pstmt.setInt(1, boardId);
						pstmt.executeUpdate();
					}
				}

			} else
				result = pstmt.executeUpdate(); // 고로 0인경우 삭제 실패.

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;
	}

	/// 글 작성자 알아내기
	public String searchMemberName(int boardId) {
		String memberName = "";
		try {
			conn();
			String sql = "select member_name from board where board_id =?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery(); // pstmt.executeQuery() 안에는 값을 넣으면 안된다!

			if (rs.next()) { // rs.next는 열 아래로 내려가면서 값을 가져옴.
				memberName = rs.getString("member_name");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return memberName;

	}

	public void RecommendUp(int boardId) { // 추천수 중복 불가
		try {
			conn();
			String sql = "update board set board_recommend=board_recommend+1 where board_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);

			int result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("추천하였습니다!");
			} else
				System.out.println("추천에 실패하였습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void ReportUp(int boardId) { // 신고수
		try {
			conn();
			String sql = "update board set board_report=board_report+1 where board_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);

			int result = pstmt.executeUpdate();
			if (result == 1) {
				
			} else
				System.out.println("실패하였습니다.");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void ViewUp(int boardId) { // 조회수 업데이트
		try {
			conn();
			String sql = "update board set board_view=board_view+1 where board_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public List<Board> getReportListBoard() { ////////신고된 게시물 조회
		List<Board> list = new ArrayList<>();
		Board brd;

		try {
			conn();
			String sql = "select * from board where board_report>0 order by board_id desc";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				brd = new Board();

				brd.setBoardId(rs.getInt("Board_id"));
				brd.setBoardName(rs.getString("Board_name"));
				brd.setMemberName(rs.getString("Member_Name"));
				brd.setBoardContents(rs.getString("Board_contents"));
				brd.setBoardDate(rs.getString("Board_date"));
				brd.setBoardReport(rs.getInt("Board_report"));//////////// 조회수

				list.add(brd); // 리스트에 계속 추가 되면서 반환.
			}

		} catch (SQLException e) {
			// ora-00001 : 어떤 이유로 오류가 났습니다.
			System.out.println("※※※※ Error ※※※※");
			System.out.println("code : " + e.getErrorCode());
			System.out.println(e.getMessage()); // 이유
			e.getMessage();
			e.getErrorCode();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	

}
