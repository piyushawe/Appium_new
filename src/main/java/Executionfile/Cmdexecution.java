package Executionfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Cmdexecution {
    static boolean realdevice=false;
    static boolean emulator=false;
    static boolean simulator=false;
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

    public static StringBuilder enterCmdCommandAndGetResult(StringBuilder command) {
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
            } catch (IOException e) {
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
                            System.out.println(line);
                            StringBuilder abc = line;
                            abc = abc.replace("Installed as ", "");
                            System.out.print(abc.replace("platform-tools\\adb.exe", ""));
                        }
                        break;
                    }
                    case "adb devices":
                    {if (line.toString().endsWith("device")) {
                        //System.out.println(line);
                        StringBuilder lin=line;
                        String[] splitted=lin.toString().split("\\s");
                        devices.add(splitted[1]);
                        //System.out.println(splitted[1]);
                    }
                        break;
                }
        }

            }
            if(command.toString().equalsIgnoreCase("adb devices")) {
                if (devices.isEmpty()) {
                    emulator=true;
                }
                else{
                    realdevice=true;
                }
            }
        }
}
    }