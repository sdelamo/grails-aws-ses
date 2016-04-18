package org.grails.plugins.awsses

import com.amazonaws.auth.AWSCredentials
//import grails.validation.Validateable

class AwsSesServiceConfig implements AWSCredentials { //, Validateable

    String awsAcessKeyId
    String awsSecretKeyId
    String awsRegion


    @Override
    String getAWSAccessKeyId() {
        return awsAcessKeyId
    }

    @Override
    String getAWSSecretKey() {
        return awsSecretKeyId
    }

    /*
    static constraints = {
        awsAcessKeyId nullable: false, blank: false
        awsSecretKeyId nullable: false, blank: false
        awsRegion nullable: false, blank: false, inList: ['us-gov-west-1', 'us-east-1', 'us-west-1', 'us-west-2', 'eu-west-1', 'eu-central-1', 'ap-southeast-1', 'ap-northeast-1', 'ap-northeast-2', 'sa-east-1', 'cn-north-1']
    }
    */
}
