/**
 * 
 */
package com.elasticpath.rules;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.drools.core.event.AfterActivationFiredEvent;
import org.drools.core.marshalling.impl.ProtobufMessages.Activation;
import org.kie.api.KieServices;
import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 * @author orlei
 *
 */
public class RulesProcessor {
	
	private String sessionName;

	private boolean isCompiled = false;

	private boolean isFired = false;

	private KieSession kieSession;

	private RulesProcessor(Builder builder) {
		sessionName = builder.sessionName.get();
	}
	
	public RulesProcessor compile() {
		try {
	        // KieServices is the factory for all KIE services 
	        KieServices ks = KieServices.Factory.get();
	        
	        // From the kie services, a container is created from the classpath
	        KieContainer kContainer = ks.getKieClasspathContainer();
			
			kieSession = kContainer.newKieSession(sessionName);
			
			kieSession.addEventListener(new TrackingAgendaEventListener());

			isCompiled = true;
		} catch (Exception e) {
			isCompiled = false;
			
			throw new RuntimeException("Compile process failed.", e);
		}
		
		return this;
	}

	public void fire() {
		if (!isCompiled) {
			throw new RuntimeException("No rules engine were found. Please, performed compile method.");
		}
		
		kieSession.fireAllRules();
		
		isFired = true;
	}

	public void close() {
		if (!isFired) {
			return;
		}
		
		kieSession.destroy();
		kieSession.dispose();
	}
	
	public FactHandle addFact(Object fact) {
		if (Objects.nonNull(kieSession)) {
			return kieSession.insert(fact);
		}
		throw new RuntimeException("No rules engine were found.");
	}

	public FactHandle getFact(Object fact) {
		if (Objects.nonNull(kieSession)) {
			return kieSession.getFactHandle(fact);
		}
		throw new RuntimeException("No rules engine were found.");
	}
	
	public class TrackingAgendaEventListener extends DefaultAgendaEventListener {

	    private List<Activation> activationList = new ArrayList<Activation>();

	    public void afterActivationFired(AfterActivationFiredEvent event) {
	        Rule rule = event.getActivation().getRule();

	        String ruleName = rule.getName();
	        Map<String, Object> ruleMetaDataMap = rule.getMetaData();

	        //activationList.add(new Activation(ruleName));
	        StringBuilder sb = new StringBuilder("Rule fired: " + ruleName);

	        if (ruleMetaDataMap.size() > 0) {
	            sb.append("\n  With [" + ruleMetaDataMap.size() + "] meta-data:");
	            for (String key : ruleMetaDataMap.keySet()) {
	                sb.append("\n    key=" + key + ", value="
	                        + ruleMetaDataMap.get(key));
	            }
	        }

	        System.out.println(sb.toString());
	    }

	    public boolean isRuleFired(String ruleName) {
	        for (Activation a : activationList) {
	            if (a.getRuleName().equals(ruleName)) {
	                return true;
	            }
	        }
	        return false;
	    }

	    public void reset() {
	        activationList.clear();
	    }

	    public final List<Activation> getActivationList() {
	        return activationList;
	    }

	    public String activationsToString() {
	        if (activationList.size() == 0) {
	            return "No activations occurred.";
	        } else {
	            StringBuilder sb = new StringBuilder("Activations: ");
	            for (Activation activation : activationList) {
	                sb.append("\n  rule: ").append(activation.getRuleName());
	            }
	            return sb.toString();
	        }
	    }

	}
	public static Builder builder() {
		return new Builder();
	}
	
	static public class Builder {
		private Optional<String> sessionName;
		
		/**
		 * @param packageRules the packageRules to set
		 */
		public Builder sessionName(String sessionName) {
			this.sessionName = Optional.ofNullable(sessionName);
			
			return this;
		}
		
		public RulesProcessor build() {
			valid();
			
			return new RulesProcessor(this);
		}
		
		private void valid() {
			if (!this.sessionName.isPresent()) {
				throw new InvalidParameterException("Session name is invalid. value: " + 
						this.sessionName.get());
			}
		}
	}
}
