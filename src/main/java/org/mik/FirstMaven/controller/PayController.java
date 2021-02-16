package org.mik.FirstMaven.controller;

import org.apache.log4j.Logger;
import org.mik.FirstMaven.entity.AbstractEntity;
import org.mik.FirstMaven.entity.Company;
import org.mik.FirstMaven.entity.Person;
import org.mik.FirstMaven.service.PaymentService;
import org.mik.FirstMaven.service.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PayController {

    private static final Logger LOG = Logger.getLogger((PayController.class.getName()));
    private PaymentService service;
    public PayController(){
        this.service = new PaymentService();
    }
    public void start(){
        List<AbstractEntity> list = new ArrayList<>();
        list.add(new Person.Builder()
                .id(1L)
                .name("John Doe")
                .address("New York 74th. street")
                .idNumber("John.D. idnumber")
                .build().get());
        list.add(new Person.Builder()
                .id(2L)
                .name("Jane Doe")
                .address("Los Angeles")
                .idNumber("Jane D. idnumber")
                .build().get());
        list.add(new Company.Builder()
                .id(3L)
                .name("Google Inc.")
                .address("Frisco")
                .amount(100000L)
                .taxNumber("G tax")
                .build().get());
        list.add(new Company.Builder()
                .id(4L)
                .name("M$")
                .address("Redmond")
                .amount(1000000L)
                .taxNumber("M$ tax")
                .build().get());

        Optional<AbstractEntity> ns = list.stream().findAny().filter(it->it.getName().equals("M$")).stream().findFirst();
        if(ns.isPresent()){
            list.stream().filter(it-> it.isPerson()).collect(Collectors.toList())
                    .forEach(it->this.service.pay(ns.get(),it,1000L));
        }

        LOG.info("---- Account List ----");
        list.forEach(it->LOG.info(String.format("%s's amount is %d", it, it.getAmount())));

        PersonService personService = new PersonService();
        LOG.info("---- XML export of persons ------");
        list.stream()
                .filter(it->it.isPerson())
                .collect(Collectors.toList())
                .forEach(it->personService.xmlGen(it));

        LOG.info("---- Json export of persons ------");
        list.stream()
                .filter(it->it.isPerson())
                .collect(Collectors.toList())
                .forEach(it->personService.jsonGen(it));
    }
}
