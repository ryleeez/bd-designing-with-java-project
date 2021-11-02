## Mastery Task 1: Testing the waters

**Unlocks after:**

* Milestone 1+: Lesson 1, *Remote Debugging and Logging*


&nbsp;

### Milestone 1: Setup IntelliJ to listen to a debug port

As we saw in preparedness task 1, there is no longer a main method in this project that we can just click on to run
from within our IDE... or to debug... So what do you do if you need to attach a debugger to your project? Now that the
code is not running directly in IntelliJ, we need to create a way for the service and our laptop to communicate about 
which line of code is currently executing and what the value of variables currently are. Then we’ll be able to attach a
debugger and inspect this information. We will accomplish this using a debug port. We’ll dive into ports in the next
unit, so for now we’ll use a port without talking about how it works. Just know that we will be using port "5005" to
pass the debug information back and forth.

First let’s give IntelliJ a way to listen to port 5005. In the IntelliJ menu select "Run" > "Edit Configurations..."
This should pop up a "Run/Debug Configurations" menu. In the top left corner of the menu select the "+" button. An "Add
New Configuration" dropdown should appear. From that dropdown select "Remote." We’re using "Remote" here since the code
is running remotely from our local IntelliJ.

Select a "Name" for this configuration that makes sense to you; We went with "Sustainability service remote debugger."

Ensure the "Debugger mode:" reads "Attach to remote JVM," "Host:" is specified as "localhost," and port says "5005."
Your configuration should look like the image below. Go ahead and select "Apply" and "OK." You may wonder what it means
that the host we are listening to for this debug information is "localhost." For now, just know that the service is
running in its own container, something called a docker container. These containers can run on other computers and you
can connect to them over the internet, but in this case the container is actually running on your computer. "localhost"
here actually means to listen to your own computer. But this container is running the lambda outside of IntelliJ.

&nbsp;

![IntelliJ Debuuger Configuration](../../src/resources/IntelliJDebuggerConfiguration.png)

&nbsp;

### Milestone 2: Debug interacting with the API

Let’s call the `PrepareShipment` API again, similar to what we did in preparedness task 1, but this time let’s add a
break point. Use the JSON below for your `mounts/events/prepare_shipment.json`:

```
{
  "queryStringParameters": {
    "fcCode": "IND1",
    "itemAsin": "random",
    "itemDescription": "random item",
    "itemLength": "5.0",
    "itemWidth": "5.0",
    "itemHeight": "5.0"
  }
}
```


In the `ShipmentService`, let’s add a breakpoint to the first line of the `findShipmentOption` method.

To call the API, listening with the debugger, we need to run a new workflow: `debug-prepare-shipment`. Let’s run that
now. In your terminal, it should appear to hang part way through the workflow after printing out the lines below:

```
Invoking com.amazon.atacurriculumsustainabilityshipmentservicelambda.activity.ShipmentServiceLambdaEntryPoint::handleRequest (java8)

Fetching lambci/lambda:java8 Docker container image......
Mounting /var/task/sam as /var/task:ro,delegated inside runtime container
```


When a command line just sits there appearing to do nothing, we say that it "hangs."
This is where it’s waiting for us to attach our debugger. Here comes our run configuration to the rescue! If you click
on the "Run" menu, you may see something that says "Debug '&lt;title you named your run configuration>.'" For example,
ours says "Debug 'Sustainability service remote debugger.'" If you don’t see that option, select "Debug...". From there
you should see an option with your run configuration name. Once you click on either of these, you should see the
following appear in the IntelliJ Console:

```
Connected to the target VM, address: 'localhost:5005', transport: 'socket'
```

And after a moment your should see the the debugger menu you are familiar with appear. Explore the current values of
the variables and step around in the code a bit.

Once you’re done exploring, you’ll either need to click the stop or resume button in the debugger. If you return to
your terminal, you should see the output of the API in the same format as when you ran `rde wflow run
invoke-prepare-shipment`.

&nbsp;

### Milestone 3: Debug the API to find a mystery bug
**Reviewer: ATA-Unit2-Instructor-Reviewers**

We think someone may have accidentally added a bug with some special logic. For the following request:

```
{
  "queryStringParameters": {
    "fcCode": "IND1",
    "itemAsin": "3141592",
    "itemDescription": "DEFAULT",
    "itemLength": "5.11",
    "itemWidth": "5.11",
    "itemHeight": "5.11"
  }
}
```

we expect the order to be packaged in 10x10x10 CORRUGATE packaging. Can you fix the code?

