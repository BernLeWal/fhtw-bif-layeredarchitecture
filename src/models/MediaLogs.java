package models;

import lombok.Getter;
import lombok.Setter;

public class MediaLogs {
    @Getter @Setter public String LogText;

    public MediaLogs(String text) {
        LogText = text;
    }
}
