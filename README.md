# Ben Trube Availity Technical Assessment

## Coding Exercise One: Lisp Checker
 - Coding Exercise One can be found under the Assignment1 directory.
 - **Requirements:** You are tasked to write a checker that validates the parentheses of a LISP code.  Write a program (in Java or JavaScript) which takes in a string as an input and returns true if all the parentheses in the string are properly closed and nested.
 - Solution TL;DR:
    - Used a stream to filter out the parentheses from the given input.
    - Used the Deque interface to keep track of them and determine if string was valid or not.

## Coding Exercise Two: CSV Read and Sort
 - Coding Exercise Two can be found under the Assignment2 directory.
 - **Requirements:** Coding exercise:  Availity receives enrollment files from various benefits management and enrollment solutions (I.e. HR platforms, payroll platforms).  Most of these files are typically in EDI format.  However, there are some files in CSV format.  For the files in CSV format, write a program in a language that makes sense to you that will read the content of the file and separate enrollees by insurance company in its own file. Additionally, sort the contents of each file by last and first name (ascending).  Lastly, if there are duplicate User Ids for the same Insurance Company, then only the record with the highest version should be included. The following data points are included in the file:
    - User Id (string)
    - First Name (string) 
    - Last Name (string)
    - Version (integer)
    - Insurance Company (string)
- Solution TL;DR:
    - CSVSorter.java is the entry point/main method.
    - Grabs predetermined path for the CSV (enrollment.csv) in the input folder, passes path to FileManager.parseFile().
    - FileManager.parseFile() takes each line from the file (excluding the header) and creates an EnrollmentProfile object for each one.
        - Somewhat overkill for this application, but would allow for us to do more with the EnrollmentProfiles if there was more to the app.
    - FileManger.parseFile() also does our duplicate checking, ensuring that we only have one of each userID per insuranceCompany before returning a map to the main method.
    - It then calls CSVSorter.sortByCompany() to group the EnrollmentProfiles by insurance company, and alphebetize them by lastName, then firstName.
    - Finally, writes each group of EnrollmentProfiles to its own file named after the given insurance company, via FileManager.writeToFiles().
    - The program then prints out the files created, and exits.

## Written Responses
 - Responses to the open-ended questions can be found in the Written Responses directory.
