package com.simonc312.searchnyt.mixins;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simonc312.searchnyt.models.MediaMetaData;
import com.simonc312.searchnyt.models.SearchArticle;

import java.io.IOException;
import java.util.List;

/**
 * Created by Simon on 2/17/2016.
 */
public class SearchArticleDeserializer extends JsonDeserializer<SearchArticle>{

    @Override
    public SearchArticle deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();
        String section = node.get("section_name").asText();
        String headline = node.get("headline").get("main").asText();
        String url = node.get("web_url").asText();
        JsonNode bylineNode = node.get("byline").get("original");
        String byline = bylineNode != null ? bylineNode.asText() : "";
        String pubDate = node.get("pub_date").asText();
        JsonNode leadParagraph = node.get("lead_paragraph");
        String summary = leadParagraph.isNull() ? node.get("snippet").asText() : leadParagraph.asText();
        String wordCount = node.get("word_count").asText();
        JsonNode mediaNode = node.get("multimedia");
        ObjectMapper mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .addMixIn(MediaMetaData.class, SearchMediaMixin.class);
        List<MediaMetaData> media = mapper.reader().forType(
                new TypeReference<List<MediaMetaData>>() {})
                .readValue(mediaNode);
        return new SearchArticle(section,headline,url,byline,pubDate,summary,media,wordCount);
    }
}
