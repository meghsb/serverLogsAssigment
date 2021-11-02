package com.cs.example.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cs.example.model.FileContent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class FileServiceImpl implements FileService{

	@Override
	public String analyzeFile(String[] location) {
		validateArg(location);
		Map<String, FileContent> eventMap = readfile(location[1]);
		while(eventMap.containsKey(eventMap.get))
		
		
		return "";
		
	}

	private void validateArg(String[] location) {
		if(location.length<1)
			throw new IllegalArgumentException("Please specify path of logfile!");		
	}

	private Map<String, FileContent> readfile(String location) {
		Map<String, FileContent> map = new HashMap<String, FileContent>();
		String line;
		String key;
		
		try{
			File file = new File(location);
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);
			
			while((line=bReader.readLine())!=null) {
				FileContent content = convertJsonStringtoObject(line);
				System.out.println("Content: "+ content);
				key = content.getId().concat("_").concat(content.getState().getValue());
				map.put(key, content);
			}
			fReader.close();
			System.out.println("Contents of File: ");  
			
		}catch(IOException ex) {
			ex.printStackTrace();
		}
		return map;
	}

	private FileContent convertJsonStringtoObject(String line) {
		 FileContent content = null;
         try {
             content = new ObjectMapper().readValue(line, FileContent.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return content;
	}
	

}
