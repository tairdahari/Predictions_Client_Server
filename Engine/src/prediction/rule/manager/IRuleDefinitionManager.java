package prediction.rule.manager;

import prediction.rule.IRule;

import java.io.Serializable;
import java.util.Map;

public interface IRuleDefinitionManager extends Serializable {
    Map<String, IRule> getRules();
}