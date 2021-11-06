package com.cs.example.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cs.example.dao.AlertRepository;
import com.cs.example.model.Alert;
import com.cs.example.model.FileContent;
import com.cs.example.util.FileUtil;

@Service
public class FileServiceImpl implements FileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

	@Autowired
	private FileUtil util;

	@Autowired
	private AlertRepository alertRepository;

	@Value("${alert.maxsize}")
	private int alertMaxSize;
	
	@Value("${alert.maxExecutionTime}")
	private int maxExecutionTime;

	@Override
	public void analyzeFile(String[] location) {
		util.validateArg(location);
		parsefile(location[1]);
	}

	/**
	 * 
	 * @param location
	 * @return void
	 * @description Parses the log file, calculates the executionTime, raises alerts on higher execution time 
	 * and saves alert information into database.
	 */
	private void parsefile(String location) {
		Map<String, FileContent> map = new HashMap<String, FileContent>();
		String line;
		long execTime = 0;
		Map<String, Alert> alerts = new HashMap<>();
		FileContent oldContent = new FileContent();
		Alert alert = new Alert();
		
		LOGGER.info("Parsing the file and saving the alerts...");
		try {
			File file = new File(location);
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);
			
			//Reading each line of file
			while ((line = bReader.readLine()) != null) {
				FileContent content = util.convertJsonStringtoObject(line); 
				LOGGER.info("Event {}: ", content);
				
				//Check if new eventId has already occurred for either STARTED or FINISHED state or not
				if (map.containsKey(content.getId())) {	
					oldContent = map.get(content.getId());
					execTime = util.calculateExecutionTime(oldContent, content);  
					alert = new Alert(content, execTime);
					if (execTime > maxExecutionTime) {
						alert.setAlert(true);			//Raise the alert on exceeding executionTime
					}
					alerts.put(content.getId(), alert);	//Add alert information for every event
					map.remove(content.getId());		//If matching event found, remove the eventId from map
				} else {
					map.put(content.getId(), content);  //If eventId is unique, save the eventId along with its content
				}
				if (alerts.size() > alertMaxSize) {		//On exceeding max number of alerts collected, 
					saveAlerts(alerts.values());		//save the current pool 
					alerts = new HashMap<>();			//and then create a new pool
				}
			}											//End of while loop 
			fReader.close();							//Close the fileReader
			if (alerts.size() != 0) {
				saveAlerts(alerts.values());			//Save all alerts
			}

		} catch (IOException ex) {
			LOGGER.error("File not accessible!: {}", ex.getMessage());
		}
	}

	/**
	 * @param alert values
	 * @description Saves the alert information in database
	 */
	private void saveAlerts(Collection<Alert> alertValues) {
		LOGGER.debug("Saving {} alerts...", alertValues.size());
		alertRepository.saveAll(alertValues);
	}

}
