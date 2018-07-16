package Executionfile;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SetCapabilities {
    private SetCapabilities()
    {}
    private SetCapabilities sc =new SetCapabilities();
    static AndroidDriver ad=null;
    public static void setCapabilitiesAndroid(StringBuilder apkname, StringBuilder port, StringBuilder devicename) {
        StringBuilder ipaddress=Cmdexecution.enterCmdCommandAndGetResult(new StringBuilder("ipconfig"));
        File fl=new File(apkname.toString());
        DesiredCapabilities dc= new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.DEVICE_NAME,devicename);
        dc.setCapability(MobileCapabilityType.APP,fl.getAbsolutePath());
        try{
            ad= new AndroidDriver(new URL(ipaddress.toString()),dc);
        }
        catch (MalformedURLException e)
        {
            System.out.println("ip address is not proper");
            System.exit(-1);
        }
    }
}
