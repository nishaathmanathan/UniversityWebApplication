
###################################
#  Run book for the current code  #
###################################
Please read this document to get overview of this application ands its implementation.
This solution has 2 tables.
1.Student table to track student details 
2.Roles table that can maintain the roles of each users including students,teachers and admin.
 The database can be accessed using url http://localhost:8080/h2-ui/login.jsp

Endpoint 1: postOnetimeAdminData: http://localhost:8080/api/postOnetimeAdminData
	This endpoint will be executed, once the application is started. This will create user "adminuser" in roles table .
    (This step can be eliminated after implementing authentication.) 

Endpoint 2: postNewStudent: http://localhost:8080/api/postNewStudent?name=adminuser
    Only admin is allowed to add new student
    Need to pass name of the admin user as parameter and student details as body.Example is given below.

{
    "studentName": "david",
    "teacherName": "mark",
    "course":"computer"
}
Note: Even if the admin passes grade and result parameter , it will not get updated in system. 
If the role does not exist for student and teacher it will get automatically added in to the role table.

Endpoint 3: getStudentData : http://localhost:8080/api/getStudentData?name=adminuser
    Based on the name supplied in the api call it will provide the below result
    (name can be name of the admin/student/teacher whose role already exist in DB)
	Student: It will show all the details of the respective student 
	Teacher: It will display the details of the students under them.
	Admin: It will show all students details with grade as 0 and result as NA.

Endpoint 4: NewRole insertion: http://localhost:8080/api/postNewRole?name=adminuser
    Only adminis able to add new role(eg."adminuser")
    Only one user can be added at a time. This can be enhanced to multiple users in future.
    Need to pass name of the admin user as parameter and name, role of the new user in body
Ex1:(in body as JSON format)
{
    "name":"david",
    "role":"student"
} 
Ex2:
{
    "name":"mark",
    "role":"teacher"
} 
Ex3:
{
    "name":"steve",
    "role":"admin"
} 

Endpoint 5: putStudentGrade: http://localhost:8080/api/putStudentGrade?name=mark
    This endpoint is used by the teachers to upgrade the grade and the result will get updated automatically based on the grade value.
    If others tried to access it will display permission issue
    Need to pass name of the teacher as parameter and student name, grade in body. 
    The teacher would be able to update the details of his/her student only. he /she can set or update grade and result will be updated automatically
Ex:(in body as JSON format)
{
    "studentName": "david",
    "grade":70
}

Endpoint 6:delUser: http://localhost:8080/api/delUser?name=adminuser
   Only admin can execute.Error message will be displayed for others.
   Need to pass name as parameter and name of the user to be deleted in body as raw text.
Ex: david

Note: If a student is deleted from roles table then, the same entry will also be deleted from students table.
