import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

class MyComparator implements Comparator<HuffmanNode> { 
    public int compare(HuffmanNode x, HuffmanNode y) 
    { 
  
        return x.freq - y.freq; 
    } 
} 

public class Encode {
	//Static variables
	static	HashMap<String,Integer> freqMap= new HashMap<String,Integer>();
	static	HashMap<String,String> codeMap= new HashMap<String,String>();
	static Writer writer = null;
	static FileOutputStream fileOuputStream = null;
	static long InputFileSize;
	static long CompressedFileSize;
	static	String filename;
	
	public static void main(String[] args) throws IOException {	
	//	getFilesNames("C:\\Users\\3alya\\Desktop\\workspace2\\FinalHuffman");
		Scanner scan=new Scanner(System.in);
		int choice;
		System.out.println("For text file compression please choose 1");
		System.out.println("For text file decompression please choose 2");
        choice =scan.nextInt();
		System.out.println("Please Enter the file name:");
         filename=scan.next();
		if(choice==1){
			long Compression_start_time = System.currentTimeMillis();
            byte[] fileChs =ReadFile(filename);
			int noOfDistinctChar=freqMap.size();
			String[] binChars=new String[noOfDistinctChar];
			int [] freq=new int[noOfDistinctChar];
			int i=0;
			for (String name: freqMap.keySet()){
		        binChars[i] =name.toString();
		        freq[i] = freqMap.get(name);  
		      //  System.out.println(binChars[i] + " " +freq[i]);
		        i++;      
		}
			  writer = new BufferedWriter(new FileWriter("a.txt"));
		
		HuffmanNode root=Encode(binChars,freq,noOfDistinctChar);  //build huffman Tree
		PrintHuffmanTree(root, "");                               //print huffman Tree
		printCompressedFile(fileChs);                             //print compressed file
		long Compression_elapsed_time = (System.currentTimeMillis()-Compression_start_time);
		System.out.println("Compression elapesed time =  "+Compression_elapsed_time +"  milliSeconds");
		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;
		
		File file = new File("a.txt");
		bytesArray = new byte[(int) file.length()];
		fileInputStream = new FileInputStream(file);
		fileInputStream.read(bytesArray);
		CompressedFileSize = file.length();
		System.out.println(CompressedFileSize);
		System.out.println("Compression Ratio = "+(double)((CompressedFileSize*100)/InputFileSize)+"  %");
		}else if(choice==2){
			long Decompression_start_time = System.currentTimeMillis();
			Decode.ReadCompressedFile(filename);
			long Decompression_elapsed_time = (System.currentTimeMillis()-Decompression_start_time);
			System.out.println("Decompression elapesed time =  "+Decompression_elapsed_time +"  milliSeconds");
		
		}

	
	} 

