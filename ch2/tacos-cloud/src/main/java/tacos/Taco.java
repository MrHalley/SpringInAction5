package tacos;

import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Data
public class Taco {
    @NotNull
    @Size(min=5,message = "name must be at least 5 character long")
    private String name;
    @Size(min = 2,message = "The ingredient should be choose at least two")
    private List<String> ingredients;
}
