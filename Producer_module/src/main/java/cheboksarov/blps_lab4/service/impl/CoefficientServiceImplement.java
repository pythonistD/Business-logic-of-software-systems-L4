package cheboksarov.blps_lab4.service.impl;

import cheboksarov.blps_lab4.exceptions.CoefficientNotFoundException;
import cheboksarov.blps_lab4.model.Coefficient;
import cheboksarov.blps_lab4.repository.CoefficientRepository;
import cheboksarov.blps_lab4.service.CoefficientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CoefficientServiceImplement implements CoefficientService {
    private CoefficientRepository coefficientRepository;
    @Override
    public Coefficient findById(Long coeff_id) throws CoefficientNotFoundException {
        Optional<Coefficient> coeffOp = coefficientRepository.findById(coeff_id);
        if (coeffOp.isPresent()){
            return coeffOp.get();
        }
        throw new CoefficientNotFoundException(String.format("No Coefficient with this coeff_id: %d", coeff_id));
    }

    @Override
    public Coefficient saveCoefficient(Coefficient coefficient) {
        return coefficientRepository.save(coefficient);
    }

    @Override
    public Coefficient createDefaultCoefficient() {
        Coefficient coefficient = Coefficient.builder().guests_wins(1f)
                .hosts_wins(1f).total_one(1f).total_two(2f).total_three(3f).build();
        return coefficientRepository.save(coefficient);
    }

    @Override
    public Coefficient updateCoefficient(Coefficient coefficient) {
        return null;
    }

    @Override
    public void deleteCoefficient(Long coeffId) {

    }
}
