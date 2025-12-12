package br.com.devfilho.screenmatch.service;

import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

public class DataConvert implements IDataConvert {
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T getData(String json, Class<T> tclass) {
        try {
            return mapper.readValue(json, tclass);
        } catch (JacksonException e){
            throw new RuntimeException(e);
        }
    }
}
