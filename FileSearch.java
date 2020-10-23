/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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


public class FileSearch2 {

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
        // Get the last modification information.
        Long lastModified = file.lastModified();

        // Create a new date object
        Date date = new Date(lastModified);

        DateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String result = fm.format(date);

        DateFormat fmd = new SimpleDateFormat("yyyy-MM-dd");

        String resultd = fmd.format(date);

        //if (resultd.matches(cutoffDate)) {
        //Show file time
        System.out.println(file + "\t" + result);
        //}
        //}

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

            //For Append the file.
            //  OutputStream out = new FileOutputStream(f2,true);

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
        FileSearch2 fs = new FileSearch2();


		
		
        fs.setCutoffDate("2019-10-06");
        fs.setCutoffTime("20:00:00");
        long datelong = fs.dateToLong(cutoffDate + " " + cutoffTime);
   
		
		// 994I	
        fs.setFolder("S:\\EDI\\994I");
        fs.setLeftWord("994IAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994IAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("994IBP20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994IBP.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
 
        // 994U
        fs.setFolder("S:\\EDI\\994U");
        fs.setLeftWord("994UAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994UAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

         // 9954
        fs.setFolder("S:\\EDI\\9954");
        fs.setLeftWord("9954AS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9954AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9954TF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9954TF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
		
        // 994F
        fs.setFolder("S:\\EDI\\994F");
        fs.setLeftWord("994FAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994FAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("994FEWS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994FEWS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("994FTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994FTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
 
        // 997G
        fs.setFolder("S:\\EDI\\997G");
        fs.setLeftWord("997GAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\997GAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("997GTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\997GTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("997GEWS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\997GEWS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
	
        // 998G       
        fs.setFolder("S:\\EDI\\998G");
        fs.setLeftWord("998GAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\998GAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("998GTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\998GTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);     	
        
        // 999L
        fs.setFolder("S:\\EDI\\999L");
        fs.setLeftWord("999LAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\999LAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("999LTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\999LTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
		
       // 9991
        fs.setFolder("S:\\EDI\\9991");
        fs.setLeftWord("9991NEWAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9991AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setFolder("S:\\EDI\\9991");
        fs.setLeftWord("9991TF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9991TF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

        // 997C
 		fs.setFolder("S:\\EDI\\997C");
        fs.setLeftWord("997CBP20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\997CBP.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setFolder("S:\\EDI\\997C");
        fs.setLeftWord("997CAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\997CAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
   
        // 9956	
        fs.setFolder("S:\\EDI\\9956");
        fs.setLeftWord("9956EWS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9956EWS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9956TF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9956TF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

        // 995G		
        fs.setFolder("S:\\EDI\\995G");
        fs.setLeftWord("995GAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\995GAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("995GEWS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\995GEWS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("995GTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\995GTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
		
        // 996M

        fs.setFolder("S:\\EDI\\996M");
        fs.setLeftWord("996MAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996MAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("996MTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996MTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

        // 996R
        fs.setFolder("S:\\EDI\\996R");
        fs.setLeftWord("996REWS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996REWS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("996RBP20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996RBP.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("996RTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996RTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("996RAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996RAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
     
        // 996S
	 
        fs.setFolder("S:\\EDI\\996S");
        fs.setLeftWord("996SAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996SAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("996SBP20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996SBP.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
		

        // 9936
        fs.setFolder("S:\\EDI\\9936");
        fs.setLeftWord("9936EWS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9936EWS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);		
        

        // 998C
        fs.setFolder("S:\\EDI\\998C");
        fs.setLeftWord("998CBP20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\998CBP.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("998CAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\998CAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
 
		// 998S
 
        fs.setFolder("S:\\EDI\\998S");
        fs.setLeftWord("998SAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\998SAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
              
        // 991F
        fs.setFolder("S:\\EDI\\991F");
        fs.setLeftWord("991FAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\991FAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("991FBP20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\991FBP.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
                		
    
		// 994X
 
        fs.setFolder("S:\\EDI\\994X");
        fs.setLeftWord("994XAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994XAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("994XTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994XTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);		
		
		
        // 9945

        fs.setFolder("S:\\EDI\\9945");
        fs.setLeftWord("9945AS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9945AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9945EWS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9945EWS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9945TF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9945TF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

  
        // 9996
        fs.setFolder("S:\\EDI\\9996");
        fs.setLeftWord("9996AS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9996AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9996TF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9996TF.DAT");
        fs.findFile();
		fs.fileBeforeCutoff(datelong);
        
		// 9998
        fs.setFolder("S:\\EDI\\9998");
        fs.setLeftWord("9998AS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9998AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9998TF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9998TF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

		
	    // Khairul 

        // 9957
        fs.setFolder("S:\\EDI\\9957");
        fs.setLeftWord("9957AS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9957AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9957TF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9957TF.DAT");
        fs.findFile();
		fs.fileBeforeCutoff(datelong);  
    
        // 9992
        fs.setFolder("S:\\EDI\\9992");
        fs.setLeftWord("9992AS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9992AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
	
        // 9941
        fs.setFolder("S:\\EDI\\9941");
        fs.setLeftWord("9941AS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9941AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9941TF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9941TF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        
     
        // 994L
        fs.setFolder("S:\\EDI\\994L");
        fs.setLeftWord("994LAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994LAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("994LTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994LTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

		// 9955
        fs.setFolder("S:\\EDI\\9955");
        fs.setLeftWord("9955AS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9955AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9955TF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9955TF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        
		// 996K
        fs.setFolder("S:\\EDI\\996K");
        fs.setLeftWord("996KAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996KAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("996KTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996KTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

		// 996T
        fs.setFolder("S:\\EDI\\996T");
        fs.setLeftWord("996TAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996TAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("996TTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996TTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

		// 998N
        fs.setFolder("S:\\EDI\\998N");
        fs.setLeftWord("998NBP20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\998NBP.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setFolder("S:\\EDI\\998N");
        fs.setLeftWord("998NAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\998NAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("998NTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\998NTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        
		// 998T
        fs.setFolder("S:\\EDI\\998T");
        fs.setLeftWord("998TTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\998TTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
     
		
	    // 9977
        fs.setFolder("S:\\EDI\\9977");
        fs.setLeftWord("9977AS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9977AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9977BP20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9977BP.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

        // 996F
        fs.setFolder("S:\\EDI\\996F");
        fs.setLeftWord("996FAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996FAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("996FTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996FTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

        // 996H
        fs.setFolder("S:\\EDI\\996H");
        fs.setLeftWord("996HNEWAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996HAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("996HTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996HTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
		
	    // 996I
        fs.setFolder("S:\\EDI\\996I");
        fs.setLeftWord("996IAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996IAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("996ITF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996ITF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
		
		// 996N
        fs.setFolder("S:\\EDI\\996N");
        fs.setLeftWord("996NAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996NAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("996NTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996NTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);


        // 996Z
        fs.setFolder("S:\\EDI\\996Z");
        fs.setLeftWord("996ZAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996ZAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);       
        
		
		// 9971
        fs.setFolder("S:\\EDI\\9971");
        fs.setLeftWord("9971TF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9971TF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9971EWS20");        
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9971EWS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        		

		// 9989
        fs.setFolder("S:\\EDI\\9989");
        fs.setLeftWord("9989AS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9989AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9989EWS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9989EWS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9989TF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9989TF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9989BP20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9989BP.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
	    
		// 998Y
        fs.setFolder("S:\\EDI\\998Y");
        fs.setLeftWord("998YNEWAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\998YAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
  
  
		// 998Z
        fs.setFolder("S:\\EDI\\998Z");
        fs.setLeftWord("998ZNEWAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\998ZAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("998ZTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\998ZTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

		// 9993
        fs.setFolder("S:\\EDI\\9993");
        fs.setLeftWord("9993AS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9993AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("9993BP20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9993BP.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
      
     
        // 999U
        fs.setFolder("S:\\EDI\\999U");
        fs.setLeftWord("999UAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\999UAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("999UTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\999UTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

     
        // 999X
        fs.setFolder("S:\\EDI\\999X");
        fs.setLeftWord("999XAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\999XAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("999XEWS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\999XEWS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);


		
    	// 994D
        fs.setFolder("S:\\EDI\\994D");
        fs.setLeftWord("994DNEWAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994DAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("994DBP20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994DBP.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("994DEWS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994DEWS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("994DTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994DTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
     

        // 994H
        fs.setFolder("S:\\EDI\\994H");
        fs.setLeftWord("994HAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994HAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("994HTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\994HTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
	    
		// 9950
        fs.setFolder("S:\\EDI\\9950");
        fs.setLeftWord("9950AS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9950AS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setFolder("S:\\EDI\\9950");
        fs.setLeftWord("9950TF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\9950TF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);

		
	    // 995W
        fs.setFolder("S:\\EDI\\995W");
        fs.setLeftWord("995WAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\995WAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
   		
	
		// 995Y
        fs.setFolder("S:\\EDI\\995Y");
        fs.setLeftWord("995YAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\995YAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("995YTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\995YTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
		
		
		// 996G
        fs.setFolder("S:\\EDI\\996G");
        fs.setLeftWord("996GAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996GAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("996GTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\996GTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
    

   
        // 999V
        fs.setFolder("S:\\EDI\\999V");
        fs.setLeftWord("999VAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\999VAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
        fs.setLeftWord("999VTF20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\999VTF.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
		
	   
		// 999Y
        fs.setFolder("S:\\EDI\\999Y");
        fs.setLeftWord("999YAS20");
        fs.setOutFile("T:\\Data Quality\\DailyEDI\\2019-10-06\\999YAS.DAT");
        fs.findFile();
        fs.fileBeforeCutoff(datelong);
  
			
	
		
	

    }
}
