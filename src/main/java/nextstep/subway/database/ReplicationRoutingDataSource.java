package nextstep.subway.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {
    private static final Logger logger = LoggerFactory.getLogger(ReplicationRoutingDataSource.class);

    public static final String DATASOURCE_KEY_MASTER = "master";
    public static final String DATASOURCE_KEY_SLAVE = "slave";

    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (isReadOnly) {
            logger.trace("connect to {}", DATASOURCE_KEY_SLAVE);
            return DATASOURCE_KEY_SLAVE;
        }
        logger.trace("connect to {}", DATASOURCE_KEY_MASTER);
        return DATASOURCE_KEY_MASTER;
    }
}
