package cm.g2s.partner.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class PartnerDtoPage extends PageImpl<PartnerDto> implements Serializable {

    static final long serialVersionUID = -725289635573526970L;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PartnerDtoPage(@JsonProperty("content") List<PartnerDto> content,
                          @JsonProperty("number") int number,
                          @JsonProperty("size") int size,
                          @JsonProperty("totalElements") Long totalElements,
                          @JsonProperty("pageable") JsonNode pageable,
                          @JsonProperty("last") boolean last,
                          @JsonProperty("totalPages") int totalPages,
                          @JsonProperty("sort") JsonNode sort,
                          @JsonProperty("first") boolean first,
                          @JsonProperty("numberOfElements") int numberOfElements) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    public PartnerDtoPage(List<PartnerDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PartnerDtoPage(List<PartnerDto> content) {
        super(content);
    }
}
