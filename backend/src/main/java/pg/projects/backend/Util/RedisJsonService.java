package pg.projects.backend.Util;

import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class RedisJsonService {

    public  final ObjectMapper mapper = new ObjectMapper();

    public <T> T convertToObject(String JSON, Class<T> Class){
        if(JSON==null) return null;
        return mapper.readValue(JSON, Class);
    }

    public String convertToString(Object object){
        return mapper.writeValueAsString(object);
    }

}
