package Executionfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Cmdexecution {
    static boolean realdevice=false;
    static boolean emulator=false;
    static boolean simulator=false;
    static StringBuilder desiredstring=null;
    static StringBuilder emulator_name=null;
    static ArrayList<StringBuilder> emulator_devices=new ArrayList<>();
   static List devices=new ArrayList();
    public static void enterCmdCommand(StringBuilder command)
    {try
    {
        Process p= Runtime.getRuntime().exec("cmd.exe /c start cmd /k \""+command.toString()+"\"");
    }
    catch (IOException e)
    {
        System.out.println("IOException occured on executing cmd command");
    }
    }

    public static void enterCmdCommandAndGetResult(StringBuilder command) {
        ProcessBuilder builder = new ProcessBuilder(
                "cmd.exe", "/c", command.toString());
        builder.redirectErrorStream(true);
        Process p = null;
        try {
            p = builder.start();
        } catch (IOException e) {
            System.out.println("Exception occurs in starting the Process Builder");
        }

        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        StringBuilder line=null;
        while (true) {
            try {
                line = new StringBuilder(r.readLine());
            } catch (IOException | NullPointerException e) {
                System.out.println("Exception occured on reading line");
            }
            if (line == null) {
                System.out.println("There is no data to print");
                break;
            } else
                switch (command.toString())
                {
                    case "ipconfig":
                    {
                        if (line.toString().contains("Installed as"))
                        {
                            System.out.println(line);
                            StringBuilder abc = line;
                            abc = abc.replace("Installed as ", "");
                            System.out.print(abc.replace("platform-tools\\adb.exe", ""));

                        }
                        break;
                    }

                    case "adb":
                    {if (line.toString().contains("Installed as")) {
                           System.out.println(line);StringBuilder abc = line;
                           String[] splitted= abc.toString().split("as ");
                           desiredstring=new StringBuilder(splitted[1]);
                           if(emulator_name==null) {
                            desiredstring = new StringBuilder("cd /d " + desiredstring.toString().
                                    substring(0, desiredstring.toString().
                                            indexOf("\\abd.exe")) + "&& emulator -avd " + emulator_devices.get(0));
                        }
                        else
                        {
                            desiredstring = new StringBuilder("cd /d " + desiredstring.toString().
                                    substring(0, desiredstring.toString().
                                            indexOf("\\abd.exe")) + "&& emulator -avd " + emulator_name);
                        }
                        }
                        break;
                    }
                    case "adb devices":
                    {if (line.toString().startsWith("device")) {
                        //System.out.println(line);
                        StringBuilder lin=line;
                        String[] splitted=lin.toString().split("\\s");
                        devices.add(splitted[0]);
                        //System.out.println(splitted[1]);
                    }
                        break;
                }
                    case "emulator -list-avds":
                    {
                        emulator_devices.add(line);
                    }
        }

            }
            if(command.toString().equalsIgnoreCase("adb devices")) {
                Iterator it=devices.iterator();
                while(it.hasNext())
                {
                    String s =(String)it.next();

                if (!(s.contains("emulator")))
                {
                    realdevice=true;
                }
                }
                if(realdevice==false)
                {
                    emulator=true;
                }
            }
        }
}
    }