package skyglass.composer.common.jdbc.sqldialect;

import skyglass.composer.common.jdbc.EventuateJdbcStatementExecutor;
import skyglass.composer.common.jdbc.EventuateSchema;

public interface EventuateSqlDialect extends EventuateSqlDialectOrder {
	boolean supports(String driver);

	String getCurrentTimeInMillisecondsExpression();

	String addLimitToSql(String sql, String limitExpression);

	default String castToJson(String sqlPart,
			EventuateSchema eventuateSchema,
			String unqualifiedTable,
			String column,
			EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor) {
		return sqlPart;
	}

	default String jsonColumnToString(Object object,
			EventuateSchema eventuateSchema,
			String unqualifiedTable,
			String column,
			EventuateJdbcStatementExecutor eventuateJdbcStatementExecutor) {
		return object.toString();
	}
}
