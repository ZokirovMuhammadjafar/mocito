package jafar.mockaroo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springdoc.api.annotations.ParameterObject;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
public class Comes implements Serializable {

    private List<FakeDto>dtos;
    private int size;
    private FileType type;


}
