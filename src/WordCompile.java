import java.io.*;

public class WordCompile {
	public static String[] Keyword= {"const","var","procedure","begin","end","odd","if","then"
			,"call","while","do","read","write"};
	public static String[] Keywordsym= {"constsym","varsym","procsym","beginsym","endsym"
			,"oddsym","ifsym","thensym"
			,"callsym","whilesym","dosym","readsym","writesym"};
	public static String[] singlechar = {"+","-","*","/","#","=","<",">","(",")",",",";","."}; 
	public static String[]  singlecharsym= {"plus","minus","times","slash","neq","eql","lss","gtr",
			"lparen","rparen","comma","semicolon","period"};
    public  String readFileByChars(String fileName) { 
    	String string = "";
        File file = new File(fileName);  
        Reader reader = null;  
        try {  
            reader = new InputStreamReader(new FileInputStream(file));  
            int tempchar;  
            while ((tempchar = reader.read()) != -1) {  
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。  
                // 但如果这两个字符分开显示时，会换两次行。  
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。  
                if (((char) tempchar) != '\r') {  
                    string = string +(char) tempchar;
                }  
            }  
            reader.close();  
           
        } catch (Exception e) {  
            e.printStackTrace();  
            
        }  
         return string;
    }  
    public String wordCompile(String string) {
    	int max = string.length();
    	int num;
    	String str="";
    	String res="";
    	for (int i = 0; i < max;i++) {
			char ch = string.charAt(i);
			if ((ch>'9'||ch<'0')&&(ch<'A'||ch>'z')) {
				if (str!=""&&str.charAt(0)>='A'&&str.charAt(0)<='z') {
					for (int j = 0; j < Keyword.length; j++) {
						if (str==Keyword[j]) {
							System.out.println("<"+str+","+str+"sym>");
							res=res+"<"+str+","+str+"sym>";
							break;
						}
						if (j==Keyword.length-1) {
							System.out.println("<"+str+","+"ident>");
							res=res+"<"+str+","+"ident>";
						}
					}
					str="";
					i--;
				}else {
					if (str!=""&&str.charAt(0)>='0'&&str.charAt(0)<='9') {
						num=Integer.parseInt(str);
						System.out.println("<"+num+","+"number>");
						res=res+"<"+num+","+"number>";
						str="";
						i--;
					}else {
						if(str==""&&ch==':') {
							if (string.charAt(i+1)=='=') {
								System.out.println("<"+string.charAt(i)+string.charAt(i+1)+","+"becomes>");
								res=res+"<"+string.charAt(i)+string.charAt(i+1)+","+"becomes>";
								str="";
								i++;
							}else {
								System.out.println("<"+string.charAt(i)+","+"nul>");
								res=res+"<"+string.charAt(i)+","+"nul>";
								str="";
							}
						}else {
							if(str==""&&ch=='<') {
							if (string.charAt(i+1)=='=') {
								System.out.println("<"+string.charAt(i)+string.charAt(i+1)+","+"lep>");
								res=res+"<"+string.charAt(i)+string.charAt(i+1)+","+"lep>";
								str="";
								i++;
							}else {
								System.out.println("<"+string.charAt(i)+","+"lss>");
								res=res+"<"+string.charAt(i)+","+"lss>";
								str="";
							}
						}	else {
							if(str==""&&ch=='>') {
								if (string.charAt(i+1)=='=') {
									System.out.println("<"+string.charAt(i)+string.charAt(i+1)+","+"gep>");
									res=res+"<"+string.charAt(i)+string.charAt(i+1)+","+"gep>";
									str="";
									i++;
								}else {
									System.out.println("<"+string.charAt(i)+","+"gtr>");
									res=res+"<"+string.charAt(i)+","+"gtr>";
									str="";
								}
							}else {
								if (str==""&&ch!='\n'&&ch!=' '&&ch!='\t') {
									for (int j = 0; j < singlechar.length; j++) {
										if (ch==singlechar[j].charAt(0)) {
								System.out.println("<"+string.charAt(i)+","+singlecharsym[j]+">");
								res=res+"<"+string.charAt(i)+","+singlecharsym[j]+">";
								str="";
										}
									}
								
								}
								
								
								
							}
							
						}
						}
					}
				}
			}else{
				
				str=str+string.charAt(i);
			}
			
		}
    	return res;
    }
    public void output(String fileName,String content) {
    	  try {  
              //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件  
              FileWriter writer = new FileWriter(fileName, false);  
              writer.write(content);  
              writer.close();  
          } catch (IOException e) {  
              e.printStackTrace();  
          }  
    }
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WordCompile wordcompile =new WordCompile();
		 String string =wordcompile.readFileByChars("D:/input.txt");
		 System.out.print(string);
		 System.out.println();
		 String res=wordcompile.wordCompile(string);
		 wordcompile.output("D:/output.txt", res);
		 
	}

}
