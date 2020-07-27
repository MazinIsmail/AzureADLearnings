package com.azureAD.learnings.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.azureAD.learnings.model.AzureADObject;
import com.azureAD.learnings.model.UserDetails;
import com.google.gson.Gson;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

@Service
public class AzureADService {

	public static final Logger logger = LoggerFactory.getLogger(AzureADService.class);

	@Autowired
	private Environment env;

	public List<UserDetails> getAllUsersFromGroup(String groupId) {
		logger.debug("Start :: getAllUsersFromGroup()");
		String nextUrl = "";
		boolean hasNext = true;
		List<UserDetails> userList = new ArrayList<>();
		Gson gson = new Gson();
		try {
			String urlString = "https://graph.microsoft.com/v1.0/groups/"+groupId+"/members";
			
			String accessToken = getAccessToken();
			String result = getDataFromURL(urlString, accessToken);
			
			while (hasNext) {
				AzureADObject azureADObject = gson.fromJson(result.toString(), AzureADObject.class);
				userList.addAll(azureADObject.getValue());
				if (azureADObject.getNextLink() != null) {
					nextUrl = azureADObject.getNextLink();
					result = getDataFromURL(nextUrl, accessToken);
				} else {
					hasNext = false;
					break;
				}
			}
			logger.info("List size {}", userList.size());
		} catch (Exception e) {
			logger.error("Exception {}", e);
		}
		logger.debug("End :: getAllUsersFromGroup()");
		return userList;
	}
	
	
	public List<UserDetails> getAllUsers() {
		logger.debug("Start :: getAllUsers()");
		String nextUrl = "";
		boolean hasNext = true;
		List<UserDetails> userList = new ArrayList<>();
		Gson gson = new Gson();
		try {
			String urlString = "https://graph.microsoft.com/v1.0/users/";
			
			String accessToken = getAccessToken();
			String result = getDataFromURL(urlString, accessToken);
			
			while (hasNext) {
				AzureADObject azureADObject = gson.fromJson(result.toString(), AzureADObject.class);
				userList.addAll(azureADObject.getValue());
				if (azureADObject.getNextLink() != null) {
					nextUrl = azureADObject.getNextLink();
					result = getDataFromURL(nextUrl, accessToken);
				} else {
					hasNext = false;
					break;
				}
			}
			logger.info("List size {}", userList.size());
		} catch (Exception e) {
			logger.error("Exception {}", e);
		}
		logger.debug("End :: getAllUsers()");
		return userList;
	}
	
	public UserDetails getSpecificUser(String userName) {
		logger.debug("Start :: getSpecificUser()");
		Gson gson = new Gson();
		UserDetails userDetail = null;
		try {
			String urlString = "https://graph.microsoft.com/v1.0/users/"+userName;
			
			String accessToken = getAccessToken();
			String result = getDataFromURL(urlString, accessToken);
			userDetail = gson.fromJson(result.toString(), UserDetails.class);
			logger.info("userDetail {}", userDetail);
		} catch (Exception e) {
			logger.error("Exception {}", e);
		}
		logger.debug("End :: getSpecificUser()");
		return userDetail;
	}

	public String getDataFromURL(String urlData, String accessToken) {
		logger.debug("Start :: getDataFromURL()");
		String result = "";
		try {
			URL url = new URL(urlData);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con = (HttpURLConnection) url.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", accessToken);
			con.setRequestProperty("Accept", "application/json");
			con.connect();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("Exception {}", e);
		}
		logger.debug("End :: getDataFromURL()");
		return result;
	}

	public String getAccessToken() {
		logger.debug("Start :: getAccessToken()");
		String accessToken = "";
		String clientId = env.getProperty("client.id");
		String tenantId = env.getProperty("tenant.id");
		String clientSecret = env.getProperty("client.secret");
		String grantType = env.getProperty("grant.type");
		String scope = env.getProperty("scope");
		String urlParameters = "grant_type="+grantType+"&client_id=" + clientId + "&client_secret=" + clientSecret
				+ "&scope="+scope;
		try {
			byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
			int postDataLength = postData.length;
			String request = "https://login.microsoftonline.com/"+tenantId+"/oauth2/v2.0/token";
			URL url = new URL(request);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setInstanceFollowRedirects(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
			conn.setUseCaches(false);
			try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
				wr.write(postData);
			}
			conn.connect();
			InputStream stream = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"), 8);
			String result = reader.readLine();

			JSONParser jsonParser = new JSONParser();
			JSONObject jsonObject = (JSONObject) jsonParser.parse(result);
			accessToken = (String) jsonObject.get("access_token");
			logger.info("Access Token {}",accessToken);
		} catch (IOException e) {
			logger.error("Exception {}", e);
		} catch (ParseException e) {
			logger.error("Exception {}", e);
		}
		logger.debug("End :: getAccessToken()");
		return accessToken;
	}

}
