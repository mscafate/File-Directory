
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;


public class FileDirectory {


    public static void menu() {
        System.out.println("0 - Exit");
        System.out.println("1 - Select Directory");
        System.out.println("2 - List directory content (first level)");
        System.out.println("3 - List directory content (all levels)");
        System.out.println("4 - Delete file");
        System.out.println("5 - Display file (hexadecimal view)");
        System.out.println("6 - Encrypt file (XOR with password)");
        System.out.println("7 - Decrypt file (XOR with password)");
        System.out.println("Select option:");
    }
    //option 1
    public static File findDirectory(String path) {
        String dirPath = "" + path; //assume working from c drive windows //String dirPath = "C:\\" + path;
    File dir = new File(dirPath); //option 1

        return dir;
    }
   
    //option 2 or option 3
    public static void displayCurrentLevelDirectory(File dir) {
        if(dir == null) {
            System.out.println("panic directory does not exist");
            return;
        }
    String[] files = dir.list();
    if (files.length == 0) {
    System.out.println("The directory is empty");
    }
    else {
        for (String aFile : files) {
        System.out.println(aFile);
        } //option 2
    }
    }
   
    //driver code for option 3
    //takes as parameter global directory from main
    public static void walkAllDirectories(File dir) {
        if(dir == null) {
            System.out.println("driver pathwalk directory is null");
            return;
        }
        ArrayList<File> subDirectoryList = new ArrayList<File>();
        ArrayList<File> fileList = new ArrayList<File>();
        recursiveUtil(subDirectoryList, fileList, dir);
        System.out.println("Here are all sub directories: ");
        for(File subDirectory : subDirectoryList) {
            System.out.println(subDirectory);
        }
        System.out.println("Here are all files: ");
        for(File file : fileList) {
            System.out.println(file);
        }
       
        return;
    }

    //helper for option 3
    public static void recursiveUtil(ArrayList<File> subDirectoryList, ArrayList<File> fileList,File dir) {
        if(dir == null) {
            System.out.println("panic directory does not exist");
            return;
        }
        File[] filesAndSubDirectories = dir.listFiles();
        try {
        if (filesAndSubDirectories.length == 0) { //try catch
            System.out.println("The directory is empty: " + dir);
            return;
        }
        }catch(Exception e) {
            return;
        }
       
        //else {
            System.out.println(filesAndSubDirectories.length);
            for (File aFile : filesAndSubDirectories) {
                if(aFile.isDirectory()) {
                      System.out.println(aFile);
                      subDirectoryList.add(aFile);
                      recursiveUtil(subDirectoryList, fileList, aFile);
                }
                else {
                    fileList.add(aFile);
                }
            }
        //}
    }

    public static void readFile(File dir, String relativePath) {
        
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            File file = new File(dir.getAbsolutePath()+ "//" + relativePath); //todo validate
            FileInputStream fis = new FileInputStream(file);

            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (FileNotFoundException fnfe) {
            System.out.println("readFile function IOException");
        } catch (IOException ioe) {
            System.out.println("readFile function IOException");
        } 
       
        byte[] bytes = bos.toByteArray();
        System.out.println("Byte offset           Bytes of file in hex");

        for (int byteNum = 0; byteNum < bytes.length ; byteNum++) {
                if (byteNum % 16 == 0) {
                    System.out.printf("%08x:  ", byteNum);
                }
                System.out.printf("%x ", bytes[byteNum]);
                if (byteNum % 16 == 15) {
                    System.out.println("");
                }
        }
        System.out.println();
    }
    
