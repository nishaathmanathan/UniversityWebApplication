package com.example.universityapplication.datamodel;

import javax.persistence.*;

/**
 * StudentData is an entity file to keep all the columns related to students
 * table
 * 
 * @author Nisha Athmanathan
 *
 */
@Entity
@Table(name = "students")
public class StudentData {

	@Id
	@Column(name = "studentname")
	private String studentName;

	@Column(name = "teachername")
	private String teacherName;

	@Column(name = "course")
	private String course;

	@Column(name = "grade")
	private int grade;

	@Column(name = "result",nullable = true)
	private String result;

	public StudentData(String studentName, String teacherName, String course, int grade, String result) {
		super();
		this.studentName = studentName;
		this.teacherName = teacherName;
		this.course = course;
		this.grade = grade;
		this.result = result;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public StudentData() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "StudentData [studentName=" + studentName + ", teacherName=" + teacherName + ", course=" + course
				+ ", grade=" + grade + ", result=" + result + "]";
	}

}
