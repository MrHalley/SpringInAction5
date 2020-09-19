package tacos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Taco {
    @NotNull
    @Size(min=5,message="Name must be at least 5 character long")
    private String name;
    @NotNull(message = "please designate the ingredient")
    @Size(min=2,message="Ingredients must be at least 2 ingredient having")
    private List<String> ingredients;
}
