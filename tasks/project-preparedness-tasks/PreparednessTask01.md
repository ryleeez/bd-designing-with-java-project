## Project Preparedness Task 1: Use the Source

### Milestone 1: Setup your workspace

Since we’re starting a new unit, let’s make sure we have the latest version of all of the build tools we use. Run
`toolbox update` in your terminal to ensure the latest versions are installed. If any tool does not say it is the latest
version you can update it by running `toolbox update <toolname>`. For example: `toolbox update rde`.

Create a local Brazil workspace using the `ATACurriculum-C2020July/SustainabilityShipmentService-<alias>` version
set. Checkout the `<Alias>ATACurriculumSustainabilityShipmentServiceLambda` Brazil package in this new workspace.

You will see the following message while creating your workspace. This is expected and you do not need to
use the `brazil ws use --platform` command.

```
Detected that version set 'ATACurriciulum-<cohort>/Unit2-SustainabilityShipmentService-<alias>' only builds for platform 'AL2012'.
Setting workspace platform to AL2012.
To change this setting, use the `brazil ws use --platform` command.
```

Don't forget you can reference the steps from the
[Obtain an Editable Copy of a Code Package How To](https://w.amazon.com/bin/view/Amazon_Technical_Academy/Internal/HowTos/ObtainAnEditableCopyOfACodePackage/).

In your new workspace, you will need to run `brazil setup platform-support`. It will tell you that it is configuring
the workspace to use ‘overlay’ mode, which requires cleaning the workspace and ask if you want to proceed. Enter `y`.
We haven’t built anything yet so there is nothing to clean!  This has completed correctly if you see the message below.

```
The workspace is configured to use 'overlay' mode.

You must now re-build all your packages, which can also be done via running:

    brazil-recursive-cmd --allPackages brazil-build
```

You don’t need to rebuild anything now (don’t run `brazil-recursive-cmd`), because we are about to build it all for the
first time!

Now inside your `<Alias>ATACurriculumSustainabilityShipmentServiceLambda` Brazil package, run `rde stack provision` to
complete setting up your workspace. You may see an error: `could not access Midway credentials, please run 'mwinit -o'`
You will often see this error if it has been a while since you've run any `rde` commands. Run `mwinit -o` on your
terminal. Follow the prompts to enter your midway pin and to do a long press of your yubikey.

Unlike the Unit 1 project you should only have the one package you will be modifying,
`<Alias>ATACurriculumSustainabilityShipmentServiceLambda`, in your Brazil workspace.

To verify everything is working correctly let’s go ahead and run the default RDE workflow, `rde wflow run`. This
workflow will build the code and start your new service. The first time it runs, this process may take a while - up to 
20 minutes. If your workflow run doesn’t end with the line below (timestamp may vary, that’s okay :) ), please open a 
CQA item with what you think might be the problem, or come to office hours to get help.

```
[13:43:48-I] Finish running workflow [OK]
```

Unfortunately for all RDE workflows run in this project you will see warning message below. This is expected and you do
not need to take any action.

```
If you are exposing Java debug ports form your RDE application(s) please ask for an exception to avoid getting SEV2'd by InfoSec:
https://sim.amazon.com/issues/ASOC-CM-PUB-2
```

Summary of your Unit 2 artifacts:
- **Pipeline:** https://pipelines.amazon.com/pipelines/<Alias>ATACurriculumSustainabilityShipmentServiceLambda
- **Version Set:** https://code.amazon.com/version-sets/ATACurriculum-<cohort_name>/SustainabilityShipmentService-<alias>
- **Package:** https://code.amazon.com/packages/<Alias>ATACurriculumSustainabilityShipmentServiceLambda

&nbsp;

### Milestone 2: Setup your IntelliJ project

