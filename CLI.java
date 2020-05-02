package com.example.CLI_OS;
//package com.zetcode;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.Files;
import java.util.Scanner;
import java.nio.file.Paths;
import java.io.File;
import java.util.stream.Stream;

class direc
{
    public static String direcName;
    private static void direc() {
        direcName=Paths.get(".").toAbsolutePath().normalize().toString();
}
    public static void setDirecName(String x)
    {
        direcName=x;
    }
    public static String getDirecName ()
    {
        return direcName;
    }

}
public class CLI {

    public static  void main (String [] args ) throws IOException {
       System.out.print("enter command, please be careful with spelling\n");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        //fi moshkla fel exit
        if (input!="exit")
        parse(input);
        else System.exit(0);

        while (input!="exit"){

            input = scan.nextLine();
            if (input=="\n") continue;

            parse(input);

        }
        System.out.print("shutting down\n");
        System.exit(0);

    }

   public static void copyFileUsingChannel(File src, File dest) throws IOException {
       FileChannel sourceChannel = null;
       FileChannel destinationChannel = null;
       try {
           sourceChannel = new FileInputStream(src).getChannel();
           destinationChannel = new FileOutputStream(dest).getChannel();
           destinationChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
          } finally {
              sourceChannel.close();
              destinationChannel.close();
          }
   }


    public static void ListContent (String path) throws IOException {
        Stream<Path> paths = Files.walk(Paths.get(path));
        paths.forEach(System.out::println);

         /*File f = new File(path); // current directory

         File[] files = f.listFiles();

             return files; */
     }

    public static boolean setCurrentDirectory(String directory_name)
    {
        boolean result = false;  // Boolean indicating whether directory was set
        File    directory;       // Desired current working directory

        directory = new File(directory_name).getAbsoluteFile();
        if (directory.exists() || directory.mkdirs())
        {
            result = (System.setProperty("user.dir", directory.getAbsolutePath()) != null);
        }

        return result;
    }



