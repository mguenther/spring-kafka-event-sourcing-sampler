package net.mguenther.gtd;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public class OnMethodFilter extends ZuulFilter {

    private static final Logger log = LoggerFactory.getLogger(OnMethodFilter.class);
    private static final List<String> methodsForCommands = Arrays.asList("POST", "PUT", "PATCH", "DELETE");

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        final RequestContext ctx = RequestContext.getCurrentContext();
        final String method = ctx.getRequest().getMethod();
        if (isCommand(ctx)) {
            log.info("Resolved incoming request using method {} to service ID 'gtd-es-command-side'.", method);
            ctx.set("serviceId", "gtd-es-command-side");
            ctx.setRouteHost(null);
            ctx.addOriginResponseHeader("X-Zuul-ServiceId", UUID.randomUUID().toString());
        } else {
            log.info("Resolved incoming request using method {} to service ID 'gtd-es-query-side'.", method);
            ctx.set("serviceId", "gtd-es-query-side");
            ctx.setRouteHost(null);
            ctx.addOriginResponseHeader("X-Zuul-ServiceId", UUID.randomUUID().toString());
        }
        return null;
    }

    private boolean isCommand(final RequestContext ctx) {
        return
                StringUtils.isNotEmpty(ctx.getRequest().getMethod()) &&
                        methodsForCommands.contains(ctx.getRequest().getMethod().toUpperCase());
    }
}
