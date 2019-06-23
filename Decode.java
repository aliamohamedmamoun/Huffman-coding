
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.concurrent.SynchronousQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Decode {
	static HashMap<String,String> codeMap= new HashMap<String,String>();


	public static void ReadCompressedFile(String filename) throws IOException{
		
		 File file = new File(filename);
			Scanner sc = new Scanner(file);
			 List<String> lines = new ArrayList<String>();
			int noOfLines=0;
			String str="";
			String temp="";
			while(sc.hasNext()){
				 lines.add(sc.nextLine());
				noOfLines++;	
			}
			System.out.println(noOfLines);
		  String[] line = lines.toArray(new String[noOfLines]);
		  List<String> c = new ArrayList<String>();
		     List<String> newC = new ArrayList<String>();
		     int noOfDistinctChar=0;		     
			  String regex ="([a-zA-Z0-9_]+)(\\s*)([a-zA-Z0-9_]+)";                    //"(([a-zA-Z0-9_\\/]+)|(\\s+))(\\s*)([a-zA-Z0-9_]+)(\\s+)([a-zA-Z0-9_]+)(\\s+)([a-zA-Z0-9_]+)";
					  do
				        {
				            Pattern pattern = Pattern.compile(regex);
				            Matcher matcher = pattern.matcher(line[noOfDistinctChar]);
				            while (matcher.find())
				            {
				            	//character[i]=matcher.group(1);
				            	c.add(matcher.group(1));		 
				               newC.add( matcher.group(3));
				             
				            }
				            noOfDistinctChar++;
				          //    System.out.println(character[i]+"    "+code[i]+"         "+newCode[i]);

				       }while(line[noOfDistinctChar].matches("([a-zA-Z0-9_]+)(\\s*)([a-zA-Z0-9_]+)"));
		  
					  System.out.println(noOfDistinctChar);
					  String[] newCode = newC.toArray(new String[noOfDistinctChar]);
					   String[] code = c.toArray(new String[noOfDistinctChar]);
					  //creating code map
					   for(int i=1;i<noOfDistinctChar;i++){
						//   System.out.println(newCode[i]+"       "+code[i]);
						   codeMap.put(newCode[i],code[i]);
					   }
					  
                                      //testing Map
//					   System.out.println("Hiiiiiii");
//					  for (String name: codeMap.keySet()){
//					        String key =name.toString();
//					        String value = codeMap.get(name).toString(); 
//					        System.out.println(key + "          " + value);        
//					} 
					  String CompressedFile="";
					  String OneCharString="";
					  String dummy="";
					  /*
					  for(int i=noOfDistinctChar+1;i<noOfLines;i++){
					  System.out.println(line[i]);
					  char[] chs=line[noOfDistinctChar].toCharArray();
					  for(int j= 0;j<chs.length;j++){
						  String ff=Integer.toBinaryString(chs[j]);
						  if(ff.length()==7){
							 CompressedFile+="0";
						  }
						  System.out.println(Integer.toBinaryString(chs[j]));
							CompressedFile+= Integer.toBinaryString(chs[j]);
						  }
				  }
				  */
					  
					  for(int i=noOfDistinctChar;i<noOfLines;i++){
						//  System.out.println(line[i]);
						  char[] chs=line[i].toCharArray();
						  for(int j= 0;j<chs.length;j++){
							  String ff=Integer.toBinaryString(chs[j]);
							  if(ff.length()<8){
								  int diff=8-ff.length();
								  dummy="";
								  for(int m=0;m<diff;m++){
									  dummy+="0";
								  }
								  OneCharString=dummy+ff;
								  CompressedFile+=OneCharString;
								  OneCharString="";
								 //CompressedFile+="0";
							  }else{
							//  System.out.println(Integer.toBinaryString(chs[j]));
								CompressedFile+= Integer.toBinaryString(chs[j]);
							  }
					  }
					 
					  
				System.out.println("COMPRESSED FILE");
						System.out.println(CompressedFile);
					  
					  DecodeFile(CompressedFile,codeMap);
					  }
	
	}
       public static void   DecodeFile(String compressedFile,HashMap<String,String> codeMap) throws IOException{
    	   String temp="";
    	    Writer writer = null;
    	    writer = new BufferedWriter(new FileWriter("decompressedInput.txt"));
    	   for(int i=0;i<compressedFile.length();i++){
    		   temp+=compressedFile.charAt(i);
    		   if(codeMap.containsKey(temp)){
    			   String binCode=codeMap.get(temp);
    			  // System.out.println(binCode);
    			   int charCode = Integer.parseInt(binCode, 2);
    		        char s = new Character((char)charCode);
    		       // System.out.println(s);
    		        writer.write(s);
    			   temp="";
    		   }
    	   }
    	   writer.close();
       }
       
	
	
	
	}

