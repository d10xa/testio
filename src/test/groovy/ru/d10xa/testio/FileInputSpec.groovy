package ru.d10xa.testio

import org.junit.Rule
import spock.lang.Specification

class FileInputSpec extends Specification {

    @Rule
    Input input = new Input()

    @Rule
    Output output = new Output()

    def 'read file'() {
        given:
        input << getClass().getClassLoader().getResource("testfile.txt").text

        when:
        Scanner sc = new Scanner(System.in)
        while (sc.hasNextLine()) {
            println sc.nextLine()
        }

        then:
        output as String == "1\n2 3\nabc\n"
    }

}
