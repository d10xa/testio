package ru.d10xa.testio

import org.junit.Rule
import spock.lang.Specification

class TestioSpec extends Specification {

    @Rule
    Testio testio = new Testio()

    def 'in and out not null'() {
        expect:
        testio.in != null
        testio.out != null
    }

    def 'testio.in'(){
        given:
        testio.in << "testio.in check"

        when:
        println new Scanner(System.in).nextLine()

        then:
        testio.out as String == "testio.in check\n"
    }

}
