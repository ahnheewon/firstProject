package com.pro.notice;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pro.common.DAO;

public class NoticeManage extends DAO {

	// 싱글톤
		private static NoticeManage nm = new NoticeManage();

		// static 일치 시켜준다.
		private NoticeManage() {

		}

		public static NoticeManage getInstance() {
			return nm; // static 일치 시켜준다.
		}

		
		// 공지 쓰기 메소드
		public int makeNotice(Notice notice) {
			int result = 0;
			int cnt = 0;

			try {
				conn();

				String sql0 = "select to_char(sysdate,'yy/mm/dd HH24:MI') from dual";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql0); /// notice_date 를 가져오기 위함.

				if (rs.next()) {
					notice.setNoticeDate(rs.getString("to_char(sysdate,'yy/mm/ddHH24:MI')"));

				}

				String sql1 = "select count(*) from notice";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql1); /// notice_id 를 가져오기 위함.

				if (rs.next()) {
					cnt = rs.getInt("count(*)");

				}

				String sql = "insert into notice (notice_Id,notice_name,member_name,notice_contents,notice_date) values (?,?,?,?,?)";
				notice.setNoticeId((cnt + 1));
				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, notice.getNoticeId());
				pstmt.setString(2, notice.getNoticeName());
				pstmt.setString(3, notice.getMemberName());
				pstmt.setString(4, notice.getNoticeContents());
				pstmt.setString(5, notice.getNoticeDate());

				result = pstmt.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
			return result;

		}
		
		// 2-2 현재 공지사항 목록 조회 // 내용 제외
		public List<Notice> getListNotice() {
			List<Notice> list = new ArrayList<>();
			Notice ntc;

			try {
				conn();
				String sql = "select * from notice order by Notice_id desc";
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);

				while (rs.next()) {
					ntc = new Notice();

					ntc.setNoticeId(rs.getInt("Notice_id"));
					ntc.setNoticeName(rs.getString("Notice_name"));
					ntc.setMemberName(rs.getString("member_Name"));
					ntc.setNoticeContents(rs.getString("Notice_contents"));
					ntc.setNoticeDate(rs.getString("Notice_date"));

					list.add(ntc); // 리스트에 계속 추가 되면서 반환.
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
	
		public int deleteNotice(int noticeId) {
			List<Notice> list = NoticeManage.getInstance().getListNotice();
			int result = 0;
			try {
				conn();
				String sql0 = "DELETE FROM Notice where Notice_ID=?";
				pstmt = conn.prepareStatement(sql0);
				pstmt.setInt(1, noticeId);
				result = pstmt.executeUpdate();
				if (result == 1) { // 1이면 삭제가 완료됨.
					if (noticeId == 1 && noticeId == (list.size() + 1)) // list.size=0 인 상태
						result = pstmt.executeUpdate();
					else if (noticeId < list.size()) { // 제일 최신글 순번을 제외 하고는 삭제 시 업데이트 해줘야함.
						String sql1 = "UPDATE Notice SET Notice_ID=Notice_ID-1 WHERE Notice_ID>?";
						pstmt = conn.prepareStatement(sql1);
						pstmt.setInt(1, noticeId);

						result = pstmt.executeUpdate();
						// 1개이상의 값이 업데이트 될 것이다.
					}
					// 제일 최신글 순번인 경우 업데이트가 없으므로 return 값 1
				} else
					result = pstmt.executeUpdate(); // 고로 0인경우 삭제 실패.

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disconnect();
			}
			return result;
		}

		
}
