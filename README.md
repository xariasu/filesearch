# filesearch

What does this javascript do?
Search the last created file based specified name and datetime from a folder and perform a copy to another folder.

What are the criteria?
1. File created datetime (24 hr)
2. File name (alphanumeric)

Sample use case:
User receive hundreds of system generated file every hour but they only need to save the last file of the day.
Using this script, user just need specify provide the datetime and wildcard filename.

For example, by specifying datetime to be today before 6 PM, the script will automatically search the last file generated before 6 PM and copy it to the user specified folder.
There is optional arguement for file rename.


Arguement:
Set date
fs.setCutoffDate("YYYY-MM-DD");  
	    
Set time in 24-hr format    
fs.setCutoffTime("HH:mm:dd");    
        
Set source directory
fs.setFolder("C:\\Source\\folder");

Set search string
fs.setLeftWord("filenamesamplewithtimestamp"); 
        
Set target directroy and filename
fs.setOutFile("D:\\Target\\folder\\filename.DAT"); 
