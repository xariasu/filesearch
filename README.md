# filesearch

What does this javascript do?
Search a file based on specified criteria from a folder and perform a copy to another folder.

What are the criteria
1. File created time (24 hr)
2. File name (alphanumeric)

Sample use case:
User receive hundreds of system generated file every hour but they only need to save the last file of the day.
Using this script, user just need specify provide the datetime and wildcard filename.

By specifying datetime to be today before 6 PM, the script will automatically search the last file generated before 6 PM and copy it to the user specified folder.
There is optional arguement for file rename.
