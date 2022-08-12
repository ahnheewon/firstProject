package com.pro.comment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pro.common.DAO;

public class CommentManage extends DAO {

	// 싱글톤
	private static CommentManage cm = new CommentManage();

	// static 일치 시켜준다.
	private CommentManage() {

	}

	public static CommentManage getInstance() {
		return cm; // static 일치 시켜준다.
	}

	// 덧글 쓰기
	public int makeComment(Comment comment) {
		int result = 0;
		int cnt = 0;

		try {
			conn();

			String sql0 = "select to_char(sysdate,'yy/mm/dd HH24:MI') from dual";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql0); /// Comment_date 를 가져오기 위함.

			if (rs.next()) {
				comment.setCommentDate(rs.getString("to_char(sysdate,'yy/mm/ddHH24:MI')"));

			}

			String sql1 = "select count(*) from board_comment";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql1); /// Comment_id 를 가져오기 위함.

			if (rs.next()) {
				cnt = rs.getInt("count(*)");

			}

			String sql = "insert into board_comment (Comment_Id,member_name,Comment_contents,Comment_date,board_id) values (?,?,?,?,?)";
			comment.setCommentId((cnt + 1));
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, comment.getCommentId());
			pstmt.setString(2, comment.getMemberName());
			pstmt.setString(3, comment.getCommentContents());
			pstmt.setString(4, comment.getCommentDate());
			pstmt.setInt(5, comment.getBoardId());

			result = pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return result;

	}

	// 2-2 현재 게시글의 댓글 조회
	public List<Comment> getListComment(int boardId) {

		List<Comment> list = new ArrayList<>();
		Comment cmt;

		try {
			conn();
			String sql = "select * from board_comment where board_id=? order by comment_id";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				cmt = new Comment();

				cmt.setCommentId(rs.getInt("comment_id"));
				cmt.setMemberName(rs.getString("Member_Name"));
				cmt.setCommentContents(rs.getString("comment_contents"));
				cmt.setCommentDate(rs.getString("comment_date"));

				list.add(cmt); // 리스트에 계속 추가 되면서 반환.
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
