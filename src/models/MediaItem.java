package models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class MediaItem {

    public MediaItem(String name) {
        Name = name;
    }

    @Getter @Setter public String Name;
    @Getter @Setter public String Url;
    @Getter @Setter public String Annotation;
    @Getter @Setter public LocalDateTime CreationTime;
}
