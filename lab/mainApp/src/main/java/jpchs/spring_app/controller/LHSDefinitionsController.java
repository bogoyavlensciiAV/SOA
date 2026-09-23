package jpchs.spring_app.controller;

import jpchs.spring_app.dto.LHSDefinitionDTO;
import jpchs.spring_app.util.LHSDefinitionHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("study-groups/lhs-definitions")
public class LHSDefinitionsController {

    private final Map<String, LHSDefinitionHelper.LHSDefinition> lhsDefinitionMap;

    @GetMapping
    public List<LHSDefinitionDTO> getDefinitions() {
        return lhsDefinitionMap.entrySet()
                .stream()
                .map(e -> new LHSDefinitionDTO(
                        e.getKey(),
                        e.getValue().ops(),
                        e.getValue().type()
                )).toList();
    }
}
