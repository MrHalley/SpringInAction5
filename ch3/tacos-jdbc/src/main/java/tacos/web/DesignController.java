package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.Ingredient;
import tacos.Taco;

import javax.validation.Valid;

import static tacos.Ingredient.Type;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/design")
public class DesignController {
    @ModelAttribute
    public void addIngredientsToModel(Model model){
        List<Ingredient> ingredientList = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE)
        );
        //将原材料按类型存入Model
        Type[] types = Type.values();
        for(Type type : types){
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredientList,type));
        }
        //将原材料的所有类型存入Model
        List<String> typesList = Arrays.stream(types).map(type -> type.toString().toLowerCase()).collect(Collectors.toList());
        model.addAttribute("types",typesList);

        //穿件一个空的Taco实例存入Model
        model.addAttribute("design",new Taco());
    }
    @GetMapping
    public String design(){
        return "design";
    }

    @PostMapping
    public String process(@Valid @ModelAttribute("design") Taco design, Errors errors,Model model){
        if(errors.hasErrors()){
            log.error("The taco has some error"+design);
            return "design";
        }
        log.info("process the design:"+design);
        return "redirect:/orders/current";
    }
    /**
     * 按类型将筛选原材料
     * @param ingredients
     * @param type
     * @return
     */
    public List<Ingredient> filterByType(List<Ingredient> ingredients,Type type){
        return ingredients.stream().filter(ingredient -> ingredient.getType().equals(type)).collect(Collectors.toList());
    }
}
