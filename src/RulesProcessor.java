/**
 * 
 */
package com.elasticpath.rules;

import java.io.File;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.Optional;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

/**
 * @author orlei
 *
 */
public class RulesProcessor {
	
	private File rule;
	private String drlFile;

	private boolean isCompiled = false;

	private boolean isFired = false;

	private KieSession kieSession;

	private RulesProcessor(Builder builder) {
		rule = builder.rule.get();
		drlFile = builder.drlFile.get();
	}
	
	public RulesProcessor compile() {
		try {
			// KieServices is the factory for all KIE services
			KieServices kieServices = KieServices.Factory.get();
			KieFileSystem kFileSystem = kieServices.newKieFileSystem();

			//Load the rules into the file system.
			Resource resource = kieServices.getResources().
					newFileSystemResource(rule).
					setResourceType(ResourceType.DRL);
			kFileSystem.write(drlFile, resource);

			kieServices.newKieBuilder(kFileSystem).buildAll();
			
			// kieModule is automatically deployed to KieRepository if successfully built.
			KieContainer kc = kieServices.
					newKieContainer(kieServices.getRepository().getDefaultReleaseId());
			
			kieSession = kc.newKieSession();
			
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

	public static Builder builder() {
		return new Builder();
	}
	
	static public class Builder {
		private Optional<String> drlFile;
		private Optional<File> rule;
		
		/**
		 * @param packageRules the packageRules to set
		 */
		public Builder drlFile(String drlFile) {
			this.drlFile = Optional.ofNullable(drlFile);
			
			if (this.drlFile.isPresent()) {
				URL url = getClass().getResource(this.drlFile.get());
				
				if (Objects.isNull(url)) {
					throw new RuntimeException("DRL file not found.");
				}
				
				rule = Optional.ofNullable(new File(url.getFile()));
			}
			return this;
		}
		
		public RulesProcessor build() {
			valid();
			
			return new RulesProcessor(this);
		}
		
		private void valid() {
			if (!this.drlFile.isPresent()) {
				throw new InvalidParameterException("Package rules is invalid. value: " + 
						this.drlFile.get());
			}
			if (!this.rule.isPresent() && !this.rule.get().exists()) {
				throw new InvalidParameterException("Package rules not found.");
			}
		}
	}
}
