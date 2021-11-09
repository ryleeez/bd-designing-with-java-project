package com.amazon.ata.integration.test;

import com.amazon.bones.conduit.ConduitCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.client.builder.AwsSyncClientBuilder;
import com.amazonaws.services.cloudformation.AmazonCloudFormation;
import com.amazonaws.services.cloudformation.AmazonCloudFormationClientBuilder;
import com.amazonaws.services.cloudformation.model.DescribeStacksResult;
import com.amazonaws.services.cloudformation.model.Output;
import com.amazonaws.services.cloudformation.model.Stack;
import com.aws.rip.RIPHelper;
import com.aws.rip.models.region.Region;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import java.util.Optional;

/**
 * A parent integration test class for any integration tests making a connection to a lambda-based NAWS service.
 * This class holds all of the logic to gather the configuration necessary to connect with the service from the cloud
 * formation template.
 *
 * If you are writing an integration test for a lambda NAWS service, extend this class and in a @BeforeClass method,
 * call getServiceClient with the appropriate AwsClientBuilder,
 * e.g. ATACurriculumMusicPlaylistServiceClientBuilder.standard()
 */
public abstract class LambdaIntegrationTestBase {

    private static final String SAM_LOCAL_ENV_VAR = "AWS_SAM_LOCAL";
    private static AWSCredentialsProvider awsCredentialsProvider;

    private static String awsAccountId;
    private static String domain;
    private static String realm;
    private static String user;
    private static String endpointOverride;
    private static Boolean useCloudStack;
    private static String cloudFormationStackName;

    private final Logger log = LogManager.getLogger(getClass());

    private String stackName;

    // CHECKSTYLE:OFF:HiddenField
    /**
     * Set parameters.
     * @param awsAccountId aws account id
     * @param domain domain
     * @param realm realm
     * @param user user
     * @param endpointOverride endpoing to override
     * @param useCloudStack use cloud stack
     * @param cloudFormationStackName Override stack name with test parameters if necessary
     */
    @Parameters({
            "awsAccountId", "domain", "realm", "user", "endpointOverride", "useCloudStack", "cloudFormationStackName"
    })
    @BeforeSuite
    public void setup(final String awsAccountId,
                      final String domain,
                      final String realm,
                      @org.testng.annotations.Optional() final String user,
                      @org.testng.annotations.Optional() final String endpointOverride,
                      @org.testng.annotations.Optional() final Boolean useCloudStack,
                      @org.testng.annotations.Optional() final String cloudFormationStackName) {
        setAwsAccountId(awsAccountId);
        setDomain(domain);
        setRealm(realm);
        setUser(user);
        setEndpointOverride(endpointOverride);
        setUseCloudStack(useCloudStack);
        setCloudFormationStackName(cloudFormationStackName);
    }
    // CHECKSTYLE:ON:HiddenField

