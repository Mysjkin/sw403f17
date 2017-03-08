package p4test;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InputTester {
	

    public static void main(String args[])
    {
    	
    	String currentFile;
    	Scanner scanner;
    	
    	try {
    		File folder = new File(System.getProperty("user.dir") + "/src/p4test/TestPrograms/");
    		File[] listOfFiles = folder.listFiles();

    		for (File file : listOfFiles) {
    		    if (file.isFile()) {
    		        currentFile = "";
    		    	currentFile = readFile(System.getProperty("user.dir") + "/src/p4test/TestPrograms/" + file.getName());
    		    	scanner = new Scanner(currentFile);
    		        try
    		        {
    		            long i = currentFile.length();
    		            while(scanner.index < scanner.inputLen)
    		                System.out.println(scanner.nextToken());
    		            ParserIaro parserIaro = new ParserIaro(scanner);
    		            parserIaro.Run();
    		            
    		        }
    		        catch (Exception e)
    		        {
    		            System.out.println("Mistakes were made: " + e);
    		        }
    		    }
    		}
			  
		} catch (Exception e){//Catch exception if any
			  System.out.println("Error: " + e.getMessage());
		}
    }
    
    static String readFile(String path) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, Charset.forName("ASCII") );
			}
	
}
