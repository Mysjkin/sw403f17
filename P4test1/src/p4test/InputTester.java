package p4test;
import java.io.*;

public class InputTester {
    public static void main(String args[])
    {

    	try {
			FileInputStream fstream = new FileInputStream(System.getProperty("user.dir") + "/src/p4test/TestPrograms/Test1.txt");
			
			 DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
			  // Print the content on the console
			  System.out.println (strLine);
			  }
			  //Close the input stream
			  in.close();
			  
		} catch (Exception e){//Catch exception if any
			  System.err.println("Error: " + e.getMessage());
		}
	
    	
   /* 	
        String input = 
                "void FirstMission (string person, number speed) " +
                    "number target is speed " +
                    "until(true) " +
                        "string turn is target " +
                    "end until " +
                "end FirstMission ";
*/
        //ParserMikkel bla = new ParserMikkel(new Scanner(input));
        //bla.Parse();

        /*
        // Run scanner on test string
        Scanner scanner = new Scanner(input);
        try
        {
            long i = input.chars().count();
            //while(scanner.index < scanner.inputLen)
                //System.out.println(scanner.nextToken());
            ParserIaro parserIaro = new ParserIaro(scanner);
            parserIaro.Run();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        */
    }
}
