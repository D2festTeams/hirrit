import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Comment;


public class CommentDB {
	
	private MysqlDB db = null;
	CommentDB(){
		db = new MysqlDB();
	}
	
	public void insertComment(Comment comment) throws SQLException {
		Map<Integer, Object> sourceInfoParams = new HashMap<Integer, Object>();
		sourceInfoParams.put(1, comment.getCommit_id());
		sourceInfoParams.put(2, comment.getPath());
		
		Long source_id = 0L;
		ResultSet result = db.getResult("select * from source_info where commit_id = ? and path = ? ", sourceInfoParams);
		if(!result.next()){
			source_id = db.setValue("insert into source_info (commit_id, path) values (?, ?);",  sourceInfoParams);
		}
		else{
			source_id = (Long) result.getObject("source_id");
		}
		
		if(source_id != null && source_id != 0L){
			Map<Integer, Object> commentInfoParams = new HashMap<Integer, Object>();
			String commentQuery = "insert into comment_info (source_id";
			commentInfoParams.put(1, source_id);
			int i = 1;
			if(comment.getPriority() != null){
				commentQuery += ", priority";
				commentInfoParams.put(++i, comment.getPriority());
			}
			if(comment.getComment() != null){
				commentQuery += ", comment";
				commentInfoParams.put(++i, comment.getComment());
			}
			if(comment.getStart_line() != null){
				commentQuery += ", start_line";
				commentInfoParams.put(++i, comment.getStart_line());
			}
			if(comment.getEnd_line() != null){
				commentQuery += ", end_line";
				commentInfoParams.put(++i, comment.getEnd_line());
			}
			if(comment.getUp_cnt() != null){
				commentQuery += ", up_cnt";
				commentInfoParams.put(++i, comment.getUp_cnt());
			}
			if(comment.getDown_cnt() != null){
				commentQuery += ", down_cnt";
				commentInfoParams.put(++i, comment.getDown_cnt());
			}
			commentQuery += ") values (?";
			for(int q=0; q<i-1; q++){
				commentQuery += ", ?";
			}
			commentQuery += ")";
			db.setValue(commentQuery, commentInfoParams);
		}
	}
	
	public List<Comment> getComments(long commit_id, String path) throws SQLException{
		Map<Integer, Object> params = new HashMap<Integer, Object>();
		params.put(1, commit_id);
		params.put(2, path);
		ResultSet rs = db.getResult("select b.* from source_info a, comment_info b where a.source_id = b.source_id and a.commit_id = ? and a.path = ?", params);
		List<Comment> commentList = new ArrayList<Comment>();
		while(rs.next()){
			Comment comment = new Comment();
			comment.setSource_id(rs.getLong("source_id"));
			comment.setCommit_id(commit_id);
			comment.setPath(path);
			comment.setComment(rs.getString("comment"));
			comment.setPriority(rs.getInt("priority"));
			comment.setStart_line(rs.getInt("start_line"));
			comment.setEnd_line(rs.getInt("end_line"));
			comment.setUp_cnt(rs.getInt("up_cnt"));
			comment.setDown_cnt(rs.getInt("down_cnt"));
			comment.setRegdate(rs.getDate("regdate"));
			commentList.add(comment);
		}
		return commentList;
	}

}
