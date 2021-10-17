
package com.example.universityapplication.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.universityapplication.datamodel.StudentData;

/**
 * StudentRepositoryImplementation is used to execute queries in database for
 * load,insert,update and delete operations
 * 
 * @author Nisha Athmanathan
 */
@Repository
public class StudentRepositoryImplementation {

	@Autowired
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * Method to extract the role for the given user
	 * 
	 * @param username
	 * 
	 * @return role name
	 */
	@Transactional
	public String getRole(String name) {
		try {
			Query getRoleQuery = entityManager.createNativeQuery("select role from roles where name=:name");
			getRoleQuery.setParameter("name", name);
			return getRoleQuery.getSingleResult().toString();
		} catch (NoResultException nre) {
			return null;
		}

	}

	/**
	 * getStudent is to extract the student data based on the role
	 * 
	 * @param username
	 * 
	 * @return studentdatalist
	 */
	@Transactional
	public List<StudentData> getStudent(String role, String name) {
		try {
			List<StudentData> studentData = new ArrayList<StudentData>();
			if (role.equalsIgnoreCase("admin")) {
				Query getStudentData = entityManager.createNativeQuery(
						"select studentname,teachername,course,0 as grade, 'NA' as result from students",
						StudentData.class);
				studentData = getStudentData.getResultList();
			} else if (role.equalsIgnoreCase("teacher")) {
				Query getStudentData = entityManager.createNativeQuery(
						"select studentname,teachername,course,coalesce(grade,0) grade, result from students where teachername= :teachername",
						StudentData.class);
				getStudentData.setParameter("teachername", name);
				studentData = getStudentData.getResultList();

			} else if (role.equalsIgnoreCase("student")) {
				Query getStudentData = entityManager.createNativeQuery(
						"select studentname,teachername,course,coalesce(grade,0) grade, result from students where studentname= :studentname",
						StudentData.class);
				getStudentData.setParameter("studentname", name);
				studentData = getStudentData.getResultList();

			}

			return studentData;
		} catch (NoResultException nre) {
			return null;
		}

	}

	/**
	 * insertNewStudent is to insert new Student in student table and corresponding
	 * roles in roles table
	 * 
	 * @param StudentData column details
	 */
	@Transactional
	public void insertNewStudent(StudentData studentData) {
		Query insertStudentData = entityManager.createNativeQuery(
				"insert into students (studentname,teachername,course) values (:studentname,:teachername,:course)");
		insertStudentData.setParameter("studentname", studentData.getStudentName());
		insertStudentData.setParameter("teachername", studentData.getTeacherName());
		insertStudentData.setParameter("course", studentData.getCourse());
		insertStudentData.executeUpdate();

		Query insertRoleData = entityManager.createNativeQuery("insert into roles (name,role) values (:name,:role)");
		String studentroleavailable = getRole(studentData.getStudentName());
		if (studentroleavailable == null) {
			insertRoleData.setParameter("name", studentData.getStudentName());
			insertRoleData.setParameter("role", "student");
			insertRoleData.executeUpdate();
		}
		String teacherRoleAvailable = getRole(studentData.getTeacherName());
		if (teacherRoleAvailable == null) {
			insertRoleData.setParameter("name", studentData.getTeacherName());
			insertRoleData.setParameter("role", "teacher");
			insertRoleData.executeUpdate();
		}
	}

	/**
	 * updateStudentGrade is to update Student grade
	 * 
	 * @param StudentData column details
	 * 
	 * @param username
	 */
	@Transactional
	public void updateStudentGrade(StudentData studentData, String name) {
		String result = (studentData.getGrade() >= 70 ? "PASS" : "FAIL");
		Query updateStudent = entityManager.createNativeQuery(
				"update students set grade=:grade,result=:result where teachername=:teachername and studentname=:studentname");
		updateStudent.setParameter("studentname", studentData.getStudentName());
		updateStudent.setParameter("teachername", name);
		updateStudent.setParameter("grade", studentData.getGrade());
		updateStudent.setParameter("result", result);
		updateStudent.executeUpdate();
	}

	/**
	 * insertRole is to insert new roles in roles table
	 * 
	 * @param username
	 * 
	 * @param role     of the user
	 */
	@Transactional
	public void insertRole(String name, String role) {
		try {
			Query insertRoleData = entityManager
					.createNativeQuery("insert into roles (name,role) values (:name,:role)");
			insertRoleData.setParameter("name", name);
			insertRoleData.setParameter("role", role);
			insertRoleData.executeUpdate();
		}

		catch (Exception E) {
			E.printStackTrace();
		}
	}

	/**
	 * deleteRole is to delete the student data and corresponding role
	 * 
	 * @param name of the data need to deleted
	 */
	@Transactional
	public void deleteRole(String deleteUserName) {
		try {
			String role = getRole(deleteUserName);
			Query deleteRoleData = entityManager.createNativeQuery("delete from  roles where name= :name");
			deleteRoleData.setParameter("name", deleteUserName);
			deleteRoleData.executeUpdate();
			if (role.equalsIgnoreCase("student")) {
				Query delStudent = entityManager.createNativeQuery("delete from  students where studentname= :name");
				delStudent.setParameter("name", deleteUserName);
				delStudent.executeUpdate();
			}
		}

		catch (Exception E) {
			E.printStackTrace();
		}
	}
}
