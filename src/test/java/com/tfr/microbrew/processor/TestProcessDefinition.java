package com.tfr.microbrew.processor;

import com.tfr.microbrew.MicroBreweryModelApplication;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = MicroBreweryModelApplication.class)
public class TestProcessDefinition {

    @Autowired
    private ProcessDefinition processDefinition;

    @Test
    public void test_Monday_Expect3() {
        test(new LocalDate(2017, 5, 1), 3);
    }

    @Test
    public void test_Tuesday_Expect2() {
        test(new LocalDate(2017, 5, 2), 2);
    }

    @Test
    public void test_Wednesday_Expect2() {
        test(new LocalDate(2017, 5, 3), 2);
    }

    @Test
    public void test_Thursday_Expect4() {
        test(new LocalDate(2017, 5, 4), 4);
    }

    @Test
    public void test_Friday_Expect4() {
        test(new LocalDate(2017, 5, 5), 4);
    }

    @Test
    public void test_Saturday_Expect5() {
        test(new LocalDate(2017, 5, 6), 5);
    }

    @Test
    public void test_Sunday_Expect5() {
        test(new LocalDate(2017, 5, 7), 5);
    }

    private void test(LocalDate date, int expectedNumberOfProcessors) {
        Queue<Processor> processors = processDefinition.getProcesses(date);
//        System.out.println(date);
//        processors.forEach(p -> System.out.println("\t" + p.getName()));
        assertEquals(expectedNumberOfProcessors, processors.size());
    }

}
