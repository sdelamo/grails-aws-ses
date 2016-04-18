package org.grails.plugins.awsses

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient
import com.amazonaws.services.simpleemail.model.Body
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.Content
import com.amazonaws.services.simpleemail.model.Destination
import com.amazonaws.services.simpleemail.model.SendEmailRequest

class MailComposer {

    Destination destination
    String subjectStr
    String bodyStr
    String from

    AWSCredentials awsCredentials
    String awsRegion


    MailComposer(AWSCredentials awsCredentials, String awsRegion) {
        this.awsCredentials = awsCredentials
        this.awsRegion = awsRegion
    }

    void from(String from) {
        this.from = from
    }

    void to(String... addresses) {
        destination = new Destination().withToAddresses(addresses)
    }

    void subject(String subject) {
        this.subjectStr = subject
    }

    void body(String body) {
        this.bodyStr = body
    }

    void sendEmail() throws Exception {
        Content subjectContent = new Content().withData(subjectStr)
        Content textBody = new Content().withData(bodyStr);
        Body body = new Body().withText(textBody);
        Message message = new Message().withSubject(subjectContent).withBody(body)
        SendEmailRequest request = new SendEmailRequest().withSource(from).withDestination(destination).withMessage(message)

        AmazonSimpleEmailServiceClient client = new AmazonSimpleEmailServiceClient(awsCredentials)
        Region region = awsRegionWithString(awsRegion)
        client.setRegion(region)

        client.sendEmail(request);
    }

    static Region awsRegionWithString(String regionStr) throws Exception {
        if(regionStr == 'us-gov-west-1') {
            return Region.getRegion(Regions.GovCloud)

        } else if(regionStr == 'us-east-1') {
            return Region.getRegion(Regions.US_EAST_1)

        } else if(regionStr == 'us-west-1') {
            return Region.getRegion(Regions.US_WEST_1)

        } else if(regionStr == 'us-west-2') {
            return Region.getRegion(Regions.US_WEST_2)

        } else if(regionStr == 'eu-west-1') {
            return Region.getRegion(Regions.EU_WEST_1)

        } else if(regionStr == 'eu-central-1') {
            return Region.getRegion(Regions.EU_CENTRAL_1)

        } else if(regionStr == 'ap-southeast-1') {
            return Region.getRegion(Regions.AP_SOUTHEAST_1)

        } else if(regionStr == 'ap-southeast-2') {
            return Region.getRegion(Regions.AP_SOUTHEAST_2)

        } else if(regionStr == 'ap-northeast-1') {
            return Region.getRegion(Regions.AP_NORTHEAST_1)

        } else if(regionStr == 'ap-northeast-2') {
            return Region.getRegion(Regions.AP_NORTHEAST_2)

        } else if(regionStr == 'sa-east-1') {
            return Region.getRegion(Regions.SA_EAST_1)

        } else if(regionStr == 'cn-north-1') {
            return Region.getRegion(Regions.CN_NORTH_1)

        }

        throw new Exception("AWS region ${regionStr} not found")
    }
}
