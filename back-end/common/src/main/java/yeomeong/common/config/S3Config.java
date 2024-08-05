package yeomeong.common.config;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {


    @Value("{aws.s3.region}")
    private String region;

    @Value("{aws.credentials.access-key-id}")
    private String awsAccessKey;

    @Value("{aws.credentials.secret-access-key}")
    private String awsSecretKey;

    @Bean
    public AmazonS3 s3Client(){

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsAccessKey,awsSecretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(String.valueOf(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

    }
}

