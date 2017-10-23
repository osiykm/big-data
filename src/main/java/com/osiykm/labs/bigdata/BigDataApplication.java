package com.osiykm.labs.bigdata;

import au.com.bytecode.opencsv.CSVReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.parsers.DOMParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@Slf4j
public class BigDataApplication {

	public static void main(String[] args) throws IOException, SAXException {
		ConfigurableApplicationContext context = SpringApplication.run(BigDataApplication.class, args);

		UsersDAO usersDAO = context.getBean(UsersDAO.class);
		usersDAO.deleteAll();
		usersDAO.saveAll(loadJson());
		log.info(String.valueOf(usersDAO.count()));
		usersDAO.saveAll(loadXML());
		log.info(String.valueOf(usersDAO.count()));
		usersDAO.saveAll(loadCSV());
		log.info(String.valueOf(usersDAO.count()));
	}

	private static List<User> loadCSV() throws IOException {
		List<User> users;
		CSVReader reader = new CSVReader(new FileReader("dataset.csv"));
		List<String[]> data = reader.readAll();
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < data.get(0).length; i++) {
			map.put(data.get(0)[i], i);
		}
		users = data.stream()
				.skip(1)
				.map(p -> User.builder()
						.firstName(p[map.get("first_name")])
						.lastName(p[map.get("last_name")])
						.email(p[map.get("email")])
						.language(p[map.get("language")])
						.build())
				.collect(Collectors.toList());
		return users;
	}

	private static List<User> loadJson() throws IOException {
		return new ObjectMapper().readValue(new File("dataset.json"), new TypeReference<List<User>>(){});
	}

	private static List<User> loadXML() throws IOException, SAXException {
		DOMParser parser = new DOMParser();
		parser.parse(new InputSource(new FileReader("dataset.xml")));
		NodeList records = parser.getDocument().getElementsByTagName("record");
		List<User> users = new ArrayList<>();
		for (int i = 0; i < records.getLength(); i++) {
			users.add(
					getUser(records.item(i))
			);
		}
		return  users;
	}

	private static User getUser(Node item) {
		NodeList childNodes = item.getChildNodes();
		Map<String, String> userData = new HashMap<>();
		for (int i = 0; i < childNodes.getLength(); i++) {
			userData.put(childNodes.item(i).getNodeName(), childNodes.item(0).getTextContent());
		}
		return User.builder()
				.email(userData.get("email"))
				.firstName(userData.get("first_name"))
				.language(userData.get("language"))
				.lastName(userData.get("last_name"))
				.build();
	}
}
