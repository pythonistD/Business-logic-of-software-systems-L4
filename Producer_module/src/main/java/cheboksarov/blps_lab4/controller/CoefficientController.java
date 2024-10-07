package cheboksarov.blps_lab4.controller;

import cheboksarov.blps_lab4.model.Coefficient;
import cheboksarov.blps_lab4.service.CoefficientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/coeff")
@AllArgsConstructor
public class CoefficientController {
    private CoefficientService coefficientService;

    @GetMapping("/{coeff_id}")
    public Coefficient findById(@PathVariable Long coeff_id){
        return coefficientService.findById(coeff_id);
    }

    @PostMapping("save_coeff")
    public Coefficient save(@RequestBody Coefficient coefficient){
        return coefficientService.saveCoefficient(coefficient);
    }
}
