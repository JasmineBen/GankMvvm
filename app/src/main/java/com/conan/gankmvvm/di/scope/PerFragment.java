package com.conan.gankmvvm.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Description：Dagger Fragment单例Scope
 * Created by：JasmineBen
 * Time：2017/11/6
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}
