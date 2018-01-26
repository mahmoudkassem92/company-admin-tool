
Build and run
Configurations

Open the application.properties file and set your database configurations.

Prerequisites
Java 8
Maven > 3.0
From terminal
Go on the project's root folder, then type:

$ mvn spring-boot:run


Or From netbeans open project and then run the application


Available API's:

URL							Method		body			Function
/companies 						Get					list all companies list
/companies/{companyId}					Get					list company details
/companies						Post		company model		Create new Company
/companies/{companyId}					Put		company model		update Company
/companies/{companyId}					Delete					delete company	
/companies/{companyId}/employees			Get					retrieve company's employees list
/employees/{employeeId}					Get					retrieve employee Details
/companies/{companyId}/employees			Post		employee model		create new employee for company
/companies/{companyId}/employees/{employeeId}		Delete					delete employee for company
/companies/{companyId}/employees/{employeeId}		Put		employee model		update employee for company
/companies/{companyId}/averagesalary			Get					get company average salary






