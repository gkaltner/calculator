package com.kaltner.calculator.adapters.entrypoints;

import com.kaltner.calculator.adapters.entrypoints.request.RequestOperation;
import com.kaltner.calculator.adapters.entrypoints.response.ResponseOperation;
import com.kaltner.calculator.core.application.Calculator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("calculator")
public class CalculatorController {

    private final Calculator calculator;

    public CalculatorController(Calculator calculator) {
        this.calculator = calculator;
    }

    @CrossOrigin
    @PostMapping
    public ResponseOperation executeOperation(@RequestBody RequestOperation requestOperation) {
        double result = Double.NaN;

        switch (requestOperation.getAction()) {
            case ADD:
                result = calculator.add(requestOperation.getOperator1(), requestOperation.getOperator2());
                break;
            case SUBTRACT:
                result = calculator.subtract(requestOperation.getOperator1(), requestOperation.getOperator2());
                break;
            case MULTIPLY:
                result = calculator.multiply(requestOperation.getOperator1(), requestOperation.getOperator2());
                break;
            case DIVIDE:
                result = calculator.divide(requestOperation.getOperator1(), requestOperation.getOperator2());
                break;
        }

        return new ResponseOperation(result);
    }
}
