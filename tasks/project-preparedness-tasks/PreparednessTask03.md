## Project Preparedness Task 3: The Lay of the Land

**Reviewer: Near-peer Reviewer**

To get more familiar with how the code base accomplishes the needs of the sustainability packaging service, let’s
create a a couple diagrams, a class and a sequence diagram. Remember that UML diagrams are for discussing mental models 
of software, and there’s a lot of infrastructure here that you won’t need to worry about. In particular, anything in 
the `activity` Java package can be considered part of a well-tested framework that isn’t relevant to your mental model: 
it’s code you don’t need to modify and can simply accept as working. You also don’t need to include the `App` class as 
a part of your diagrams.

First let's create the class diagram. The package picking logic that you’ll be dealing with actually begins in the 
`ShipmentService`, so that’s a good place to start your class diagram. Your diagram should include the classes in the 
following java packages:
- `com.amazon.atacurriculumsustainabilityshipmentservicelambda.dao`
- `com.amazon.atacurriculumsustainabilityshipmentservicelambda.exceptions`
- `com.amazon.atacurriculumsustainabilityshipmentservicelambda.service`
- `com.amazon.atacurriculumsustainabilityshipmentservicelambda.types`

You may remember some conventions from
[the UML Introduction, Part 1](https://amazon.awsapps.com/workdocs/index.html#/document/8726bc13715701cd88e4c8ca89fb233232f0b76122678fcb036cdd66678fb88c)
doc, such as omitting constructors and getters, and displaying private members. Keep in mind that these are
**conventions**, not rules; if including a getter helps you reason about the code, or makes a discussion with a peer
more fruitful, do it! Notes can also be helpful, especially when an arrow or class member is insufficient to
communicate something subtle.

Create a new file in the `src/resources` directory called `sustainable_packaging_CD.puml` and add the plant uml source 
code to the file.

Next, let's better understand the interactions within the system. Build a sequence diagram showing the flow through the
service starting with a request made for a packaging option in the `ShipmentService`. Your diagram should
show the inputs and outputs of each method in the process.

If you need a review on sequence diagrams, watch this video from the last unit on
[how to make a UML sequence diagram](https://www.youtube.com/watch?v=pCK6prSq8aw). You do not need to create a
LucidChart account. (9 mins). As a starting place here is the
[plantuml for a simplified version of the last project’s sequence diagram](https://plantuml.corp.amazon.com/plantuml/form/encoded.html#encoded=bLJBQiCm4BphAtHi3_c1K4BI1Df0Iqf3Fq1OIo9gMKPQ1_ptrKgoBUNLzkI7PcPdrhjUEXGMErqp9Yeqbdyvi5mutlahQ_sPgbGh6kGlW1_MQENWLJcFx9_xdPLWjt9LeJefx8acqdr13RYDC0GmT5-S9jNDQaVdRNowvB-BHhLTBL2PPjNXqLXVPbqhy03IrQBWRA1M9x1zKj-3FQaA9hni7MCKaiyMesi-vuURCHzClCxv7grg3eyiq0J2b9gDIburuAFa7Mvi28jWOcaHEUlErzTWG_c_kA0M47kMrGwiJt1TZKn20HrAbla3eO16Vtcmz8rIj2htkKNGLyqDtvKDv9jHIMA86zHIv5lAUUp9MHQSc1VXVxNntuvVBNslx4GGKd8y0dT7rMgQJqbuDE7bjSRvuOsEpwRyFssZ1I2TqdiIods7JHcBbzFovdVaAN4MEMbqdSzDHcn5zJs075UD3rkkNSaCYpe_XsJrfWrSGYFfV_O3).
The sequence diagram for this project should be even simpler than this example.

There are a few things not available in this example that you may or may not need:

* alt/else
    * This is used if there are two potential flows in the sequence. You can see an example of this in the video. Here
    is a code snippet of how the
    [PlantUML for alt/else](https://plantuml.corp.amazon.com/plantuml/form/encoded.html#encoded=TL7DRi8m3BxxAJvnvmKu863YK26sQR3ThTH8OIMmnAt4syzk2n9ekoIElpzRMHJbhEsf8UAWvupF8XbSqDfD5yDYY1TEYkL-wttMqqDxikBqZPtaJlB42QKl_-0iGpYtINiI3pMHns0wTrlCK2LxAbuurpvEx5YbJoJ7ZJQO6_DBSZnSdvXZD76Zg0u81Y8MTDp4ccZKJkzfBiRRvkRTZpB3X_oqKXJlrIkHD4NWnt_oAWtkjJnFyUYwFaeO5oD9zMtnkvcbOPrKiXYsC8P_oIy0)
    may look.

* opt
    * This is used if there is a path the code will flow through only in certain cases. Here is a code snippet of how
    the [PlantUML for opt](https://plantuml.corp.amazon.com/plantuml/form/encoded.html#encoded=VP11Zi8m34NtFeMNmw8Nw09H19hf2YJ40QB6qx6K9fNZ0hqzIOk0BeQL8-Lz__-zYMf4Xyu1c4Q3u3cIe8auf0bz-kE6U-CLlqcF4ZgEzCCnaUDsF8WbgMtLA5zOnompZ_T2Ed6xwf01Qm90Nex5-abZYUs_wx_2F4iygR1lLp39ZDBB3KAlMF_Y0qMEw0StbveTYcGtsPQeVnAkM3j7hN6Lj4D7NlUtXdhbu078stVXEcUU4tw8adjbhbZMcZg0fGK7csIHRtu7)
    may look.
    
* loop
    * This is used if you need to repeat a path many times. Here is a code snippet of how the
    [PlantUML for loop](https://plantuml.corp.amazon.com/plantuml/form/encoded.html#encoded=VP91QiCm44NtEiKicuKlO8g9IqDh2AHGUW1XJvq1MHBIkAblNzbI4ikqNKbc_jUy-UEzP-bu79GGicNZuCEZ0-bX3AUmeKOjMQaPNf3Vd1d8uolve9oUffFhq3LTtJ9z4K-pJQPxPbrqp_Lf5fZvdbNJgwbkuvSGCm0KrUr5AA7_4-71nBE4TtQa-vrOR9BnJYICPMW-8Kc1FEXHnS5ZXoAqMzgMm6TdBj0eXRrKjUl70JKVlbkqJ4OBWRfR6wluQcJy1oPE7xL5FjmoYUXmenNA60i7sPwZec4SWBId3WCa9bk6aYBCO8BhLw1ht5VF1WgE8UZ757grfhljQPD4Yh24V5DhwtqZ-s2U_xqV)
    may look.

Create a new file in the `src/resources` directory called `sustainable_packaging_SD.puml` and add the plant uml source 
code to the file. 

Run `rde wflow run preparedness-task3` and ensure it passes, create a commit and submit a CR with the diagrams to your 
near-peer reviewer. The title of your commit and CR must begin with `[PPT03]`. Please include links to your diagrams
in the plantuml tool in the details section of your CR. This will allow your near-peer reviewer to view your rendered
diagrams alongside the uml.

&nbsp;

**Exit Checklist**
- You’ve added your class diagram plant uml to `src/resources/sustainable_packaging_CD.puml`
- You’ve added your sequence diagram plant uml to `src/resources/sustainable_packaging_SD.puml`
- `rde wflow run preparedness-task3` passes
- Your CR adding class and sequence diagrams is approved by your near-pear reviewer and pushed
