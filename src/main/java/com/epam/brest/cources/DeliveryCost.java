package com.epam.brest.cources;

import com.epam.brest.cources.calc.Calculator;
import com.epam.brest.cources.calc.DataItem;
import com.epam.brest.cources.files.CSVFileReader;
import com.epam.brest.cources.files.FileReader;
import com.epam.brest.cources.menu.CorrectValue;
import com.epam.brest.cources.menu.EnteredValue;
import com.epam.brest.cources.menu.EnteredValue.Types;
import com.epam.brest.cources.menu.ExitValue;
import com.epam.brest.cources.menu.IncorrectValue;
import com.epam.brest.cources.selector.PriceSelector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;


public class DeliveryCost {

    private static final String QUIT_SYMBOL = "q";

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws IOException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("app.xml", "calc.xml");
        Properties messages = (Properties) applicationContext.getBean("appMessages");
        Calculator calculator = applicationContext.getBean(Calculator.class);

        DeliveryCost deliveryCost = new DeliveryCost();
        Scanner scanner = new Scanner(System.in);

        FileReader fileReader = new CSVFileReader();
        Map<Integer, BigDecimal> kgs = fileReader.readData("price_kg.csv");
        if (kgs == null || kgs.isEmpty()) {
            throw new FileNotFoundException("File with prices per kg not found.");
        }

        Map<Integer, BigDecimal> kms = fileReader.readData("price_km.csv");
        if (kms == null || kms.isEmpty()) {
            throw new FileNotFoundException("File with prices per km not found.");
        }

        EnteredValue mass = deliveryCost.receiveValueFromConsole(messages.getProperty("weight.message"), scanner);
        if (deliveryCost.exitNotNeeded(mass)) {
            EnteredValue distance = deliveryCost.receiveValueFromConsole(messages.getProperty("distance.message"), scanner);
            if (deliveryCost.exitNotNeeded(distance)) {
                PriceSelector selector = new PriceSelector();

                DataItem dataItem = new DataItem();
                dataItem.setWeight(((CorrectValue) mass).getValue());
                dataItem.setDistance(((CorrectValue) mass).getValue());
                dataItem.setPricePerKg(selector.selectPriceValue(kgs, dataItem.getWeight()));
                dataItem.setPricePerKm(selector.selectPriceValue(kms, dataItem.getDistance()));

                BigDecimal calcResult = calculator.calc(dataItem);
                LOGGER.info("Data item: {}", dataItem);
                LOGGER.info("Delivery cost: {} {}", dataItem, calcResult);
            }
        }
        System.out.println(messages.getProperty("bye.message"));
    }

    private boolean exitNotNeeded(EnteredValue enteredValue) {
        return enteredValue != null && enteredValue.getType() != Types.EXIT;
    }

    private EnteredValue receiveValueFromConsole(String message, Scanner scanner) {
        EnteredValue result = new IncorrectValue();
        while (result.getType() == Types.INCORRECT) {
            System.out.println(message);
            result = parseInputValue(scanner.nextLine());
        }
        return result;
    }

    private EnteredValue parseInputValue(String inputValue) {
        EnteredValue result = new ExitValue();
        if (!inputValue.trim().toLowerCase().equals(QUIT_SYMBOL)) {
            try {
                BigDecimal value = new BigDecimal(inputValue);
                if (value.compareTo(BigDecimal.ZERO) > 0) {
                    result = new CorrectValue(new BigDecimal(inputValue));
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                System.out.format("Incorrect value: %s%n", inputValue);
                result = new IncorrectValue();
            }
        }
        return result;
    }
}