        // empty string for encrypt, filename string for decrypt output file
        public static void encryptOrDecryptFile(File dir, String relativePath, String password, String decryptFileName) {
        
        //System.out.println(file.exists() + "!!");
        //InputStream in = resource.openStream();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        OutputStream os;
        byte[] buf = new byte[1024];
        try {
            File file = new File(dir.getAbsolutePath()+ "//" + relativePath); //todo validate
            FileInputStream fis = new FileInputStream(file);

            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
            }
            
            byte[] bytes = bos.toByteArray();

            for (int byteNum = 0; byteNum < bytes.length ; byteNum++) {
                    bytes[byteNum] ^= password.charAt(byteNum%password.length());
            }
            if (decryptFileName.isEmpty()) {
                os  = new FileOutputStream(file); 
            } else {
                File decryptFile = new File(dir.getAbsolutePath()+ "//" + decryptFileName); //todo validate
                os  = new FileOutputStream(decryptFile); 

            }
            // Starts writing the bytes in it 
            os.write(bytes); //check that it overwrites  
            os.flush();
            os.close(); 
            
        } catch (FileNotFoundException fnfe) {
            System.out.println("readFile function file not found exception:" + fnfe);
        } catch (IOException ioe) {
            System.out.println("readFile function IOException:" + ioe);
        } 
        
    }
   
       
    public static void main(String[] args) throws IOException {
   
       
        //File mainDirectory = findDirectory("");
        //displayCurrentLevelDirectory(mainDirectory);
        //walkAllDirectories(mainDirectory);
       
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(System.in));
        File mainDirectory = findDirectory(""); //File mainDirectory = findDirectory("Users\\mscaf\\Desktop");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String userInput = "";
        while(!userInput.equals("0")){
            
            menu();
            userInput = reader.readLine();
            if(userInput.equals("1")) {
                System.out.println("You selected 1\n");
                System.out.println("Enter the pathway for your directory");
               
                mainDirectory = findDirectory(reader2.readLine());
                System.out.println(mainDirectory);
            }
           
            else if(userInput.equals("2")) {
                try{
                System.out.println("You selected 2\n");
                displayCurrentLevelDirectory(mainDirectory);
                }catch(NullPointerException np) {
                    System.out.println("no directory chosen");
                }
                
            }
           
            else if(userInput.equals("3")) {
                System.out.println("You selected 3\n");
               
                walkAllDirectories(mainDirectory);
            }
           
            else if(userInput.equals("4")) {
                System.out.println("You selected 4\n");
                try{
                    displayCurrentLevelDirectory(mainDirectory);
                    System.out.println("Enter filename: ");
                    BufferedReader readerDelete = new BufferedReader(new InputStreamReader(System.in));
                    File fileToDelete = new File(mainDirectory + readerDelete.readLine());
                    //check if file is located in this directory
                    if(fileToDelete.delete()) {
                        System.out.println("File deleted successfully");
                    }
                    else {
                        System.out.println("Failed to delete the file");

                    }
                } catch(NullPointerException np) {
                    System.out.println("no directory chosen");
                } catch(Exception e){
                    System.out.println("delete failed");
                }
            }

            else if(userInput.equals("5")) {
                System.out.println("You selected 5\n");
                //tell user to give a file name that is in this directory
                System.out.println("Enter filename: ");
                BufferedReader readerFileDisplay = new BufferedReader(new InputStreamReader(System.in));
                try{
                    String pathFileToDisplay = (readerFileDisplay.readLine());
                    //check if file is located in this directory and is not empty--currently done in try catch of function
                    readFile(mainDirectory, pathFileToDisplay);
                }catch(Exception e){ //optionally remove this, try catch is done properly in readFile
                    System.out.println("outputFile failed");
                }
               
            }

            else if(userInput.equals("6")) {
                System.out.println("You selected 6\n");
                System.out.println("Enter filename: ");
               BufferedReader readerFileEncrypt = new BufferedReader(new InputStreamReader(System.in));
               System.out.println("Enter password: ");
               //tell user, file name first then passowrd  
                String pathFileToEncrypt = (readerFileEncrypt.readLine());
                String password = (readerFileEncrypt.readLine()); //optinally validate password length <256 byte
                    //check if file is located in this directory and is not empty--currently done in try catch of function
                encryptOrDecryptFile(mainDirectory, pathFileToEncrypt, password, "");
            }
           
            else if(userInput.equals("7")) {
                System.out.println("You selected 7\n");
                System.out.println("Enter filename: ");
                BufferedReader readerFileDecrypt = new BufferedReader(new InputStreamReader(System.in));
               //tell user, file name first then passowrd  
                String pathFileToEncrypt = (readerFileDecrypt.readLine());
                String password = (readerFileDecrypt.readLine()); //optinally validate password length <256 byte
                String outputFileDecrypt = (readerFileDecrypt.readLine());                                    
                //check if file is located in this directory and is not empty--currently done in try catch of function
                encryptOrDecryptFile(mainDirectory, pathFileToEncrypt, password, outputFileDecrypt);
                
            }
        }
        System.out.println("Goodbye");
       
    }
   
}
