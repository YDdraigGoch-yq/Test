package bean;

import java.io.Serializable;

public class ClassNum implements Serializable {

	private String class_num;
	private School school;
	
// ゲッター(Getter)とセッター(Setter)の設置
	public String getClass_num() {
		return class_num;
	}
	public School getSchool() {
		return school;
	}
	
	
	public void setClass_num(String class_num) {
		this.class_num = class_num;
	}
	public void setSchool(School school) {
		this.school = school;
	}
}