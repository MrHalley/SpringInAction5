package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tacos.Ingredient;
import tacos.Taco;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.Inflater;

import static tacos.Ingredient.Type;

@Controller
@Slf4j
@RequestMapping("/design")
public class DesignController {

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        //初始化得到所有原材料
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
        Type[] types = Ingredient.Type.values();
        for(Type type : types){
            model.addAttribute(type.toString().toLowerCase(),filterByType(ingredientList,type));
        }
        //将原材料的类型存入Model,让页面遍历类型来创建标签
        List<String> typesList = Arrays.stream(types).map(type -> type.toString().toLowerCase()).collect(Collectors.toList());
        model.addAttribute("types",typesList);

        //创建一个空的Taco对象存入Model,用于接收页面用户选择的原材料
        model.addAttribute("design",new Taco());
    }
    @GetMapping
    public String design(){
        return "design";
    }

    /**
     * @ModelAttribute("design")是为了将页面传入的绑定的Taco对象存入
     * model的design中，若没有指定则会存入taco（对应Taco）中。
     * @param design
     * @param model
     * @return
     */
    @PostMapping
    public String process(@Valid @ModelAttribute("design") Taco design, Errors errors, Model model){
        if(errors.hasErrors()){
            log.error("design实例不合法");
            return "design";
        }
        log.info("process the taco:" + design);
        return "redirect:/orders/current";
    }

    /**
     * 按类型筛选原材料
     * @param ingredientList 被筛选的原材料集合
     * @param type 筛选的类型
     * @return 对应类型的原材料集合
     */
    public List<Ingredient> filterByType(List<Ingredient> ingredientList,Type type){
        return ingredientList.stream().filter(ingredient -> ingredient.getType().equals(type)).collect(Collectors.toList());
    }
}
