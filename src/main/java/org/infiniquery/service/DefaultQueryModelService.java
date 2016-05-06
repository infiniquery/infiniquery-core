/*
* Copyright (c) 2015, Daniel Doboga
* All rights reserved.
* 	
* Redistribution and use in source and binary forms, with or without modification, 
* are permitted provided that the following conditions are met:
* 
*   1. Redistributions of source code must retain the above copyright notice, this 
*   list of conditions and the following disclaimer.
*   
*   2. Redistributions in binary form must reproduce the above copyright notice, this 
*   list of conditions and the following disclaimer in the documentation and/or other 
*   materials provided with the distribution.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
* ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
* IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
* INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
* NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
* PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
* ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
* POSSIBILITY OF SUCH DAMAGE.
*/

package org.infiniquery.service;

import static org.infiniquery.Constants.DEFAULT_DATE_FORMAT;
import static org.infiniquery.Constants.DEFAULT_DATE_FORMATTER;
import static org.infiniquery.Constants.DEFAULT_DATE_PATTERN;
import static org.infiniquery.Constants.DEFAULT_DATE_TIME_FORMAT;
import static org.infiniquery.Constants.DEFAULT_DATE_TIME_FORMATTER;
import static org.infiniquery.util.Utils.isEntity;
import static org.infiniquery.util.Utils.resolveClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.parsers.ParserConfigurationException;

import org.infiniquery.Constants;
import org.infiniquery.connector.JpaConnector;
import org.infiniquery.model.ConditionSeparatorKeyword;
import org.infiniquery.model.EntityAttribute;
import org.infiniquery.model.EntityAttributeOperator;
import org.infiniquery.model.ExecutableQuery;
import org.infiniquery.model.InfiniqueryContext;
import org.infiniquery.model.JpaEntity;
import org.infiniquery.model.LogicalQueryItem;
import org.infiniquery.model.UserInputControlType;
import org.infiniquery.model.decoder.AttributeOperator2UserInputControlMap;
import org.infiniquery.model.decoder.Type2OperatorMap;
import org.infiniquery.model.view.ConditionSeparatorNamesView;
import org.infiniquery.model.view.EntityAttributeDisplayNamesView;
import org.infiniquery.model.view.EntityAttributeOperatorNamesView;
import org.infiniquery.model.view.EntityDisplayNamesView;
import org.infiniquery.model.view.PossibleValuesView;
import org.infiniquery.model.view.QueryResultItem;
import org.infiniquery.model.view.QueryResultsView;

/**
 * Service empowering the interaction of above layers (e.g. frontend or MVC controllers) with the infiniquery model.
 * 
 * @author Daniel Doboga
 * @since 1.0.0
 */
public class DefaultQueryModelService implements QueryModelService {

    private DatabaseAccessService databaseAccessService;

    private SecurityService securityService = new DefaultSecurityService();

    private InfiniqueryContext dynamicQueryContextCache;

    /*
     * (non-Javadoc)
     * @see org.infiniquery.service.QueryModelService#setDatabaseAccessService(org.infiniquery.service.DatabaseAccessService)
     */
    public void setDatabaseAccessService(DatabaseAccessService databaseAccessService) {
        this.databaseAccessService = databaseAccessService;
    }

    /*
     * (non-Javadoc)
     * @see org.infiniquery.service.QueryModelService#getSecurityService()
     */
    public SecurityService getSecurityService() {
		return securityService;
	}

    /*
     * (non-Javadoc)
     * @see org.infiniquery.service.QueryModelService#setSecurityService(org.infiniquery.service.SecurityService)
     */
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	/*
	 * (non-Javadoc)
	 * @see org.infiniquery.service.QueryModelService#getDatabaseAccessService()
	 */
	public DatabaseAccessService getDatabaseAccessService() {
		return databaseAccessService;
	}

	/*
	 * (non-Javadoc)
	 * @see org.infiniquery.service.QueryModelService#getFindKeyword()
	 */
    @Override
	public String getFindKeyword() {
        InfiniqueryContext model = getDynamicQueryContext();
        return model.getFindKeyword();
	}

	/*
	 * (non-Javadoc)
	 * @see org.infiniquery.service.QueryModelService#getEntityDisplayNames()
	 */
    @Override
    public List<String> getEntityDisplayNames() {
        InfiniqueryContext model = getDynamicQueryContext();
        List<String> entityNames = new ArrayList<>();
        for(JpaEntity entity : model.getEntities()) {
            if(userAccessAllowed(entity)) {
                entityNames.add(entity.getDisplayName());
            }
        }
        return entityNames;
    }

