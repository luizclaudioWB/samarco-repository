package br.com.wisebyte.samarco.core.query;

import br.com.wisebyte.samarco.core.graphql.GraphQLQueryList;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryManager<Entity> {

    private final StringBuilder whereBuilder;
    private final Map<String, Object> parameters;
    private final Class<Entity> entityClass;
    private boolean hasCondition = false;

    public QueryManager( Class<Entity> entityClass ) {
        this.entityClass = entityClass;
        this.whereBuilder = new StringBuilder();
        this.parameters = new HashMap<>();
    }

    public void addLikeParam( String field, String parameter, String value ) {
        if ( value != null && !value.trim().isBlank() ) {
            appendCondition();
            whereBuilder.append( "LOWER(e." ).append( field ).append( ") LIKE LOWER(:" ).append( parameter ).append( ")" );
            parameters.put( parameter, "%" + value.trim() + "%" );
        }
    }

    public void addEqualsParam( String field, String parameter, Object value ) {
        if ( value != null ) {
            appendCondition();
            whereBuilder.append( "e." ).append( field ).append( " = :" ).append( parameter );
            parameters.put( parameter, value );
        }
    }

    public void addInParam( String field, String parameter, List<?> values ) {
        if ( values != null && !values.isEmpty() ) {
            appendCondition();
            whereBuilder.append( "e." ).append( field ).append( " IN (:" ).append( parameter ).append( ")" );
            parameters.put( parameter, values );
        }
    }

    public void addBetweenParam( String field, Object startValue, Object endValue ) {
        if ( startValue != null && endValue != null ) {
            appendCondition();
            String startParam = field + "Start";
            String endParam = field + "End";
            whereBuilder.append( "e." ).append( field ).append( " BETWEEN :" ).append( startParam ).append( " AND :" ).append( endParam );
            parameters.put( startParam, startValue );
            parameters.put( endParam, endValue );
        }
    }

    public void addGreaterOrEqualsParam( String field, String parameter, Object value ) {
        if ( value != null ) {
            appendCondition();
            whereBuilder.append( "e." ).append( field ).append( " >= :" ).append( parameter );
            parameters.put( parameter, value );
        }
    }

    public void addLessOrEqualsParam( String field, String parameter, Object value ) {
        if ( value != null ) {
            appendCondition();
            whereBuilder.append( "e." ).append( field ).append( " <= :" ).append( parameter );
            parameters.put( parameter, value );
        }
    }

    public void addIsNullParam( String field ) {
        appendCondition();
        whereBuilder.append( "e." ).append( field ).append( " IS NULL" );
    }

    public void addIsNotNullParam( String field ) {
        appendCondition();
        whereBuilder.append( "e." ).append( field ).append( " IS NOT NULL" );
    }

    private void appendCondition() {
        if ( hasCondition ) {
            whereBuilder.append( " AND " );
        }
        hasCondition = true;
    }

    public GraphQLQueryList<Entity> buildQuery(
            EntityManager entityManager,
            String sortField,
            String sortDirection,
            Integer page,
            Integer size
    ) {
        String entityName = entityClass.getSimpleName();
        String baseQuery = "SELECT e FROM " + entityName + " e";
        String countQuery = "SELECT COUNT(e) FROM " + entityName + " e";

        String whereClause = whereBuilder.length() > 0 ? " WHERE " + whereBuilder : "";

        String sort = sortField != null ? sortField : "id";
        String direction = sortDirection != null ? sortDirection : "ASC";
        String orderClause = " ORDER BY e." + sort + " " + direction;

        // Query de contagem
        TypedQuery<Long> countTypedQuery = entityManager.createQuery( countQuery + whereClause, Long.class );
        parameters.forEach( countTypedQuery::setParameter );
        Long totalCount = countTypedQuery.getSingleResult();

        // Query paginada
        TypedQuery<Entity> typedQuery = entityManager.createQuery( baseQuery + whereClause + orderClause, entityClass );
        parameters.forEach( typedQuery::setParameter );

        int pageNumber = page != null ? page : 0;
        int pageSize = size != null ? size : 10;

        typedQuery.setFirstResult( pageNumber * pageSize );
        typedQuery.setMaxResults( pageSize );

        List<Entity> entities = typedQuery.getResultList();

        int totalPages = (int) Math.ceil( (double) totalCount / pageSize );

        return GraphQLQueryList.<Entity>builder()
                .entities( entities )
                .count( totalCount )
                .totalOfPages( totalPages )
                .page( pageNumber )
                .size( pageSize )
                .build();
    }

    public GraphQLQueryList<Entity> buildQuery(
            EntityManager entityManager,
            jakarta.data.Sort<Entity> sort,
            Integer page,
            Integer size
    ) {
        String sortField = null;
        String sortDirection = "ASC";

        if ( sort != null ) {
            sortField = sort.property();
            sortDirection = sort.isAscending() ? "ASC" : "DESC";
        }

        return buildQuery( entityManager, sortField, sortDirection, page, size );
    }
}
