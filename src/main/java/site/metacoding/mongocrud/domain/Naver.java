package site.metacoding.mongocrud.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "navers")
public class Naver {

    // 해쉬로 알아서 들어간다.(전략이 필요없음)
    @Id
    private String _id;
    private String company;
    private String title;
}