    /*
     * (non-Javadoc)
     * @see org.infiniquery.service.QueryModelService#getEntityDisplayNamesView()
     */
    @Override
    public EntityDisplayNamesView getEntityDisplayNamesView() {
        return new EntityDisplayNamesView(getEntityDisplayNames().toArray(new String[0]));
    }

    /*
     * (non-Javadoc)
     * @see org.infiniquery.service.QueryModelService#getEntityAttributeDisplayNames(java.lang.String)
     */
    @Override
    public List<String> getEntityAttributeDisplayNames(String entityDisplayName) {
        InfiniqueryContext model = getDynamicQueryContext();
        List<String> attributeNames = new ArrayList<>();
        for(JpaEntity entity : model.getEntities()) {
            if(entityDisplayName.equals(entity.getDisplayName())) {
                for(EntityAttribute attribute : entity.getAttributes()) {
                    if( (!attribute.isDisplayOnly()) && userAccessAllowed(entity) && userAccessAllowed(attribute)) {
                        attributeNames.add(attribute.getDisplayName());
                    }
                }
            }
        }
        return attributeNames;
    }

    /*
     * (non-Javadoc)
     * @see org.infiniquery.service.QueryModelService#getEntityAttributeDisplayNamesView(java.lang.String)
     */
    @Override
    public EntityAttributeDisplayNamesView getEntityAttributeDisplayNamesView(String entityDisplayName) {
        return new EntityAttributeDisplayNamesView(getEntityAttributeDisplayNames(entityDisplayName).toArray(new String[0]));
    }

    /**
     * Get the names of the operators applicable for filtering the values of a particular entity attribute.
     * @param entityDisplayName the name of the entity
     * @param attributeDisplayName the name of the attribute from the entity
     * @return an array of {@link java.lang.String}
     */
    @Override
    public String[] getEntityAttributeOperatorNames(String entityDisplayName, String attributeDisplayName) {
        try {
            EntityAttribute attribute = resolveAttribute(entityDisplayName, attributeDisplayName);
            return getApplicableOperatorsDisplayNames(attribute).toArray(new String[0]);
        } catch (Exception e) {
            throw new InfiniqueryLoadError(e.getMessage(), e);
        }
    }

    /**
     * Get the information necessary in the user interface to display the names of the operators applicable for filtering the values of a particular entity attribute.
     * @param entityDisplayName the name of the entity
     * @param attributeDisplayName the name of the attribute from the entity
     * @return {@link org.infiniquery.model.view.EntityAttributeOperatorNamesView}
     */
    @Override
    public EntityAttributeOperatorNamesView getEntityAttributeOperatorNamesView(String entityDisplayName, String attributeDisplayName) {
        return new EntityAttributeOperatorNamesView(getEntityAttributeOperatorNames(entityDisplayName, attributeDisplayName));
    }

    /*
     * (non-Javadoc)
     * @see org.infiniquery.service.QueryModelService#getEntityAttributeOperatorValue(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public PossibleValuesView getEntityAttributeOperatorValue(String entityDisplayName, String attributeDisplayName, String operatorDisplayName) {
        try {
            PossibleValuesView possibleValuesView;
            EntityAttribute attribute = resolveAttribute(entityDisplayName, attributeDisplayName);
            Class<?> attributeType = resolveAttributeType(attribute);
            EntityAttributeOperator operator = EntityAttributeOperator.getByDisplayName(operatorDisplayName);
            UserInputControlType userInputControlType = AttributeOperator2UserInputControlMap.decode(attributeType, operator);

            if(attribute.getPossibleValuesQuery() != null) {
                List<?> possibleValues = databaseAccessService.retrieveReferenceData(attribute.getPossibleValuesQuery());
                possibleValuesView = PossibleValuesView.getInstance(userInputControlType, preparePossibleValues(possibleValues, attribute));
            } else {
                possibleValuesView = PossibleValuesView.getInstance(userInputControlType, null);
            }

            return possibleValuesView;
        } catch (Exception e) {
            throw new InfiniqueryLoadError(e.getMessage(), e);
        }
    }

    /**
     * Get the display names of all available condition separators keywords.
     * @return an array of {@link java.lang.String}
     */
    @Override
    public String[] getConditionSeparatorNames() {
        String[] conditionSeparatorDisplayNames = new String[ConditionSeparatorKeyword.values().length];

        int i = 0;
        for(ConditionSeparatorKeyword conditionSeparator : ConditionSeparatorKeyword.values()) {
            conditionSeparatorDisplayNames[i ++] = conditionSeparator.getDisplayName();
        }
        return conditionSeparatorDisplayNames;
    }

