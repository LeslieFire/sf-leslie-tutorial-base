package ds.serv.bubo.jpmml;

import com.google.common.collect.RangeSet;
import com.sun.javafx.sg.prism.NGShape;
import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.dmg.pmml.Value;
import org.jpmml.evaluator.*;
import org.jpmml.model.PMMLUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * JpmmlDemo
 *
 * @author Leslie
 * @since 2016/11/28
 */
public class JpmmlDemo {

    public static void main(String[] args) {
        PMML pmml;
        try(InputStream is = new FileInputStream("estimator.pmml")){
            pmml = PMMLUtil.unmarshal(is);
            ModelEvaluatorFactory factory = ModelEvaluatorFactory.newInstance();
            ModelEvaluator<?> modelEvaluator = factory.newModelEvaluator(pmml);
            Evaluator evaluator = (Evaluator) modelEvaluator;

//            // query and analyze input fields
//            List<InputField> inputFields = evaluator.getInputFields();
//            for(InputField inputField : inputFields){
//                org.dmg.pmml.DataField pmmlDataField = (org.dmg.pmml.DataField)inputField.getField();
//                org.dmg.pmml.MiningField pmmlMiningField = inputField.getMiningField();
//
//                org.dmg.pmml.DataType dataType = inputField.getDataType();
//                org.dmg.pmml.OpType opType = inputField.getOpType();
//
//                switch(opType){
//                    case CONTINUOUS:
//                        RangeSet<Double> validArgumentRanges = FieldValueUtil.getValidRanges(pmmlDataField);
//                        break;
//                    case CATEGORICAL:
//                    case ORDINAL:
//                        List<Value> validArgumentValues = FieldValueUtil.getValidValues(pmmlDataField);
//                        break;
//                    default:
//                        break;
//                }
//            }
//            // Querying and analyzing target fields:
//            List<TargetField> targetFields = evaluator.getTargetFields();
//            for(TargetField targetField : targetFields){
//                org.dmg.pmml.DataField pmmlDataField = targetField.getDataField();
//                org.dmg.pmml.MiningField pmmlMiningField = targetField.getMiningField(); // Could be null
//                org.dmg.pmml.Target pmmlTarget = targetField.getTarget(); // Could be null
//
//                org.dmg.pmml.DataType dataType = targetField.getDataType();
//                org.dmg.pmml.OpType opType = targetField.getOpType();
//
//                switch(opType){
//                    case CONTINUOUS:
//                        break;
//                    case CATEGORICAL:
//                    case ORDINAL:
//                        List<Value> validResultValues = FieldValueUtil.getValidValues(pmmlDataField);
//                        break;
//                    default:
//                        break;
//                }
//            }
//
//
//            // Querying and analyzing output fields:
//            List<OutputField> outputFields = evaluator.getOutputFields();
//            for(OutputField outputField : outputFields){
//                org.dmg.pmml.OutputField pmmlOutputField = outputField.getOutputField();
//
//                org.dmg.pmml.DataType dataType = outputField.getDataType(); // Could be null
//                org.dmg.pmml.OpType opType = outputField.getOpType(); // Could be null
//
//                boolean finalResult = outputField.isFinalResult();
//                if(!finalResult){
//                    continue;
//                }
//            }

            Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();

            List<InputField> inputFields = evaluator.getInputFields();
            for(InputField inputField : inputFields){
                FieldName inputFieldName = inputField.getName();

                // The raw (ie. user-supplied) value could be any Java primitive value
                Object rawValue = (int) Math.random();

                // The raw value is passed through: 1) outlier treatment, 2) missing value treatment, 3) invalid value treatment and 4) type conversion
                FieldValue inputFieldValue = inputField.prepare(rawValue);

                arguments.put(inputFieldName, inputFieldValue);
            }

            Map<FieldName, ?> results = evaluator.evaluate(arguments);
            List<TargetField> targetFields = evaluator.getTargetFields();
            for(TargetField targetField : targetFields){
                FieldName targetFieldName = targetField.getName();

                Object targetFieldValue = results.get(targetFieldName);
                System.out.println(targetFieldValue);
            }

        }catch (Exception e){
            e.printStackTrace();
        }




    }
}
