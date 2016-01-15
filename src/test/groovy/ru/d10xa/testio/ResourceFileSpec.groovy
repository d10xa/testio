package ru.d10xa.testio

import spock.lang.Specification

import static ru.d10xa.testio.Testio.resourceFile

class ResourceFileSpec extends Specification {

    def 'resource file found'() {
        when:
        def content = resourceFile("testfile.txt").text

        then:
        content == "1\n2 3\nabc"
    }

}
