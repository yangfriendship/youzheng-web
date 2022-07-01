package me.youzheng.common.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class SimplePageSerializer extends JsonSerializer<Page> {

    @Override
    public void serialize(Page page, JsonGenerator gen, SerializerProvider serializers)
        throws IOException {
        Map<String, Object> result = new HashMap<>();
        List<?> content = page.getContent();
        Map<String, Object> pageInfo = new HashMap<>();
        Pageable pageable = page.getPageable();

        pageInfo.put("total", page.getTotalElements());
        pageInfo.put("size", pageable.getPageSize());
        pageInfo.put("page", pageable.getPageNumber());

        result.put("content", content);
        result.put("page", pageInfo);
        gen.writeObject(result);
    }

}