package com.example.universityapplication.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.universityapplication.datamodel.RoleData;
import com.example.universityapplication.datamodel.StudentData;
import com.example.universityapplication.repository.StudentRepositoryImplementation;

/**
 * UniversityApplicationController is used to create and execute endpoints
 * 
 * @author Nisha Athmanathan
 *
 */
@RestController
@RequestMapping("/api")
public class UniversityApplicationController {

	@Autowired
	StudentRepositoryImplementation studentRepositoryImplementation;

	/**
	 * getStudentData method used in API to load student data
	 * 
	 * @param username
	 * 
	 * @return studentdatalist
	 */
	@GetMapping("/getStudentData")
	public ResponseEntity<String> getStudentData(@RequestParam String name) {
		List<StudentData> studentDataList = new ArrayList<StudentData>();
		String role = studentRepositoryImplementation.getRole(name);
		if (role == null) {
			return new ResponseEntity<>("User " + name + " does not have access to University api", HttpStatus.OK);
		}
		studentDataList = studentRepositoryImplementation.getStudent(role, name);
		if (studentDataList == null || studentDataList.isEmpty()) {
			return new ResponseEntity<>("No data to display ", HttpStatus.OK);
		} else {
			return new ResponseEntity<>(studentDataList.toString(), HttpStatus.OK);

		}

	}

	/**
	 * postAdminData is to create initial admin user from API
	 * 
	 * @return response of creation of admin entry in DB
	 */
	@PostMapping("/postOnetimeAdminData")
	public ResponseEntity<String> postAdminData() {
		studentRepositoryImplementation.insertRole("adminuser", "admin");
		return new ResponseEntity<>("adminuser Added", HttpStatus.OK);

	}

	/**
	 * This is the method to add new roles by the admin user
	 * 
	 * @param username
	 * 
	 * @RequestBody Roles table column details
	 * 
	 * @return response of creation of role in roles table
	 */
	@PostMapping("/postNewRole")
	public ResponseEntity<String> postNewRole(@RequestParam String name, @RequestBody RoleData roleData) {
		String role = studentRepositoryImplementation.getRole(name);
		if (role == null) {
			return new ResponseEntity<>("User " + name + " does not have access to University api", HttpStatus.OK);
		} else if (!role.equalsIgnoreCase("admin")) {
			return new ResponseEntity<>("User " + name + " does not have admin role to add new user", HttpStatus.OK);
		}
		studentRepositoryImplementation.insertRole(roleData.getName(), roleData.getRole());
		return new ResponseEntity<>(roleData.getName() + " User Added", HttpStatus.OK);

	}

	/**
	 * This is the method to add new student by the admin user
	 * 
	 * @param username
	 * 
	 * @RequestBody StudentData column details
	 * 
	 * @return response of creation of student details in students table
	 */
	@PostMapping("/postNewStudent")
	public ResponseEntity<String> postNewStudent(@RequestParam String name, @RequestBody StudentData studentData) {
		String role = studentRepositoryImplementation.getRole(name);
		if (role == null) {
			return new ResponseEntity<>("User " + name + " does not have access to University api", HttpStatus.OK);
		} else if (!role.equalsIgnoreCase("admin")) {
			return new ResponseEntity<>("User " + name + " does not have admin role to add new user", HttpStatus.OK);
		}
		studentRepositoryImplementation.insertNewStudent(studentData);
		return new ResponseEntity<>(studentData.getStudentName() + " Student Added in to University ", HttpStatus.OK);

	}

	/**
	 * This is the method to update student grade by teacher
	 * 
	 * @param username
	 * 
	 * @RequestBody StudentData column details
	 * 
	 * @return response of updating details in student table
	 */
	@PutMapping("/putStudentGrade")
	public ResponseEntity<String> putStudentGrade(@RequestParam String name, @RequestBody StudentData studentData) {
		String role = studentRepositoryImplementation.getRole(name);
		if (role == null) {
			return new ResponseEntity<>("User " + name + " does not have access to University api", HttpStatus.OK);
		} else if (!role.equalsIgnoreCase("teacher")) {
			return new ResponseEntity<>("User " + name + " does not have teacher role to add new user", HttpStatus.OK);
		}
		studentRepositoryImplementation.updateStudentGrade(studentData, name);
		return new ResponseEntity<>(
				studentData.getStudentName() + " Student grade and result updated in to University ", HttpStatus.OK);

	}

	/**
	 * deleteUser is to delete user by the admin user
	 * 
	 * @param username
	 * 
	 * @RequestBody name of the data need to deleted
	 * 
	 * @return response of updating details in student table
	 */
	@DeleteMapping("/delUser")
	public ResponseEntity<String> deleteUser(@RequestParam String name, @RequestBody String deleteUserName) {
		String role = studentRepositoryImplementation.getRole(name);
		if (role == null) {
			return new ResponseEntity<>("User " + name + " does not have access to University api", HttpStatus.OK);
		} else if (!role.equalsIgnoreCase("admin")) {
			return new ResponseEntity<>("User " + name + " does not have admin role to delete user", HttpStatus.OK);
		}
		String userPresence = studentRepositoryImplementation.getRole(deleteUserName);
		if (userPresence == null) {
			return new ResponseEntity<>("User " + deleteUserName + " is not present in University", HttpStatus.OK);
		}
		studentRepositoryImplementation.deleteRole(deleteUserName);
		return new ResponseEntity<>(deleteUserName + " User Deleted from University", HttpStatus.OK);

	}
}
