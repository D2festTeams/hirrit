import java.sql.SQLException;
import java.util.List;

import model.Comment;

public class DBSample {
	
	public static void main(String[] args) throws SQLException {
		CommentDB db = new CommentDB();
		
		/*
		 * insert sample
		 */
		Comment comment = new Comment();
		comment.setCommit_id(1234L);
		comment.setPath("/ham/ham");
		comment.setPriority(2);
		comment.setComment("코멘트.");
		comment.setUp_cnt(1);
		comment.setStart_line(2);
		comment.setEnd_line(5);
		db.insertComment(comment);
		
		/*
		 * select sample 
		 */
		List<Comment> commentList = db.getComments(1234, "/ham/ham");
		for(Comment comm : commentList){
			System.out.println(">>> "+comm.getComment());
		}
	}

}
