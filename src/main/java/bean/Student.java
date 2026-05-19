package bean;

import java.io.Serializable;

public class Student implements Serializable {
	
	private String no;
	private String name;
	private int entYear;
	private String classNum;
	private boolean isAttend;
	private School school;
	
//	ゲッター(Getter)とセッター(Setter)の設置
	public String getNo() {
		return no;
	}
	public String getName() {
		return name;
	}
	public int getEntYear() {
		return entYear;
	}
	public String getClassNum() {
		return classNum;
	}
	public boolean isAttend() {
		return isAttend;
	}
	public School getSchool() {
		return school;
	}
	
	
	public void setNo(String no) {
		this.no = no;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	public void setAttend(boolean isAttend) {
		this.isAttend = isAttend;
	}
	public void setSchool(School school) {
		this.school = school;
	}
}