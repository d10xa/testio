package ru.d10xa.testio

import org.junit.Rule
import spock.lang.Specification

class InputRuleSpec extends Specification {

    @Rule
    Input input = new Input()

    @Rule
    Output output = new Output()

    def 'use multiple scanners'() {
        given:
        input << ["hello", "world"]

        when:
        println new Scanner(System.in).nextLine()
        println new Scanner(System.in).nextLine()

        then:
        output as String == "hello\nworld\n"
    }

    def 'start string with line separator'() {
        given:
        input << [String.format("%n"), "hello"]

        when:
        println new Scanner(System.in).nextLine()
        println new Scanner(System.in).nextLine()
        println new Scanner(System.in).nextLine()

        then:
        output as String == "\n\nhello\n"
    }
}
