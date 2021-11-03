package seedu.duke.storage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import seedu.duke.data.Catalogue;
import seedu.duke.data.Item;
import seedu.duke.data.Audio;
import seedu.duke.data.Book;
import seedu.duke.data.Magazine;
import seedu.duke.data.Miscellaneous;
import seedu.duke.data.Video;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@@author exetr

/**
 * The JsonFactory class handles all logic pertaining to serialization and deserialization of the catalogue.
 */
public class JsonFactory {
    private static final String KEY_AUDIO = "audio";
    private static final String KEY_BOOK = "book";
    private static final String KEY_ITEM = "item";
    private static final String KEY_MAGAZINE = "magazine";
    private static final String KEY_VIDEO = "video";
    private static final ObjectMapper mapper = new ObjectMapper();
    private final ObjectNode allItems;

    /**
     * Default constructor, configures relevant parameters of the Object Mapper.
     */
    public JsonFactory() {
        //Disable serializing dates as timestamp
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //Exclude null values
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //Specify time module
        JavaTimeModule timeModule = new JavaTimeModule();
        mapper.registerModule(timeModule);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        this.allItems = mapper.createObjectNode();
    }

    public String toJson(Catalogue catalogue) throws JsonProcessingException {
        allItems.set(KEY_AUDIO, audioToJson(catalogue));
        allItems.set(KEY_BOOK, bookToJson(catalogue));
        allItems.set(KEY_ITEM, itemToJson(catalogue));
        allItems.set(KEY_MAGAZINE, magazineToJson(catalogue));
        allItems.set(KEY_VIDEO, videoToJson(catalogue));
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(allItems);
    }

    public ArrayList<Item> fromJson(File data) throws IOException, NullPointerException {
        ArrayList<Item> itemArrayList = new ArrayList<>();

        ObjectNode json = mapper.readValue(data, ObjectNode.class);
        itemArrayList.addAll(jsonToAudio((ArrayNode) json.get(KEY_AUDIO)));
        itemArrayList.addAll(jsonToBook((ArrayNode) json.get(KEY_BOOK)));
        itemArrayList.addAll(jsonToItem((ArrayNode) json.get(KEY_ITEM)));
        itemArrayList.addAll(jsonToMagazine((ArrayNode) json.get(KEY_MAGAZINE)));
        itemArrayList.addAll(jsonToVideo((ArrayNode) json.get(KEY_VIDEO)));

        return itemArrayList;
    }

    private ArrayNode audioToJson(Catalogue catalogue) {
        ArrayNode audioArray = mapper.createArrayNode();
        List<Audio> audioObjects = catalogue.getAllItems().stream().filter(p -> p instanceof Audio)
                .map(p -> (Audio) p).collect(Collectors.toList());
        for (Audio oneItem : audioObjects) {
            audioArray.add(mapper.convertValue(oneItem, ObjectNode.class));
        }
        return audioArray;
    }

    private ArrayList<Audio> jsonToAudio(ArrayNode arrayNode) throws JsonProcessingException {
        ArrayList<Audio> itemList = new ArrayList<>();
        for (JsonNode item : arrayNode) {
            itemList.add(mapper.treeToValue(item, Audio.class));
        }
        return itemList;
    }

    private ArrayNode bookToJson(Catalogue catalogue) {
        ArrayNode bookArray = mapper.createArrayNode();
        List<Book> bookObjects = catalogue.getAllItems().stream().filter(p -> p instanceof Book)
                .map(p -> (Book) p).collect(Collectors.toList());
        for (Book oneItem : bookObjects) {
            bookArray.add(mapper.convertValue(oneItem, ObjectNode.class));
        }
        return bookArray;
    }

    private ArrayList<Book> jsonToBook(ArrayNode arrayNode) throws JsonProcessingException {
        ArrayList<Book> itemList = new ArrayList<>();
        for (JsonNode item : arrayNode) {
            itemList.add(mapper.treeToValue(item, Book.class));
        }
        return itemList;
    }

    private ArrayNode itemToJson(Catalogue catalogue) {
        ArrayNode itemArray = mapper.createArrayNode();
        List<Miscellaneous> itemObjects = catalogue.getAllItems().stream().filter(p -> p instanceof Miscellaneous)
                .map(p -> (Miscellaneous) p).collect(Collectors.toList());
        for (Miscellaneous oneItem : itemObjects) {
            itemArray.add(mapper.convertValue(oneItem, ObjectNode.class));
        }
        return itemArray;
    }

    private ArrayList<Miscellaneous> jsonToItem(ArrayNode arrayNode) throws JsonProcessingException {
        ArrayList<Miscellaneous> itemList = new ArrayList<>();
        for (JsonNode item : arrayNode) {
            itemList.add(mapper.treeToValue(item, Miscellaneous.class));
        }
        return itemList;
    }

    private ArrayNode magazineToJson(Catalogue catalogue) {
        ArrayNode magazineArray = mapper.createArrayNode();
        List<Magazine> magazineObjects = catalogue.getAllItems().stream().filter(p -> p instanceof Magazine)
                .map(p -> (Magazine) p).collect(Collectors.toList());
        for (Magazine oneItem : magazineObjects) {
            magazineArray.add(mapper.convertValue(oneItem, ObjectNode.class));
        }
        return magazineArray;
    }

    private ArrayList<Magazine> jsonToMagazine(ArrayNode arrayNode) throws JsonProcessingException {
        ArrayList<Magazine> itemList = new ArrayList<>();
        for (JsonNode item : arrayNode) {
            itemList.add(mapper.treeToValue(item, Magazine.class));
        }
        return itemList;
    }

    private ArrayNode videoToJson(Catalogue catalogue) {
        ArrayNode videoArray = mapper.createArrayNode();
        List<Video> videoObjects = catalogue.getAllItems().stream().filter(p -> p instanceof Video)
                .map(p -> (Video) p).collect(Collectors.toList());
        for (Video oneItem : videoObjects) {
            videoArray.add(mapper.convertValue(oneItem, ObjectNode.class));
        }
        return videoArray;
    }

    private ArrayList<Video> jsonToVideo(ArrayNode arrayNode) throws JsonProcessingException {
        ArrayList<Video> itemList = new ArrayList<>();
        for (JsonNode item : arrayNode) {
            itemList.add(mapper.treeToValue(item, Video.class));
        }
        return itemList;
    }
}