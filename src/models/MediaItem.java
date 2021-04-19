package models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class MediaItem {

    @Getter @Setter public Integer Id;
    @Getter @Setter public String Name;
    @Getter @Setter public String Url;
    @Getter @Setter public String Annotation;
    @Getter @Setter public LocalDateTime CreationTime;

    public MediaItem(Integer id, String name, String annotation, String url, LocalDateTime creationTime) {
        this.Id = id;
        this.Name = name;
        this.Annotation = annotation;
        this.Url = url;
        this.CreationTime = creationTime;
    }
}
