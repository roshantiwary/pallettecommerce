package com.pallette.solr;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@EnableSolrRepositories(basePackages={"com.pallette.solr.repository"}, multicoreSupport=true)
public class SolrContext {

}
