package com.pcl.core.swagger;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
	private boolean enabled;
	private String title;
	private String description;
	private String version;
	private List<GlobalParameter> globalParameters;

	public static class GlobalParameter {
		private String name;
		private String description;
		private String parameterType;
		private String defaultValue;
		private List<String> allowedValues;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDefaultValue() {
			return defaultValue;
		}

		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public List<String> getAllowedValues() {
			return allowedValues;
		}

		public void setAllowedValues(List<String> allowedValues) {
			this.allowedValues = allowedValues;
			this.defaultValue = allowedValues.get(0);
		}

		public String getParameterType() {
			return parameterType;
		}

		public void setParameterType(String paramType) {
			this.parameterType = paramType;
		}

		@Override
		public String toString() {
			return "GlobalParameter [name=" + name + ", description=" + description + ", paramType=" + parameterType
					+ ", defaultValue=" + defaultValue + ", allowedValues=" + allowedValues + "]";
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public List<GlobalParameter> getGlobalParameters() {
		return globalParameters;
	}

	public void setGlobalParameters(List<GlobalParameter> globalParameters) {
		this.globalParameters = globalParameters;
	}



	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}



}