	public static byte[] ReadFile(String filename){
		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;
		try {

		File file = new File(filename);
		bytesArray = new byte[(int) file.length()];

		//read file into bytes[]
		fileInputStream = new FileInputStream(file);
		fileInputStream.read(bytesArray);
		InputFileSize = file.length();
		System.out.println(InputFileSize);
		} catch (IOException e) {
		e.printStackTrace();
		} finally {
		if (fileInputStream != null) {
		    try {
		        fileInputStream.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		 //for (int i = 0; i < bytesArray.length; i++) {
		   // System.out.println(Integer.toBinaryString((char)bytesArray[i]));
		    
		//}
		List<Character> c = new ArrayList<Character>();
		String temp2="";
		for(int i=0;i<bytesArray.length;i++){
			Integer val= freqMap.get(Integer.toBinaryString((char)bytesArray[i]));
			if(val !=null){
				freqMap.put(Integer.toBinaryString((char)bytesArray[i]),new Integer(val+1));	
			}else{
			
				freqMap.put(Integer.toBinaryString((char)bytesArray[i]),1);
			}
		//	System.out.println(Integer.toBinaryString((char)bytesArray[i]));
		//	System.out.println(Integer.toBinaryString(chs[i]));	
		}
		char[] characters= new char[freqMap.size()]; 

	                                            	//testing map
//		for (String name: freqMap.keySet()){
//	        String key =name.toString();
//	        String value = freqMap.get(name).toString(); 
//	        int charCode = Integer.parseInt(name, 2);
//	        char s = new Character((char)charCode);
//	        System.out.println(s+" "+ key + " " + value); 
//	        
//	} 
		
		
		
	}
		return bytesArray;
	}
public static HuffmanNode Encode(String[]chars,int[] freq, int n) throws IOException{


             //		 Creates a PriorityQueue with the specified initial capacity that orders its elements according
             //		 to the specified comparator.
      PriorityQueue<HuffmanNode> minHeap = new PriorityQueue<HuffmanNode>(n, new MyComparator()); 
	 for (int i = 0; i < n; i++) {  
             // creating a huffman node object  and adding it to the priority-queue. 
         HuffmanNode hn = new HuffmanNode(); 
         hn.data = chars[i]; 
         hn.freq = freq[i]; 
        // System.out.println(chars[i]);
         if(chars[i].equals("1101")||chars[i].equals("1010")){
        	 hn.c="/n";
         }else if(chars[i].equals("100000")){
        	 hn.c="/t";
        	 }else{
         int charCode =(int)Integer.parseInt(chars[i],2);
          hn.c = new Character((char)charCode).toString();
         }
          hn.left = null; 
         hn.right = null;  
         minHeap.add(hn); 
     } 
     // create a root node 
     HuffmanNode root = null; 
     // Here we will extract the two minimum value 
     // from the heap each time until 
     // its size reduces to 1, extract until 
     // all the nodes are extracted. 
     while (minHeap.size() > 1) { 

         // first min extract. 
         HuffmanNode x = minHeap.peek(); 
         minHeap.poll();
         // second min extarct. 
         HuffmanNode y = minHeap.peek(); 
         minHeap.poll(); 
         // new node f which is equal to the sum of the frequency of the two nodes 
         HuffmanNode f = new HuffmanNode(); 
         // assigning values to the f node. 
         f.freq = x.freq + y.freq; 
         f.data = "-"; 
         // first extracted node as left child. 
         f.left = x; 
         // second extracted node as the right child. 
         f.right = y; 
         // marking the f node as the root node. 
         root = f; 
         // add this node to the priority-queue. 
         minHeap.add(f); 
     } 
     // print the codes by traversing the tree 
     writer.write("Code      NewCode");
     ((BufferedWriter) writer).newLine();	  
     return root;
 } 
public static void PrintHuffmanTree(HuffmanNode root, String s) throws IOException 
{ 
    // base case; if the left and right are null 
     //then its a leaf node and we print 
    // the code s generated by traversing the tree. 
	//if leaf node
    if (root.left == null&& root.right == null )//&& isLetter(root.c))
               
        { 
      //write the table  
     writer.write(root.data+"       "+s); 
     ((BufferedWriter) writer).newLine();	
        codeMap.put(root.data,s);
        return; 
    } 
//	for (String name: codeMap.keySet()){
//    String key =name.toString();
//    String value = codeMap.get(name).toString(); 
//    int charCode = Integer.parseInt(name, 2);
//    char s2 = new Character((char)charCode);
//    System.out.println(s+" "+ key + " " + value); 
//    
//} 
    // if we go to left then add "0" to the code. 
    // if we go to the right add"1" to the code.  
    PrintHuffmanTree(root.left, s + "0"); 
    PrintHuffmanTree(root.right, s + "1"); 
}


public static void printCompressedFile(byte[] charsInFile) throws IOException{
	
	String temp="";
	String ByteString="";
for(int i=0;i<charsInFile.length;i++){
String code=Integer.toBinaryString(charsInFile[i]);
 String newCode = codeMap.get(code);
 char[] bits=newCode.toCharArray();
	 for(int j=0;j<bits.length;j++){
		 if(temp.length()==8){
			 //System.out.println(temp);
			 int charCode =(int)Integer.parseInt(temp,2);
	          char c = (char)charCode;
	         // System.out.println(c);
	          String d= Integer.toBinaryString(c);
	         // System.out.println(d);
				 writer.write(c);
			 temp="";
		 }
		 temp+=bits[j];
	 }
	 
 }

//System.out.println(temp);
int charCode =(int)Integer.parseInt(temp,2);
char c = new Character((char)charCode);
//System.out.println(c);
String d= Integer.toBinaryString(c);
System.out.println(d);
writer.write(c);
writer.close();
  }

public static void getFilesNames(String FolderName){
File folder = new File(FolderName);
File[] listOfFiles = folder.listFiles();

for (int i = 0; i < listOfFiles.length; i++) {
  if (listOfFiles[i].isFile()) {
    System.out.println("File " + listOfFiles[i].getName());
  } else if (listOfFiles[i].isDirectory()) {
    System.out.println("Directory " + listOfFiles[i].getName());
  }
}
}
}

