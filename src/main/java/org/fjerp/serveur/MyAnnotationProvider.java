package org.fjerp.serveur;

import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.servlet.ServletContext;

import com.sun.faces.config.DelegatingAnnotationProvider;

public class MyAnnotationProvider extends DelegatingAnnotationProvider
{

    public MyAnnotationProvider(ServletContext sc)
    {
        super(sc);
    }

    @Override
    public Map<Class<? extends Annotation>, Set<Class<?>>> getAnnotatedClasses(Set<URI> urls)
    {
        Map<Class<? extends Annotation>, Set<Class<?>>> parentRes = super.getAnnotatedClasses(urls);

        // simplified implementation, without annotations scan
        Map<Class<? extends Annotation>, Set<Class<?>>> ret = new HashMap<>(parentRes);

        Set<Class<?>> parentSet = ret.get(ManagedBean.class);

        Set<Class<?>> set = (parentSet == null) ? new HashSet<>() : new HashSet<>(parentSet);

        set.add(HelloBean.class);

        ret.put(ManagedBean.class, set);

        return ret;
    }

}