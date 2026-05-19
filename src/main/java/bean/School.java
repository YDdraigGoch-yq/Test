package bean;

import java.io.Serializable;

public class School implements Serializable {

	private String cd;
	private String name;
	
// ゲッター(Getter)とセッター(Setter)の設置
	public String getCd() {
		return cd;
	}
	public String getName() {
		return name;
	}
	
	
	public void setCd(String cd) {
		this.cd = cd;
	}
	public void setName(String name) {
		this.name = name;
	}
}
