package com.company;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Socket client = new Socket(InetAddress.getLocalHost().getHostAddress(), 5461)) {
            Scanner in=new Scanner(System.in);
            System.out.println("connected !");
            PrintWriter pw=new PrintWriter(client.getOutputStream(),true);
            BufferedReader bf=new BufferedReader(new InputStreamReader(client.getInputStream()));

            String s;
            while(true){
                s=bf.readLine();
                if(s.equals("bye")){
                    client.close();
                    break;
                }
                InputStream is=por(s);
                if(is==null){
                    pw.println("Invalid command !");
                    pw.println("`");
                    pw.flush();
                    continue;
                }
                BufferedReader br=new BufferedReader(new InputStreamReader(is));

                br.lines().forEach(pw::println);

                pw.println("`");
                pw.flush();
            }

        }catch (IOException e){
            System.out.println("Error in client");
        }

    }
    public static InputStream por(String cmd){
        try {
            Runtime rt=Runtime.getRuntime();
            Process p=rt.exec(cmd);
            return p.getInputStream();
        } catch (IOException e) {
            return null;
        }
    }
}