    public static void parse(String x) throws IOException {
        direc myDirec = new direc();
        myDirec.setDirecName(Paths.get(".").toAbsolutePath().normalize().toString());
        String firstArg;
        String[] command= x.split(" ");
        switch (command[0])
        {
            case "cp":
                if (command.length<3 || command.length>3) {
                    System.out.print("wrong count of args\n");
                    return;
                }

                else
                {
                    File index = new File(command[1]);

                    if (index.isDirectory()) {
                        String[] entries = index.list();
                    for (String s : entries) {

                        File currentFile = new File(index.getPath(), s);

                        String[] name= s.split("\\u0001");
                        String dest = command[2] + "\\"+ name[name.length-1];
                        File desired = new File(dest);

                        copyFileUsingChannel(currentFile,desired);
                    }}
                    else
                    { //not working properly
                        File currentFile = new File(index.getPath(), command[1]);
                        System.out.print(command[1]);
                        String[] name= command[1].split("\\u0001");
                        System.out.print("\n"+name[name.length-1]+"\n");
                        String dest = command[2] + "\\"+ name[name.length-1];
                        System.out.print(dest);
                        File desired = new File(dest);
                        copyFileUsingChannel(currentFile,desired);

                    }
                    index.delete();
                    System.out.print("successful, what else do you desire?\n");


                }
                break;
            case "pwd":

                if (command.length==1)
                   System.out.print( myDirec.getDirecName() );
                else if (command.length==3 && (command[2]==">>" || command[2]==">" )) //arg[0] pwd arg[1] el 3lama arg[2] el file
                {
                    if (command[2]==">>")
                    {File file = new File(command[2]);
                    FileWriter fr = new FileWriter(file, true);
                    BufferedWriter br = new BufferedWriter(fr);
                    br.write(myDirec.getDirecName());}
                    else
                    {
                        File file = new File(command[2]);
                        FileWriter fr = new FileWriter(file, false);
                        BufferedWriter br = new BufferedWriter(fr);
                        br.write(myDirec.getDirecName());}
                }
                else
                    System.out.print("wrong number of args");

                break;
            case "list":

                ListContent(command[1]);
                /*
                if (command.length==2) {
                  System.out.print(  ListContent(command[1]));
                }
                else if (command.length==3 && (command[2]==">>" || command[2]==">" )) //arg[0] pwd arg[1] el 3lama arg[2] el file
                {
                    if (command[2]==">>")
                    {File file = new File(command[2]);
                        FileWriter fr = new FileWriter(file, true);
                        BufferedWriter br = new BufferedWriter(fr);
                        br.write(ListContent(command[1]).toString());
                    }
                    else {
                        File file = new File(command[2]);
                        FileWriter fr = new FileWriter(file, false);
                        BufferedWriter br = new BufferedWriter(fr);
                        br.write(ListContent(command[1]).toString());
                    }
                }
                else
                    System.out.print("wrong number of args"); */

                break;

            case "clear":
                if (command.length>1) System.out.print("wrong number of args");
                else
                {
                for(int i = 0; i < 300; i++) // Default Height of cmd is 300
                    System.out.print("\n");


                }
                break;
            case "date":

                if (command.length>1) System.out.print("wrong number of args");
                else if (command.length==3 && (command[2]==">>" || command[2]==">" )) //arg[0] pwd arg[1] el 3lama arg[2] el file
                {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    if (command[2]==">>")
                    {File file = new File(command[2]);
                        FileWriter fr = new FileWriter(file, true);
                        BufferedWriter br = new BufferedWriter(fr);
                        br.write(dateFormat.format(date));}
                    else
                    {
                        File file = new File(command[2]);
                        FileWriter fr = new FileWriter(file, false);
                        BufferedWriter br = new BufferedWriter(fr);
                        br.write(dateFormat.format(date));}
                }
                else
                {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                System.out.println(dateFormat.format(date));
                }
                break;
            case "rmdir":
                if (command.length >1) System.out.print("wrong number of args");
                else {

                    File index = new File(command[1]);
                    String[] entries = index.list();
                    for (String s : entries) {
                        File currentFile = new File(index.getPath(), s);
                        currentFile.delete();
                    }
                    index.delete();
                }
                break;
            case "more":
                if (command.length >4) System.out.print("wrong number of args");
                else if (command.length==4 && (command[3]==">>" || command[3]==">" )) //arg[0] pwd arg[1] el 3lama arg[2] el file
                {
                    Path path = Paths.get(command[1]);
                    BufferedReader reader = Files.newBufferedReader(path);

                    String line = reader.readLine();

                    if (command[3]==">>")
                    {File file = new File(command[4]);
                        FileWriter fr = new FileWriter(file, true);
                        BufferedWriter br = new BufferedWriter(fr);
                        br.write(line+"\n");
                        while (line != null) { //bytba3 null fel a5er
                            line = reader.readLine();
                            br.write(line+"\n");
                        }
                    }
                    else
                    {
                        File file = new File(command[4]);
                        FileWriter fr = new FileWriter(file, false);
                        BufferedWriter br = new BufferedWriter(fr);
                        br.write(line+"\n");
                        while (line != null) { //bytba3 null fel a5er
                            line = reader.readLine();
                            br.write(line+"\n");
                        }
                    }
                }

                else
                {
                    Path path = Paths.get(command[1]);
                    BufferedReader reader = Files.newBufferedReader(path);

                    String line = reader.readLine();
                    System.out.print(line+"\n");

                    while (line != null) { //bytba3 null fel a5er
                        line = reader.readLine();
                        System.out.print(line+"\n");
                    }

                }
                break;
            case "cd": //change directory

                if (setCurrentDirectory(command[1])==true) {
                    myDirec.setDirecName(command[1]);


                    System.out.print("was successful\n"+"new direc is:\n"+ myDirec.getDirecName()+"\n");

                }

                else System.out.print("wrong num of args\n");


                break;
            case "help":
                if (command.length==1) {
                    System.out.print("cp" + "\t" + "copy files or directory\n");
                    System.out.print("mv" + "\t" + "move files or directory\n");
                    System.out.print("rm" + "\t" + "remove files or directory\n");
                    System.out.print("pwd" + "\t" + "display current directory\n");
                    System.out.print("cat" + "\t" + "concatenate file data to another file or to console\n");
                    System.out.print("clr" + "\t" + "clear screen\n");
                    System.out.print("cd" + "\t" + "change directory\n");
                    System.out.print("mkdir" + "\t" + "create directory\n");
                    System.out.print("rmdir" + "\t" + "remove directory\n");
                    System.out.print("more" + "\t" + "show file input on console\n");
                    System.out.print("args" + "\t" + "show avaialable commands with parameters\n");
                    System.out.print("help" + "\t" + "copy files or directoryshow job of available commands \n");
                    System.out.print("date" + "\t" + "show and edit date\n");
                    System.out.print("exit" + "\t" + "exit console \n");
                    System.out.print("> and >>" + "\t" + "print commands in a file\n");
                }
                else if (command.length==3 && (command[2]==">>" || command[2]==">"))
            {
                if (command[2]==">>")
                {File file = new File(command[2]);
                    FileWriter fr = new FileWriter(file, true);
                    BufferedWriter br = new BufferedWriter(fr);
                    br.write("cp" + "\t" + "copy files or directory\n");
                    br.write("mv" + "\t" + "move files or directory\n");
                    br.write("rm" + "\t" + "remove files or directory\n");

                }
                else {
                    File file = new File(command[2]);
                    FileWriter fr = new FileWriter(file, false);
                    BufferedWriter br = new BufferedWriter(fr);
                    br.write("cp" + "\t" + "copy files or directory\n");
                    br.write("mv" + "\t" + "move files or directory\n");
                    br.write("rm" + "\t" + "remove files or directory\n");
                }
            }
            else {
                System.out.print("wrong numbers of commands \n");
            }

                break;

            case ("exit"):
                System.exit(0);
                break;


            default: System.out.print("wrong command, use help or try again\n");




        }

    }


}
