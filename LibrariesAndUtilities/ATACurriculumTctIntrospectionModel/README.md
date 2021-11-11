#ATACurriculumTctIntrospectionModel
This package contains the coral model for a shared operation to execute tct test suites. The `ExecuteTctSuite` operation
accepts a `ExecuteTctSuiteRequest` which wraps a `TctSuiteId`. The response returns a report detailing each test run - 
containing its id, result, and an error message in the case of a failure. 

This package is intended to be depended upon by another coral model package. It should be added to the dependencies 
section of the `Config` file. This has been tested with both a Lambda and an ECS service.
```
dependencies = {
    1.0 = {
        ATACurriculumTctIntrospectionModel = 1.0;
    };
};
```

The operation can then be added to the service definition.
```
<service name="ATACurriculumKindlePublishingService">
  <operation target="com.amazon.ata.test.tct.introspection.types#ExecuteTctSuite" />
</service>

```
In order to define this new API for your service you will need to add a dependency on the code package 
[ATACurriculumTctIntrospection](https://code.amazon.com/packages/ATACurriculumTctIntrospection/blobs/mainline/--/README.md)
and follow the instructions in the README.
