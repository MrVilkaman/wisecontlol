package io.someapp.wisecontlol.di

import javax.inject.Qualifier
import javax.inject.Scope


@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class UIScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope

@Qualifier
annotation class SomeId