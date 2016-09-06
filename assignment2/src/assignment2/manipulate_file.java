package assignment2;


import java.util.*;
import java.io.*;

public class manipulate_file
{

	public static void main(String args[]) throws IOException {
                String input_filename;
                String output_filename;
                String keywords_filename;
                Scanner sc = new Scanner(System.in);
                
		input_filename = sc.next();
                keywords_filename = sc.next();
		output_filename = sc.next();
		
		base_manipulate_file1 obj = new base_manipulate_file1(input_filename, keywords_filename, output_filename);
		obj.file_change();
                
                sc.close();
	}
}
