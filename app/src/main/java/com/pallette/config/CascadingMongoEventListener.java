/**
 * 
 */
package com.pallette.config;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mapping.MappingException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.util.ReflectionUtils;

public class CascadingMongoEventListener extends AbstractMongoEventListener {
	
	@Autowired
	private MongoOperations mongoOperations;

	public void onBeforeConvert(final Object source) {
		ReflectionUtils.doWithFields(source.getClass(), new ReflectionUtils.FieldCallback() {

			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				ReflectionUtils.makeAccessible(field);
	
				if (field.isAnnotationPresent(DBRef.class) && field.isAnnotationPresent(CascadeSave.class)) {
					final Object fieldValue = field.get(source);
					
					if (fieldValue instanceof List<?>) {
						for (Object item : (List<?>) fieldValue) {
							checkNSave(item);
						}
					} else {
						checkNSave(fieldValue);
					}

				}
			}
		}
	);
	}
	
	private void checkNSave(Object fieldValue) {
		
		if (null == fieldValue)
			return;

		DbRefFieldCallback callback = new DbRefFieldCallback();
		ReflectionUtils.doWithFields(fieldValue.getClass(), callback);

		if (!callback.isIdFound()) {
			throw new MappingException("Oops, something went wrong. Child doesn't have @Id?");
		}

		mongoOperations.save(fieldValue);
	}

	private static class DbRefFieldCallback implements ReflectionUtils.FieldCallback {
		private boolean idFound;

		public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
			ReflectionUtils.makeAccessible(field);

			if (field.isAnnotationPresent(Id.class)) {
				idFound = true;
			}
		}

		public boolean isIdFound() {
			return idFound;
		}
	}
}
