package edu.jsu.mcis.cs310.tas_fa24;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Punch {

    // Attributes
    private final int id; 
    private final int terminalid; 
    private final String badgeid; 
    private final LocalDateTime originaltimestamp; 
    private LocalDateTime adjustedtimestamp; 
    private PunchAdjustmentType adjustmentType; 
    private int eventTypeId; 

    // Constructor for a new punch (without ID)
    public Punch(int terminalid, String badgeid, int eventTypeId) {
        this.id = -1;
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.originaltimestamp = LocalDateTime.now();
        this.adjustedtimestamp = null;
        this.adjustmentType = null;
        this.eventTypeId = eventTypeId;
    }

    // Constructor for an existing punch (with ID)
    public Punch(int id, int terminalid, String badgeid, LocalDateTime originaltimestamp, int eventTypeId) {
        this.id = id;
        this.terminalid = terminalid;
        this.badgeid = badgeid;
        this.originaltimestamp = originaltimestamp;
        this.adjustedtimestamp = null;
        this.adjustmentType = null;
        this.eventTypeId = eventTypeId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public String getBadgeid() {
        return badgeid;
    }

    public LocalDateTime getOriginaltimestamp() {
        return originaltimestamp;
    }

    public LocalDateTime getAdjustedtimestamp() {
        return adjustedtimestamp;
    }

    public int getEventTypeId() {
        return eventTypeId;
    }

    // Setters
    public void setAdjustedTimestamp(LocalDateTime adjustedtimestamp) {
        this.adjustedtimestamp = adjustedtimestamp;
    }

    public void setAdjustmentType(PunchAdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    // Print method that handles different event types
    public String printOriginal() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String timestamp = originaltimestamp.format(formatter);

        String punchType;
        switch (eventTypeId) {
            case 1:
                punchType = "CLOCK IN";
                break;
            case 2:
                punchType = "TIME OUT";
                break;
            default:
                punchType = "CLOCK OUT";
                break;
            
        }

        return String.format("#%s %s: %S", badgeid, punchType, timestamp);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String originaltime = originaltimestamp.format(formatter);
        String result = String.format("#%s: Terminal %d, Original Timestamp: %s", 
                                       badgeid, 
                                       terminalid, 
                                       originaltime);

        if (adjustedtimestamp != null) {
            String adjustedTime = adjustedtimestamp.format(formatter);
            result += String.format("; Adjusted Timestamp: %s (%s)", adjustedTime, adjustmentType);
        }
        return result;
    }
}
