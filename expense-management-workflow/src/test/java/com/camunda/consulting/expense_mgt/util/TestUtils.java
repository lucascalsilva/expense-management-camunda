package com.camunda.consulting.expense_mgt.util;

import com.camunda.consulting.expense_mgt.model.common.Approver;
import com.camunda.consulting.expense_mgt.model.expenses.ExpenseReport;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

import java.util.List;
import java.util.stream.Collectors;

public class TestUtils {

    private static EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
            .seed(123L)
            .objectPoolSize(100)
            .stringLengthRange(4, 10)
            .collectionSizeRange(1, 10)
            .scanClasspathForConcreteTypes(true)
            .build();

    public static List<Approver> createRandomApprovers(){
        return enhancedRandom.objects(Approver.class, enhancedRandom.nextInt(10))
                .collect(Collectors.toList());
    }

    public static ExpenseReport createRandomExpenseReport(){
        return enhancedRandom.nextObject(ExpenseReport.class);
    }

}
