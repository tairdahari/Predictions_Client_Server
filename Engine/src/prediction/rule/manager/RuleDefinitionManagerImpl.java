package prediction.rule.manager;

import prediction.defenition.entity.manager.EntityDefinitionManager;
import prediction.rule.IRule;
import prediction.rule.RuleImpl;
import xmlJavaFXReader.schema.generated.PRDRule;
import xmlJavaFXReader.schema.generated.PRDRules;

import java.util.LinkedHashMap;
import java.util.Map;

public class RuleDefinitionManagerImpl implements IRuleDefinitionManager {
    private final Map<String, IRule> rules= new LinkedHashMap<>();

    public RuleDefinitionManagerImpl(PRDRules prdRules, EntityDefinitionManager entityManager) {

        for (PRDRule oneRule : prdRules.getPRDRule()) {
            IRule rule = new RuleImpl(oneRule, entityManager);
            rules.put(rule.getName(), rule);
        }
    }
    @Override
    public Map<String, IRule> getRules() {
        return this.rules;
    }
}