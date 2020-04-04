package com.pallette.solr.products.search.products;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "search")
@Configuration
public class FacetConfig {

	private List<Facet> facets = new ArrayList<Facet>();

	public List<Facet> getFacets() {
		return facets;
	}

	public void setFacets(List<Facet> facets) {
		this.facets = facets;
	}

	public static class Facet {

		private String id;
		private String value;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "Facet{" + "id='" + id + '\'' + ", value='" + value + '\'' + '}';
		}
	}
}
