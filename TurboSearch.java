/*
* @author: Sailendra Karki
*
* Instruction to run program:
* 1. create all the files in same folder where this file locate.
* 2. Compile program using javac TurboSearch.java
* 3. Run :java TurboSearch human  apple.txt apple2.txt human.txt
*   here human is keyword to search and remaining arguments are filename
* 4. programe will create thread for each file on arguments
*/

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class TurboSearch implements Runnable{
    private Thread t;
    private String threadName;
    private String keywords;
    private String filenames;
    private static int countsum=0;
   

    /* constructor */
    TurboSearch(int i,String filename,String keyword,int totalcount){
       threadName="thread"+i;
       keywords= keyword;
       filenames=filename;
    }

    public static synchronized int getTotal() {
        return countsum;
    }

     private synchronized void sum(int value) {
        countsum += value;
     }

    public void run(){
            FileReader fi=null;
            File input= new File(filenames);
            String readword;
            int countfound=0;
        try{
            fi= new FileReader(input);
            BufferedReader br= new BufferedReader(fi);
            while((readword=br.readLine())!=null){
                if(readword.contains(keywords)){
                    countfound=countfound+1;
                    System.out.println(filenames+" : "+readword);
                }

            }
            fi.close();
        }catch(IOException e){
            e.printStackTrace();
        }
        sum(countfound);
    }

   

    public void start () {
        if (t == null) {
           t = new Thread (this, threadName);
           t.start ();
    
        }
        
     }

    public static void main(String[] args) {
        int totalcount = 0;
        if(args.length<2){
            System.out.println("Usage:java TurboSearch keyword sourcefile1 sourcefiel2...");

        }
        totalcount=args.length-1;
        
        String keyword=args[0];
        
        
        Thread[] th=new Thread[totalcount];
        for(int i=0;i<args.length-1;i++){

            th[i] =new Thread(new TurboSearch(i,args[i+1],keyword,totalcount));
            th[i].start();
            //String filename= args[i+1];
        }
        for (int i = 0; i < args.length-1; i++) {
            try {
                th[i].join();
       
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\nTotal Result Found are:");
        System.out.println("-----------------------------");
        System.out.println(getTotal());
    }
}