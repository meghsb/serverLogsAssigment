package com.cs.example.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cs.example.model.FileContent;
import com.cs.example.model.State;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class FileUtil{
	private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 
	 * @param location
	 * @description Validates the number of commandLine args
	 */
	public void validateArg(String[] location) {
		if(location.length<1)
			throw new IllegalArgumentException("Please specify a path for logfile!");
		if(location.length>1)
			throw new IllegalArgumentException("Please specify only one path for logfile!");
	}
	
	/**
	 * 
	 * @param oldContent
	 * @param content
	 * @return Compares the states of the two events (having same eventId) i.e STARTED and FINISHED and calculates the execution time between them
	 */
	public long calculateExecutionTime(FileContent oldContent, FileContent content) {
		long execTime = 0;
		if (oldContent.getState().getValue().equals(State.FINISHED.getValue()) && content.getState().getValue().equals(State.STARTED.getValue())) {
			execTime = oldContent.getTimeStamp() - content.getTimeStamp();
		} else if (content.getState().getValue().equals(State.FINISHED.getValue()) && oldContent.getState().getValue().equals(State.STARTED.getValue())) {
			execTime = content.getTimeStamp() - oldContent.getTimeStamp();
		}else {
			LOGGER.error("Unable to derive execution time!");
		}
		return execTime;
	}

	/**
	 * 
	 * @param jsonString
	 * @return FileContent object
	 * @decription Deserializes the json string
	 */
	public FileContent convertJsonStringtoObject(String jsonString) {
		 FileContent content = null;
         try {
             content = new ObjectMapper().readValue(jsonString, FileContent.class);
		} catch (JsonProcessingException e) {
			LOGGER.error("Unable to parse the file!: {}", e.getMessage());
		}
		return content;
	}
	

}
