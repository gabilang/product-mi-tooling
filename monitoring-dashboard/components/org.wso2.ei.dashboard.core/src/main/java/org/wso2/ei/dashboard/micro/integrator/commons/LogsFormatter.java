package org.wso2.ei.dashboard.micro.integrator.commons;

import com.google.gson.JsonArray;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogsFormatter {

    private static final String LOG_REGEX = "\\[(.*?)]\\s+(\\w+)\\s+\\{(.*?)}\\s+-\\s+(.*)";
    private static final Pattern pattern = Pattern.compile(LOG_REGEX);

    public static JsonArray parseLogsAsJsonArray(String[] logs, String nodeId) {
        JsonArray array = new JsonArray();

        for (String log : logs) {
            Matcher matcher = pattern.matcher(log);

            if (matcher.find()) {
                JsonArray jsonArray = new JsonArray();

                // Add node ID
                jsonArray.add(nodeId);

                // Extract timestamp and convert to Unix epoch
                String timestampStr = matcher.group(1);
                long unixEpoch = convertToUnixEpoch(timestampStr);
                jsonArray.add(unixEpoch);

                // Extract log level
                String logLevel = matcher.group(2);
                jsonArray.add(logLevel);

                // Extract logger name
                String loggerName = matcher.group(3);
                jsonArray.add(loggerName);

                // Extract the log message
                String message = matcher.group(4);
                jsonArray.add(message);

                array.add(jsonArray);
            }
        }
        return array;
    }

    // Method to convert timestamp to Unix epoch format
    public static long convertToUnixEpoch(String timestampStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
            Date date = sdf.parse(timestampStr);
            return date.getTime(); // return Unix epoch time
        } catch (Exception e) {
            return -1;
        }
    }
}
