package model;

import java.sql.Date;

public class Comment {
	
	private Long commit_id;
	private String path;
	
	private Long source_id;
	private Integer priority;
	private String comment;
	private Integer start_line;
	private Integer end_line;
	private Integer up_cnt;
	private Integer down_cnt;
	
	private Date regdate;
	
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Long getCommit_id() {
		return commit_id;
	}
	public void setCommit_id(Long commit_id) {
		this.commit_id = commit_id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Long getSource_id() {
		return source_id;
	}
	public void setSource_id(Long source_id) {
		this.source_id = source_id;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Integer getStart_line() {
		return start_line;
	}
	public void setStart_line(Integer start_line) {
		this.start_line = start_line;
	}
	public Integer getEnd_line() {
		return end_line;
	}
	public void setEnd_line(Integer end_line) {
		this.end_line = end_line;
	}
	public Integer getUp_cnt() {
		return up_cnt;
	}
	public void setUp_cnt(Integer up_cnt) {
		this.up_cnt = up_cnt;
	}
	public Integer getDown_cnt() {
		return down_cnt;
	}
	public void setDown_cnt(Integer down_cnt) {
		this.down_cnt = down_cnt;
	}
	
}
