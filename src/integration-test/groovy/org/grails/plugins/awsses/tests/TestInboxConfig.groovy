package org.grails.plugins.awsses.tests


import groovy.transform.CompileStatic

@CompileStatic
class TestInboxConfig {
    String email
    String host
    String password
    String folder
    String provider
}
