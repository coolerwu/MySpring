package vip.wulang.spring.token;

import vip.wulang.spring.token.structure.UserInfo;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This class implements class of {@link ITokenLifecycle}.
 *
 * @author CoolerWu on 2019/1/14.
 * @version 1.0
 */
public abstract class AbstractTokenLifecycle implements ITokenLifecycle {
    private ConcurrentHashMap<String, UserInfo> tokenContainer = new ConcurrentHashMap<>();
    private ITokenFactory tokenFactory;

    public AbstractTokenLifecycle() {
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void end() {

    }

    @Override
    public void create() {

    }

    @Override
    public void delete() {

    }

    @Override
    public void update() {

    }

    @Override
    public void query() {

    }
}
