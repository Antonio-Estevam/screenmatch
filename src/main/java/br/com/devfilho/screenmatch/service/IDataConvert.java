package br.com.devfilho.screenmatch.service;

public interface IDataConvert {
     <T> T getData(String json, Class<T> tclass);
}
