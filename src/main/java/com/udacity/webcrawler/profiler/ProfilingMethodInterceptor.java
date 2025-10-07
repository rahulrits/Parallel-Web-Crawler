package com.udacity.webcrawler.profiler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import com.udacity.webcrawler.crawler.Profiled;

public final class ProfilingMethodInterceptor implements InvocationHandler {
    private final Object delegate;
    private final Map<String, Long> profileData;

    public ProfilingMethodInterceptor(Object delegate, Map<String, Long> profileData) {
        this.delegate = delegate;
        this.profileData = profileData;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class && method.getName().equals("equals")) {
            return delegate.equals(args != null && args.length > 0 ? args[0] : null);
        }

        if (method.isAnnotationPresent(Profiled.class)) {
            long start = System.nanoTime();
            try {
                Object result = method.invoke(delegate, args);
                profileData.merge(method.getName(), System.nanoTime() - start, Long::sum);
                return result;
            } catch (InvocationTargetException e) {
                // FIX: Unwrap InvocationTargetException and throw the cause directly
                profileData.merge(method.getName(), System.nanoTime() - start, Long::sum);
                throw e.getTargetException();
            } catch (Throwable t) {
                // Catch and rethrow other general exceptions
                profileData.merge(method.getName(), System.nanoTime() - start, Long::sum);
                throw t;
            }
        }
        return method.invoke(delegate, args);
    }
}