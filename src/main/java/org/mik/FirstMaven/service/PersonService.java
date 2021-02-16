package org.mik.FirstMaven.service;


import org.apache.log4j.Logger;
import org.mik.FirstMaven.entity.AbstractEntity;
import org.mik.FirstMaven.entity.Person;
import org.mik.FirstMaven.export.XMLGenerator;
import org.mik.FirstMaven.export.json.JsonGenerator;


public class PersonService {

    private static final Logger LOG = Logger.getLogger(PersonService.class);

    private XMLGenerator xmlGenerator = new XMLGenerator();
    private JsonGenerator jsonGenerator = new JsonGenerator();

    public <T extends AbstractEntity> String xmlGen(T person){
        String result = xmlGenerator.convertToXML(person);
        LOG.info(String.format("XML of person %s is %s", person,result));
        return result;
    }

    public <T extends AbstractEntity> String jsonGen(T person) {
        String result = jsonGenerator.convertToJson(person);
        LOG.info(String.format("Json of person %s is %s", person,result));
        return result;
    }
}
