package model.utils;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Serializer<T> {
	public String serialize(T obj) {
		return new Gson().toJson(obj);
	}
	
	public String serialize(List<T> objs) {
		return new Gson().toJson(objs);
	}
	
	public T desserialize(String json) {
		Type type = new TypeToken<T>(){}.getType();
		return new Gson().fromJson(json, type);
	}
	
	public List<T> desserializeCollection(String json) {
		Type listType = new TypeToken<ArrayList<T>>(){}.getType();
		return new Gson().fromJson(json, listType);
	}
}
