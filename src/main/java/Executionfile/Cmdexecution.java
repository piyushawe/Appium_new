package Executionfile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cmdexecution {
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
            System.out.println("Exception occurs in stating the Process Builder");
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
                }
        }
            }
        }
    }