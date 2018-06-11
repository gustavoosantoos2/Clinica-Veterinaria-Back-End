package model.utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Serializer {
	public String serialize(Object obj) {
		return createParser().toJson(obj);
	}

	public String serialize(List<Object> objs) {
		return createParser().toJson(objs);
	}

	public <T> T desserialize(String json, Class<T> classType) {
		return (T) createParser().fromJson(json, classType);
	}
	
	public <T> T desserialize(BufferedReader reader, Class<T> classType) {
		return (T) createParser().fromJson(reader, classType);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> desserializeCollection(String json, Class<T> classType) {
		return createParser().fromJson(json, ArrayList.class);
	}
	
	private Gson createParser() {
		return new GsonBuilder()
				.setDateFormat("dd/MM/yyyy")
				.create();
	}
}