    /**
     * Returns a client for the ATACurriculumMusicPlaylistService.
     *
     * @param <S> type of service client to be returned
     * @param <B> type of builder to use to build the service client
     * @param builder Builder for service client
     * @param cloudFormationStackName The name of the cloud formation stack for this project
     * @return ATACurriculumMusicPlaylistService, ready for service
     * @throws Exception If error creating endpoint from parameters
     */
    protected <S, B extends AwsSyncClientBuilder<B, S>> S getServiceClient(final B builder,
                                                                           final String cloudFormationStackName)
                                                                           throws Exception {
        this.stackName = LambdaIntegrationTestBase.cloudFormationStackName == null
                ? cloudFormationStackName : LambdaIntegrationTestBase.cloudFormationStackName;
        final String endPoint = getEndpoint(awsAccountId, domain, realm, user);
        log.info("Using the endpoint " + endPoint);

        if (StringUtils.isBlank(endPoint)) {
            throw new Exception("Unable to determine endpoint, did you specify your domain and realm?");
        }

        builder.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, realm))
               .withCredentials(getCredentialsProvider(awsAccountId, domain, realm));
        return builder.build();
    }

    private static String getAwsAccountId() {
        return awsAccountId;
    }

    private static String getDomain() {
        return domain;
    }

    private static String getRealm() {
        return realm;
    }

    private static String getUser() {
        return user;
    }

    private static String getEndpointOverride() {
        return endpointOverride;
    }

    private static Boolean getUseCloudStack() {
        return useCloudStack;
    }

    private static void setAwsAccountId(String awsAccountId) {
        LambdaIntegrationTestBase.awsAccountId = awsAccountId;
    }

    private static void setDomain(String domain) {
        LambdaIntegrationTestBase.domain = domain;
    }

    private static void setRealm(String realm) {
        LambdaIntegrationTestBase.realm = realm;
    }

    private static void setUser(String user) {
        LambdaIntegrationTestBase.user = user;
    }

    private static void setEndpointOverride(String endpointOverride) {
        LambdaIntegrationTestBase.endpointOverride = endpointOverride;
    }

    private static void setUseCloudStack(Boolean useCloudStack) {
        LambdaIntegrationTestBase.useCloudStack = useCloudStack;
    }

    private static void setCloudFormationStackName(String cloudFormationStackName) {
        LambdaIntegrationTestBase.cloudFormationStackName = cloudFormationStackName;
    }

    // CHECKSTYLE:OFF:HiddenField
    private String getEndpoint(final String awsAccountId, final String domain, final String realm, final String user) {
        if (!StringUtils.isEmpty(getEndpointOverride())) {
            return getEndpointOverride();
        }
        try {
            final Optional<Output> apiId = getOutput(awsAccountId, domain, realm, user, "ApiId");
            final Optional<Output> apiStage = getOutput(awsAccountId, domain, realm, user, "ApiStage");
            return String.format("https://%s.execute-api.%s.amazonaws.com/%s/",
                    apiId.get().getOutputValue(), realm, apiStage.get().getOutputValue());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to determine your service's internal dns name.", e);
        }
    }
    // CHECKSTYLE:ON:HiddenField

    // CHECKSTYLE:OFF:ReturnCount
    // CHECKSTYLE:OFF:HiddenField
    private AWSCredentialsProvider getCredentialsProvider(final String awsAccountId,
                                                          final String domain,
                                                          final String realm) {
        if (null != this.awsCredentialsProvider) {
            return this.awsCredentialsProvider;
        }
        try {
            if (null == System.getenv("AWS_LAMBDA_FUNCTION_NAME") &&
                    null == System.getenv("AWS_CONTAINER_CREDENTIALS_RELATIVE_URI")) {

                // I don't like this, but I needed some way to make sure we weren't in a lambda function or fargate so
                // we could use conduit or isengard credentials
                final Region region = RIPHelper.getRegion(realm);

                log.info("Getting your credentials from Conduit.");
                this.awsCredentialsProvider = new ConduitCredentialsProvider(region, awsAccountId);

            } else {
                // Use default aws credentials when they're available
                log.info("Using the default AWS credentials.");
                return DefaultAWSCredentialsProviderChain.getInstance();
            }
        } catch (Exception e) {
            return null;
        }
        return this.awsCredentialsProvider;
    }
    // CHECKSTYLE:ON:HiddenField
    // CHECKSTYLE:ON:ReturnCount

    // CHECKSTYLE:OFF:HiddenField
    private Optional<Output> getOutput(final String awsAccountId,
                                       final String domain,
                                       final String realm,
                                       final String user,
                                       final String outputName) {
        final AmazonCloudFormation cloudFormation = AmazonCloudFormationClientBuilder.standard()
                .withCredentials(getCredentialsProvider(awsAccountId, domain, realm))
                .withRegion(realm).build();
        final DescribeStacksResult describeStacks = cloudFormation.describeStacks();
        final String stackName = getStackName(domain, user);
        log.info("Calling cloudformation stack " + stackName + " to get output" + outputName);
        final Optional<Stack> serviceStack = describeStacks.getStacks().stream()
                .filter(stack -> stack.getStackName().equals(stackName))
                .findFirst();
        serviceStack.orElseThrow(() -> new RuntimeException(
            String.format("Could not find stack %s in AWS account %s", stackName, awsAccountId)));
        final Optional<Output> op = serviceStack.get().getOutputs().stream()
                .filter(output -> output.getOutputKey().equals(outputName))
                .findFirst();
        op.orElseThrow(() -> new RuntimeException(
            String.format("Could not retrieve output %s from stack %s in AWS account %s",
                          outputName, stackName, awsAccountId)));
        return op;
    }
    // CHECKSTYLE:ON:HiddenField

    // CHECKSTYLE:OFF:HiddenField
    private String getStackName(final String domain, final String user) {
        log.info("Domain = " + domain);
        if (domain.toLowerCase().equals("development")) {
            if (null == user) {
                final String message = "Unable to determine the name of your development stack, " +
                        "did you include the user parameter?";
                throw new IllegalArgumentException(message);
            }
            this.stackName = String.format("sam-dev-%s-%s", user, stackName);
        }
        return this.stackName;
    }
    // CHECKSTYLE:ON:HiddenField
}