    /**
     * Get the information necessary to the user interface to display the available condition separator options.
     * @return {@link ConditionSeparatorNamesView}
     */
    @Override
    public ConditionSeparatorNamesView getConditionSeparatorNamesView() {
        return new ConditionSeparatorNamesView(getConditionSeparatorNames());
    }

    /**
     * Execute a query against the database.
     * @param executableQuery an instance of {@link org.infiniquery.model.ExecutableQuery} which needs to have filled in (at least) the logical dimension.
     * @return an instance of {@link org.infiniquery.model.view.QueryResultsView} containing the results of the query execution.
     */
    @Override
    public QueryResultsView executeQuery(ExecutableQuery executableQuery) {
        try {
            performSecurityChecks(executableQuery);
            compileJpql(executableQuery);
            QueryResultsView queryResults = runQuery(executableQuery);
            return queryResults;
        } catch (Exception e) {
            throw new InfiniqueryLoadError(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.infiniquery.service.QueryModelService#executeQuery(org.infiniquery.model.ExecutableQuery)
     */
    @Override
    public String compileQuery(ExecutableQuery executableQuery) {
        try {
            performSecurityChecks(executableQuery);
            compileJpql(executableQuery);
            return executableQuery.getJpqlDimension();
        } catch (Exception e) {
            throw new InfiniqueryLoadError(e.getMessage(), e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerSecurityService(SecurityService securityService) {
        if(securityService != null) {
            this.securityService = securityService;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.infiniquery.service.QueryModelService#reloadQueryContext()
     */
    public void reloadQueryContext() {
        try {
            dynamicQueryContextCache = JpaConnector.getDynamicQueryContext();
        } catch (ParserConfigurationException e) {
            throw new InfiniqueryLoadError("Unable to load dynamic query context.", e);
        }
    }

    /**
     * Run a given {@link org.infiniquery.model.ExecutableQuery} against the database.
     * @param executableQuery
     * @return
     */
    private QueryResultsView runQuery(ExecutableQuery executableQuery) {

		List<?> queryResults = databaseAccessService.executeQuery(executableQuery.getJpqlDimension(),
				executableQuery.getJpqlParams());

		String entityName = extractEntityDisplayName(executableQuery);
		JpaEntity entity;
		try {
			entity = resolveEntity(entityName);
		} catch (ParserConfigurationException e) {
			throw new RuntimeException(
					"Failed to run the query. Unable to resolve entity with displayName: " + entityName, e);
		}
		QueryResultsView queryResultsView;
		if (queryResults != null && !queryResults.isEmpty()) {
			queryResultsView = new QueryResultsView(queryResults.size());
			for (Object queryResult : queryResults) {
				QueryResultItem virtualInstance = new QueryResultItem();
				for (EntityAttribute attribute : entity.getAttributesInReverseOrder()) {
					if(userAccessAllowed(attribute)) {
						virtualInstance.add(attribute.getDisplayName(),
								readAttributeValue(attribute, entity, queryResult));
					}
				}
				queryResultsView.addEntity(virtualInstance);
			}
		} else {
			queryResultsView = new QueryResultsView(0);
		}
		queryResultsView.setEntityName(entityName);

		return queryResultsView;
    }

    /**
     * Fill, under the propertyLabel key, in the given "target" map, the value of the attribute denoted by the given propertyPath 
     * (e.g: children.address.street), also considering the multipleValueLabelAttribute for the cases where on the given path
     * is encountered a collection (or other {@link Iterable}).
     * @param propertyPath
     * @param source
     * @param target
     * @param propertyLabel
     * @param multipleValueLabelAttribute
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private static void fillProperty(String propertyPath, Object source, Map<String, Object> target, String propertyLabel, String multipleValueLabelAttribute) throws InvocationTargetException, IllegalAccessException {
        String[] pathFragments = propertyPath.split(".");
        if(pathFragments.length == 0) {
            pathFragments = new String[] {propertyPath};
        }

        for(int i=0; i<pathFragments.length; i++) {
            source = resolveGetterMethod(pathFragments[i], source.getClass()).invoke(source);
            if(source == null) {
                target.put(propertyLabel, "");
                return;
            } else if(source instanceof Iterable) {
                target.put(propertyLabel, "");
                for(Object item : (Iterable<?>) source) {
                    fillProperty(joinPathFragments(pathFragments, i, multipleValueLabelAttribute), item, target, propertyLabel, multipleValueLabelAttribute);
                }
                return;
            }
            if(i==pathFragments.length - 1) {
                if(target.containsKey(propertyLabel)) {
                    Object existingValue = target.get(propertyLabel);
                    Object newValue = "".equals(existingValue) ? source : existingValue + "; " + source;
                    target.put(propertyLabel, newValue);
                } else {
                    target.put(propertyLabel, source);
                }
            }
        }
    }

    private static String joinPathFragments(String[] fragments, int startIndex, String multipleValueLabelAttribute) {
        StringBuilder builder = new StringBuilder();
        for(int i=startIndex + 1; i<fragments.length; i++) {
            builder.append(fragments[i]);
            if(i != fragments.length -1) {
                builder.append('.');
            }
        }
        if(! (builder.length() == 0)) {
            builder.append('.');
        }
        builder.append(multipleValueLabelAttribute);
        return builder.toString();
    }

    private static Method resolveGetterMethod(String propertyName, Class clazz) {
        final String firstChar = propertyName.substring(0, 1);
        final String nonPrefixedGetter = propertyName.replaceFirst(firstChar, firstChar.toUpperCase());
        Method getterMethod;
        try {
        	getterMethod = clazz.getDeclaredMethod("get".concat(nonPrefixedGetter));
        } catch(NoSuchMethodException e) {
            try {
            	getterMethod = clazz.getDeclaredMethod("is".concat(nonPrefixedGetter));
            } catch (NoSuchMethodException e1) {
                throw new RuntimeException("Could not find public getter method for property " + propertyName + " in class " + clazz.getName());
            }
        }
        if(Modifier.isPublic(getterMethod.getModifiers())) {
            return getterMethod;
        } else {
        	throw new RuntimeException("Method " + getterMethod + " is not public. Infiniquery exposes, in the results, only entity attributes that are exposed through public getter methods. Please, either make the getter public, or remove this entity attribute from infiniquery config.");
        }
    }
    
    private static Object readAttributeValue(final EntityAttribute attribute, final JpaEntity entity, final Object instance) {
    	try {
	    	Class<?> clazz = Class.forName(entity.getClassName());
	    	
	    	Method getterMethod = resolveGetterMethod(attribute.getAttributeName(), clazz);
			Object value = getterMethod.invoke(instance);
			if(value != null) {
				if(attribute.getPossibleValueLabelAttributePath() != null) {
					Queue<String> objectTreePath = extractDotSeparatedPathFragments(attribute.getPossibleValueLabelAttributePath());
		    		objectTreePath.poll(); //remove the first entry - which is exactly the attribute name itself
					value = resolveAttributeValue(value, objectTreePath);
				}
			}
			return value;
		} catch (Exception e) {
			throw new RuntimeException("Failed to read attribute value from results, for attribute " + attribute.getAttributeName() + " of entity " + entity.getClassName(), e);
		}
    }
    
    private static Object resolveAttributeValue(Object object, Queue<String> objectTreePath) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    	if(object == null) {
    		return null;
    	}
    	if(Collection.class.isAssignableFrom(object.getClass())) {
    		List<Object> virtualObjects = new ArrayList<>();
			for(Object item : (Collection<?>) object) {
				virtualObjects.add(resolveAttributeValue(item, new LinkedList<String>(objectTreePath)));
			}
			return virtualObjects;
    	} else {
    		String attributeName = objectTreePath.poll();
    		Method getterMethod = resolveGetterMethod(attributeName, object.getClass());
	    	Object value = getterMethod.invoke(object);
	    	if(objectTreePath.isEmpty()) {
	    		return value;
	    	} else {
	    		return resolveAttributeValue(value, objectTreePath);
	    	}
    	}
    }
    
//    private static boolean isTypeAllowedForDisplay(Class<?> type) throws ClassNotFoundException {
//    	if(type.isPrimitive() || Number.class.isAssignableFrom(type) || String.class.equals(type)
//    			|| java.util.Date.class.isAssignableFrom(type) ) {
//    		return true;
//    	}
//    	
//    	//Resolve less likely implementation via reflection, in order to avoid forcing a compile-time dependency to those libraries.
//		final Class<?>[] rareTypes = new Class<?>[] { 
//				Class.forName("org.joda.time.ReadableInstant"),
//				Class.forName("java.time.temporal.Temporal") 
//		};
//    	for(Class<?> clazz : rareTypes) {
//    		if(clazz.isAssignableFrom(type)) {
//    			return true;
//    		}
//    	}
//
//		return false;
//    }

    private String extractEntityDisplayName(ExecutableQuery executableQuery) {
        for(LogicalQueryItem logicalQueryItem : executableQuery.getLogicalDimension()) {
            if(LogicalQueryItem.Type.entityName.name().equals(logicalQueryItem.getType())) {
                return logicalQueryItem.getDisplayValue();
            }
        }
        throw new RuntimeException("Logical query item doesn't contain an entity name: ".concat(executableQuery.getLogicalDimension().toString()));
    }

    /**
     * Create and fill into the given ExecutableQuery the JPQL dimension of it.
     *
     * @param executableQuery
     * @throws ParserConfigurationException
     * @throws NoSuchFieldException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws java.lang.reflect.InvocationTargetException
     */
    private void compileJpql(ExecutableQuery executableQuery) throws ParserConfigurationException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException, ParseException {
        LogicalQueryItem[] logicalDimension = executableQuery.getLogicalDimension();
        AtomicInteger aliasUnicityKey = new AtomicInteger(0); //increment and append to every alias, to ensure its uniqueness
        AtomicInteger joinAdditionsOffset = new AtomicInteger(0); //place in the query statement where to insert eventual joins with additional entities
        List<Object> queryParams = new ArrayList<>();
        StringBuilder jpqlStatement = new StringBuilder();
        JpaEntity jpaEntity = null;
        EntityAttribute lastAttribute = null;
        for(LogicalQueryItem logicalQueryItem : logicalDimension) {
            final String itemType = logicalQueryItem.getType();
            if(LogicalQueryItem.Type.findKeyword.name().equals(itemType)) {
                jpqlStatement.append("SELECT DISTINCT");
            } else if(LogicalQueryItem.Type.entityName.name().equals(itemType)) {
                jpaEntity = resolveEntity(logicalQueryItem.getDisplayValue());
                String entityClassName = jpaEntity.getClassName();
                jpqlStatement.append(" x FROM ").append(entityClassName).append(" x");
                joinAdditionsOffset.set(jpqlStatement.length());
            } else if(LogicalQueryItem.Type.havingKeyword.name().equals(itemType)) {
                jpqlStatement.append(" WHERE");
            } else if(LogicalQueryItem.Type.openBracket.name().equals(itemType)) {
                jpqlStatement.append(" (");
            } else if(LogicalQueryItem.Type.closeBracket.name().equals(itemType)) {
                jpqlStatement.append(" )");
            } else if(LogicalQueryItem.Type.entityAttributeName.name().equals(itemType)) {
                EntityAttribute attribute = resolveAttribute(jpaEntity, logicalQueryItem.getDisplayValue());
                appendEntityAttributeName(jpqlStatement, jpaEntity, attribute, aliasUnicityKey, joinAdditionsOffset);
                lastAttribute = attribute;
            } else if(LogicalQueryItem.Type.operatorKeyword.name().equals(itemType)) {
                EntityAttributeOperator operator = EntityAttributeOperator.getByDisplayName(logicalQueryItem.getDisplayValue());
                jpqlStatement.append(' ').append(operator.getValue());
            } else if(LogicalQueryItem.Type.entityAttributeValue.name().equals(itemType)) {
                QueryFragment queryFragment = parseEntityAttributeValue(lastAttribute, logicalQueryItem.getDisplayValue());
                jpqlStatement.append(' ').append(queryFragment.fragment);
                for(Object parameter : queryFragment.parameters) {
                    queryParams.add(parameter);
                }
            } else if(LogicalQueryItem.Type.conditionSeparatorKeyword.name().equals(itemType)) {
                ConditionSeparatorKeyword keyword = ConditionSeparatorKeyword.getByDisplayName(logicalQueryItem.getDisplayValue());
                jpqlStatement.append(' ').append(keyword.getValue());
            }
        }
        executableQuery.setJpqlDimension(jpqlStatement.toString());
        executableQuery.setJpqlParams(queryParams);
    }

    private QueryFragment parseEntityAttributeValue(EntityAttribute attribute, String displayedValue) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, ParseException {
        Class<?> valueClass = resolveAttributeType(attribute);
        if(String.class.equals(valueClass)) {
            return new QueryFragment("?", displayedValue);
        }
        Class<?> jodaDateTimeClass = resolveClass("org.joda.time.DateTime");
        if(jodaDateTimeClass != null && jodaDateTimeClass.equals(valueClass)) {
            return new QueryFragment("?", parseJodaDateTime(displayedValue));
        }
        if(java.sql.Date.class.isAssignableFrom(valueClass)) {
            return new QueryFragment("?", new java.sql.Date(DEFAULT_DATE_FORMAT.parse(displayedValue).getTime()));
        }
        if(java.time.LocalDate.class.isAssignableFrom(valueClass)) {
        	return new QueryFragment("?", LocalDate.parse(displayedValue, DEFAULT_DATE_FORMATTER));
        }
        if(java.util.Date.class.isAssignableFrom(valueClass)) {
            return new QueryFragment("?", DEFAULT_DATE_TIME_FORMAT.parse(displayedValue));
        }
        if(Collection.class.isAssignableFrom(valueClass) || isEntity(valueClass)) {
        	String[] values = displayedValue.split(",");
        	StringBuilder queryFragment = new StringBuilder("(");
        	for(String value : values) {
        		if(queryFragment.length() != 1) {
        			queryFragment.append(",");
        		}
        		queryFragment.append("?");
        	}
        	queryFragment.append(")");
        	return new QueryFragment(queryFragment.toString(), (Object[]) values);
            /* TODO  remove possibilities for predefined values in current version
                * if it has possibleValuesLabelAttribute and target entity attribute is entity type
                    - create inner select (from DB entity where label attribute in values)
                * if it has possibleValues
                    - return in <and values split by comma>
             */

        }
        //if not caught in above scenarios, then go looking for a static valueOf() method or a string-argument constructor
        Method publicStaticValueOfMethod = getPublicStaticValueOfMethod(valueClass);
        if(publicStaticValueOfMethod != null) {

            return new QueryFragment("?", publicStaticValueOfMethod.invoke(null, displayedValue));
        }
        Constructor<?> stringArgumentConstructor = getStringArgumentConstructor(valueClass);
        if(stringArgumentConstructor != null) {
            return new QueryFragment("?", stringArgumentConstructor.newInstance(displayedValue));
        }
        throw new UnsupportedOperationException("Unsupported attribute value type: " + valueClass);
    }

    private static Method getPublicStaticValueOfMethod(Class<?> clazz) {
        try {
            Method method = clazz.getMethod("valueOf", String.class);
            if(Modifier.isStatic(method.getModifiers())) {
                if(!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            }
        } catch (NoSuchMethodException e) {
            //do nothing
        }
        return null;
    }

    private static Constructor<?> getStringArgumentConstructor(Class<?> clazz) {
        try {
            Constructor<?>  constructor = clazz.getDeclaredConstructor(String.class);
            if(!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor;
        } catch (NoSuchMethodException e) {
            //do nothing
        }
        return null;
    }

    private EntityAttribute resolveAttribute(String entityDisplayName, String attributeDisplayName) {
        try {
            JpaEntity entity = resolveEntity(entityDisplayName);
            return resolveAttribute(entity, attributeDisplayName);
        } catch (Exception e) {
            throw new InfiniqueryLoadError(e.getMessage(), e);
        }
    }

    private EntityAttribute resolveAttribute(JpaEntity entity, String attributeDisplayName) throws ParserConfigurationException, NoSuchFieldException, ClassNotFoundException {
        for(EntityAttribute attribute : entity.getAttributes()) {
            if(attributeDisplayName.equals(attribute.getDisplayName())) {
                return attribute;
            }
        }
        throw new InfiniqueryLoadError("Attribute not found in configuration: " + entity.getDisplayName() + "." + attributeDisplayName);
    }

    private JpaEntity resolveEntity(String entityDisplayName) throws ParserConfigurationException {
        InfiniqueryContext model = getDynamicQueryContext();
        for(JpaEntity entity : model.getEntities()) {
            if(entityDisplayName.equals(entity.getDisplayName())) {
                return entity;
            }
        }
        throw new InfiniqueryLoadError("Entity not found in configuration: " + entityDisplayName);
    }

    private List<String> getApplicableOperatorsDisplayNames(EntityAttribute attribute) throws ClassNotFoundException, NoSuchFieldException {
        String attributeName = attribute.getAttributeName();
        Class<?> entityClass = Class.forName(attribute.getParentEntity().getClassName());
        Field field = entityClass.getDeclaredField(attributeName);
        Class<?> fieldType = field.getType();
        final EntityAttributeOperator[] applicableOperators = Type2OperatorMap.getApplicableOperatorsForType(fieldType);
        List<String> list = new ArrayList<String>() {{
            for(EntityAttributeOperator operator : applicableOperators) {
                add(operator.getDisplayName());
            }
        }};
        return list;
    }

    private Class<?> resolveAttributeType(EntityAttribute attribute) throws ClassNotFoundException, NoSuchFieldException {
        String entityClassName = attribute.getParentEntity().getClassName();
        Class<?> entityClass = Class.forName(entityClassName);
        Field attributeField = entityClass.getDeclaredField(attribute.getAttributeName());
        return attributeField.getType();
    }

    /**
     * Extract the effective possible values from the given entities.
     * @param originalItems
     * @param attribute
     * @return
     */
    private String[] preparePossibleValues(List originalItems, EntityAttribute attribute) {
        try {
            if(originalItems == null || originalItems.isEmpty()) {
                return new String[0];
            } else {
                Class<?> originalItemType = originalItems.get(0).getClass();

                Field labelField;
                if(attribute.getPossibleValueLabelAttribute() != null) {
                    labelField = originalItemType.getDeclaredField(attribute.getPossibleValueLabelAttribute());
                } else {
                    labelField = getTheFirstEncounteredStringField(originalItemType);
                }
                labelField.setAccessible(true);

                List<String> items = new ArrayList<String>(originalItems.size());
                for(Object originalItem : originalItems) {
                    items.add(labelField.get(originalItem).toString());
                }
                return items.toArray(new String[0]);
            }
        } catch (Exception e) {
            throw new InfiniqueryLoadError(e.getMessage(), e);
        }
    }

    private Field getTheFirstEncounteredStringField(Class clazz) {
        for(Field field : clazz.getDeclaredFields()) {
            ignoreField:
            if(String.class.equals(field.getType())) {
                for(Annotation annotation : field.getDeclaredAnnotations()) {
                    if(annotation.getClass().getName().toLowerCase().contains("transient")) {
                        break ignoreField;
                    }
                }
                return field;
            }
        }
        // if nothing returned until here, do the below:
        Class<?> superclass = clazz.getSuperclass();
        if(Object.class.equals(superclass)) {
            throw new RuntimeException("Unable to find any String field in object of class ");
        } else {
            return getTheFirstEncounteredStringField(superclass);
        }
    }

    private void performSecurityChecks(ExecutableQuery executableQuery) throws ParserConfigurationException, NoSuchFieldException, ClassNotFoundException {
        JpaEntity jpaEntity = null;
        for(LogicalQueryItem logicalQueryItem : executableQuery.getLogicalDimension()) {
            final String itemType = logicalQueryItem.getType();
            if (LogicalQueryItem.Type.entityName.name().equals(itemType)) {
                jpaEntity = resolveEntity(logicalQueryItem.getDisplayValue());
                if (! (userAccessAllowed(jpaEntity))) {
                    throw new SecurityException("Users with roles " + printableCurrentUserRoles() + " are not allowed to access entity " + jpaEntity.getDisplayName());
                }
            } else if (LogicalQueryItem.Type.entityAttributeName.name().equals(itemType)) {
                EntityAttribute attribute = resolveAttribute(jpaEntity, logicalQueryItem.getDisplayValue());
                if(! (userAccessAllowed(attribute))) {
                    throw new SecurityException("Users with roles " + printableCurrentUserRoles() + " are not allowed to access attribute " + attribute.getDisplayName() + " of entity " + jpaEntity.getDisplayName());
                }
            }
        }
    }

    /**
     * Check if the current logged user, as returned by securityService.getCurrentUserRole(), is allowed access to the given {@link org.infiniquery.model.JpaEntity}
     * @param entity
     * @return
     */
    private boolean userAccessAllowed(JpaEntity entity) {
        return currentUserMatchesAllowedRoles(entity.getRoleValues());
    }

    /**
     * Check if the current logged user, as returned by securityService.getCurrentUserRole(), is allowed access to the given attribute of the given entity.
     * @param attribute {@link org.infiniquery.model.EntityAttribute}
     * @return
     */
    private boolean userAccessAllowed(EntityAttribute attribute) {
        return currentUserMatchesAllowedRoles(attribute.getRoleValues());
    }

    private String printableCurrentUserRoles() {
        StringBuilder builder = new StringBuilder();
        for(String role : securityService.getCurrentUserRoles()) {
            if(builder.length() != 0) {
                builder.append(", ");
            }
            builder.append(role);
        }
        return builder.toString();
    }

    private InfiniqueryContext getDynamicQueryContext() {
        if(dynamicQueryContextCache == null) {
            reloadQueryContext();
        }
        return dynamicQueryContextCache;
    }

    /**
     * Parse a String input into an org.joda.time.DateTime object without forcing a compile-time dependency to joda dateTime library.
     * @param stringDate
     * @return
     */
    private Object parseJodaDateTime(final String stringDate) {
        try {
            final Class<?> jodaDateTimeClass = resolveClass("org.joda.time.DateTime");
            final Class<?> dateTimeFormatClass = resolveClass("org.joda.time.format.DateTimeFormat");
            final Class<?> dateTimeFormatterClass = resolveClass("org.joda.time.format.DateTimeFormatter");
            final Method parseMethod = jodaDateTimeClass.getMethod("parse", String.class, dateTimeFormatterClass);
            final Method forPatternMethod = dateTimeFormatClass.getMethod("forPattern", String.class);
            final Object formatter = forPatternMethod.invoke(null, Constants.DEFAULT_DATE_TIME_FORMAT);
            final Object jodaDateTimeObject = parseMethod.invoke(null, stringDate, formatter);
            return jodaDateTimeObject;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * Check if the current application user (as roles returned by securityService) has access permissions to a component where the allowed permissions are granted to given "allowedRoles".
     * @param allowedRoles the roles that are allowed access to the component we want to check the current user's access to.
     * @return
     */
    private boolean currentUserMatchesAllowedRoles(final Set<String> allowedRoles) {

        final Set<String> userRoles = securityService.getCurrentUserRoles();
        final Set<String> userRolesUppercased = new HashSet<String>() {{
            if(userRoles != null) {
                for (String role : userRoles) {
                    if (role != null) {
                        add(role.toUpperCase());
                    }
                }
            }
        }};
        final Set<String> allowedRolesUppercased = new HashSet<String>() {{
            for(String role : allowedRoles) {
                if(role != null){
                    add(role.toUpperCase());
                }
            }
        }};
        if(allowedRoles.isEmpty() || allowedRolesUppercased.contains("ALL") || haveMatchingPoint(allowedRolesUppercased, userRolesUppercased)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if two sets have (at least one) element in common (or, other said, that their intersection is not empty).
     * @param set1
     * @param set2
     * @return
     */
    private boolean haveMatchingPoint(Set<String> set1, Set<String> set2) {
        for(String value : set1) {
            if(set2.contains(value)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 
     * Append the entity attribute name to the JPQL query statement.
     * 
     * @param jpqlStatement
     * @param jpaEntity
     * @param attribute
     * @throws java.lang.SecurityException 
     * @throws NoSuchFieldException 
     * @throws ClassNotFoundException 
     */
    private void appendEntityAttributeName(StringBuilder jpqlStatement, JpaEntity jpaEntity, EntityAttribute attribute, AtomicInteger aliasUnicityKey, AtomicInteger joinAdditionsOffset) throws NoSuchFieldException, java.lang.SecurityException, ClassNotFoundException {
    	Class<?> entityClass = Class.forName(jpaEntity.getClassName());
    	Field field = entityClass.getDeclaredField(attribute.getAttributeName());
    	boolean isJointRelationship = 
    			field.getAnnotation(OneToOne.class) != null
    			|| field.getAnnotation(OneToMany.class) != null
    			|| field.getAnnotation(ManyToOne.class) != null
    			|| field.getAnnotation(ManyToMany.class) != null;
    	if(isJointRelationship) {
    		StringBuilder joinFragment = new StringBuilder();
    		Queue<String> objectTreePath = extractDotSeparatedPathFragments(attribute.getPossibleValueLabelAttributePath());
    		completeJoinFragment(joinFragment, entityClass, objectTreePath, aliasUnicityKey);
    		jpqlStatement.insert(joinAdditionsOffset.get(), joinFragment);
    		joinAdditionsOffset.set(joinAdditionsOffset.get() + joinFragment.length());
    		jpqlStatement.append(" x").append(aliasUnicityKey.get()).append('.').append(attribute.getPossibleValueLabelAttribute());
    	} else {
    		jpqlStatement.append(" x.").append(attribute.getAttributeName());
    	}
    }
    
    @SuppressWarnings("serial")
	private static Queue<String> extractDotSeparatedPathFragments(String content) {
    	final String[] contentTokens = content.split("\\.");

		return new LinkedList<String>(){{
    		for(String token : contentTokens) {
    			add(token);
    		}
    	}};
    }
    
    private void completeJoinFragment(StringBuilder joinFragment, Class<?> clazz, Queue<String> objectTreePath, AtomicInteger aliasUnicityKey) throws NoSuchFieldException, java.lang.SecurityException {
    	String previousAlias = " x";
    	while( objectTreePath.size() > 1 ) {
	    	String crtAttributeName = objectTreePath.poll();
//	    	Field field = resolveField(clazz, crtAttributeName);
//	    	field.setAccessible(true);
//	    	clazz = field.getType();
//	    	if(Collection.class.isAssignableFrom(clazz)) {
//	            ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
//	            clazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];
//	    	}
	    	String newAlias = " x" + aliasUnicityKey.incrementAndGet();
			joinFragment.append(" LEFT JOIN ")
				.append(previousAlias).append('.').append(crtAttributeName)
				.append(newAlias);
			previousAlias = newAlias;
    	}
    }
    
    private Field resolveField(Class<?> entityClass, String fieldName) throws NoSuchFieldException, java.lang.SecurityException {
    	try {
    		return entityClass.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			Class<?> superclass = entityClass.getSuperclass();
			if(superclass == Object.class) {
				return null;
			} else {
				return resolveField(superclass, fieldName);
			}
		}
    }
    
}
