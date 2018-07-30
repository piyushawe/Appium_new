package Executionfile;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.*;

@CucumberOptions(
        features= {"src/Feature"}
        ,glue= {"Cucmber_test"}
        ,monochrome=true
        ,plugin= {"pretty","html:target/cucumber_html_report",
        "json:target/cucumber.json",
        "junit:target/cucumber.xml"}
        //  ,tags= {"@testing"}
)
//@Listeners(Cucmber_test.Listeners.class)
public class RunnerFile {
    private TestNGCucumberRunner testing;
    @BeforeClass
    @Parameters({"OS","platform","emulator_name"})
    public void cmd(String OS ,String platform,@Optional("null") String emulator_name)
    {
        if(OS.equalsIgnoreCase("windows"))
        {
            Cmdexecution.enterCmdCommandAndGetResult(new StringBuilder("adb devices"));
            if(Cmdexecution.emulator)
            {
                if(emulator_name==null) {
                    Cmdexecution.enterCmdCommandAndGetResult(new StringBuilder("emulator -list-avds"));
                }
                Cmdexecution.enterCmdCommandAndGetResult((new StringBuilder("adb")));
                Cmdexecution.enterCmdCommand(Cmdexecution.desiredstring);

            }
        }
        else if(OS.equalsIgnoreCase("mac"))
        {

        }else{
            System.out.println("Enter valid OS");
        }
    }
    @BeforeClass(alwaysRun = true)
    public void before(){
        System.out.println("This is Beforeclass");
        testing=new TestNGCucumberRunner(this.getClass());
    }
    @DataProvider
    public Object[][] data(){
        System.out.println("this is dataprovider");
        return testing.provideFeatures();

    }
    @BeforeTest


    @Parameters({"apkname","port","devicename"})
    @Test()
    public void startAppiumserverAndEmulator(String apkname,String port,String devicename)
    {
        SetCapabilities.setCapabilitiesAndroid(new StringBuilder(apkname),new StringBuilder(port),new StringBuilder(devicename));

    }

    @Test(dataProvider = "data")
    public void ruunnnn(CucumberFeatureWrapper cuc){
        System.out.println("This is Test");
        testing.runCucumber(cuc.getCucumberFeature());

    }
}

