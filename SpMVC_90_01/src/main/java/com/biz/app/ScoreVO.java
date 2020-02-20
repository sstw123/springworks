package com.biz.app;

public class ScoreVO {
	
	public int kor;
	public int eng;
	public int math;
	public int sci;
	public int music;
	
	public int sum;
	public int avg;
	public int count;
	
	public ScoreVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ScoreVO(int kor, int eng, int math, int sci, int music, int sum, int avg, int count) {
		super();
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.sci = sci;
		this.music = music;
		this.sum = sum;
		this.avg = avg;
		this.count = count;
	}

	public int getKor() {
		return kor;
	}

	public void setKor(int kor) {
		this.kor = kor;
	}

	public int getEng() {
		return eng;
	}

	public void setEng(int eng) {
		this.eng = eng;
	}

	public int getMath() {
		return math;
	}

	public void setMath(int math) {
		this.math = math;
	}

	public int getSci() {
		return sci;
	}

	public void setSci(int sci) {
		this.sci = sci;
	}

	public int getMusic() {
		return music;
	}

	public void setMusic(int music) {
		this.music = music;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	public int getAvg() {
		return avg;
	}

	public void setAvg(int avg) {
		this.avg = avg;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
