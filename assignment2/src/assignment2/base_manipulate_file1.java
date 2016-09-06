package assignment2;


import java.util.*;
import java.io.*;
import java.net.Socket;

public class base_manipulate_file1
{
	private String input_filename;
        private String keywords_filename;
	private String output_filename;
        private Enumeration names;
        private int count_newline = 0;
        private int old_newline = -1;
        char c1, c2;
        List<String> list = new ArrayList<>();
        private Hashtable balance = new Hashtable();
        //private Hashtable balance2 = new Hashtable();
	String result;
	Scanner sc = null;
        PrintWriter writer = null;
        HashMap<String, ArrayList<String>> dictMap;
	
	base_manipulate_file1(String open1, String keywords_file, String dump2) {
            this.dictMap = new HashMap<String, ArrayList<String>>();
            input_filename = open1;
            keywords_filename = keywords_file;
            make_list(keywords_file);
            output_filename = dump2;
	}
	
        private void make_list(String filename) {
            int i = 0;
            Scanner sc = null;
            String input = "";
            int last_i = 0;
            
            System.out.println(filename);
            
            try {
                sc = new Scanner(new BufferedReader(new FileReader(keywords_filename)));
            } catch (IOException e) {
                System.out.println("The keyword filename is missing/ could not be read !");
            }
            
            input = sc.useDelimiter("\\Z").next();
            System.out.println(input);
            for (i = 0; i < input.length(); i++) {
                
                if (input.charAt(i) == '\n' || input.charAt(i) == ' ' || input.charAt(i) == '\t') {
                    if (i - last_i != 0) {
                        if (!input.substring(last_i, i).matches(".*[a-zA-Z]+.*"))
                            continue;
                        list.add(input.substring(last_i, i));
                        //System.out.println(input.substring(last_i, i) + "k");
                    }
                        
                    last_i = i + 1;
                }
              
            }
            
            //last_i = i+1;
            
            if (i - last_i != 1 || i - last_i != 0)
                list.add(input.substring(last_i, i));
            
            System.out.println(input.substring(last_i, i));
            
        }
        
        private boolean check_list(String word) {
            int i;

            if (!word.matches(".*[a-zA-Z]+.*"))
                return false;
            
            for (i = 0; i < list.size(); i++) {
                if (word.equals(list.get(i))) {
                    return true;
                }
            }
            
            return false;
        }
        
	private String splitfile(String input) {
                boolean allow = true, allow2 = true, allow3 = true;
		String op = "";
		String new_word = "";
                char input_c;
                
                System.out.println(input);
		for (int i = 0; i < input.length(); i++) {
                    input_c = input.charAt(i);
                    if (input_c == '\n'  || i == 0)  {
                        count_newline++;
                        if (!allow3) {
                            allow3 = true;
                            
                        } else if (!allow2) {
                            allow2 = true;
                        }
                        
                    }

                    if (input.charAt(i) == '/' && input.charAt(i+1) == '*' && allow2 && allow3) {
                        c1 = '/';
                        c2 = '*';
                        allow = false;
                        continue;
                    } else if (input.charAt(i) == '"' && allow && allow3) {
                        allow2 = false;
                        continue;
                    }

                    if (input.charAt(i) == '*' && input.charAt(i + 1) == '/' && !allow) {
                        allow =  true;
                    } else if (input.charAt(i) == '"' && !allow2) {
                        allow2 = true;
                    }

                    if (allow && allow2 && i + 1 < input.length()) {
                        if (old_newline == count_newline) {
                            //System.out.println("yes");
                            continue;
                        }

                        char c1_temp = input.charAt(i);
                        char c2_temp = input.charAt(i + 1);

                        if (c1_temp == '/' && c2_temp == '/') {
                            old_newline = count_newline;
                            allow3 = false;
                        }
                    } else { // that is there is lock and hence will not be allowed
                        continue;
                    }



                    if (input_c == ' ' || input_c == ',' || input_c == '.' || input_c == '!' || input_c == '?' || input_c == ';' || input_c == ':'|| input_c == '(' || input_c == ')'|| input_c == '{' || input_c == '}' || input_c == '<' || input_c == '>' || input_c == '#' || input_c == '*' || input_c == '[' || input_c == ']' || input_c == '\n' || input_c == '+' || input_c == '-' || input_c == '/' || input_c=='\t' || input_c=='=') {
                            //op += new_word + "\n";
                        //System.out.println(new_word);
                            if (check_list(new_word)) {
                                //op += new_word + "\n";

                                //System.out.println((int)new_word.charAt(0));
                                /*if ((c1 == '/' && c2 == '/') || !allow) {
                                    System.out.print("found");
                                    continue;
                                }*/



                                if (balance.containsKey(new_word)) {
                                    dictMap.get(new_word).add(count_newline + "");
                                    //System.out.print(balance.get(new_word));
                                    double temp = (Double)balance.get(new_word);
                                    balance.put(new_word, 1.0 + temp);
                                    //balance2.put(new_word, new Double((Double)balance.get(new_word)).doubleValue() + 1.0));
                                } else {
                                    balance.put(new_word, 1.0d);
                                    ArrayList<String> a = new ArrayList<String>();
                                    a.add(count_newline+"");
                                    dictMap.put(new_word, a);
                                }
                                
                                //System.out.println(new_word);
                            }

                            new_word = "";
                            continue;
                    }

                    new_word += input.charAt(i);
		}
                
                String temp_string = "";
                names = balance.keys();
                //names = enum{list.toArray()};
                while(names.hasMoreElements()) {
                    temp_string = (String) names.nextElement();
                    op += (String) temp_string;
                    op += "\t,\t" +  (balance.get(temp_string)) + " (freq)\t\t\t";
                    
                    ArrayList<String> a = null;
                    
                    if ((a = dictMap.get(temp_string)) != null) {
                        int i = 0;
                        while(i < a.size()) {
                            op += "  " + a.get(i);
                            i++;
                        }
                    }
                    
                    op += "\n";
                }
                
                
                //System.out.println(op);
		return op;
		
	}

	public void file_change() {
		try {
			sc = new Scanner(new BufferedReader(new FileReader(input_filename)));
			writer = new PrintWriter(output_filename, "UTF-8");
		} catch (Exception e) {
			System.out.println("Exception Caught.. wrong filename");		
		} /*finally {
			if (sc != null) {
				sc.close();
			}
			if (writer != null) {
				writer.close();
			}
		}*/
                
                System.out.println(input_filename);
                String temp = sc.useDelimiter("\\Z").next();
                
                System.out.println(temp);
                
                if (!sc.hasNext()) 
                    result = splitfile(temp);
                
                result = splitfile(temp);
                
		//System.out.println(check_list("\r\t"));
		
		writer.println(result);
		writer.close();
	}
}
