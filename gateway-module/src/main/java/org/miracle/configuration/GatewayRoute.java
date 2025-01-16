package org.miracle.configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Класс, необходимый для работы {@link GatewayConfiguration}
 * @author Mark Valiev
 * @since 08.09.2024
 */
@Getter
@Setter
public class GatewayRoute {
    private String contextPath;
    private String uri;
}
