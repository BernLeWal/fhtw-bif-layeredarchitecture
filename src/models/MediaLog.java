package models;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class MediaLog {

    @Getter @Setter public Integer Id;
    @Getter @Setter public String LogText;
    @Getter @Setter public MediaItem LogMediaItem;

    public MediaLog(Integer id, String logText, MediaItem loggedItem) {
        this.Id = id;
        this.LogText = logText;
        this.LogMediaItem = loggedItem;
    }
}
