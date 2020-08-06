package zda.task.webchat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import zda.task.webchat.controller.WebSocketEventController;
import zda.task.webchat.service.UserService;

import javax.annotation.PostConstruct;

/**
 * Insert ROLE_USER if not exist
 *
 * @author Zhevnov D.A.
 * @ created 2020-08-08
 */
@Configuration
public class RoleConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleConfig.class);

    public static final String ROLE_USER = "ROLE_USER";
    public static final String CREATE_ROLE = "INSERT INTO role(id, name) VALUES (1, 'ROLE_USER');";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void initDb() {
        if (!userService.isRoleExists(ROLE_USER)) {
            jdbcTemplate.execute(CREATE_ROLE);
            LOGGER.info("Add ROLE_USER into database");
        }
    }
}