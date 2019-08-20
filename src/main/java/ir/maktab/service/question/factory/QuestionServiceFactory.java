package ir.maktab.service.question.factory;

import ir.maktab.service.base.BaseService;
import ir.maktab.service.question.descriptiveQuestion.DescriptiveQuestionService;
import ir.maktab.service.question.testQuestion.TestQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionServiceFactory {

    @Autowired
    TestQuestionService testQuestionService;

    @Autowired
    DescriptiveQuestionService descriptiveQuestionService;


    public BaseService getBaseService(String type){

        if (type.equals("TestQuestion"))
            return testQuestionService;
        else if (type.equals("DescriptiveQuestion"))
            return descriptiveQuestionService;

        return null;
    }
}
