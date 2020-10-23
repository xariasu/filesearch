package filecopy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;


public class FileSearch {

    private File[] files;
    private static String cutoffDate;
    private static String cutoffTime;
    private String folder;
    private String leftWord;
    private String outFile;

    public void getFileTime() {

        System.out.println(files.length);

        //for (int i = 0; i < files.length; i++) {

        File file = files[0];
        // Get the last modification date.
        Long lastModified = file.lastModified();

        // Create a new date object
        Date date = new Date(lastModified);

        DateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String result = fm.format(date);

        DateFormat fmd = new SimpleDateFormat("yyyy-MM-dd");

        String resultd = fmd.format(date);


        //Show file time
        System.out.println(file + "\t" + result);


    }

    public void findFile() {

        File dir = new File(folder);
        // To filter the list of returned files.
        FilenameFilter filter = new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {

                return name.startsWith(leftWord);
            }
        };

        // The list of files as File
        files = dir.listFiles(filter);

    }

    public void fileBeforeCutoff(long cutoffLong) {
        //System.out.println(files.length);
        if (files.length > 0) {
            int j = 0;
            long lastMod = Long.MIN_VALUE;
            File choise = null;
            for (File file : files) {
                long lm = file.lastModified();
                long fs = file.length();
                if (lm > lastMod & lm < cutoffLong & fs > 0) {
                    choise = file;
                    lastMod = lm;
                }
            }

            files[0] = choise;
            // Create a new date object
            Date date = new Date(lastMod);

            DateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String result = fm.format(date);

            System.out.println(choise + "\t" + result);

            copyfile("", outFile);

        } else {
            System.out.println("No file found.");
        }
    }

    public void fileSort() {
        Arrays.sort(files, new Comparator<File>() {

            @Override
            public int compare(File f1, File f2) {
                return Long.valueOf(f2.lastModified()).compareTo(f1.lastModified());
            }
        });

    }

    public long dateToLong(String cutoffDateTime) {
        long longDate = Long.MIN_VALUE;
        try {
            DateFormat fm;
            Date date;
            fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = (Date) fm.parse(cutoffDateTime);
            longDate = date.getTime();
            System.out.println("Cutoff time is " + cutoffDateTime + ", long format: " + longDate);
        } catch (ParseException e) {
            System.out.println("Exception : " + e);
        }
        return longDate;
    }

    private void copyfile(String srFile, String dtFile) {
        try {
            File f1 = new File(srFile);
            f1 = files[0];
            File f2 = new File(dtFile);
            InputStream in = new FileInputStream(f1);

            //For Overwrite the file.
            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
            System.out.println("File copied.");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + " in the specified directory.");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setCutoffDate(String cutoffDate) {
        this.cutoffDate = cutoffDate;
    }

    public void setCutoffTime(String cutoffTime) {
        this.cutoffTime = cutoffTime;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public void setLeftWord(String leftWord) {
        this.leftWord = leftWord;
    }

    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }

    public File[] getFiles() {
        return files;
    }

    public static void main(String[] args) {
        FileSearch fs = new FileSearch();

	//Insert Date "YYYY-MM-DD"
        fs.setCutoffDate("YYYY-MM-DD");  
	    
	//Insert Date "HH:mm:ss" 24-hr format    
        fs.setCutoffTime("HH:mm:dd");    
        
	long datelong = fs.dateToLong(cutoffDate + " " + cutoffTime);
   
		
	// Sample Search Criteria
        fs.setFolder("C:\\Source\\folder"); //Source Directory
        fs.setLeftWord("filenamesamplewithtimestamp"); // File Keyword from left
        fs.setOutFile("D:\\Target\\folder\\filename.DAT"); // Exact filename output and directory
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

  
	
    }
}
