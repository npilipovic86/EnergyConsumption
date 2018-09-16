# Energy Consumption
- Dependencies:   mySql, Eclipse, MySql Workbench

# Tests
- For test use empty database for start, if is there error in tests, try to clean database after every test run
	 (when testing Repositories, @Transactional does not work)
	 
# How to run
- Create database 	
- Check database connection in file "src/main/resources/application.properties" (lines 1-3), 
	line 1 - db name, line 2 - db username, line 3 - password, if you have error with starting 
	App also check line 8 and change to "create"
- in  "/src/main/sql/" database file   "db-init.sql", fill the database

	 
# Api 
- get - api/meters?month=jan&meterId=1

- get- api/files  //Start reading from file

- post - api/fractions   body: {"profile":"a","month":"jan","id":null,"fraction":0.5}

- post - api/meters    body: { "meterId":1, "profile":"a",	"month":"jan","meterReading":10 }


# What to add?
- Validation for reading from file (Like Meter Readings Validations)

- REST interface for constants changing, and saving in database

- Improved log file description

- REST interface to upload file


