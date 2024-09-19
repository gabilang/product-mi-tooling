package org.wso2.ei.dashboard.micro.integrator.commons;

import com.google.gson.JsonArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogsFormatter {

    private static final String LOG_REGEX = "\\[(.*?)]\\s+(\\w+)\\s+\\{(.*?)}\\s+-\\s+(.*)";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";
    private static final Pattern pattern = Pattern.compile(LOG_REGEX);
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);

    public static JsonArray parseLogsAsJsonArray(String[] logs, String nodeId) {
        JsonArray array = new JsonArray();

        for (String log : logs) {
            Matcher matcher = pattern.matcher(log);

            if (!matcher.find()) {
                continue;
            }
            JsonArray logLine = new JsonArray();

            // Add node ID
            logLine.add(nodeId);

            // Extract timestamp and convert to Unix epoch
            String timestampStr = matcher.group(1);
            long unixEpoch = convertToUnixEpoch(timestampStr);
            logLine.add(unixEpoch);

            // Extract log level
            String logLevel = matcher.group(2);
            logLine.add(logLevel);

            // Extract logger name
            String loggerName = matcher.group(3);
            logLine.add(loggerName);

            // Extract the log message
            String message = matcher.group(4);
            logLine.add(message);

            array.add(logLine);
        }
        return array;
    }

    // Method to convert timestamp to Unix epoch format
    public static long convertToUnixEpoch(String timestampStr) {
        try {
            Date date = sdf.parse(timestampStr);
            return date.getTime(); // return Unix epoch time
        } catch (ParseException e) {
            return -1;
        }
    }
}
