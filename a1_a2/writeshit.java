import java.io.*;
public class writeshit{

public static void main(String[] args ){

try {
        BufferedWriter out = new BufferedWriter(new FileWriter("tentothe6.txt"));
    
    int range = 1000000; //10^6
	out.write("1"+"\n");
	for (int i = 0; i <= range; i++) {
        int randomNum = (int)(Math.random() * range);

        out.write(randomNum+"\n");
        
        }
    out.close();
}catch (IOException e){


	}
}
    
}








