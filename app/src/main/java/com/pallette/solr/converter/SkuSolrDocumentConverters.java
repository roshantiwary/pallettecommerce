package com.pallette.solr.converter;

import java.io.IOException;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pallette.browse.documents.SkuDocument;

public class SkuSolrDocumentConverters {

	private static final Logger logger = LoggerFactory.getLogger(SkuSolrDocumentConverters.class);

	@WritingConverter
	  public enum SkuDocumentToStringConverter implements Converter<SkuDocument, String> {
	      INSTANCE;
	      @Override
	      public String convert(final SkuDocument source) {
	          if (source == null) {
	              return null;
	          }
	          final ObjectMapper mapper = new ObjectMapper();

	          try {
	              return mapper.writeValueAsString(source);
	          } catch (final JsonProcessingException e) {
	              logger.error(MessageFormat.format("Unable to serialize to json; source: {0}", source), e);
	              return null;
//	              throw new BusinessException(ErrorCode.INTERNAL_ERROR, "Unable to serialize to json.");
	          }
	      }
	  }

	  @ReadingConverter
	  public enum StringToSkuDocumentConverter implements Converter<String, SkuDocument> {
	      INSTANCE;

	      @Override
	      public SkuDocument convert(final String source) {
	          if (source == null) {
	              return null;
	          }
	          try {
	              return new ObjectMapper().readValue(source, SkuDocument.class);
	          } catch (final IOException e) {
	              logger.error(MessageFormat.format("Unable to deserialize from json; source: {0}", source), e);
	              return null;
//	              throw new BusinessException(ErrorCode.INTERNAL_ERROR, "Unable to deserialize from json.");
	          }
	      }
	  }
}