Open the workspace you created in IntelliJ. Don't forget you can reference the steps from the
[Open Your Workspace in IntelliJ](https://w.amazon.com/bin/view/Amazon_Technical_Academy/Internal/HowTos/Open_Your_Workspace_In_IntelliJ/)
How To.

Let’s ensure everything is also configured in your IDE to build correctly. In IntelliJ click on the "Build" menu and
then select "Build Project."

You may see an Error message pop up saying the SDK is not specified. If you do, select OK. The Project Structure menu
should open. If it doesn’t select "File" > "Project Structure..." In the Project SDK drop down select "1.8". Ensure the
Project language level drop down reads "8 - Lambdas, type annotations etc." Click "Apply" and "OK." Once again select
the "Build" > "Build Project" menu option.

You may see an Error message pop up saying the output path is not specified. Open the Project Structure menu; select
"File" > "Project Structure..." For "Project compiler output", select the folder icon in the text box. Select the `out`
folder in the root of your unit 2 project workspace. If there isn’t an `out` folder, select your package folder and
then in the text box append `/out`. Once again select the "Build" > "Build Project" menu option.

&nbsp;

### Milestone 3: Interacting with the SustainabilityShipmentService

This project doesn’t have a main method we can run like the Unit 1 project had, nor does it have a CLI workflow we can
run. So how do we interact with the project when we want to try different inputs and do some manual testing?

Remember, all we really need to do is give our program inputs and receive it’s outputs. We no longer do that through an
interactive CLI, but by specifying the inputs, sending them off to the lambda running our code, and waiting for the
outputs to come back.

First let’s look at how we specify the inputs. When we call
`PrepareShipment` we need to specify the `fcCode` of the fulfillment
center we want to ship the order from, and the item to ship. The item is
defined by its ASIN, description, length, width, and height. Take a look
at the `mounts/events/prepare_shipment.json` file. Can you see how this
file may map to those necessary inputs? This file is in a format called
JSON. [What is JSON?](https://developers.squarespace.com/what-is-json)
covers the basic syntax of JSON, what is a key, and what is a value.

Update the values to find out what kind of packaging we would use to ship the item with ASIN "12345", a description
of "Table lamp", of size 15x10x7 cm (length x width x height), out of fulfillment center "ABE2." Now that we’ve updated
the inputs we need to call the service. We can do this by running an RDE workflow!

```
rde wflow run invoke-prepare-shipment
```

This calls our service running in the lambda with the JSON you’ve just specified. The outputs from our service (the
item, fulfillment center, and packaging the item will be shipped in) are printed out to the screen as a part of the RDE
workflow. The response is made up of a status code and either a body or an error type and message. We’ll get more into
this in a later unit, but a 200 status code means a successful call, a 400 status code means there was an error with
the request (the sender of the request has done something incorrect), and a 500 status code means there was an error
with the service.

Paste the output from this call (the status and either the body or the error type and message) into a file
`src/resources/ppt1-response1.txt`. 

What if the lamp is so big that there are no packages that can fit it... say
100x100x100 cm? Update the `prepare_shipment.json` file with the new size, and rerun the rde workflow. Paste the output
into `src/resources/ppt1-response2.txt`. This one may appear slightly strange to you, but don’t worry. We’ll validate
the response in our next milestone. 

Last one! Let’s send a malformed input. Replace the `fcCode` in `prepare_shipment.json` with the empty string `""`. 
Paste the output into `src/resources/ppt1-response3.txt`. We’ll be submitting these changes for CR in our final 
milestone.

&nbsp;

### Milestone 4: Finally the tests!
**Reviewer: ATA-Unit2-Instructor-Reviewers**

One thing we can still count on are our PPT and TCT tests! Run the `preparedness-task1` RDE workflow to see if the
outputs you’ve pasted are correct. Once the workflow is passing, put out a CR to the `ATA-Unit2-Instructor-Reviewers` 
team. The title of your commit and CR must begin with `[PPT01]`. Do not commit the changes to  your
`mounts/events/prepare_shipment.json` file. You’ll change this file locally to run different manual tests, but you’ll
never need to cr or commit these changes. Once you have a ship it, push your change and watch your code flow through
your brand new pipeline! 

&nbsp;

**Exit Checklist**
- You have read the the project `README`
- You are able to successfully run `rde wflow run`
- You have set up your project in IntelliJ
- You understand what a JSON key and value are
- You've called the service with three sets of input, storing the responses in the appropriate files
- `rde wflow run preparedness-task1` passes
- Your CR adding the response files is approved by an instructor and pushed
