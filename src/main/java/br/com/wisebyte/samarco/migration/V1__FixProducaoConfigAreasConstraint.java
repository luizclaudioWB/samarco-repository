package br.com.wisebyte.samarco.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Migration to fix incorrect UNIQUE constraint on producao_config_areas table.
 *
 * Problem: The table has UNIQUE(area_id) which prevents the same area
 * from being in multiple ProducaoConfig (from different revisions).
 *
 * Solution: Drop the incorrect index if it exists.
 */
public class V1__FixProducaoConfigAreasConstraint extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        try (Statement stmt = context.getConnection().createStatement()) {
            // Check if the index exists
            String checkQuery = """
                SELECT COUNT(*) as cnt
                FROM information_schema.statistics
                WHERE table_schema = DATABASE()
                  AND table_name = 'producao_config_areas'
                  AND index_name = 'UKi8ghshor9dtvc18dsukyuyeh2'
                """;

            ResultSet rs = stmt.executeQuery(checkQuery);
            if (rs.next() && rs.getInt("cnt") > 0) {
                // Index exists, drop it
                stmt.execute("ALTER TABLE producao_config_areas DROP INDEX UKi8ghshor9dtvc18dsukyuyeh2");
            }
            // If index doesn't exist, do nothing (idempotent)
        }
    }
}
