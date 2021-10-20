
###################################
#      Future Enhancements        #
###################################

This has been developed using in-memory spring H2 Database.
 In future based on the need this can be pointed to any database by changing the relevant properties and driver dialects in spring properties.
 The database can be accessed using url http://localhost:8080/h2-ui/login.jsp

This solution has 2 tables.
1.Student table to track student details 
2.Roles table that can maintain the roles of each users including students,teachers and admin.

This initial Version is developed with following assumptions.In future enhancements this can be updated.
1.No duplicate names are allowed. This means no same name can be repeated even if it is of different role. 
2.Only one role per person. This means he/she can not play two different roles like admin and teacher. 
3.only one course can be enrolled per student.

The above mentioned limitations can be overcome in future by having unique id for each person in a separate master table and 
refer them instead of "name" in all the child tables like student, roles etc.

Authentication not implemented currently. However in current code, based on the name supplied in the parameter of API call, this is being handled now. 
That is an admin user can add/delete new students and add new roles. Student can view data only corresponds to them. 
Teacher only be able to update the grade of the student corresponding to them. 

An authentication can added in future using the following steps:
1. adding security core dependency in pom.xml
2. Creating configuration for security and restricting the access for each end points based on the roles in the database

Loggers need to be implemented for method starting and ending.
Exception handling need to be done for certain functions

Note: Please refer the README.md for the current implementation details.


