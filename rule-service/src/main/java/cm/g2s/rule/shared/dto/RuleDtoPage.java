package cm.g2s.rule.shared.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class RuleDtoPage extends PageImpl<RuleDto> implements Serializable {

    static final long serialVersionUID = 3791518797373151017L;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RuleDtoPage(@JsonProperty("content") List<RuleDto> content,
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

    public RuleDtoPage(List<RuleDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public RuleDtoPage(List<RuleDto> content) {
        super(content);
    }
}
