# Test running instructions  
## CommandLine  
To run the whole test on beta (see more available targets in the build.xml):  
```sh
 brazil-build run-tests-beta
```
To run a specific test:  
```sh
 brazil-build run-tests-beta -Dmethods=com.amazon.testudodemo.test.integration.CreateBeerIntegrationTests.createBeerWithValidParameters
```

In order to debug the Regional Test on Demand scripts locally:

```sh
 # Generating the runtimefarm (http://w?BrazilBuildSystem/Tools/BrazilPath/Recipe#.2A.runtimefarm)  
 brazil-build clean && brazil-build && brazil-bootstrap --environmentType test-runtime;
 
 # Creating a symlink to the runtimefarm
 ln -s -f ../../env/ATACurriculumSustainabilityShipmentServiceTCTs-1.0/test-runtime;
 # Running the tests
 ./test-runtime/test/TodScripts/run_testng_suite.sh --domain test --realm USAmazon --testngxml src/testng.xml
```

## IDE  
1. Before using the IDE run a *brazil-build* command in your workspace. 
2. Create a new run configuration in the IDE.  
  - ***Eclipse:*** Run>Run Configurations>TestNG  
  - ***Idea:*** Run>Edit Configurations>TestNG (the settings can be done either in the default configuration or in a specific configuration)  
    - `Important`: to assure the correct relative paths set in the TestNG configuration:  
       **Working directory**=/path_to_workspace/src/ATACurriculumSustainabilityShipmentServiceTCTs or $MODULE_DIR$ (if the module dir is correctly set) 
3. Set the following common IDE settings in the TestNG run configuration:  
 ```
 -Ddomain=test  
 -Drealm=USAmazon  
 -Dconfig.root=build/private/fake-apollo-root  
 -DCORAL_CONFIG_PATH=build/private/fake-apollo-root/coral-config
 -Dlog4j.configurationFile=src/log4j2.xml
 -Djava.library.path=build/private/fake-apollo-root/lib
 ```
4. Only if tests use AAA - depending on your use case set *one* of the following Environment Variables:
 ```
 For AAA workspace support set: LD_LIBRARY_PATH=build/private/fake-apollo-root/lib
 For AAA with shim environment set: APOLLO_ACTUAL_ENVIRONMENT_ROOT=/apollo/env/AAAEnvironment
 ```
5. Select to run the tests from *src* folder or a specific test classs.
  
## Run tests in pipelines  
Running tests in pipelines is possible using approval workflows. More details on: http://w?Pipelines/ApprovalWorkflows  

### Test On Demand (ToD)  
Set:  
**Build Target:** run-tests-beta  
You can use any other target from the build.xml as a build target.  

### Regional ToD  
**Package Major Version:** use this package  
**Script Name:** test-runtime/test/TodScripts/run_testng_suite.sh  
**Script Arguments:** --testngxml src/testng.xml --domain test --realm USAmazon  

**Details on how it works:** the shell script from the *TestudoTodScripts* dependency package will replicate the run logic from the build.xml without using brazil (which is not available in prod fabric). More details about why this is needed can be found here: w/?QualityToolsTeam/CorpProdSplit/Onboarding.  