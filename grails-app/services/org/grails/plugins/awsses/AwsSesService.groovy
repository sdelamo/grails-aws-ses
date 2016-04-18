package org.grails.plugins.awsses

import com.amazonaws.auth.AWSCredentials
import org.grails.plugins.awsses.MailComposer

class AwsSesService {

    def awsSesServiceConfig

    void mail(@DelegatesTo(MailComposer) Closure composer) throws Exception {
        if(!(awsSesServiceConfig instanceof AWSCredentials) || !awsSesServiceConfig.awsRegion) {
            throw new Exception('You need to configure the service bean to have valid AWS credentails and a valid region')
        }
        Closure cl = composer.clone()
        MailComposer mailComposer = new MailComposer(awsSesServiceConfig, awsSesServiceConfig.awsRegion)
        cl.delegate = mailComposer
        cl.resolveStrategy = Closure.DELEGATE_FIRST
        cl()
        mailComposer.sendEmail()

    }
}
