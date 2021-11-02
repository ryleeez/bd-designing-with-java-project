## Mastery Task 4: Time is Still Marching On

**Unlocks after**: 

* Milestone 1: Lesson 4, *Hashcode and Set*
* Milestone 2+: Lesson 5, *Map*

&nbsp;

### Milestone 1: Clean Up on Aisle 82
**Reviewer: Project Buddy**

IAD2 is the first FC to make arrangements to onboard with new polybag options, and they’ll be ready in two weeks. You
decided to have a look at the `PackagingDatastore` and noticed that IAD2 has A4 boxes (`CORRUGATE` boxes that are
20cm x 20cm x 20cm) added twice!

You could just remove the extra line from the initialization code in `PackagingDatastore`, but that information comes
from somewhere; the next time the list is changed (two weeks from now!) whoever updates the code might just re-insert
the duplicate. This is a perfect time to exercise Postel's Law (aka the Principle of Robustness):
"Be conservative in what you do, be liberal in what you accept from others".

Update the `PackagingDAO` (and any other necessary classes) to detect and ignore duplicate `FcPackagingOptions`.
"Duplicate" in this case means that the FC code is identical, and the `Packaging` has the same size and the same
material. Write unit tests for your updated code.

Let’s get a review. Make sure the `tct-task4-unique` workflow passes, make a commit with a message starting with
`[MT04][Unique]`, then submit a CR to your project buddy. As usual, once you get a ship-it, push your commit.

&nbsp;

### Milestone 2: Lookup on Aisle 82

To find the packaging options at an FC that can fit an item, our code must check every `FcPackagingOption` object,
which takes O(n) time. That sounds okay until you realize that *n* is the number of FCs multiplied by the number of
packaging options at each FC, so it’s really O(n*m) in disguise (where *n* is the number of FCs, and *m* is the number
of packaging options at each FC). Then you consider the numbers: Amazon currently has 75 FCs in North America alone,
each of which may have up to 100 different package types. That’s something like 7500 checks to find the
`PackagingOption`s that fit a shipment, and we make nearly 2 million shipments a day. That’s... well, that’s a lot of
checks.

Update the `PackagingDAO` so the `findShipmentOptions()` method only has to check the unique `Packaging`s for the one
provided `FulfillmentCenter`, instead of searching through all combinations. (Don’t look it up by its code; use the
`FulfillmentCenter` object.) Reduce the number of checks from from O(n*m) to O(m). 

There’s just one more milestone to go, so you can hold off on your CR a little longer.

&nbsp;

### Milestone 3: Setup on IAD2
**Reviewer: ATA-Unit2-Instructor-Reviewers**

The system is ready, and you’ve improved its correctness and efficiency. Add the new polybag options for IAD2. In
`PackagingDatastore`, you’ll see a collection of `FcPackagingOption`s named `fcPackagingOptions`.
In task 3, you had to update the `createFcPackagingOption` to create a `Box` instead of a `Packaging`. Now you’ll need
to provide a way to create a `PolyBag`, too. It’s up to you how you’d like to do this; just be certain to add two
`PolyBag` options to IAD2: one with 2,000cc volume, and one with 10,000cc volume.

When you’re done, the `tct-task4-polybag` workflow should pass. Submit a CR for a commit prefixed with `[MT04][Polybag]`
to the `ATA-Unit2-Instructor-Reviewer`.
Once you’ve gotten a Ship-It!, push your code and check your pipeline to verify that another test is passing.

**Exit Checklist**
- `rde wflow run tct-task4` passes
- Your change to remove the duplicate box has been approved and merged
- You've improved the runtime of finding a package to O(m), where m is the number of packages at a single FC
- You've added the new `PolyBag` options to IAD2
- An instructor has approved your `PolyBag` CR