To reproduce the issue we’ve written a test. Let’s start by running the `tct-task1` workflow to run this test
and see what happens...

It seems to be failing with a very *mysterious* error message...

```
FAILED: validPrepareShipment_prepareShipment_returnValidShipmentOption
java.lang.AssertionError: Mysterious error message...
        at org.junit.Assert.fail(Assert.java:93)
        at org.junit.Assert.assertTrue(Assert.java:43)
        at org.junit.Assert.assertNotNull(Assert.java:526)
        at com.amazon.atacurriculumsustainabilityshipmentservicelambda.mastery.one.MasteryTaskOneTests.validPrepareShipment_prepareShipment_returnValidShipmentOption(MasteryTaskOneTests.java:36)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.testng.internal.MethodInvocationHelper.invokeMethod(MethodInvocationHelper.java:85)
        at org.testng.internal.Invoker.invokeMethod(Invoker.java:639)
        at org.testng.internal.Invoker.invokeTestMethod(Invoker.java:816)
        at org.testng.internal.Invoker.invokeTestMethods(Invoker.java:1124)
        at org.testng.internal.TestMethodWorker.invokeTestMethods(TestMethodWorker.java:125)
        at org.testng.internal.TestMethodWorker.run(TestMethodWorker.java:108)
        at org.testng.TestRunner.privateRun(TestRunner.java:773)
        at org.testng.TestRunner.run(TestRunner.java:623)
        at org.testng.SuiteRunner.runTest(SuiteRunner.java:357)
        at org.testng.SuiteRunner.runSequentially(SuiteRunner.java:352)
        at org.testng.SuiteRunner.privateRun(SuiteRunner.java:310)
        at org.testng.SuiteRunner.run(SuiteRunner.java:259)
        at org.testng.SuiteRunnerWorker.runSuite(SuiteRunnerWorker.java:52)
        at org.testng.SuiteRunnerWorker.run(SuiteRunnerWorker.java:86)
        at org.testng.TestNG.runSuitesSequentially(TestNG.java:1185)
        at org.testng.TestNG.runSuitesLocally(TestNG.java:1110)
        at org.testng.TestNG.run(TestNG.java:1018)
        at com.amazonaws.gigahydra.libs.HydraTestPlatformDefaultTestNGTestRunner.runTestNGTest(HydraTestPlatformDefaultTestNGTestRunner.java:125)
        at com.amazonaws.gigahydra.libs.HydraTestPlatformDefaultTestNGTestRunner.call(HydraTestPlatformDefaultTestNGTestRunner.java:82)
        at com.amazonaws.gigahydra.libs.HydraTestPlatformDefaultTestNGTestRunner.call(HydraTestPlatformDefaultTestNGTestRunner.java:32)
        at java.util.concurrent.FutureTask.run(FutureTask.java:266)
        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
        at java.lang.Thread.run(Thread.java:748)


===============================================
    IntegrationTests
    Tests run: 1, Failures: 1, Skips: 0
===============================================


```

&nbsp;

To help us figure out what is actually going on, let’s debug the service! For each of the TCTs there will be two
workflows - one to run the test and the second to run the test listening for the debugger. To look up the name of any
workflow, including the debug workflows, you can view the `definition.yaml` file. Look for the section of the file with
the header "workflows:" Let’s run the debug workflow for this
task: `debug-task1`. The workflow should hang after you see the following message. This is the message you
should look for to attach the debugger when you want to debug the service with the input from a TCT:

```
+ export RDE_APP_NAME=HydraTestApp
+ RDE_APP_NAME=HydraTestApp
+ /home/deceneu/run-hydra-tests
```

You’ll notice that this is a different message than last time. When you want to debug the API using the
`debug-prepare-shipment` workflow, you will look for `Mounting /var/task/sam as /var/task:ro,delegated inside runtime
container` to attach the debugger. When you want to debug the service with a TCT triggering the service to start, you
will look for `+ /home/deceneu/run-hydra-tests` to attach the debugger.

Once the debugger is attached, determine what is causing this mysterious error message. It may feel silly...
Remove this code and try rerunning the the RDE workflow. Once the RDE workflow is passing, create a CR for your code
change. Both the commit and the CR title must begin with `[MT01]`. Send the code review to the
`ATA-Unit2-Instructor-Reviewers` team.

**Exit Checklist**
- `rde wflow run tct-task1` passes
- You've set up a remote debugging configuration in IntelliJ
- You've run and attached a debugger to the `PrepareShipment` API
- You've found the root cause and fixed the mysterious error message
- Your CR for the fix has been approved by an instructor and pushed
