package com.personal.godou.ui.settings;

import com.personal.godou.data.preferences.UserPreferencesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ThemeViewModel_Factory implements Factory<ThemeViewModel> {
  private final Provider<UserPreferencesRepository> preferencesRepositoryProvider;

  private ThemeViewModel_Factory(
      Provider<UserPreferencesRepository> preferencesRepositoryProvider) {
    this.preferencesRepositoryProvider = preferencesRepositoryProvider;
  }

  @Override
  public ThemeViewModel get() {
    return newInstance(preferencesRepositoryProvider.get());
  }

  public static ThemeViewModel_Factory create(
      Provider<UserPreferencesRepository> preferencesRepositoryProvider) {
    return new ThemeViewModel_Factory(preferencesRepositoryProvider);
  }

  public static ThemeViewModel newInstance(UserPreferencesRepository preferencesRepository) {
    return new ThemeViewModel(preferencesRepository);
  }
}
