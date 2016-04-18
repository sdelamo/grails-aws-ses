package org.grails.plugins.awsses.tests

import grails.test.mixin.integration.Integration
import org.grails.plugins.awsses.AwsSesService
import org.grails.plugins.awsses.AwsSesServiceConfig
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
class AwsSesServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    @Autowired
    AwsSesService awsSesService

    void "test something"() {

        when:
        def accessKey = System.getenv('AWS_ACCESS_KEY')
        def secretKey = System.getenv('AWS_SECRET_KEY')

        String email = System.getenv('TEST_INBOX_EMAIL')
        String host = System.getenv('TEST_INBOX_HOST')
        String password = System.getenv('TEST_INBOX_PASSWORD')
        String folder = System.getenv('TEST_INBOX_FOLDER')
        String provider = System.getenv('TEST_INBOX_PROVIDER')

        then:
        accessKey
        secretKey
        email
        host
        password
        folder
        provider

        when:
        def config = new AwsSesServiceConfig(awsAcessKeyId: accessKey, awsSecretKeyId: secretKey, awsRegion: 'eu-west-1')
        def testInboxConfig = new TestInboxConfig(email: email, host: host, password: password, folder: folder, provider: provider)
        def readEmail = new ReadMail(testInboxConfig: testInboxConfig)
        def subjectStr = 'TEST AWS SES'
        awsSesService.awsSesServiceConfig = config
        awsSesService.mail {
            to 'sergio.delamo@gmx.es'
            from 'sergio.delamo@softamo.com'
            subject subjectStr
            body 'Hi, this is a test email'
        }
        sleep(10_000)
        boolean emailFound = readEmail.fetchFolderMessageSubjects().any { it == subject}

        then:
        awsSesService
        emailFound

        when:
        readEmail.deleteMessagesAtInboxWithSubject(subjectStr)
        emailFound = readEmail.fetchFolderMessageSubjects().any { it == subject}

        then:
        !emailFound
    }
}
